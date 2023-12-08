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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import models.Staff;
import services.FirebaseConnection;
import services.StaffAdapter;

public class ManageStaffFragment extends Fragment {

    ArrayList<Staff> staffs;
    StaffAdapter staffAdapter;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        staffs = new ArrayList<Staff>();
        staffAdapter = new StaffAdapter(getActivity(), staffs);
        databaseReference = FirebaseConnection.getDatabaseRef();
        fetchStaffs();
        return inflater.inflate(R.layout.fragment_manage_staff,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridView = view.findViewById(R.id.gvStaff);
        gridView.setAdapter(staffAdapter);
    }

    private void fetchStaffs() {
        databaseReference.child("staffs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Staff staff = snapshot.getValue(Staff.class);
                    staffs.add(staff);
                }
                staffAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}