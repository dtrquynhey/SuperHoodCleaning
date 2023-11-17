package controllers.customer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.superhoodcleaning.R;


public class ManageCustomerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        btnList();
        return inflater.inflate(R.layout.fragment_customer_management, container, false);
    }

    private void btnList() {
        listCustomers();
    }

    private void listCustomers() {

    }

}