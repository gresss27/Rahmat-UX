package com.example.rahmat_ux;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rahmat_ux.data.UserStorage;
import com.example.rahmat_ux.model.User;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView profileImage;

    private EditText inputName, inputJob, inputEmail, inputPhone, donasiPerSwipe;
    private TextView profileName,profileJob;

    private ActivityResultLauncher<String> pickImageLauncher;

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

        inputName=findViewById(R.id.inputName);
        inputEmail=findViewById(R.id.inputEmail);
        inputJob=findViewById(R.id.inputJob);
        inputPhone=findViewById(R.id.inputPhone);
        donasiPerSwipe=findViewById(R.id.donasiPerSwipe);
        profileName=findViewById(R.id.profileName);
        profileJob=findViewById(R.id.profileJob);

        inputName.setText(UserStorage.getInstance().getLoggedInUser().getName());
        inputEmail.setText(UserStorage.getInstance().getLoggedInUser().getEmail());
        inputJob.setText(UserStorage.getInstance().getLoggedInUser().getPekerjaan());
        inputPhone.setText(UserStorage.getInstance().getLoggedInUser().getPhoneNumber());
        donasiPerSwipe.setText(String.valueOf(UserStorage.getInstance().getLoggedInUser().getDonationPerSwipe()));
        profileName.setText(UserStorage.getInstance().getLoggedInUser().getName());
        profileJob.setText(UserStorage.getInstance().getLoggedInUser().getPekerjaan());








        profileImage = findViewById(R.id.profileImage);

        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        profileImage.setImageURI(uri);
                    }
                });

        ImageView editProfileImage = findViewById(R.id.editProfileImage);

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageLauncher.launch("image/*");
            }
        });

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button updateProfileButton = findViewById(R.id.updateProfileButton);
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=inputName.getText().toString().trim();
                String email=inputEmail.getText().toString().trim();
                String job=inputJob.getText().toString().trim();
                String phone=inputPhone.getText().toString().trim();
                String donasi=donasiPerSwipe.getText().toString().trim();
                long donasii;
                if(name.isEmpty()){
                    name= UserStorage.getInstance().getLoggedInUser().getName();
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    email= UserStorage.getInstance().getLoggedInUser().getEmail();
                }
                if(job.isEmpty()){
                    job= UserStorage.getInstance().getLoggedInUser().getPekerjaan();
                }
                if(phone.isEmpty()){
                    phone= UserStorage.getInstance().getLoggedInUser().getPhoneNumber();
                }
                if(donasi.isEmpty()){
                    donasii= UserStorage.getInstance().getLoggedInUser().getDonationPerSwipe();
                }else{
                    donasii=Long.valueOf(donasi);
                }
                UserStorage.getInstance().getLoggedInUser().setName(name);
                UserStorage.getInstance().getLoggedInUser().setPekerjaan(job);
                UserStorage.getInstance().getLoggedInUser().setphoneNumber(phone);
                UserStorage.getInstance().getLoggedInUser().setEmail(email);
                UserStorage.getInstance().getLoggedInUser().setDonationPerSwipe(donasii);


                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        TextView pageTitle = findViewById(R.id.pageTitle);
        pageTitle.setText("Edit Profil");
    }
}
