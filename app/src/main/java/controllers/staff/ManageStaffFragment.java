package controllers.staff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.superhoodcleaning.R;

import java.util.ArrayList;
import java.util.List;

import models.Staff;
import services.GridAdapter;

public class ManageStaffFragment extends Fragment {

    private List<Staff> staffList;

    String names[] = {"Sizar Haj Hasan", "Truc Dang Quynh", "Ajinkya Bhintade", "Haitham Elkalmoushy"};
    String imgURL[] = {"https://firebasestorage.googleapis.com/v0/b/superhoodcleaningfirebase.appspot.com/o/images%2F00995e80-a49e-4a4d-9dfc-bd0f1de243f6?alt=media&token=1c11968f-3198-4a28-b3ca-ba1fcdf2fe5a",
    "https://firebasestorage.googleapis.com/v0/b/superhoodcleaningfirebase.appspot.com/o/images%2F00995e80-a49e-4a4d-9dfc-bd0f1de243f6?alt=media&token=1c11968f-3198-4a28-b3ca-ba1fcdf2fe5a",
    "https://firebasestorage.googleapis.com/v0/b/superhoodcleaningfirebase.appspot.com/o/images%2Fhaitham.png?alt=media&token=b634fbac-2269-47a9-8b9a-60becbf4feac",
    "https://firebasestorage.googleapis.com/v0/b/superhoodcleaningfirebase.appspot.com/o/images%2Fimage_2.png?alt=media&token=70e8f9c3-586c-4e54-bce0-ea25a2913ff2"};
    GridView gridView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manage_staff,container,false);
//        staffList = getSampleData();
//        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
//        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(),2);
//        recyclerView.setLayoutManager(layoutManager);
//        Staff staff = new Staff(staffList);
        //recyclerView.setAdapter(staff);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = view.findViewById(R.id.gvStaff);
        gridView.setAdapter(new GridAdapter(names, imgURL,requireContext()));
    }

//
//    private List<Staff> getSampleData() {
//        List<Staff> dataList = new ArrayList<>();
//
//        // Add sample data
//        dataList.add(new Staff("John","Doe", "john@example.com","123456789", "https://iheartcraftythings.com/wp-content/uploads/2021/04/How-to-Draw-Cartoon-Face-%E2%80%93-Featured-Image.jpg"));
//        dataList.add(new Staff("Jane", "Smith", "jane@example.com", "987654321", "https://iheartcraftythings.com/wp-content/uploads/2021/04/How-to-Draw-Cartoon-Face-%E2%80%93-Featured-Image.jpg"));
//        dataList.add(new Staff("Bob", "Johnson", "bob@example.com", "555555555", "https://iheartcraftythings.com/wp-content/uploads/2021/04/How-to-Draw-Cartoon-Face-%E2%80%93-Featured-Image.jpg"));
//        return dataList;
//    }
}