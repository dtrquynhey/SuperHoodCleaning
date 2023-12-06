package controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.superhoodcleaning.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import controllers.customer.ManageCustomerFragment;
import controllers.customer.ModifyCustomerFragment;
import controllers.customer.NewCustomerFragment;
import controllers.staff.ManageStaffFragment;
import controllers.staff.ModifyStaffFragment;
import controllers.staff.NewStaffFragment;
import services.IAddButton;

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
            ManageCustomerFragment manageCustomerFragment = new ManageCustomerFragment();
            NewCustomerFragment newCustomerFragment = new NewCustomerFragment();
            ModifyCustomerFragment modifyCustomerFragment = new ModifyCustomerFragment();
            ManageStaffFragment manageStaffFragment = new ManageStaffFragment();
            NewStaffFragment newStaffFragment = new NewStaffFragment();
            ModifyStaffFragment modifyStaffFragment = new ModifyStaffFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, manageStaffFragment)
                    .commit();


            Log.d("MyAppTag", "Before Debug message");
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
}