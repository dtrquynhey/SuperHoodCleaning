package controllers.staff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.superhoodcleaning.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import controllers.TabBarActivity;
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
        ((TabBarActivity)getActivity()).tvTitle.setText("Staffs Management");
        return inflater.inflate(R.layout.fragment_manage_staff,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);

        fab.show();
        bottomAppBar.performShow(); // Request layout pass

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        staffs = new ArrayList<Staff>();
        staffAdapter = new StaffAdapter(getActivity(), staffs);
        databaseReference = FirebaseConnection.getDatabaseRef();
        fetchStaffs();
        GridView gridView = view.findViewById(R.id.gvStaff);
        gridView.setAdapter(staffAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openFragment(staffs.get(position));
            }
        });
    }

    private void openFragment(Staff staff) {
        if (getActivity() instanceof TabBarActivity) {
            Serializable itemStaff = staff;
            ((TabBarActivity) getActivity()).replaceFragmentWith(new ModifyStaffFragment(), itemStaff);
        }
    }

    private void fetchStaffs() {
        databaseReference.child("staffs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Staff staff = snapshot.getValue(Staff.class);
                    staff.setStaffId(snapshot.getKey());
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