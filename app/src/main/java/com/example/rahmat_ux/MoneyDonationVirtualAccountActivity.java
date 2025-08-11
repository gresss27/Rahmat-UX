package com.example.rahmat_ux;

import android.content.Intent;
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

import com.example.rahmat_ux.adapter.BankAdapter;
import com.example.rahmat_ux.data.DummyDataRepository;
import com.example.rahmat_ux.model.Bank;

import java.util.List;

public class MoneyDonationVirtualAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_money_donation_virtual_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerBank);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Bank> bankList = DummyDataRepository.getInstance().getBankList();
        BankAdapter adapter = new BankAdapter(DummyDataRepository.getInstance().getBankList(), bank -> {
            Intent intent = new Intent(MoneyDonationVirtualAccountActivity.this, MoneyDonationVirtualAccountDetailActivity.class);
            intent.putExtra("selected_bank_name", bank.getBankName());
            intent.putExtra("selected_bank_image", bank.getBankLogoResId());
            intent.putExtra("amount", String.valueOf(getIntent().getLongExtra("donation_amount",-1)));
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        //        BACK BUTTON
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigates back to the previous screen
            }
        });
    }
}