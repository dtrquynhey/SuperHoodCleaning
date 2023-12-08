package controllers.customer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.superhoodcleaning.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import controllers.TabBarActivity;
import models.Address;
import models.Customer;
import services.FirebaseConnection;

public class ModifyCustomerFragment extends Fragment {
     EditText edNameModifyCustomer, edManagerModifyCustomer, edStreetModifyCustomer,
                    edCityModifyCustomer, edZipModifyCustomer, edPhoneModifyCustomer ;
    Spinner spState;
     Button viewAppointment, btnUpdate, btnDelete;

    Customer customerToUpdate;
    public static ModifyCustomerFragment newInstance(){
        return new ModifyCustomerFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((TabBarActivity)getActivity()).tvTitle.setText("Modify Customer");
        View view = inflater.inflate(R.layout.fragment_modify_customer, container, false);
        Serializable data = getArguments().getSerializable("dataKey");
        if (data instanceof Customer) {
            Customer customer = (Customer) data;
            customerToUpdate = customer ;
        }
        initialize(view);
        return view;
    }
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);

        fab.hide();
        bottomAppBar.performShow(); // Request layout pass

    }
    private void initialize(View view) {
        edNameModifyCustomer = view.findViewById(R.id.edNameModifyCustomer);
        edManagerModifyCustomer = view.findViewById(R.id.edManagerModifyCustomer);
        edStreetModifyCustomer = view.findViewById(R.id.edStreetModifyCustomer);
        edCityModifyCustomer = view.findViewById(R.id.edCityModifyCustomer);
        spState = view.findViewById(R.id.spStateModifyCustomer);
        edZipModifyCustomer = view.findViewById(R.id.edZipModifyCustomer);
        edPhoneModifyCustomer = view.findViewById(R.id.edPhoneModifyCustomer);
        viewAppointment = view.findViewById(R.id.viewAppointment);

        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnUpdateClicked();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });

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


    private void btnUpdateClicked() {
        String name = edNameModifyCustomer.getText().toString();
        String manager = edManagerModifyCustomer.getText().toString();
        String street = edStreetModifyCustomer.getText().toString();
        String city = edCityModifyCustomer.getText().toString();
        String state = spState.getSelectedItem().toString();
        String zip = edZipModifyCustomer.getText().toString();
        String phone = edPhoneModifyCustomer.getText().toString();
        Address address = new Address(street, city, state, zip);
        Customer customer = new Customer(name, manager, address, phone);

        updateCustomer(customerToUpdate.getCustomerId(), customer);
        backToManageCustomers();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Customer");
        builder.setMessage("Are you sure you want to delete " + customerToUpdate.getName() + " ?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked the "Delete" button, so delete the item.
                deleteCustomer(customerToUpdate.getCustomerId());
                backToManageCustomers();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


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

    private void backToManageCustomers() {
        if (getActivity() instanceof TabBarActivity) {
            ((TabBarActivity) getActivity()).replaceFragmentWith(new ManageCustomerFragment(), null);
        }
    }


}