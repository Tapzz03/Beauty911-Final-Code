package com.beauty911;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // Firebase Firestore instance
    private FirebaseFirestore db;

    // UI elements
    private SearchView searchView;
    private LinearLayout searchResultsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Find the SearchView and results layout by their IDs
        searchView = findViewById(R.id.searchBar);
        searchResultsLayout = findViewById(R.id.searchResultsLayout);

        // Set the hint programmatically with italics
        searchView.setQueryHint("What are you looking for?");

        // Set up the search bar listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when the user submits the query
                searchForServices(query);
                return false; // Return false to allow the SearchView to handle the query as well
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search as the user types
                searchForServices(newText);
                return true; // Return true to indicate the query was handled
            }
        });

        // Find the LinearLayout buttons by their IDs and set onClick listeners
        LinearLayout hairButton = findViewById(R.id.hairButton);
        hairButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, HairServiceActivity.class));
        });

        LinearLayout nailButton = findViewById(R.id.nailButton);
        nailButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, NailsServiceActivity.class));
        });

        LinearLayout eyelashButton = findViewById(R.id.eyelashButton);
        eyelashButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, EyeLashServiceActivity.class));
        });

        LinearLayout makeupButton = findViewById(R.id.makeupButton);
        makeupButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MakeupServiceActivity.class));
        });

        LinearLayout facialButton = findViewById(R.id.facialButton);
        facialButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, FacialServiceActivity.class));
        });

        // IV Drips Button: Always ask for consent before showing the IV Drip service
        LinearLayout IVDripsButton = findViewById(R.id.IVDripsButton);
        IVDripsButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, IVDripConsentFormActivity.class));
        });

        // Find the ImageViews for promotions, account, cart, and about buttons
        ImageView promotionsButton = findViewById(R.id.promotionsButton);
        ImageView accountButton = findViewById(R.id.accountButton);
        ImageView cartButton = findViewById(R.id.cartButton);
        ImageView aboutButton = findViewById(R.id.aboutButton);

        // Set onClick listeners for the ImageViews
        promotionsButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, PromotionsActivity.class));
        });

        accountButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, UserProfileActivity.class));
        });

        cartButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, CartActivity.class));
        });

        aboutButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        });
    }

    // Method to search for services based on the user's input
    private void searchForServices(String query) {
        if (query.isEmpty()) {
            // Clear search results if query is empty
            searchResultsLayout.removeAllViews();
            return;
        }

        // Query the Firestore database for services that match the query
        db.collection("Services")
                .orderBy("name") // You can change this to price or any other field you want
                .startAt(query)
                .endAt(query + "\uf8ff") // This enables a "like" search
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            List<DocumentSnapshot> services = querySnapshot.getDocuments();
                            displaySearchResults(services);
                        } else {
                            Toast.makeText(HomeActivity.this, "No services found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method to display the search results
    private void displaySearchResults(List<DocumentSnapshot> services) {
        searchResultsLayout.removeAllViews();

        for (DocumentSnapshot service : services) {
            String serviceName = service.getString("name");
            String serviceType = service.getString("type"); // Add this field in Firestore to indicate the type of service.

            // Create a new layout for each service
            LinearLayout serviceView = new LinearLayout(this);
            serviceView.setOrientation(LinearLayout.HORIZONTAL);

            // Add a TextView for the service name
            TextView serviceNameTextView = new TextView(this);
            serviceNameTextView.setText(serviceName);
            serviceNameTextView.setTextColor(getResources().getColor(android.R.color.black));
            serviceNameTextView.setTypeface(null, Typeface.BOLD);
            serviceView.addView(serviceNameTextView);

            // Add click listener to the serviceView
            serviceView.setOnClickListener(v -> {
                switch (serviceType) {
                    case "Hair":
                        startActivity(new Intent(HomeActivity.this, HairServiceActivity.class));
                        break;
                    case "Nails":
                        startActivity(new Intent(HomeActivity.this, NailsServiceActivity.class));
                        break;
                    case "Eyelash":
                        startActivity(new Intent(HomeActivity.this, EyeLashServiceActivity.class));
                        break;
                    case "Makeup":
                        startActivity(new Intent(HomeActivity.this, MakeupServiceActivity.class));
                        break;
                    case "Facial":
                        startActivity(new Intent(HomeActivity.this, FacialServiceActivity.class));
                        break;
                    case "IV Drips":
                        startActivity(new Intent(HomeActivity.this, IVDripsServiceActivity.class));
                        break;
                    default:
                        Toast.makeText(HomeActivity.this, "Unknown service type", Toast.LENGTH_SHORT).show();
                        break;
                }
            });

            // Add the created view to the results layout
            searchResultsLayout.addView(serviceView);
        }
    }
}
