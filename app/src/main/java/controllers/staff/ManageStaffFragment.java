package controllers.staff;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.superhoodcleaning.R;

import java.util.ArrayList;
import java.util.List;

import models.Staff;

public class ManageStaffFragment extends Fragment {

    private List<Staff>staffList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_manage_staff,container,false);
        staffList = getSampleData();
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        Staff staff = new Staff(staffList);
        //recyclerView.setAdapter(staff);
        // Inflate the layout for this fragment
        return rootView;
    }
    private List<Staff> getSampleData() {
        List<Staff> dataList = new ArrayList<>();

        // Add sample data
        dataList.add(new Staff("John","Doe", "john@example.com","123456789", "https://iheartcraftythings.com/wp-content/uploads/2021/04/How-to-Draw-Cartoon-Face-%E2%80%93-Featured-Image.jpg"));
        dataList.add(new Staff("Jane", "Smith", "jane@example.com", "987654321", "https://iheartcraftythings.com/wp-content/uploads/2021/04/How-to-Draw-Cartoon-Face-%E2%80%93-Featured-Image.jpg"));
        dataList.add(new Staff("Bob", "Johnson", "bob@example.com", "555555555", "https://iheartcraftythings.com/wp-content/uploads/2021/04/How-to-Draw-Cartoon-Face-%E2%80%93-Featured-Image.jpg"));
        return dataList;
    }
}