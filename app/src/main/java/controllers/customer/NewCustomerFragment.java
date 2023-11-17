package controllers.customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.superhoodcleaning.R;
import com.google.firebase.database.DatabaseReference;
import models.Address;
import models.Customer;
import services.FirebaseConnection;

public class NewCustomerFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //btnAdd();
        return inflater.inflate(R.layout.fragment_new_customer, container, false);
    }




    public void addCustomer(String name, String manager, Address address, String phone) {
        try {
            Customer customer = new Customer(name, manager, address, phone);
            //databaseReference = firebaseServices.provideDatabaseReference();

            //Add customer to the database
            DatabaseReference dbRef = FirebaseConnection.getDatabaseRef();
            dbRef.child("customers").push().setValue(customer)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Customer added successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to add customer!", Toast.LENGTH_SHORT).show();
                    });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        };
    }





    public void btnAdd() {
        //Customer Info
        String name = "Chipotle Mexican Grill";
        String manager = "Sizar Haj Hasan";
        String street = "200 Consumer Square Ste 208";
        String city = "Plattsburgh";
        String state = "NY";
        String zip = "12901";
        String phone = "4385551010";
        Address address = new Address(street, city, state, zip);
        addCustomer(name, manager, address, phone);
    }
}