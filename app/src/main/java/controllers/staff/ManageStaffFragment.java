package controllers.staff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.superhoodcleaning.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import models.Staff;
import services.FirebaseConnection;

public class ManageStaffFragment extends Fragment {

    private ArrayList<Staff> staffs;
    private DatabaseReference databaseReference = FirebaseConnection.getDatabaseRef();
    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_manage_staff,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = view.findViewById(R.id.gvStaff);
        //gridView.setAdapter(new GridAdapter(names, imgURL,requireContext()));
    }
}