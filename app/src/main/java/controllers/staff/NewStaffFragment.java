package controllers.staff;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.superhoodcleaning.R;
import com.google.firebase.database.DatabaseReference;

import models.Staff;
import services.FirebaseConnection;

public class NewStaffFragment extends Fragment {

    ActivityResultLauncher activityResultLauncher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        btnAdd();
        initialize();
        return inflater.inflate(R.layout.fragment_new_staff, container, false);
    }


    private void initialize() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
//                         getPhoto(result);
                    }
                }
        );
    }
    private void btnAdd() {
        String firstName = "Haitham";
        String lastName = "Elkalmoushy";
        String email = "haitham@gmail.com";
        String phone = "4389991010";
        String photoUrl = "https://firebasestorage.googleapis.com/v0/b/superhoodcleaningfirebase.appspot.com/o/images%2Fhaitham.png?alt=media&token=b634fbac-2269-47a9-8b9a-60becbf4feac";

        addStaff(firstName, lastName, email, phone, photoUrl);
    }

    private void addStaff(String firstName, String lastName, String email, String phone, String photoUrl) {
        try {
            Staff staff = new Staff(firstName, lastName, email, phone, photoUrl);
            DatabaseReference dbRef = FirebaseConnection.getDatabaseRef();
            dbRef.child("staffs").push().setValue(staff)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getContext(), "Staff added successfully!", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getContext(), "Failed to add staff!", Toast.LENGTH_SHORT).show();
                                });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void UploadPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent,"select image"));
    }

}