package com.beauty911;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserProfileActivity extends AppCompatActivity {
    private EditText nameEditText, surnameEditText, emailEditText, newEmailEditText, phoneEditText, newPhoneEditText, currentPasswordEditText, newPasswordEditText, addressEditText, newAddressEditText;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private TextView greetingTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize UI elements
        nameEditText = findViewById(R.id.editTextName);
        surnameEditText = findViewById(R.id.editTextSurname);
        emailEditText = findViewById(R.id.editTextEmail);
        newEmailEditText = findViewById(R.id.editTextNewEmail);
        phoneEditText = findViewById(R.id.editTextPhone);
        newPhoneEditText = findViewById(R.id.editTextNewPhone);
        currentPasswordEditText = findViewById(R.id.editTextPassword);
        newPasswordEditText = findViewById(R.id.editTextNewPassword);
        addressEditText = findViewById(R.id.editTextAddress);
        newAddressEditText = findViewById(R.id.editTextNewAddress);
        greetingTextView = findViewById(R.id.greetingTextView);

        // Initialize Firebase instances
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        // Load current user data
        loadUserData();

        // Set update profile button click listener
        findViewById(R.id.buttonUpdateProfile).setOnClickListener(v -> updateProfile());

        // Set logout button click listener
        findViewById(R.id.buttonLogout).setOnClickListener(v -> logoutUser());
    }

    private void loadUserData() {
        if (currentUser != null) {
            db.collection("Users").document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(document -> {
                        if (document.exists()) {
                            String name = document.getString("name");
                            String surname = document.getString("surname");
                            String email = document.getString("email");
                            String phone = document.getString("phone");
                            String address = document.getString("address");

                            nameEditText.setText(name);
                            surnameEditText.setText(surname);
                            emailEditText.setText(email);
                            phoneEditText.setText(phone);
                            addressEditText.setText(address);

                            // Display greeting message
                            greetingTextView.setText("Hello " + name);
                        }
                    });
        }
    }

    private void updateProfile() {
        String name = nameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String currentEmail = emailEditText.getText().toString().trim();
        String newEmail = newEmailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String newPhone = newPhoneEditText.getText().toString().trim();
        String currentPassword = currentPasswordEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String newAddress = newAddressEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(currentEmail) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verify current password for reauthentication
        currentUser.reauthenticate(EmailAuthProvider.getCredential(currentUser.getEmail(), currentPassword))
                .addOnSuccessListener(aVoid -> {
                    // Update user profile in Firestore
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    user.put("surname", surname);
                    user.put("email", currentEmail);
                    user.put("phone", phone);
                    user.put("address", address);

                    // Check for new email, phone, or address and update them if provided
                    if (!TextUtils.isEmpty(newEmail)) {
                        currentUser.updateEmail(newEmail)
                                .addOnSuccessListener(aVoid1 -> {
                                    user.put("email", newEmail);
                                    Toast.makeText(UserProfileActivity.this, "Email updated", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> Toast.makeText(UserProfileActivity.this, "Error updating email", Toast.LENGTH_SHORT).show());
                    }

                    if (!TextUtils.isEmpty(newPhone)) {
                        user.put("phone", newPhone);
                        Toast.makeText(UserProfileActivity.this, "Phone updated", Toast.LENGTH_SHORT).show();
                    }

                    if (!TextUtils.isEmpty(newAddress)) {
                        user.put("address", newAddress);
                        Toast.makeText(UserProfileActivity.this, "Address updated", Toast.LENGTH_SHORT).show();
                    }

                    db.collection("Users").document(currentUser.getUid())
                            .update(user)
                            .addOnSuccessListener(aVoid1 -> {
                                Toast.makeText(UserProfileActivity.this, "Profile successfully updated", Toast.LENGTH_SHORT).show();

                                // Update password if a new one is provided
                                if (!TextUtils.isEmpty(newPassword)) {
                                    currentUser.updatePassword(newPassword)
                                            .addOnSuccessListener(aVoid2 -> Toast.makeText(UserProfileActivity.this, "Password updated", Toast.LENGTH_SHORT).show())
                                            .addOnFailureListener(e -> Toast.makeText(UserProfileActivity.this, "Error updating password", Toast.LENGTH_SHORT).show());
                                }
                            })
                            .addOnFailureListener(e -> Toast.makeText(UserProfileActivity.this, "Error updating profile", Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e -> Toast.makeText(UserProfileActivity.this, "Reauthentication failed. Please check your current password.", Toast.LENGTH_SHORT).show());
    }

    private void logoutUser() {
        auth.signOut();
        Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
