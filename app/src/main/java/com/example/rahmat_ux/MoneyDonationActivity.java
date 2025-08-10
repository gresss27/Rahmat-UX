package com.example.rahmat_ux;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.rahmat_ux.data.UserStorage;
import com.example.rahmat_ux.model.User;


public class MoneyDonationActivity extends AppCompatActivity {
    private long totalDonasi=-1;
    private int setPembayaran=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_donation);

        //Set Total Saldo
        TextView userBalance;
        userBalance=findViewById(R.id.userBalance);

        userBalance.setText("Rp"+formatCurrency(String.valueOf(UserStorage.getInstance().getLoggedInUser().getBalance())));



        //Select nominal pembayaran
        ConstraintLayout ten,twenty,fifty;

        CardView isiSendiri;
        EditText iniInput;


        ten=findViewById(R.id.ten);
        twenty=findViewById(R.id.twenty);
        fifty=findViewById(R.id.fifty);
        isiSendiri=findViewById(R.id.nominalisisendiri);
        iniInput=findViewById(R.id.editTextAmount);
        EditText userInput =findViewById(R.id.editTextAmount);
        ten.setOnClickListener(vi->{
            totalDonasi=10000;
            ten.setBackgroundResource(R.color.white);
            twenty.setBackgroundResource(R.color.white);
            fifty.setBackgroundResource(R.color.white);
            isiSendiri.setBackgroundResource(R.color.white);
            ten.setBackgroundResource(R.drawable.auth_button_background);
        });
        twenty.setOnClickListener(vi->{
            totalDonasi=20000;
            ten.setBackgroundResource(R.color.white);
            twenty.setBackgroundResource(R.color.white);
            fifty.setBackgroundResource(R.color.white);
            isiSendiri.setBackgroundResource(R.color.white);
            twenty.setBackgroundResource(R.drawable.auth_button_background);
        });
        fifty.setOnClickListener(vi->{
            totalDonasi=50000;
            ten.setBackgroundResource(R.color.white);
            twenty.setBackgroundResource(R.color.white);
            fifty.setBackgroundResource(R.color.white);
            isiSendiri.setBackgroundResource(R.color.white);
            fifty.setBackgroundResource(R.drawable.auth_button_background);
        });
        isiSendiri.setOnClickListener(vi->{
            totalDonasi=-2;
            ten.setBackgroundResource(R.color.white);
            twenty.setBackgroundResource(R.color.white);
            fifty.setBackgroundResource(R.color.white);
            isiSendiri.setBackgroundResource(R.color.white);
            isiSendiri.setBackgroundResource(R.drawable.auth_button_background);
        });

        iniInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                totalDonasi=-2;
                ten.setBackgroundResource(R.color.white);
                twenty.setBackgroundResource(R.color.white);
                fifty.setBackgroundResource(R.color.white);
                isiSendiri.setBackgroundResource(R.color.white);
                isiSendiri.setBackgroundResource(R.drawable.auth_button_background);
            }
        });

        //select tipe pembayaran
        CardView Saldo,VirtualAccount;
        VirtualAccount=findViewById(R.id.VirtualAccount);
        Saldo=findViewById(R.id.Saldo);
        VirtualAccount.setOnClickListener(vi->{
            setPembayaran=1;
            VirtualAccount.setBackgroundResource(R.color.white);
            Saldo.setBackgroundResource(R.color.white);
            VirtualAccount.setBackgroundResource(R.drawable.auth_button_background);
        });
        Saldo.setOnClickListener(vi->{
            setPembayaran=2;
            VirtualAccount.setBackgroundResource(R.color.white);
            Saldo.setBackgroundResource(R.color.white);
            Saldo.setBackgroundResource(R.drawable.auth_button_background);
        });


        //button Bayar
        CardView buttonBayar = findViewById(R.id.ButtonBayar);
        buttonBayar.setOnClickListener(vi->{
            if(totalDonasi==-2){
                if(userInput.getText().toString().isEmpty()){
                    Toast.makeText(this, "Kosong Koplak", Toast.LENGTH_SHORT).show();
                }else {
                    totalDonasi = Integer.valueOf(userInput.getText().toString().trim());
                }
            } else if(totalDonasi==-1){
                Toast.makeText(this, "Pilih dong mau donasi berapa", Toast.LENGTH_SHORT).show();
            } else if (totalDonasi<1000) {
                Toast.makeText(this, "Kan udh dibilang ga bole lebih kecil dari 1000", Toast.LENGTH_SHORT).show();
            } else if (setPembayaran==0) {
                Toast.makeText(this, "Pilih gmn bayarnya kocag", Toast.LENGTH_SHORT).show();
            } else if (setPembayaran==1) {
                Toast.makeText(this, "VA nihhh", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MoneyDonationActivity.this,MoneyDonationVirtualAccountActivity.class);
                intent.putExtra("donation_amount",totalDonasi);
                startActivity(intent);
            } else if (totalDonasi>UserStorage.getInstance().getLoggedInUser().getBalance()){
                Toast.makeText(this, "Duid luh kaga cukup jir", Toast.LENGTH_SHORT).show();
            } else{
                UserStorage.getInstance().substractBalance(totalDonasi);
                userBalance.setText("Rp"+formatCurrency(String.valueOf(UserStorage.getInstance().getLoggedInUser().getBalance())));
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView=inflater.inflate(R.layout.popup_donasi_money_berhasil,null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable=true;
                PopupWindow popupWindow = new PopupWindow(popupView,width,height,focusable);

                // Show the popup at the center of the screen
                popupWindow.showAtLocation(vi, Gravity.CENTER, 0, 0);

                // Dim background
                View container = popupWindow.getContentView().getRootView();
                Context context = popupWindow.getContentView().getContext();

                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
                p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.5f; // 0.0 = no dim, 1.0 = fully dark
                wm.updateViewLayout(container, p);

            }

        });


        // TOp up button
        LinearLayout TopUpBtn=findViewById(R.id.topUpButton);
        TopUpBtn.setOnClickListener(vi->{
            Intent intent=new Intent(MoneyDonationActivity.this,TopUpActivity.class);
            startActivity(intent);
        });

        // BACK BUTTON
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Go back to previous screen
            }
        });




    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    // Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private String formatCurrency(String amount) {
        try {
            long number = Long.parseLong(amount.replace(".", "").replace(",", ""));
            return String.format("%,d", number).replace(',', '.');
        } catch (NumberFormatException e) {
            return amount;
        }
    }

}
