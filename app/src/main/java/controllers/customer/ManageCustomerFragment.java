package controllers.customer;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.superhoodcleaning.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import controllers.TabBarActivity;
import models.Customer;
import services.FirebaseConnection;
import services.CustomerAdapter;


public class ManageCustomerFragment extends Fragment {
    List<Customer> items;
    CustomerAdapter adapter;
    DatabaseReference dbRef;

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);

        fab.show();
        bottomAppBar.performShow(); // Request layout pass

    }
    public static ManageCustomerFragment newInstance(){
        return new ManageCustomerFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((TabBarActivity)getActivity()).tvTitle.setText("Customers Management");
        items = new ArrayList<>();
         adapter = new CustomerAdapter(getActivity(), items); // Use getActivity() for context

        dbRef = FirebaseConnection.getDatabaseRef();
        // Find your ListView
        fetchCustomers();

        return inflater.inflate(R.layout.fragment_manage_customer, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ListView listView = view.findViewById(R.id.lvCustomer);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);


        // Set up the onItemClickListener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openFragment(items.get(position));
            }
        });
    }
    private void openFragment(Customer customer) {
        // Implement the logic to replace the current fragment with another
        // This is typically done by interacting with the MainActivity
        if (getActivity() instanceof TabBarActivity) {
            Serializable itemCustomer = (Serializable)customer;
            ((TabBarActivity) getActivity()).replaceFragmentWith(new ModifyCustomerFragment(), itemCustomer);

        }
    }


    public void fetchCustomers() {
        dbRef.child("customers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Customer customer = snapshot.getValue(Customer.class);
                    customer.setCustomerId(snapshot.getKey());
                    items.add(customer);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void btnList() {
        listCustomers();
    }

    private void listCustomers() {

    }

}