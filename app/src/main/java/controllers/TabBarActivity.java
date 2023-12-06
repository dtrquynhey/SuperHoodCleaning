package controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.superhoodcleaning.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import controllers.customer.ManageCustomerFragment;
import controllers.customer.ModifyCustomerFragment;
import controllers.customer.NewCustomerFragment;
import controllers.staff.ModifyStaffFragment;
import controllers.staff.NewStaffFragment;
import models.Customer;
import services.CustomerAdapter;

public class TabBarActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_bar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
//        floatingActionButton.setVisibility(View.INVISIBLE);



        // Check that the activity is using the layout version with the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of the fragment you want to start
            NewCustomerFragment newCustomerFragment = new NewCustomerFragment();
            ModifyCustomerFragment modifyCustomerFragment = new ModifyCustomerFragment();
            NewStaffFragment newStaffFragment = new NewStaffFragment();
            ModifyStaffFragment modifyStaffFragment = new ModifyStaffFragment();
            ManageCustomerFragment manageCustomerFragment = new ManageCustomerFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, manageCustomerFragment)
                    .commit();
        }
    }

//    private void openFragment(int position) {
//        ModifyCustomerFragment fragment = ModifyCustomerFragment.newInstance();
//
//        Bundle args = new Bundle();
//        args.putInt("position", position); // Example of passing the clicked position
//        fragment.setArguments(args);
//
//        // Perform the fragment transaction
//        FragmentManager fragmentManager = getSupportFragmentManager(); // Use getFragmentManager() in older APIs
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//        transaction.replace(R.id.fragment_container, fragment); // Replace whatever is in the fragment_container view with this fragment
//        transaction.addToBackStack(null); // and add the transaction to the back stack so the user can navigate back
//
//        transaction.commit(); // Commit the transaction
//    }
    public void replaceFragmentWith(Fragment fragment, Serializable data) {
        // Create a new Bundle to hold the data
        Bundle args = new Bundle();

        // Put the data into the Bundle. Use a key that both sending and receiving fragments understand.
        args.putSerializable("dataKey", data);

        // Set the arguments for the fragment
        fragment.setArguments(args);

        // Perform the fragment transaction to replace the current fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null) // Optional, if you want to add the transaction to the back stack
                .commit();

    }
}