package com.beauty911;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MakeupServiceActivity extends AppCompatActivity {

    // Corrected declaration: Use commas instead of semicolons
    private Button addToCartButton1, addToCartButton2, addToCartButton3, addToCartButton4, addToCartButton5,
            addToCartButton6, addToCartButton7, addToCartButton8, addToCartButton9, addToCartButton10;

    private ArrayList<String> cartServicesNames = new ArrayList<>();
    private ArrayList<Double> cartServicesPrices = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeup_service);

        // Initialize buttons
        addToCartButton1 = findViewById(R.id.add_to_cart_button_1);
        addToCartButton2 = findViewById(R.id.add_to_cart_button_2);
        addToCartButton3 = findViewById(R.id.add_to_cart_button_3);
        addToCartButton4 = findViewById(R.id.add_to_cart_button_4);
        addToCartButton5 = findViewById(R.id.add_to_cart_button_5);
        addToCartButton6 = findViewById(R.id.add_to_cart_button_6);
        addToCartButton7 = findViewById(R.id.add_to_cart_button_7);
        addToCartButton8 = findViewById(R.id.add_to_cart_button_8);
        addToCartButton9 = findViewById(R.id.add_to_cart_button_9);
        addToCartButton10 = findViewById(R.id.add_to_cart_button_10);

        // Set click listeners for each button
        addToCartButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart("Natural Makeup", 350.0);
            }
        });

        addToCartButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(" Bridal Makeup", 1500.0);
            }
        });

        addToCartButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart("Glam Makeup", 600.0);
            }
        });

        addToCartButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart("Airbrush Makeup", 800.0);
            }
        });

        addToCartButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart("Evening Makeup", 500.0);
            }
        });

        addToCartButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(" Editorial/Photoshoot Makeup", 900.0);
            }
        });

        addToCartButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart("Prom Makeup", 450.0);
            }
        });

        addToCartButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(" Festival Makeup", 550.0);
            }
        });

        addToCartButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart("Corporate/Professional Makeup", 300.0);
            }
        });

        addToCartButton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart("Special Effects (SFX) Makeup", 1200.0);
            }
        });
    }

    // Method to add the service to the cart
    private void addToCart(String serviceName, double servicePrice) {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> savedNames = sharedPreferences.getStringSet("service_names", new HashSet<>());
        Set<String> savedPrices = sharedPreferences.getStringSet("service_prices", new HashSet<>());

        // Add new items
        savedNames.add(serviceName);
        savedPrices.add(String.valueOf(servicePrice));

        // Save back to SharedPreferences
        editor.putStringSet("service_names", savedNames);
        editor.putStringSet("service_prices", savedPrices);
        editor.apply();

        // Go to CartActivity to display items
        Intent intent = new Intent(MakeupServiceActivity.this, CartActivity.class);
        startActivity(intent);
    }
}
