package com.beauty911;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

public class CheckoutActivity extends AppCompatActivity {
    private TextView selectedDateTime;
    private TextView totalCostCheckout;
    private EditText nameInput, surnameInput, emailInput, addressInput, phoneInput;
    private Button dateTimeButton, makePaymentButton;
    private double totalCost; // Total cost should be a double
    private int year, month, dayOfMonth, hour, minute;
    private ArrayList<String> servicesList; // List of services

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        selectedDateTime = findViewById(R.id.selectedDateTime);
        totalCostCheckout = findViewById(R.id.totalCostCheckout);
        nameInput = findViewById(R.id.nameInput);
        surnameInput = findViewById(R.id.surnameInput);
        emailInput = findViewById(R.id.emailInput);
        addressInput = findViewById(R.id.addressInput);
        phoneInput = findViewById(R.id.phoneInput);
        dateTimeButton = findViewById(R.id.dateTimeButton);
        makePaymentButton = findViewById(R.id.makePaymentButton);

        // Retrieve total cost and services list from the previous activity
        Intent intent = getIntent();
        totalCost = intent.getDoubleExtra("totalCost", 0.0); // Receive as double
        servicesList = intent.getStringArrayListExtra("bookedServices"); // Ensure this key matches where data is passed

        totalCostCheckout.setText("Total: R" + String.format("%.2f", totalCost)); // Display with 2 decimal places

        // Get the current date to set the default for DatePicker
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        // DateTime Button Click Listener
        dateTimeButton.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

                // TimePickerDialog for selecting time
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timeView, selectedHour, selectedMinute) -> {
                    selectedCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    selectedCalendar.set(Calendar.MINUTE, selectedMinute);

                    // Ensure the selected time is not in the past
                    if (selectedCalendar.before(Calendar.getInstance())) {
                        Toast.makeText(this, "You cannot select a past time!", Toast.LENGTH_SHORT).show();
                    } else {
                        String selectedDateTimeStr = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear +
                                " " + String.format("%02d:%02d", selectedHour, selectedMinute);
                        selectedDateTime.setText("Selected Date and Time: " + selectedDateTimeStr);
                    }
                }, hourOfDay, minute, true);

                timePickerDialog.show();
            }, year, month, dayOfMonth);

            // Set minimum date to the current date to disallow past dates
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        });



        // Make Payment Button Click Listener
        makePaymentButton.setOnClickListener(v -> initiatePayment());
    }

    private void initiatePayment() {
        String name = nameInput.getText().toString().trim();
        String surname = surnameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String dateTime = selectedDateTime.getText().toString();

        // Validation
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty() || dateTime.isEmpty()) {
            Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed to ConfirmationActivity
        Intent confirmationIntent = new Intent(CheckoutActivity.this, ConfirmationActivity.class);
        confirmationIntent.putExtra("name", name);
        confirmationIntent.putExtra("surname", surname);
        confirmationIntent.putExtra("selectedDateTime", dateTime);
        confirmationIntent.putExtra("totalCost", totalCost); // Pass total cost as double
        confirmationIntent.putStringArrayListExtra("services", servicesList); // Pass services list

        startActivity(confirmationIntent);
        finish();
    }
}
