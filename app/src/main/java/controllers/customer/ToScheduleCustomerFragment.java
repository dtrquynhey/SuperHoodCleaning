package controllers.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.superhoodcleaning.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import controllers.TabBarActivity;


public class ToScheduleCustomerFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);

        fab.hide();
        bottomAppBar.performShow(); // Request layout pass

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((TabBarActivity)getActivity()).tvTitle.setText("Pending Customers");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_schedule_customer, container, false);
    }
}