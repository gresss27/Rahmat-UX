package com.example.rahmat_ux;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView backButton;
    private EditText inputName, inputJob, inputEmail, inputPhone;
    private Button updateProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile); // Pastikan nama filenya activity_edit_profile.xml

        // Inisialisasi View
        backButton = findViewById(R.id.backButton);
        inputName = findViewById(R.id.inputName);
        inputJob = findViewById(R.id.inputJob);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhone = findViewById(R.id.inputPhone);
        updateProfileButton = findViewById(R.id.updateProfileButton);

        // Tombol kembali
        backButton.setOnClickListener(v -> finish());

        // Tombol ubah profil (sementara tampilkan data di logcat)
        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String job = inputJob.getText().toString();
                String email = inputEmail.getText().toString();
                String phone = inputPhone.getText().toString();

                // TODO: Simpan data ini ke database / API atau shared preferences
                // Contoh debug log
                android.util.Log.d("EditProfile", "Nama: " + name + ", Pekerjaan: " + job + ", Email: " + email + ", Telpon: " + phone);

                // Misalnya tampilkan Toast
                android.widget.Toast.makeText(EditProfileActivity.this, "Profil diperbarui!", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }
}
