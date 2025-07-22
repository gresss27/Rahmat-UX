package com.example.rahmat_ux;
// nama file ini adalah CreateCampaignActivity.java
import android.app.DatePickerDialog;
import android.os.Bundle;
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

import com.example.rahmat_ux.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class CreateCampaignActivity extends AppCompatActivity {

    private EditText etEndDate, etTargetAmount, etItemInput, etBankAccountNumber, etBankAccountName;
    private LinearLayout storyListContainer;

    private FlexboxLayout pillContainer;

    private int storyCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        etEndDate = findViewById(R.id.etEndDate);
        etEndDate.setOnClickListener(v -> showDatePicker());

        Spinner spinnerType = findViewById(R.id.spinnerCampaignType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.campaign_types,
                R.layout.spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        etItemInput = findViewById(R.id.etItemInput);
        pillContainer = findViewById(R.id.pillContainer);
        Button btnAddItem = findViewById(R.id.btnAddItem);

        btnAddItem.setOnClickListener(v -> {
            String item = etItemInput.getText().toString().trim();
            if (!item.isEmpty()) {
                addItemPill(item);
                etItemInput.setText("");
            }
        });

        Button btnAddStory = findViewById(R.id.btnAddStory);
        storyListContainer = findViewById(R.id.storyListContainer);

        btnAddStory.setOnClickListener(v -> {
            storyCount++;
            TextView tv = new TextView(this);
            tv.setText("Cerita Tambahan " + storyCount);
            tv.setTextColor(getResources().getColor(android.R.color.black));
            tv.setTextSize(14);
            storyListContainer.addView(tv);
        });

        Button btnSubmit = findViewById(R.id.btnSubmitCampaign);
        btnSubmit.setOnClickListener(v -> {
            // Simpan data, bisa tambah ke repository atau database
            Toast.makeText(this, "Ajukan galang dana berhasil", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            etEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1);
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
}
