package controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.superhoodcleaning.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;

import controllers.customer.ManageCustomerFragment;
import controllers.customer.ModifyCustomerFragment;
import controllers.customer.NewCustomerFragment;
import controllers.customer.ToScheduleCustomerFragment;
import controllers.staff.ManageStaffFragment;
import controllers.staff.ModifyStaffFragment;
import controllers.staff.NewStaffFragment;
import services.IAddButton;

public class TabBarActivity extends AppCompatActivity {
    TextView tvTitle;
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
            ManageCustomerFragment manageCustomerFragment = new ManageCustomerFragment();
            NewCustomerFragment newCustomerFragment = new NewCustomerFragment();
            ModifyCustomerFragment modifyCustomerFragment = new ModifyCustomerFragment();

            ManageStaffFragment manageStaffFragment = new ManageStaffFragment();
            NewStaffFragment newStaffFragment = new NewStaffFragment();
            ModifyStaffFragment modifyStaffFragment = new ModifyStaffFragment();

            ToScheduleCustomerFragment toScheduleCustomerFragment = new ToScheduleCustomerFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, manageCustomerFragment)
                    .commit();

            bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.iCustomers){

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, manageCustomerFragment)
                                .commit();
                    } else if (item.getItemId() == R.id.iStaff) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, manageStaffFragment)
                                .commit();
                    } else if (item.getItemId() == R.id.iSchedule) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, toScheduleCustomerFragment)
                                .commit();
                    } else if(item.getItemId() == R.id.iCalender){

                    }

                    return true;
                }
            });

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    if (currentFragment instanceof IAddButton) {
                        ((IAddButton) currentFragment).addButton();
                    }else if(currentFragment instanceof ManageCustomerFragment){
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, newCustomerFragment)
                                .commit();
                    } else if(currentFragment instanceof ManageStaffFragment){
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, newStaffFragment)
                                .commit();
                    }
                }
            });

        }
    }

//
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