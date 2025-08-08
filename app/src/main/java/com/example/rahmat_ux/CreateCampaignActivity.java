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
import android.text.Editable;
import android.text.TextWatcher;
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
    LinearLayout containerBarang;
    private static final int MAX_BARIS = 3;
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
        containerBarang = findViewById(R.id.containerBarang);
        tambahBarisBarang();
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
    private void tambahBarisBarang() {
        if (containerBarang.getChildCount() >= MAX_BARIS) return; // Batas maksimal

        View baris = getLayoutInflater().inflate(R.layout.item_target_barang, containerBarang, false);

        EditText inputNama = baris.findViewById(R.id.inputNamaBarang);
        EditText inputKuantitas = baris.findViewById(R.id.inputKuantitas);
        EditText inputSatuan = baris.findViewById(R.id.inputSatuan);

        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isLastRow(baris) && !isEmptyRow(baris)) {
                    tambahBarisBarang();
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        inputNama.addTextChangedListener(watcher);
        inputKuantitas.addTextChangedListener(watcher);
        inputSatuan.addTextChangedListener(watcher);

        containerBarang.addView(baris);
    }

    private boolean isLastRow(View baris) {
        return containerBarang.indexOfChild(baris) == containerBarang.getChildCount() - 1;
    }

    private boolean isEmptyRow(View baris) {
        EditText inputNama = baris.findViewById(R.id.inputNamaBarang);
        EditText inputKuantitas = baris.findViewById(R.id.inputKuantitas);
        EditText inputSatuan = baris.findViewById(R.id.inputSatuan);

        return inputNama.getText().toString().trim().isEmpty()
                && inputKuantitas.getText().toString().trim().isEmpty()
                && inputSatuan.getText().toString().trim().isEmpty();
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

                "", 0, 0, "",                     // item1Name, item1Progress, item1Qty, item1Unit
                "", 0, 0, "",                     // item2Name, item2Progress, item2Qty, item2Unit
                "", 0, 0, "",                     // item3Name, item3Progress, item3Qty, item3Unit

                "",                               // description
                "",                               // longDescription
                "",                               // organizerName
                "",                               // organizerOccupation
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
        String organizerName = inputOrganizerName.getText().toString().trim();
        String contact = inputContact.getText().toString().trim();
        String donationAddress = inputDonationAddress.getText().toString().trim();
        String bankAccountNumber = inputBankAccountNumber.getText().toString().trim();
        String bankAccountName = inputBankAccountName.getText().toString().trim();

        // Ambil data barang dari layout
        String[] itemNames = new String[3];
        int[] itemQtys = new int[3];
        String[] itemUnits = new String[3];

        for (int i = 0; i < Math.min(containerBarang.getChildCount(), 3); i++) {
            View row = containerBarang.getChildAt(i);
            EditText nama = row.findViewById(R.id.inputNamaBarang);
            EditText qty = row.findViewById(R.id.inputKuantitas);
            EditText unit = row.findViewById(R.id.inputSatuan);

            itemNames[i] = nama.getText().toString().trim();
            try {
                itemQtys[i] = Integer.parseInt(qty.getText().toString().trim());
            } catch (NumberFormatException e) {
                itemQtys[i] = 0;
            }
            itemUnits[i] = unit.getText().toString().trim();
        }

        boolean anyFieldFilled = !title.isEmpty() || !description.isEmpty() ||
                !targetText.isEmpty() || selectedImageUri != null ||
                !organizerName.isEmpty() || !contact.isEmpty() ||
                !donationAddress.isEmpty() || !bankAccountNumber.isEmpty() ||
                !bankAccountName.isEmpty() ||
                (itemNames[0] != null && !itemNames[0].isEmpty());

        if (currentDraftCampaign == null && anyFieldFilled) {
            currentDraftCampaign = createDraftCampaign();
        }

        if (currentDraftCampaign != null) {
            long targetAmount = 0;
            try {
                targetAmount = Long.parseLong(targetText);
            } catch (NumberFormatException ignored) {}

            currentDraftCampaign.setTitle(title);
            currentDraftCampaign.setDescription(description);
            currentDraftCampaign.setTargetAmount(targetAmount);
            currentDraftCampaign.setOrganizerName(organizerName);
            currentDraftCampaign.setOrganizerOccupation(contact); // kalau occupation beda, ganti ini
            currentDraftCampaign.setLongDescription(donationAddress);

            // Simpan barang
            if (itemNames.length > 0) {
                currentDraftCampaign.setItem1Qty(itemQtys[0]);
                currentDraftCampaign.setItem1Unit(itemUnits[0]);
            }
            if (itemNames.length > 1) {
                currentDraftCampaign.setItem2Qty(itemQtys[1]);
                currentDraftCampaign.setItem2Unit(itemUnits[1]);
            }
            if (itemNames.length > 2) {
                currentDraftCampaign.setItem3Qty(itemQtys[2]);
                currentDraftCampaign.setItem3Unit(itemUnits[2]);
            }

            if (selectedImageUri != null) {
                currentDraftCampaign.setCoverImageUri(selectedImageUri.toString());
            }

            DummyDataRepository.getInstance().updateCampaign(currentDraftCampaign);
            Toast.makeText(this, "Draft tersimpan", Toast.LENGTH_SHORT).show();
        }
    }
    private void submitCampaign() {
        if (inputTitle.getText().toString().trim().isEmpty() ||
                inputDeskripsi.getText().toString().trim().isEmpty() ||
                tvEndDate.getText().toString().trim().isEmpty() ||
                inputTargetUang.getText().toString().trim().isEmpty() ||
                inputOrganizerName.getText().toString().trim().isEmpty() ||
                inputContact.getText().toString().trim().isEmpty() ||
                inputDonationAddress.getText().toString().trim().isEmpty() ||
                inputBankAccountNumber.getText().toString().trim().isEmpty() ||
                inputBankAccountName.getText().toString().trim().isEmpty()) {

            Toast.makeText(this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        // Baca data barang
        String[] itemNames = new String[3];
        int[] itemQtys = new int[3];
        String[] itemUnits = new String[3];

        for (int i = 0; i < Math.min(containerBarang.getChildCount(), 3); i++) {
            View row = containerBarang.getChildAt(i);
            EditText nama = row.findViewById(R.id.inputNamaBarang);
            EditText qty = row.findViewById(R.id.inputKuantitas);
            EditText unit = row.findViewById(R.id.inputSatuan);

            itemNames[i] = nama.getText().toString().trim();
            try {
                itemQtys[i] = Integer.parseInt(qty.getText().toString().trim());
            } catch (NumberFormatException e) {
                itemQtys[i] = 0;
            }
            itemUnits[i] = unit.getText().toString().trim();
        }

        if (currentDraftCampaign == null) {
            currentDraftCampaign = createDraftCampaign();
        }

        // Set data campaign
        currentDraftCampaign.setTitle(inputTitle.getText().toString().trim());
        currentDraftCampaign.setDescription(inputDeskripsi.getText().toString().trim());
        currentDraftCampaign.setTargetAmount(Long.parseLong(inputTargetUang.getText().toString().trim()));
        currentDraftCampaign.setOrganizerName(inputOrganizerName.getText().toString().trim());
        currentDraftCampaign.setOrganizerOccupation(inputContact.getText().toString().trim()); // atau ganti kalau bukan occupation
        currentDraftCampaign.setLongDescription(inputDonationAddress.getText().toString().trim());

        // Set barang
        currentDraftCampaign.setItem1Name(itemNames[0]);
        currentDraftCampaign.setItem1Qty(itemQtys[0]);
        currentDraftCampaign.setItem1Unit(itemUnits[0]);

        currentDraftCampaign.setItem2Name(itemNames[1]);
        currentDraftCampaign.setItem2Qty(itemQtys[1]);
        currentDraftCampaign.setItem2Unit(itemUnits[1]);

        currentDraftCampaign.setItem3Name(itemNames[2]);
        currentDraftCampaign.setItem3Qty(itemQtys[2]);
        currentDraftCampaign.setItem3Unit(itemUnits[2]);

        // Set gambar
        if (selectedImageUri != null) {
            currentDraftCampaign.setCoverImageUri(selectedImageUri.toString());
        }

        currentDraftCampaign.setStatus("Diajukan");

        // Simpan ke repository
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
