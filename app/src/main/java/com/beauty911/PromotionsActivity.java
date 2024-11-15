package com.beauty911;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class PromotionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);

        // Setup each promotion's add to cart button
        setupPromotionButtons();
    }

    private void setupPromotionButtons() {
        // Promotion 1
        LinearLayout promotion1 = findViewById(R.id.promotion1);
        Button addToCartButton1 = promotion1.findViewById(R.id.add_to_cart_button_1);
        addToCartButton1.setOnClickListener(v -> addToCart("50% Off on IV Therapy", 250.0));

        // Promotion 2
        LinearLayout promotion2 = findViewById(R.id.promotion2);
        Button addToCartButton2 = promotion2.findViewById(R.id.add_to_cart_button_2);
        addToCartButton2.setOnClickListener(v -> addToCart("Winter Special! Only R450!", 450.0));

        // Promotion 3
        LinearLayout promotion3 = findViewById(R.id.promotion3);
        Button addToCartButton3 = promotion3.findViewById(R.id.add_to_cart_button_3);
        addToCartButton3.setOnClickListener(v -> addToCart("20% Off on Lashes", 250.0));

        // Promotion 4
        LinearLayout promotion4 = findViewById(R.id.promotion4);
        Button addToCartButton4 = promotion4.findViewById(R.id.add_to_cart_button_4);
        addToCartButton4.setOnClickListener(v -> addToCart("Father's Day Special!", 300.0));

        // Promotion 5
        LinearLayout promotion5 = findViewById(R.id.promotion5);
        Button addToCartButton5 = promotion5.findViewById(R.id.add_to_cart_button_5);
        addToCartButton5.setOnClickListener(v -> addToCart("VitaLife Cocktail Drip!", 500.0));
    }

    private void addToCart(String promotionName, double promotionPrice) {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Retrieve or create the existing sets for names and prices
        Set<String> savedNames = sharedPreferences.getStringSet("service_names", new HashSet<>());
        Set<String> savedPrices = sharedPreferences.getStringSet("service_prices", new HashSet<>());

        // Add the new promotion name and price
        savedNames.add(promotionName);
        savedPrices.add(String.valueOf(promotionPrice));

        // Save the updated sets back to SharedPreferences
        editor.putStringSet("service_names", savedNames);
        editor.putStringSet("service_prices", savedPrices);
        editor.apply();

        Log.d("PromotionsActivity", "Added to cart: " + promotionName + " - Price: R" + promotionPrice);

        // Navigate to the CartActivity immediately after adding to the cart
        Intent intent = new Intent(PromotionsActivity.this, CartActivity.class);
        startActivity(intent);  // This opens the CartActivity with the updated cart
    }


    public void goToCart(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);
        Set<String> cartPromotions = sharedPreferences.getStringSet("service_names", new HashSet<>());

        Log.d("PromotionsActivity", "Cart items: " + cartPromotions);

        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("cartPromotions", cartPromotions.toArray(new String[0]));
        startActivity(intent);
    }
}
