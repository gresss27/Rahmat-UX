package com.example.rahmat_ux;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahmat_ux.adapter.DonatorAdapter;
import com.example.rahmat_ux.data.DummyDataRepository;

public class DonatorListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donator_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //        BACK BUTTON
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigates back to the previous screen
            }
        });

//        LIST DONASI
        RecyclerView recyclerDonators = findViewById(R.id.recyclerDonators);
        recyclerDonators.setLayoutManager(new LinearLayoutManager(this));
        DonatorAdapter adapter = new DonatorAdapter(DummyDataRepository.getDonatorList(), 0); // 0 = show all
        recyclerDonators.setAdapter(adapter);


    }
}