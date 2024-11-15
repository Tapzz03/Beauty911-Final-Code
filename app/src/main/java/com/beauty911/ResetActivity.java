package com.beauty911;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.beauty911.R;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private TextView resetPasswordMessage;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        // Bind UI elements
        emailEditText = findViewById(R.id.email);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);
        resetPasswordMessage = findViewById(R.id.resetPasswordMessage);

        // Button click listener to send the reset password email
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the email input from the user
                String email = emailEditText.getText().toString().trim();

                // Validate email input
                if (TextUtils.isEmpty(email)) {
                    resetPasswordMessage.setText("Please enter a valid email address.");
                    resetPasswordMessage.setVisibility(View.VISIBLE);
                    return;
                }

                // Send password reset email using FirebaseAuth
                auth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(aVoid -> {
                            // Show success message if email is sent
                            resetPasswordMessage.setText("Password reset email sent. Please check your inbox.");
                            resetPasswordMessage.setVisibility(View.VISIBLE);
                        })
                        .addOnFailureListener(e -> {
                            // Show error message if something goes wrong
                            resetPasswordMessage.setText("Failed to send reset email. Please try again.");
                            resetPasswordMessage.setVisibility(View.VISIBLE);
                        });
            }
        });
    }
}
