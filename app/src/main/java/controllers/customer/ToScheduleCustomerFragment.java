package controllers.customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import controllers.appointment.NewAppointmentFragment;
import models.Customer;
import services.CustomerAdapter;
import services.FirebaseConnection;
import services.ScheduleAdapter;

import controllers.TabBarActivity;


public class ToScheduleCustomerFragment extends Fragment {

    List<Customer> items;
    ScheduleAdapter adapter;
    DatabaseReference dbRef;
    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);

        fab.hide();
        bottomAppBar.performShow(); // Request layout pass

    }

    public static ToScheduleCustomerFragment newInstance(){ return  new ToScheduleCustomerFragment(); }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((TabBarActivity)getActivity()).tvTitle.setText("Pending Customers");
        items = new ArrayList<>();
        adapter = new ScheduleAdapter(getActivity(), items); // Use getActivity() for context

        dbRef = FirebaseConnection.getDatabaseRef();
        // Find your ListView
        fetchCustomers();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_schedule_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.lvCustomerSchedule);

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
            ((TabBarActivity) getActivity()).replaceFragmentWith(new NewAppointmentFragment(), itemCustomer);
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
}