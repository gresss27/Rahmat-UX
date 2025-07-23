package com.example.rahmat_ux;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rahmat_ux.R;
import com.example.rahmat_ux.adapter.NotificationAdapter;
import com.example.rahmat_ux.data.DummyDataRepository;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification); // Your layout file

        //        BACK BUTTON
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigates back to the previous screen
            }
        });

//        MENAMPILKAN DATA DI RECYCLERVIEW
        RecyclerView recyclerView = findViewById(R.id.recyclerNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NotificationAdapter adapter = new NotificationAdapter(DummyDataRepository.getNotificationList());
        recyclerView.setAdapter(adapter);
    }
}
