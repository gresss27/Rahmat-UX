package com.example.rahmat_ux;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahmat_ux.data.UserStorage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);


    }
    private String formatCurrency(String amount) {
        try {
            long number = Long.parseLong(amount.replace(".", "").replace(",", ""));
            return String.format("%,d", number).replace(',', '.');
        } catch (NumberFormatException e) {
            return amount;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView settingIcon = view.findViewById(R.id.settingIcon);
        ImageView editProfile = view.findViewById(R.id.editProfile);

        LinearLayout column1 = view.findViewById(R.id.column1);
        LinearLayout column2 = view.findViewById(R.id.column2);

        LinearLayout loggedOutButton=view.findViewById(R.id.LogOutButton);

        TextView profileName=view.findViewById(R.id.profileName);
        profileName.setText(UserStorage.getInstance().getLoggedInUser().getName());


        if(UserStorage.getInstance().getLoggedInUser()!=null){
            TextView totalBalanceValue = view.findViewById(R.id.totalBalanceValue);
            totalBalanceValue.setText(formatCurrency(String.valueOf(UserStorage.getInstance().getLoggedInUser().getBalance())));
        }


        loggedOutButton.setOnClickListener(v->{
            UserStorage.getInstance().logout();
            Intent intent= new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        settingIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        });

        editProfile.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        column1.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MenungguPenyelesaianActivity.class); // Ganti dengan Activity tujuan Rahmat
            startActivity(intent);
        });

        // Mengatur OnClickListener untuk column2 (Status Donasi)
        column2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), StatusDonasiActivity.class); // Ganti dengan Activity tujuan Rahmat
            startActivity(intent);
        });

//        NAVIGATE TO TOP UP ACTIVITY
        Button topUpButton = view.findViewById(R.id.topUpButton);

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), TopUpActivity.class);
                startActivity(intent);
            }
        });

    }

}