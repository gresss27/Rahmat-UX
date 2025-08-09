package com.example.rahmat_ux;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rahmat_ux.model.User;

import com.example.rahmat_ux.SettingActivity;
import com.example.rahmat_ux.EditProfileActivity;
import com.example.rahmat_ux.TopUpActivity;
import com.example.rahmat_ux.MenungguPenyelesaianActivity;
import com.example.rahmat_ux.StatusDonasiActivity;
import com.example.rahmat_ux.DonationHistoryActivity;


public class UserFragment extends Fragment {

    public static final String EXTRA_UPDATED_USER = "extra_updated_user";
    public static final int REQUEST_CODE_EDIT_PROFILE = 1001;

    // Perbaikan: Konstruktor User disesuaikan dengan 5 argumen yang ada di model
    private static User currentUserProfile = new User("Rahmat Hidayat", "Mahasiswa", "rahmat.hidayat@example.com", 100000, 5000, "");

    private TextView userNameTextView;
    private TextView userEmailTextView;
    private TextView userJobTextView;
    private ImageView profileImageView;
    private Button topUpButton; // Deklarasi untuk tombol top up

    public UserFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userNameTextView = view.findViewById(R.id.userNameTextView);
        userEmailTextView = view.findViewById(R.id.userEmailTextView);
        userJobTextView = view.findViewById(R.id.userJobTextView);
        profileImageView = view.findViewById(R.id.profileImageView);

        ImageView settingIcon = view.findViewById(R.id.settingIcon);
        ImageView editProfile = view.findViewById(R.id.editProfile);
        LinearLayout column1 = view.findViewById(R.id.column1);
        LinearLayout column2 = view.findViewById(R.id.column2);
        LinearLayout column3 = view.findViewById(R.id.column3); // DITAMBAHKAN
        topUpButton = view.findViewById(R.id.topUpButton); // INISIALISASI UNTUK TOMBOL TOP UP

        if (settingIcon != null) {
            settingIcon.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            });
        }
        if (editProfile != null) {
            editProfile.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra(EXTRA_UPDATED_USER, currentUserProfile);
                startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE);
            });
        }

        if (column1 != null) {
            column1.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), MenungguPenyelesaianActivity.class);
                startActivity(intent);
            });
        }

        if (column2 != null) {
            column2.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), StatusDonasiActivity.class);
                startActivity(intent);
            });
        }

        // LOGIKA UNTUK TOP UP DI DALAM onViewCreated
        if (topUpButton != null) {
            topUpButton.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), TopUpActivity.class);
                startActivity(intent);
            });
        }

        // LOGIKA UNTUK DONATION HISTORY DI DALAM onViewCreated
        if (column3 != null) {
            column3.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DonationHistoryActivity.class);
                startActivity(intent);
            });
        }


        updateProfileUI();
    }

    private void updateProfileUI() {
        if (currentUserProfile != null) {
            userNameTextView.setText(currentUserProfile.getName());
            userEmailTextView.setText(currentUserProfile.getEmail());
            userJobTextView.setText(currentUserProfile.getJob());

            String imageUri = currentUserProfile.getProfileImageUri();
            if (imageUri != null && !imageUri.isEmpty()) {
                profileImageView.setImageURI(android.net.Uri.parse(imageUri));
            } else {
                profileImageView.setImageResource(R.drawable.profile); // fallback image
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT_PROFILE && resultCode == getActivity().RESULT_OK && data != null) {
            User updatedUser = data.getParcelableExtra(EXTRA_UPDATED_USER);
            if (updatedUser != null) {
                currentUserProfile = updatedUser;
                updateProfileUI();
            }
        }
    }
}
