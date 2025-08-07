package com.example.rahmat_ux;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.model.Story;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CreateStoryActivity extends AppCompatActivity {

    private EditText storyTitleInput;
    private LinearLayout storyEditorContainer;
    private TextView btnSave;
    private ImageView btnBack;
    private Campaign currentCampaign;
    private List<Story> storyList;

    // Untuk fungsionalitas undo/redo
    private Stack<List<Story>> undoStack = new Stack<>();
    private Stack<List<Story>> redoStack = new Stack<>();
    private boolean isUndoingOrRedoing = false;

    private ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_story);

        // Ambil data Campaign dari Intent
        currentCampaign = (Campaign) getIntent().getSerializableExtra("campaign_data");
        if (currentCampaign != null && currentCampaign.getStories() != null) {
            storyList = currentCampaign.getStories();
        } else {
            storyList = new ArrayList<>();
        }

        storyTitleInput = findViewById(R.id.storyTitleInput);
        storyEditorContainer = findViewById(R.id.storyEditorContainer);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        // Set judul default jika belum ada
        if (currentCampaign != null && currentCampaign.getStoryTitle() != null && !currentCampaign.getStoryTitle().isEmpty()) {
            storyTitleInput.setText(currentCampaign.getStoryTitle());
        } else {
            storyTitleInput.setHint("Judul Donasi");
        }

        // Tampilkan cerita yang sudah ada
        loadStories();
        saveStateForUndo();

        btnBack.setOnClickListener(v -> saveAndFinish());
        btnSave.setOnClickListener(v -> saveStory());
        setupEditorButtons();

        // --- Perbaikan di sini: Panggilan ke addImage() di dalam callback galleryLauncher ---
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        addImage(imageUri); // Panggilan yang sudah diperbaiki
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveStory();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveAndFinish();
    }

    private void setupEditorButtons() {
        ImageView btnUndo = findViewById(R.id.btnUndo);
        ImageView btnRedo = findViewById(R.id.btnRedo);
        ImageView btnInsertImage = findViewById(R.id.btnInsertImage);

        btnUndo.setOnClickListener(v -> undo());
        btnRedo.setOnClickListener(v -> redo());
        btnInsertImage.setOnClickListener(v -> launchGallery());
    }

    private void saveAndFinish() {
        saveStory();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_campaign_stories", (ArrayList<Story>) new ArrayList<>(storyList));
        resultIntent.putExtra("story_title", storyTitleInput.getText().toString());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void saveStory() {
        storyList.clear();
        for (int i = 0; i < storyEditorContainer.getChildCount(); i++) {
            View child = storyEditorContainer.getChildAt(i);
            if (child instanceof EditText) {
                EditText editText = (EditText) child;
                if (!editText.getText().toString().isEmpty()) {
                    storyList.add(new Story("text", editText.getText().toString()));
                }
            } else if (child instanceof ImageView) {
                ImageView imageView = (ImageView) child;
                if (imageView.getTag() != null) {
                    storyList.add(new Story("image", imageView.getTag().toString()));
                }
            }
        }

        // Simpan state untuk undo
        saveStateForUndo();
        Toast.makeText(this, "Cerita disimpan!", Toast.LENGTH_SHORT).show();
    }

    private void loadStories() {
        storyEditorContainer.removeAllViews();
        if (storyList != null && !storyList.isEmpty()) {
            for (Story story : storyList) {
                if (story.getType().equals("text")) {
                    addParagraph(story.getContent());
                } else if (story.getType().equals("image")) {
                    addImage(Uri.parse(story.getContent()));
                }
            }
        } else {
            addParagraph(""); // Paragraf kosong untuk memulai
        }
    }

    private void addParagraph(String text) {
        EditText newParagraph = new EditText(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8);
        newParagraph.setLayoutParams(params);
        newParagraph.setText(text);
        newParagraph.setHint("Tulis cerita Anda...");
        newParagraph.setBackgroundResource(R.drawable.edittext_selector_null);
        newParagraph.setInputType(newParagraph.getInputType() | 0x00020000); // TYPE_TEXT_FLAG_NO_SUGGESTIONS

        newParagraph.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Tambahkan paragraf baru saat ENTER ditekan
                int index = storyEditorContainer.indexOfChild(newParagraph);
                addParagraphAfter(index);
                return true;
            }
            return false;
        });

        storyEditorContainer.addView(newParagraph);
        newParagraph.requestFocus();
        // Sembunyikan keyboard secara manual jika perlu
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(newParagraph, InputMethodManager.SHOW_IMPLICIT);
    }

    private void addParagraphAfter(int index) {
        EditText newParagraph = new EditText(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8);
        newParagraph.setLayoutParams(params);
        newParagraph.setHint("Tulis cerita Anda...");
        newParagraph.setBackgroundResource(R.drawable.edittext_selector_null);
        newParagraph.requestFocus();
        storyEditorContainer.addView(newParagraph, index + 1);
    }

    private void addImage(Uri uri) {
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8);
        imageView.setLayoutParams(params);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        // Menggunakan setImageURI standar
        imageView.setImageURI(uri);

        imageView.setTag(uri.toString());
        storyEditorContainer.addView(imageView);
    }

    private void launchGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private void saveStateForUndo() {
        if (!isUndoingOrRedoing) {
            List<Story> currentState = new ArrayList<>();
            for (int i = 0; i < storyEditorContainer.getChildCount(); i++) {
                View child = storyEditorContainer.getChildAt(i);
                if (child instanceof EditText) {
                    EditText editText = (EditText) child;
                    currentState.add(new Story("text", editText.getText().toString()));
                } else if (child instanceof ImageView) {
                    ImageView imageView = (ImageView) child;
                    if (imageView.getTag() != null) {
                        currentState.add(new Story("image", imageView.getTag().toString()));
                    }
                }
            }
            undoStack.push(currentState);
            redoStack.clear();
        }
    }

    private void restoreState(List<Story> state) {
        isUndoingOrRedoing = true;
        storyEditorContainer.removeAllViews();
        if (state != null) {
            for (Story story : state) {
                if (story.getType().equals("text")) {
                    addParagraph(story.getContent());
                } else if (story.getType().equals("image")) {
                    addImage(Uri.parse(story.getContent()));
                }
            }
        }
        isUndoingOrRedoing = false;
    }

    private void undo() {
        if (undoStack.size() > 1) {
            redoStack.push(undoStack.pop());
            List<Story> lastState = undoStack.peek();
            restoreState(lastState);
        } else {
            Toast.makeText(this, "Tidak ada yang bisa di-undo", Toast.LENGTH_SHORT).show();
        }
    }

    private void redo() {
        if (!redoStack.empty()) {
            List<Story> nextState = redoStack.pop();
            undoStack.push(nextState);
            restoreState(nextState);
        } else {
            Toast.makeText(this, "Tidak ada yang bisa di-redo", Toast.LENGTH_SHORT).show();
        }
    }
}
