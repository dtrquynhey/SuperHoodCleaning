package controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.superhoodcleaning.R;

import controllers.appointment.ModifyAppointmentFragment;
import controllers.appointment.NewAppointmentFragment;
import controllers.customer.ModifyCustomerFragment;
import controllers.customer.NewCustomerFragment;
import controllers.staff.ModifyStaffFragment;
import controllers.staff.NewStaffFragment;

public class TabBarActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_bar);

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
            NewAppointmentFragment newAppointmentFragment = new NewAppointmentFragment();
            ModifyAppointmentFragment modifyAppointmentFragment = new ModifyAppointmentFragment();
            
            
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, modifyAppointmentFragment)
                    .commit();
        }
    }
}