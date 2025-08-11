package com.example.rahmat_ux;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.view.Gravity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backBtn = findViewById(R.id.btnBack);
        backBtn.setOnClickListener(vi->{
            onBackPressed();
        });



        LinearLayout FAQbtn = findViewById(R.id.FAQButton);
        FAQbtn.setOnClickListener(vii->{
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView=inflater.inflate(R.layout.popup_faq,null);

            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable=true;
            PopupWindow popupWindow = new PopupWindow(popupView,width,height,focusable);

            // Show the popup at the center of the screen
            popupWindow.showAtLocation(vii,Gravity.CENTER, 0, 0);

            // Dim background
            View container = popupWindow.getContentView().getRootView();
            Context context = popupWindow.getContentView().getContext();

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.5f; // 0.0 = no dim, 1.0 = fully dark
            wm.updateViewLayout(container, p);
        });

        LinearLayout KebijakanPrivasiButton = findViewById(R.id.KebijakanPrivasiButton);
        KebijakanPrivasiButton.setOnClickListener(vii->{
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView=inflater.inflate(R.layout.popup_kebijakanprivasi,null);

            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable=true;
            PopupWindow popupWindow = new PopupWindow(popupView,width,height,focusable);

            // Show the popup at the center of the screen
            popupWindow.showAtLocation(vii,Gravity.CENTER, 0, 0);

            // Dim background
            View container = popupWindow.getContentView().getRootView();
            Context context = popupWindow.getContentView().getContext();

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.5f; // 0.0 = no dim, 1.0 = fully dark
            wm.updateViewLayout(container, p);
        });

        LinearLayout SyaratdanKetentuanButton = findViewById(R.id.SyaratdanKetentuanButton);
        SyaratdanKetentuanButton.setOnClickListener(vii->{
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView=inflater.inflate(R.layout.popup_syaratdanketentuan,null);

            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable=true;
            PopupWindow popupWindow = new PopupWindow(popupView,width,height,focusable);

            // Show the popup at the center of the screen
            popupWindow.showAtLocation(vii,Gravity.CENTER, 0, 0);

            // Dim background
            View container = popupWindow.getContentView().getRootView();
            Context context = popupWindow.getContentView().getContext();

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.5f; // 0.0 = no dim, 1.0 = fully dark
            wm.updateViewLayout(container, p);
        });

        LinearLayout TentangRahmatButton = findViewById(R.id.TentangRahmatButton);
        TentangRahmatButton.setOnClickListener(vii->{
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView=inflater.inflate(R.layout.popup_tentangrahmat,null);

            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable=true;
            PopupWindow popupWindow = new PopupWindow(popupView,width,height,focusable);

            // Show the popup at the center of the screen
            popupWindow.showAtLocation(vii,Gravity.CENTER, 0, 0);

            // Dim background
            View container = popupWindow.getContentView().getRootView();
            Context context = popupWindow.getContentView().getContext();

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.5f; // 0.0 = no dim, 1.0 = fully dark
            wm.updateViewLayout(container, p);
        });
    }





}