package com.beauty911;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the LinearLayout buttons by their IDs and set onClick listeners
        LinearLayout hairButton = findViewById(R.id.hairButton);
        hairButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HairServiceActivity.class);
            startActivity(intent);
        });

        LinearLayout nailButton = findViewById(R.id.nailButton);
        nailButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, NailsServiceActivity.class);
            startActivity(intent);
        });

        LinearLayout eyelashButton = findViewById(R.id.eyelashButton);
        eyelashButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EyeLashServiceActivity.class);
            startActivity(intent);
        });

        LinearLayout makeupButton = findViewById(R.id.makeupButton);
        makeupButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MakeupServiceActivity.class);
            startActivity(intent);
        });

        LinearLayout facialButton = findViewById(R.id.facialButton);
        facialButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FacialServiceActivity.class);
            startActivity(intent);
        });

        // IV Drips Button: Always ask for consent before showing the IV Drip service
        LinearLayout IVDripsButton = findViewById(R.id.IVDripsButton);
        IVDripsButton.setOnClickListener(v -> {
            // Always redirect to the IVDripConsentFormActivity
            Intent intent = new Intent(HomeActivity.this, IVDripConsentFormActivity.class);
            startActivity(intent);
        });

        // Find the ImageViews for promotions, account, cart, and about buttons
        ImageView promotionsButton = findViewById(R.id.promotionsButton);
        ImageView accountButton = findViewById(R.id.accountButton);
        ImageView cartButton = findViewById(R.id.cartButton);
        ImageView aboutButton = findViewById(R.id.aboutButton);

        // Set onClick listeners for the ImageViews
        promotionsButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PromotionsActivity.class);
            startActivity(intent);
        });

        accountButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });

        cartButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        aboutButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }
}
