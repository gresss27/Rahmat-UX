package com.example.rahmat_ux;

import android.os.Bundle;
import com.example.rahmat_ux.R;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SwipeFragment())
                    .commit();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.navigation_swipe) {
                selectedFragment = new SwipeFragment();
            } else if (id == R.id.navigation_explore) {
                selectedFragment = new ExploreFragment();
            } else if (id == R.id.navigation_add_campaign) {
                selectedFragment = new AddCampaignFragment();
            } else if (id == R.id.navigation_social) {
                selectedFragment = new SocialFragment();
            } else if (id == R.id.navigation_user) {
                selectedFragment = new UserFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });
    }

}