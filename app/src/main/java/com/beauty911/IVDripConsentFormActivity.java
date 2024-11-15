package com.beauty911;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class IVDripConsentFormActivity extends AppCompatActivity {

    private Button approveButton, disapproveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iv_drip_consent_form);

        approveButton = findViewById(R.id.approveButton);
        disapproveButton = findViewById(R.id.disapproveButton);

        approveButton.setOnClickListener(v -> {
            // Get the logged-in user's email from FirebaseAuth
            String userEmail = FirebaseAuth.getInstance().getCurrentUser() != null ?
                    FirebaseAuth.getInstance().getCurrentUser().getEmail() : null;

            if (userEmail != null) {
                // Save the consent approval to Firestore
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // Create a map to store consent details
                Map<String, Object> consent = new HashMap<>();
                consent.put("userEmail", userEmail);
                consent.put("status", "Approved");

                // Add the consent information to the "ConsentForms" collection
                db.collection("ConsentForms")
                        .add(consent)
                        .addOnSuccessListener(documentReference -> {
                            // Successfully added data to Firestore, redirect to IVDripsServiceActivity
                            Intent intent = new Intent(IVDripConsentFormActivity.this, IVDripsServiceActivity.class);
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            // Handle failure
                            Toast.makeText(IVDripConsentFormActivity.this, "Error saving consent. Please try again.", Toast.LENGTH_SHORT).show();
                        });
            } else {
                // Handle case where the user is not logged in
                Toast.makeText(IVDripConsentFormActivity.this, "User not logged in. Please log in and try again.", Toast.LENGTH_SHORT).show();
            }
        });

        disapproveButton.setOnClickListener(v -> {
            // Redirect back to the HomeActivity if the user disapproves
            Intent intent = new Intent(IVDripConsentFormActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
