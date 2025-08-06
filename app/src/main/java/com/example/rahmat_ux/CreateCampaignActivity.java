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

    private EditText etEndDate, etItemInput;
    private LinearLayout storyListContainer;

    private FlexboxLayout pillContainer;
    private ImageView imagePlaceholder;

    private Uri selectedImageUri;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Uri> cameraLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    private int storyCount = 0;
    private Uri cameraImageUri;
    private Campaign newCampaign;
    private ActivityResultLauncher<Intent> storyLauncher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign);

        // Inisialisasi UI
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        etEndDate = findViewById(R.id.etEndDate);
        etEndDate.setOnClickListener(v -> showDatePicker());

        imagePlaceholder = findViewById(R.id.imagePlaceholder);

        // Inisialisasi ActivityResultLaunchers
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        previewImage();
                    }
                }
        );

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
                }
        );

        imagePlaceholder.setOnClickListener(v -> showImageSourceDialog());

        Spinner spinnerType = findViewById(R.id.spinnerCampaignType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.campaign_types,
                R.layout.spinner_item
        );
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

        Button btnAddStory = findViewById(R.id.btnAddStory);
        storyListContainer = findViewById(R.id.storyListContainer);

        int generatedId = DummyDataRepository.getInstance().getCampaignList().size() + 1;
        btnAddStory = findViewById(R.id.btnAddStory);
        btnAddStory.setOnClickListener(v -> {
            // Inisialisasi newCampaign dengan data dasar, setiap kali tombol diklik
            // Ini memastikan objek Campaign selalu tersedia dan tidak null
            // Data dasar bisa diisi dari input field yang sudah diisi
            if (newCampaign == null) {
                newCampaign = new Campaign(
                        generatedId,
                        "", // title
                        R.drawable.campaign_gempa, // mainImageResId
                        "", // dateStarted
                        "", // timeRemaining
                        0L, // amountCollected
                        0L, // targetAmount
                        0, 0, 0, // progress
                        "", // description
                        "", // organizerName
                        "", // organizerOccupation
                        R.drawable.logo_organizer, // organizerImageResId
                        "", // lastChat
                        "Draft", // status - Perubahan di sini
                        0 // remainingDays
                );
            }

            Intent intent = new Intent(CreateCampaignActivity.this, CreateStoryActivity.class);
            intent.putExtra("campaign_data", (CharSequence) newCampaign);
            storyLauncher.launch(intent);
        });

        storyLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        // Perbarui stories dari hasil CreateStoryActivity
                        ArrayList<Story> updatedStories = (ArrayList<Story>) result.getData().getSerializableExtra("updated_campaign_stories");
                        if (newCampaign != null) {
                            newCampaign.setStories(updatedStories);
                        }
                    }
                }
        );

        Button btnSubmit = findViewById(R.id.btnSubmitCampaign);
        btnSubmit.setOnClickListener(v -> {
            EditText inputTitle = findViewById(R.id.inputTitle);
            EditText inputDeskripsi = findViewById(R.id.inputDeskripsi);
            EditText inputTargetUang = findViewById(R.id.inputTargetUang);

            String title = inputTitle.getText().toString().trim();
            String description = inputDeskripsi.getText().toString().trim();
            long targetAmount = 0;
            try {
                targetAmount = Long.parseLong(inputTargetUang.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Target Uang tidak valid", Toast.LENGTH_SHORT).show();
                return;
            }

            if (title.isEmpty() || description.isEmpty() || etEndDate.getText().toString().isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newCampaign != null) {
                DummyDataRepository.getInstance().addCampaign(newCampaign);
                Toast.makeText(this, "Ajukan galang dana berhasil", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Harap lengkapi data dan cerita galang dana", Toast.LENGTH_SHORT).show();
            }

            // Buat objek Campaign baru
            Campaign newCampaign = new Campaign(
                    generatedId,
                    title,
                    R.drawable.campaign_gempa,
                    "Sekarang",
                    "Sisa 90 hari",
                    0L,
                    targetAmount,
                    0, 0, 0,
                    description,
                    "Penggalang Baru",
                    "Pengguna",
                    R.drawable.logo_organizer,
                    "",
                    "Diajukan",
                    90
            );

            DummyDataRepository.getInstance().addCampaign(newCampaign);

            Toast.makeText(this, "Ajukan galang dana berhasil", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            etEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
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
            if (which == 0) { // Kamera
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                }
            } else { // Galeri
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