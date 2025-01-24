package com.example.inventory_esp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText rfidInput, quantityInput, statusInput;
    private TextView resultTextView;
    private Button createBtn, readBtn, updateBtn, deleteBtn;

    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rfidInput = findViewById(R.id.rfidInput);
        quantityInput = findViewById(R.id.quantityInput);
        statusInput = findViewById(R.id.statusInput);
        resultTextView = findViewById(R.id.resultTextView);

        createBtn = findViewById(R.id.createBtn);
        readBtn = findViewById(R.id.readBtn);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        firebaseHelper = new FirebaseHelper();

        // Create Inventory
        createBtn.setOnClickListener(v -> {
            String rfid = rfidInput.getText().toString();
            String status = statusInput.getText().toString();
            int quantity = Integer.parseInt(quantityInput.getText().toString());

            firebaseHelper.createOrUpdateInventory(rfid, status, quantity);
            Toast.makeText(MainActivity.this, "Inventory Created/Updated", Toast.LENGTH_SHORT).show();
        });

        // Read Inventory
        readBtn.setOnClickListener(v -> {
            String rfid = rfidInput.getText().toString();
            firebaseHelper.readInventory(rfid, new FirebaseHelper.InventoryCallback() {
                @Override
                public void onInventoryReceived(Inventory inventory) {
                    resultTextView.setText("Status: " + inventory.getStatus() + "\nQuantity: " + inventory.getQuantity());
                }

                @Override
                public void onError(Exception e) {
                    resultTextView.setText("Error: " + e.getMessage());
                }
            });
        });

        // Update Inventory
        updateBtn.setOnClickListener(v -> {
            String rfid = rfidInput.getText().toString();
            String status = statusInput.getText().toString();
            int quantity = Integer.parseInt(quantityInput.getText().toString());

            firebaseHelper.createOrUpdateInventory(rfid, status, quantity);
            Toast.makeText(MainActivity.this, "Inventory Updated", Toast.LENGTH_SHORT).show();
        });

        // Delete Inventory
        deleteBtn.setOnClickListener(v -> {
            String rfid = rfidInput.getText().toString();
            firebaseHelper.deleteInventory(rfid);
            Toast.makeText(MainActivity.this, "Inventory Deleted", Toast.LENGTH_SHORT).show();
        });
    }
}
