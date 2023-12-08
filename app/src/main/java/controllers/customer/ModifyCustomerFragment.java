package controllers.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.superhoodcleaning.R;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import models.Address;
import models.Customer;
import services.FirebaseConnection;

public class ModifyCustomerFragment extends Fragment {
    private EditText edNameModifyCustomer, edManagerModifyCustomer, edStreetModifyCustomer,
                    edCityModifyCustomer, edZipModifyCustomer, edPhoneModifyCustomer ;
    private Button viewAppointment, btnUpdate, btnDelete;

    Customer customerToUpdate;
    public static ModifyCustomerFragment newInstance(){
        return new ModifyCustomerFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_customer, container, false);

        Serializable data = getArguments().getSerializable("dataKey");
        if (data instanceof Customer) {
            Customer customer = (Customer) data;
            Toast.makeText(getContext(), customer.getCustomerId(), Toast.LENGTH_SHORT).show();
            customerToUpdate = customer ;
        }
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        edNameModifyCustomer = view.findViewById(R.id.edNameModifyCustomer);
        edManagerModifyCustomer = view.findViewById(R.id.edManagerModifyCustomer);
        edStreetModifyCustomer = view.findViewById(R.id.edStreetModifyCustomer);
        edCityModifyCustomer = view.findViewById(R.id.edCityModifyCustomer);
        edZipModifyCustomer = view.findViewById(R.id.edZipModifyCustomer);
        edPhoneModifyCustomer = view.findViewById(R.id.edPhoneModifyCustomer);
        viewAppointment = view.findViewById(R.id.viewAppointment);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);

        Toast.makeText(getContext(), "The customer name is " + customerToUpdate.getName(), Toast.LENGTH_SHORT).show();
        //Set customer info
        edNameModifyCustomer.setText(customerToUpdate.getName());
        edManagerModifyCustomer.setText(customerToUpdate.getManager());
        edPhoneModifyCustomer.setText(customerToUpdate.getPhone());

        //Set address info
        Address address = customerToUpdate.getAddress();
        edStreetModifyCustomer.setText(address.getStreet());
        edCityModifyCustomer.setText(address.getCity());
        edZipModifyCustomer.setText(address.getZip());


    }

    private void btnUpdate() {
        String name = "Chipotle Mexican Grill";
        String manager = "Truc Quynh Dang";
        String street = "200 Consumer Square Ste 208";
        String city = "Plattsburgh";
        String state = "NY";
        String zip = "12901";
        String phone = "4383375346";
        Address address = new Address(street, city, state, zip);
        Customer customer = new Customer(name, manager, address, phone);

        updateCustomer("-NjP_URyryhUQhUPsP8r", customer);

    }

    private void btnDelete() {
        deleteCustomer("-NjP_URyryhUQhUPsP8r");
    }

    //--------------------------------------------------------------------TESTING WITH MOCK DATA
    private void updateCustomer(String customerId, Customer customer) {
        try {
            DatabaseReference dbRef = FirebaseConnection.getDatabaseRef().child("customers").child(customerId);
            dbRef.setValue(customer)
                    .addOnSuccessListener(aVoid -> {
                        // Handle success
                        Toast.makeText(getContext(), "Customer updated successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(getContext(), "Failed to update customer!", Toast.LENGTH_SHORT).show();
                    });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteCustomer(String customerId) {
        try {
            DatabaseReference dbRef = FirebaseConnection.getDatabaseRef().child("customers").child(customerId);
            dbRef.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Handle success
                        Toast.makeText(getContext(), "Customer deleted successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(getContext(), "Failed to delete customer!", Toast.LENGTH_SHORT).show();
                    });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCustomer(Customer customer) {

    }

}