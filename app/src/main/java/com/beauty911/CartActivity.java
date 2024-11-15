package com.beauty911;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CartActivity extends AppCompatActivity {

    private TextView cartItemsTextView, totalCostTextView;
    private Button checkoutButton;
    private LinearLayout cartItemsLayout;
    private ArrayList<String> cartServicesNames = new ArrayList<>();
    private ArrayList<Double> cartServicesPrices = new ArrayList<>();
    private double totalCost = 0.0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize UI components
        cartItemsTextView = findViewById(R.id.cartItemsTextView);
        totalCostTextView = findViewById(R.id.totalCostTextView);
        checkoutButton = findViewById(R.id.checkoutButton);
        cartItemsLayout = findViewById(R.id.cartItemsLayout);

        // Load cart data from SharedPreferences
        loadCartData();

        // Display cart items and total cost
        displayCartItems();
        displayTotalCost();

        // Set up checkout button click event
        checkoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            // Pass the total cost and booked services
            intent.putExtra("totalCost", totalCost);
            intent.putStringArrayListExtra("bookedServices", cartServicesNames); // assuming cartServicesNames represent the booked services
            startActivity(intent);
        });

    }


    private void loadCartData() {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);
        Set<String> savedNames = sharedPreferences.getStringSet("service_names", new HashSet<>());
        Set<String> savedPrices = sharedPreferences.getStringSet("service_prices", new HashSet<>());

        for (String name : savedNames) {
            cartServicesNames.add(name);
        }

        for (String price : savedPrices) {
            cartServicesPrices.add(Double.parseDouble(price));
        }
    }

    private void displayCartItems() {
        cartItemsLayout.removeAllViews();
        for (int i = 0; i < cartServicesNames.size(); i++) {
            String itemName = cartServicesNames.get(i);
            double itemPrice = cartServicesPrices.get(i);

            TextView itemTextView = new TextView(this);
            itemTextView.setText(itemName + " - R" + String.format("%.2f", itemPrice));
            itemTextView.setTextSize(16);

            // Capture the current index in a final variable for use in the lambda
            final int currentIndex = i;
            Button removeButton = new Button(this);
            removeButton.setText("Remove");
            removeButton.setOnClickListener(v -> {
                removeItemFromCart(currentIndex);
            });

            cartItemsLayout.addView(itemTextView);
            cartItemsLayout.addView(removeButton);
        }
    }


    private void removeItemFromCart(int index) {
        cartServicesNames.remove(index);
        cartServicesPrices.remove(index);
        saveCartData();
        displayCartItems();
        displayTotalCost();
        Toast.makeText(this, "Item removed from cart", Toast.LENGTH_SHORT).show();
    }

    private void saveCartData() {
        SharedPreferences sharedPreferences = getSharedPreferences("CartPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> namesSet = new HashSet<>(cartServicesNames);
        Set<String> pricesSet = new HashSet<>();
        for (Double price : cartServicesPrices) {
            pricesSet.add(String.valueOf(price));
        }
        editor.putStringSet("service_names", namesSet);
        editor.putStringSet("service_prices", pricesSet);
        editor.apply();
    }

    private void displayTotalCost() {
        totalCost = 0.0;
        for (double price : cartServicesPrices) {
            totalCost += price;
        }
        totalCostTextView.setText("Total: R" + String.format("%.2f", totalCost));
    }
}