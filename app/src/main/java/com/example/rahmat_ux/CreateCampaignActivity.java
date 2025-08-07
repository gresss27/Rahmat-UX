package com.example.rahmat_ux;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.model.Campaign;
import com.example.rahmat_ux.model.Story;
import com.google.android.flexbox.FlexboxLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CreateCampaignActivity extends AppCompatActivity {

    private TextView tvEndDate;
    private EditText etItemInput, inputTitle, inputDeskripsi, inputTargetUang, inputOrganizerName, inputContact, inputDonationAddress, inputBankAccountNumber, inputBankAccountName;
    private LinearLayout storyListContainer;

    private FlexboxLayout pillContainer;
    private ImageView imagePlaceholder;
    private Button btnAddStory;

    private Uri selectedImageUri;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Uri> cameraLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> storyLauncher;

    private Campaign currentDraftCampaign;
    private Uri cameraImageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign);

        // Inisialisasi UI
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> onBackPressed());

        tvEndDate = findViewById(R.id.tvEndDate);
        tvEndDate.setOnClickListener(v -> showDatePicker());

        imagePlaceholder = findViewById(R.id.imagePlaceholder);
        imagePlaceholder.setOnClickListener(v -> showImageSourceDialog());

        inputTitle = findViewById(R.id.inputTitle);
        inputDeskripsi = findViewById(R.id.inputDeskripsi);
        inputTargetUang = findViewById(R.id.inputTargetUang);
        inputOrganizerName = findViewById(R.id.inputOrganizerName);
        inputContact = findViewById(R.id.inputContact);
        inputDonationAddress = findViewById(R.id.inputDonationAddress);
        inputBankAccountNumber = findViewById(R.id.inputBankAccountNumber);
        inputBankAccountName = findViewById(R.id.inputBankAccountName);

        // Inisialisasi ActivityResultLaunchers
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        previewImage();
                    }
                });

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        dispatchTakePictureIntent();
                    } else {
                        Toast.makeText(this, "Izin kamera diperlukan untuk mengambil foto", Toast.LENGTH_SHORT).show();
                    }
                });

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                success -> {
                    if (success) {
                        selectedImageUri = cameraImageUri;
                        previewImage();
                    }
                });

        storyLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        ArrayList<Story> updatedStories = (ArrayList<Story>) result.getData().getSerializableExtra("updated_campaign_stories");
                        String storyTitle = result.getData().getStringExtra("story_title");

                        if (currentDraftCampaign != null) {
                            currentDraftCampaign.setStories(updatedStories);
                            currentDraftCampaign.setStoryTitle(storyTitle);
                            updateStoryButtonText();
                        }
                    }
                });

        Spinner spinnerType = findViewById(R.id.spinnerCampaignType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.campaign_types, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        etItemInput = findViewById(R.id.etItemInput);
        pillContainer = findViewById(R.id.pillContainer);
        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(v -> {
            String item = etItemInput.getText().toString().trim();
            if (!item.isEmpty()) {
                addItemPill(item);
                etItemInput.setText("");
            }
        });

        btnAddStory = findViewById(R.id.btnAddStory);
        btnAddStory.setOnClickListener(v -> {
            Intent intent = new Intent(CreateCampaignActivity.this, CreateStoryActivity.class);
            if (currentDraftCampaign == null) {
                currentDraftCampaign = createDraftCampaign();
            }
            intent.putExtra("campaign_data", currentDraftCampaign);
            storyLauncher.launch(intent);
        });

        Button btnSubmit = findViewById(R.id.btnSubmitCampaign);
        btnSubmit.setOnClickListener(v -> submitCampaign());
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDraftCampaign();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Simpan Draft?");
        builder.setMessage("Apakah Anda ingin menyimpan galang dana ini sebagai draft atau menghapusnya?");
        builder.setPositiveButton("Simpan Draft", (dialog, which) -> {
            saveDraftCampaign();
            super.onBackPressed();
        });
        builder.setNegativeButton("Hapus", (dialog, which) -> {
            if (currentDraftCampaign != null) {
                DummyDataRepository.getInstance().removeCampaign(currentDraftCampaign);
            }
            super.onBackPressed();
        });
        builder.setNeutralButton("Batal", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void updateStoryButtonText() {
        if (currentDraftCampaign != null && currentDraftCampaign.getStoryTitle() != null && !currentDraftCampaign.getStoryTitle().isEmpty()) {
            btnAddStory.setText(currentDraftCampaign.getStoryTitle());
        } else {
            btnAddStory.setText("Tambahkan Cerita (+)");
        }
    }

    private Campaign createDraftCampaign() {
        int generatedId = DummyDataRepository.getInstance().getCampaignList().size() + 1;
        Campaign draft = new Campaign(
                generatedId,                      // id
                "",                               // title
                0,                                // mainImageResId
                "",                               // dateStarted
                "",                               // timeRemaining
                0L,                               // amountCollected
                0L,                               // targetAmount
                "", 0,                            // item1Name, item1Progress
                "", 0,                            // item2Name, item2Progress
                "", 0,                            // item3Name, item3Progress
                "",                               // description
                "",                               // longDescription
                "", "",                           // organizerName, organizerOccupation
                0,                                // organizerImageResId
                "",                               // lastChat
                "Draft",                          // status
                0,                                // remainingDays
                new ArrayList<>(),                // stories
                "",                               // coverImageUri
                ""                                // storyTitle
        );
        DummyDataRepository.getInstance().addCampaign(draft);
        return draft;
    }

    private void saveDraftCampaign() {
        String title = inputTitle.getText().toString().trim();
        String description = inputDeskripsi.getText().toString().trim();
        String targetText = inputTargetUang.getText().toString().trim();

        // Cek jika ada field yang diisi
        boolean anyFieldFilled = !title.isEmpty() || !description.isEmpty() ||
                !targetText.isEmpty() || selectedImageUri != null;

        // Jika draft belum ada, tapi ada isian -> buat dulu
        if (currentDraftCampaign == null && anyFieldFilled) {
            currentDraftCampaign = createDraftCampaign();
        }

        // Jika draft sudah ada, atau baru dibuat di atas
        if (currentDraftCampaign != null) {
            long targetAmount = 0;
            try {
                targetAmount = Long.parseLong(targetText);
            } catch (NumberFormatException e) {
                // Biarkan targetAmount 0 jika tidak valid
            }

            currentDraftCampaign.setTitle(title);
            currentDraftCampaign.setDescription(description);
            currentDraftCampaign.setTargetAmount(targetAmount);

            if (selectedImageUri != null) {
                currentDraftCampaign.setCoverImageUri(selectedImageUri.toString());
            }

            DummyDataRepository.getInstance().updateCampaign(currentDraftCampaign);
            Toast.makeText(this, "Draft tersimpan", Toast.LENGTH_SHORT).show();
        }
    }
    private void submitCampaign() {
        if (inputTitle.getText().toString().isEmpty() || inputDeskripsi.getText().toString().isEmpty() ||
                tvEndDate.getText().toString().isEmpty() || inputTargetUang.getText().toString().isEmpty() ||
                inputOrganizerName.getText().toString().isEmpty() || inputContact.getText().toString().isEmpty() ||
                inputDonationAddress.getText().toString().isEmpty() || inputBankAccountNumber.getText().toString().isEmpty() ||
                inputBankAccountName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentDraftCampaign == null) {
            currentDraftCampaign = createDraftCampaign();
        }
        currentDraftCampaign.setTitle(inputTitle.getText().toString());
        currentDraftCampaign.setDescription(inputDeskripsi.getText().toString());
        currentDraftCampaign.setTargetAmount(Long.parseLong(inputTargetUang.getText().toString()));
        currentDraftCampaign.setCoverImageUri(selectedImageUri != null ? selectedImageUri.toString() : null);
        currentDraftCampaign.setStatus("Diajukan");

        DummyDataRepository.getInstance().updateCampaign(currentDraftCampaign);
        Toast.makeText(this, "Galang dana berhasil diajukan!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            tvEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
        }, year, month, day);

        dpd.show();
    }

    private void addItemPill(String itemText) {
        View pill = getLayoutInflater().inflate(R.layout.item_pill, pillContainer, false);
        TextView tv = pill.findViewById(R.id.tvPillText);
        ImageView close = pill.findViewById(R.id.ivClose);
        tv.setText(itemText);
        close.setOnClickListener(v -> pillContainer.removeView(pill));
        pillContainer.addView(pill);
    }

    private void showImageSourceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Sumber Gambar");
        String[] options = {"Ambil dari Kamera", "Pilih dari Galeri"};
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                }
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(intent);
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Gagal membuat file foto", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                cameraImageUri = FileProvider.getUriForFile(
                        this,
                        getApplicationContext().getPackageName() + ".fileprovider",
                        photoFile
                );
                cameraLauncher.launch(cameraImageUri);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void previewImage() {
        if (selectedImageUri != null) {
            imagePlaceholder.setImageURI(selectedImageUri);
        } else {
            imagePlaceholder.setImageResource(R.drawable.ic_image_placeholder);
        }
    }
}
