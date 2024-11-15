package com.beauty911;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConfirmationActivity extends AppCompatActivity {

    private TextView nameSurnameTextView, dateTimeTextView, servicesTextView, totalTextView;
    private Button backToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // Initialize TextViews
        nameSurnameTextView = findViewById(R.id.nameSurnameTextView);
        dateTimeTextView = findViewById(R.id.dateTimeTextView);
        servicesTextView = findViewById(R.id.servicesTextView);
        totalTextView = findViewById(R.id.totalTextView);

        // Initialize back button
        backToHomeButton = findViewById(R.id.backToHomeButton);

        // Get data passed from CheckoutActivity
        String name = getIntent().getStringExtra("name");
        String surname = getIntent().getStringExtra("surname");
        String dateTime = getIntent().getStringExtra("selectedDateTime");
        double total = getIntent().getDoubleExtra("totalCost", 0.0);
        ArrayList<String> services = getIntent().getStringArrayListExtra("services");

        // Set data to the TextViews
        nameSurnameTextView.setText("Name: " + name + " " + surname);
        dateTimeTextView.setText("Appointment: " + dateTime);
        if (services != null && !services.isEmpty()) {
            servicesTextView.setText("Booked Services: " + String.join(", ", services));
        } else {
            servicesTextView.setText("Booked Services: None");
        }
        totalTextView.setText("Total: R " + String.format("%.2f", total));

        // Store data in Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> appointmentDetails = new HashMap<>();
        appointmentDetails.put("Name", name + " " + surname);
        appointmentDetails.put("Appointment", dateTime);
        appointmentDetails.put("Booked Services", services != null ? services : new ArrayList<>());
        appointmentDetails.put("Total", total);

        db.collection("Checkout")
                .add(appointmentDetails)
                .addOnSuccessListener(documentReference -> {
                    // Handle success (e.g., log success or show a toast)
                    System.out.println("DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    // Handle failure (e.g., log failure or show an error message)
                    System.err.println("Error adding document: " + e.getMessage());
                });

        // Set up the back to home button
        backToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmationActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
