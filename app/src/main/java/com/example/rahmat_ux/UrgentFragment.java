package com.example.rahmat_ux;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UrgentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UrgentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UrgentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UrgentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UrgentFragment newInstance(String param1, String param2) {
        UrgentFragment fragment = new UrgentFragment();
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

    public static UrgentFragment newInstance(String keyword) {
        UrgentFragment fragment = new UrgentFragment();
        Bundle args = new Bundle();
        args.putString("keyword", keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_urgent, container, false);

        EditText searchView = view.findViewById(R.id.kolom_pencarian);
        String keyword = getArguments() != null ? getArguments().getString("keyword", "") : "";
        searchView.setText(keyword);

        searchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String newKeyword = v.getText().toString().trim();

                if (newKeyword.isEmpty()) {
                    // Balik ke ExploreFragment
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new ExploreFragment())
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Tetap di Urgent", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        return view;
    }

}