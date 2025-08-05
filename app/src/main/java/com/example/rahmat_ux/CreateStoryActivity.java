package com.example.rahmat_ux;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rahmat_ux.model.Story;
import com.example.rahmat_ux.model.Campaign;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CreateStoryActivity extends AppCompatActivity {

    private EditText storyTitleInput;
    private EditText storyContentInput;
    private TextView btnSave;
    private Campaign currentCampaign;
    private List<Story> storyList;

    // Variabel untuk fungsionalitas undo/redo
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();
    private boolean isUndoingOrRedoing = false;

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
        storyContentInput = findViewById(R.id.storyContentInput);
        btnSave = findViewById(R.id.btnSave);
        ImageView btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> onBackPressed());

        btnSave.setOnClickListener(v -> saveStory());

        setupTextFormattingButtons();

        // Inisialisasi undo/redo
        storyContentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!isUndoingOrRedoing) {
                    undoStack.push(s.toString());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isUndoingOrRedoing) {
                    isUndoingOrRedoing = false;
                } else {
                    redoStack.clear();
                }
            }
        });

        // Tambahkan state awal ke undoStack
        undoStack.push(storyContentInput.getText().toString());
    }

    private void saveStory() {
        // Simpan data cerita ke objek Campaign
        // Untuk demo, kita simpan teks dan gambar yang terakhir diunggah
        List<Story> tempStories = new ArrayList<>();
        String title = storyTitleInput.getText().toString();
        String content = storyContentInput.getText().toString();

        if (!title.isEmpty()) {
            tempStories.add(new Story("text", title));
        }
        if (!content.isEmpty()) {
            tempStories.add(new Story("text", content));
        }
        // Jika ada gambar, tambahkan juga
        // ... (Logika untuk menyimpan gambar)

        storyList = tempStories;
        Toast.makeText(this, "Cerita disimpan!", Toast.LENGTH_SHORT).show();
    }

    // Metode ini dipanggil saat user kembali
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveStory();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("updated_campaign_stories", (ArrayList<Story>) storyList);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void setupTextFormattingButtons() {
        // Logika undo/redo
        ImageView btnUndo = findViewById(R.id.btnUndo);
        ImageView btnRedo = findViewById(R.id.btnRedo);

        btnUndo.setOnClickListener(v -> undo());
        btnRedo.setOnClickListener(v -> redo());

        // Logika insert gambar
        ImageView btnInsertImage = findViewById(R.id.btnInsertImage);
        btnInsertImage.setOnClickListener(v -> insertImage());
    }

    private void undo() {
        if (!undoStack.empty()) {
            String lastText = undoStack.pop();
            redoStack.push(storyContentInput.getText().toString());
            isUndoingOrRedoing = true;
            storyContentInput.setText(lastText);
            storyContentInput.setSelection(lastText.length());
        } else {
            Toast.makeText(this, "Tidak ada yang bisa di-undo", Toast.LENGTH_SHORT).show();
        }
    }

    private void redo() {
        if (!redoStack.empty()) {
            String nextText = redoStack.pop();
            undoStack.push(storyContentInput.getText().toString());
            isUndoingOrRedoing = true;
            storyContentInput.setText(nextText);
            storyContentInput.setSelection(nextText.length());
        } else {
            Toast.makeText(this, "Tidak ada yang bisa di-redo", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertImage() {
        // Logika untuk insert gambar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Sumber Gambar");
        String[] options = {"Pilih dari Galeri"};
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(intent);
            }
        });
        builder.show();
    }

    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    // Untuk demo, kita tambahkan gambar ke editor sebagai tag
                    SpannableStringBuilder ssb = new SpannableStringBuilder(storyContentInput.getText());
                    int start = storyContentInput.getSelectionStart();
                    ssb.insert(start, " ");
                    storyContentInput.setText(ssb);
                    Toast.makeText(this, "Gambar berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                }
            }
    );
}
