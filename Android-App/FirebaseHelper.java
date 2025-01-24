package com.example.inventory_esp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    private DatabaseReference mDatabase;

    public FirebaseHelper() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    // Create or Update Inventory
    public void createOrUpdateInventory(String rfid, String status, int quantity) {
        Inventory inventory = new Inventory(status, quantity);
        mDatabase.child("inventory").child(rfid).setValue(inventory);
    }

    // Read Inventory
    public void readInventory(String rfid, final InventoryCallback callback) {
        mDatabase.child("inventory").child(rfid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Inventory inventory = task.getResult().getValue(Inventory.class);
                callback.onInventoryReceived(inventory);
            } else {
                callback.onError(task.getException());
            }
        });
    }

    // Delete Inventory
    public void deleteInventory(String rfid) {
        mDatabase.child("inventory").child(rfid).removeValue();
    }

    // Interface for callback methods
    public interface InventoryCallback {
        void onInventoryReceived(Inventory inventory);
        void onError(Exception e);
    }
}