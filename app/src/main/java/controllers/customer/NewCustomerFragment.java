package controllers.customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.superhoodcleaning.R;
import com.google.firebase.database.DatabaseReference;
import models.Address;
import models.Customer;
import services.FirebaseConnection;
import services.IAddButton;

public class NewCustomerFragment extends Fragment implements IAddButton {
    // Declare EditText member variables
    private EditText edNewCustomer,edManagerNewCustomer, edStreetNewCustomer, edStateNewCustomer,
                    edCityNewCustomer, edZipNewCustomer, edPhoneNewCustomer ;

    Spinner spinnerStates;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
    }
    private void initialize(View view) {
        edNewCustomer = view.findViewById(R.id.edNewCustomer);
        edManagerNewCustomer = view.findViewById(R.id.edManagerNewCustomer);
        edStreetNewCustomer = view.findViewById(R.id.edStreetNewCustomer);
        edCityNewCustomer = view.findViewById(R.id.edCityNewCustomer);
        edZipNewCustomer = view.findViewById(R.id.edZipNewCustomer);
        spinnerStates = view.findViewById(R.id.spinnerStateNewCustomer);
        edPhoneNewCustomer = view.findViewById(R.id.edPhoneNewCustomer);
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


    @Override
    public void addButton() {
        Log.d("MyAppTag", "btn clicked");
        String customerName = edNewCustomer.getText().toString().trim();
        String managerName =  edManagerNewCustomer.getText().toString().trim();
        String street = edStreetNewCustomer.getText().toString().trim();
        String city = edCityNewCustomer.getText().toString().trim();
        String selectedState = spinnerStates.getSelectedItem().toString();
        String zip = edZipNewCustomer.getText().toString().trim();
        String phone = edPhoneNewCustomer.getText().toString().trim();
        try {
            if (customerName.isEmpty() || managerName.isEmpty() || street.isEmpty() || city.isEmpty() || zip.isEmpty() || phone.isEmpty()){
                Toast.makeText(getContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                return;
            }
            Address address = new Address(street, city, selectedState,zip );
            addCustomer(customerName, managerName, address, phone );
            clearWidgets();

        } catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void clearWidgets() {
        edNewCustomer.setText(null);
        edManagerNewCustomer.setText(null);
        edStreetNewCustomer.setText(null);
        edCityNewCustomer.setText(null);
        spinnerStates.setSelection(0);
        edZipNewCustomer.setText(null);
        edPhoneNewCustomer.setText(null);
    }
}