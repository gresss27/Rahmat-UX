package com.example.rahmat_ux;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rahmat_ux.data.UserStorage;
import com.example.rahmat_ux.model.User;
import com.example.rahmat_ux.UserFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private EditText inputName, inputJob, inputEmail, inputPhone, donasiPerSwipe;
    private TextView profileName, profileJob;
    private ImageView backButton, editProfileImage;
    private Button updateProfileButton;
    private TextView pageTitle;

    private ActivityResultLauncher<String> pickImageLauncher;
    private User userToEdit;

    // Store permanent URI here
    private Uri tempProfileImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Init views
        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputJob = findViewById(R.id.inputJob);
        inputPhone = findViewById(R.id.inputPhone);
        donasiPerSwipe = findViewById(R.id.donasiPerSwipe);
        profileName = findViewById(R.id.profileName);
        profileJob = findViewById(R.id.profileJob);
        profileImage = findViewById(R.id.profileImage);
        backButton = findViewById(R.id.backButton);
        editProfileImage = findViewById(R.id.editProfileImage);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        pageTitle = findViewById(R.id.pageTitle);
        pageTitle.setText("Edit Profil");

        // Get logged-in user
        userToEdit = UserStorage.getInstance().getLoggedInUser();
        loadUserProfileToUI();

        // Back button
        backButton.setOnClickListener(v -> onBackPressed());

        // Image picker
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        Uri savedUri = copyImageToInternalStorage(uri);
                        if (savedUri != null) {
                            profileImage.setImageURI(savedUri);
                            tempProfileImageUri = savedUri;
                            Toast.makeText(this, "Gambar profil disimpan permanen!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Gagal menyimpan gambar", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        editProfileImage.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        updateProfileButton.setOnClickListener(v -> updateProfile());
    }

    private void loadUserProfileToUI() {
        if (userToEdit != null) {
            inputName.setText(userToEdit.getName());
            inputJob.setText(userToEdit.getPekerjaan());
            inputEmail.setText(userToEdit.getEmail());
            inputPhone.setText(userToEdit.getPhoneNumber());
            donasiPerSwipe.setText(String.valueOf(userToEdit.getDonationPerSwipe()));

            profileName.setText(userToEdit.getName());
            profileJob.setText(userToEdit.getPekerjaan());

            String profileUriString = userToEdit.getProfileImageUri();
            if (profileUriString != null && !profileUriString.isEmpty()) {
                profileImage.setImageURI(Uri.parse(profileUriString));
            }
        }
    }

    private void updateProfile() {
        if (userToEdit != null) {
            String newName = inputName.getText().toString().trim();
            String newJob = inputJob.getText().toString().trim();
            String newEmail = inputEmail.getText().toString().trim();
            String newPhone = inputPhone.getText().toString().trim();
            String newDonasiString = donasiPerSwipe.getText().toString().trim();

            if (newName.isEmpty() || newJob.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                Toast.makeText(this, "Email tidak valid.", Toast.LENGTH_SHORT).show();
                return;
            }

            userToEdit.setName(newName);
            userToEdit.setPekerjaan(newJob);
            userToEdit.setEmail(newEmail);
            userToEdit.setphoneNumber(newPhone);
            if (!newDonasiString.isEmpty()) {
                userToEdit.setDonationPerSwipe(Long.valueOf(newDonasiString));
            }

            // Save permanent URI
            if (tempProfileImageUri != null) {
                userToEdit.setProfileImageUri(tempProfileImageUri.toString());
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra(UserFragment.EXTRA_UPDATED_USER, userToEdit);
            setResult(RESULT_OK, resultIntent);

            Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Copy image into internal storage so it stays available
     */
    private Uri copyImageToInternalStorage(Uri sourceUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(sourceUri);
            if (inputStream == null) return null;

            File directory = new File(getFilesDir(), "profile_images");
            if (!directory.exists()) directory.mkdirs();

            File file = new File(directory, "profile.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return Uri.fromFile(file);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}















//package com.example.rahmat_ux;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Patterns;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.rahmat_ux.data.UserStorage;
//import com.example.rahmat_ux.model.User;
//import com.example.rahmat_ux.UserFragment; // Import UserFragment untuk EXTRA_UPDATED_USER
//
//public class EditProfileActivity extends AppCompatActivity {
//
//    private ImageView profileImage;
//
//    private EditText inputName, inputJob, inputEmail, inputPhone, donasiPerSwipe;
//    private TextView profileName,profileJob;
//
//    private ActivityResultLauncher<String> pickImageLauncher;
//
//    // DITAMBAHKAN: Deklarasi Views
//    private ImageView backButton;
//    private ImageView editProfileImage;
//    private Button updateProfileButton;
//    private TextView pageTitle;
//
//    // DITAMBAHKAN: Variabel untuk objek User yang akan diedit
//    private User userToEdit;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_edit_profile);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        inputName=findViewById(R.id.inputName);
//        inputEmail=findViewById(R.id.inputEmail);
//        inputJob=findViewById(R.id.inputJob);
//        inputPhone=findViewById(R.id.inputPhone);
//        donasiPerSwipe=findViewById(R.id.donasiPerSwipe);
//        profileName=findViewById(R.id.profileName);
//        profileJob=findViewById(R.id.profileJob);
//
//        inputName.setText(UserStorage.getInstance().getLoggedInUser().getName());
//        inputEmail.setText(UserStorage.getInstance().getLoggedInUser().getEmail());
//        inputJob.setText(UserStorage.getInstance().getLoggedInUser().getPekerjaan());
//        inputPhone.setText(UserStorage.getInstance().getLoggedInUser().getPhoneNumber());
//        donasiPerSwipe.setText(String.valueOf(UserStorage.getInstance().getLoggedInUser().getDonationPerSwipe()));
//        profileName.setText(UserStorage.getInstance().getLoggedInUser().getName());
//        profileJob.setText(UserStorage.getInstance().getLoggedInUser().getPekerjaan());
//
//
//
//
//
//
//
//
//        // Inisialisasi Views dari layout
//        profileImage = findViewById(R.id.profileImage);
//        backButton = findViewById(R.id.backButton);
//        editProfileImage = findViewById(R.id.editProfileImage);
//        updateProfileButton = findViewById(R.id.updateProfileButton);
//        pageTitle = findViewById(R.id.pageTitle);
//        pageTitle.setText("Edit Profil");
//
//        // DITAMBAHKAN: Inisialisasi Views dari layout
//        profileName = findViewById(R.id.profileName);
//        profileJob = findViewById(R.id.profileJob);
//        inputName = findViewById(R.id.inputName);
//        inputJob = findViewById(R.id.inputJob);
//        inputEmail = findViewById(R.id.inputEmail);
//
//        // DITAMBAHKAN: Ambil objek User dari Intent
//        if (getIntent().hasExtra(UserFragment.EXTRA_UPDATED_USER)) {
//            userToEdit = getIntent().getParcelableExtra(UserFragment.EXTRA_UPDATED_USER);
//            userToEdit=UserStorage.getInstance().getLoggedInUser();
//        } else {
//            // Fallback jika tidak ada User yang dikirim
//            userToEdit = new User();
//            userToEdit=UserStorage.getInstance().getLoggedInUser();
//            Toast.makeText(this, "Memuat profil dummy default.", Toast.LENGTH_SHORT).show();
//        }
//        userToEdit=UserStorage.getInstance().getLoggedInUser();
//
//        // DITAMBAHKAN: Muat data profil ke UI
//        loadUserProfileToUI();
//
//        // Mengatur listener untuk tombol kembali (dari kode Anda)
//        backButton.setOnClickListener(v -> onBackPressed());
//
//        // Mengatur listener untuk memilih gambar profil (dari kode Anda)
//        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
//                uri -> {
//                    if (uri != null) {
//                        profileImage.setImageURI(uri);
//                        userToEdit.setProfileImageUri(uri.toString());
//                    }
//                });
//
//        editProfileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pickImageLauncher.launch("image/*");
//            }
//        });
//
//        // DIUBAH: Mengganti logika OnClickListener untuk tombol "Perbarui Profil"
//        updateProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name=inputName.getText().toString().trim();
//                String email=inputEmail.getText().toString().trim();
//                String job=inputJob.getText().toString().trim();
//                String phone=inputPhone.getText().toString().trim();
//                String donasi=donasiPerSwipe.getText().toString().trim();
//                long donasii;
//                if(name.isEmpty()){
//                    name= UserStorage.getInstance().getLoggedInUser().getName();
//                }
//                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                    email= UserStorage.getInstance().getLoggedInUser().getEmail();
//                }
//                if(job.isEmpty()){
//                    job= UserStorage.getInstance().getLoggedInUser().getPekerjaan();
//                }
//                if(phone.isEmpty()){
//                    phone= UserStorage.getInstance().getLoggedInUser().getPhoneNumber();
//                }
//                if(donasi.isEmpty()){
//                    donasii= UserStorage.getInstance().getLoggedInUser().getDonationPerSwipe();
//                }else{
//                    donasii=Long.valueOf(donasi);
//                }
//                UserStorage.getInstance().getLoggedInUser().setName(name);
//                UserStorage.getInstance().getLoggedInUser().setPekerjaan(job);
//                UserStorage.getInstance().getLoggedInUser().setphoneNumber(phone);
//                UserStorage.getInstance().getLoggedInUser().setEmail(email);
//                UserStorage.getInstance().getLoggedInUser().setDonationPerSwipe(donasii);
//
//                updateProfile(); // Panggil metode updateProfile
//                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//
//            }
//        });
//    }
//
//    /**
//     * DITAMBAHKAN: Memuat data dari objek User ke dalam elemen UI.
//     */
//    private void loadUserProfileToUI() {
//        if (userToEdit != null) {
//            // Mengisi EditText dengan data dari objek User
//            inputName.setText(userToEdit.getName());
//            inputJob.setText(userToEdit.getPekerjaan());
//            inputEmail.setText(userToEdit.getEmail());
//
//            // Memperbarui TextView di bagian atas
//            profileName.setText(userToEdit.getName());
//            profileJob.setText(userToEdit.getPekerjaan());
//        }
//    }
//
//    /**
//     * DITAMBAHKAN: Mengambil data dari input, memperbarui objek User, dan mengirimkannya kembali ke UserFragment.
//     */
//    private void updateProfile() {
//        if (userToEdit != null) {
//            String newName = inputName.getText().toString().trim();
//            String newJob = inputJob.getText().toString().trim();
//            String newEmail = inputEmail.getText().toString().trim();
//
//            if (newName.isEmpty() || newJob.isEmpty() || newEmail.isEmpty()) {
//                Toast.makeText(this, "Semua kolom harus diisi.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // Memperbarui objek userToEdit dengan data baru dari input
//            userToEdit.setName(newName);
//            userToEdit.setPekerjaan(newJob);
//            userToEdit.setEmail(newEmail);
//
//            // Membuat Intent untuk mengirim data kembali ke UserFragment
//            Intent resultIntent = new Intent();
//            resultIntent.putExtra(UserFragment.EXTRA_UPDATED_USER, userToEdit);
//            setResult(RESULT_OK, resultIntent);
//
//            Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show();
//            finish(); // Menutup Activity ini dan kembali ke fragment sebelumnya
//        }
//    }
//}














//package com.example.rahmat_ux;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class EditProfileActivity extends AppCompatActivity {
//
//    private ImageView profileImage;
//
//    private ActivityResultLauncher<String> pickImageLauncher;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_edit_profile);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        profileImage = findViewById(R.id.profileImage);
//
//        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
//                uri -> {
//                    if (uri != null) {
//                        profileImage.setImageURI(uri);
//                    }
//                });
//
//        ImageView editProfileImage = findViewById(R.id.editProfileImage);
//
//        editProfileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pickImageLauncher.launch("image/*");
//            }
//        });
//
//        ImageView backButton = findViewById(R.id.backButton);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        Button updateProfileButton = findViewById(R.id.updateProfileButton);
//        updateProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        TextView pageTitle = findViewById(R.id.pageTitle);
//        pageTitle.setText("Edit Profil");
//    }
//}
