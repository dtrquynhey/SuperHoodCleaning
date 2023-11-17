package controllers.staff;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.superhoodcleaning.R;
import com.google.firebase.database.DatabaseReference;
import models.Staff;
import services.FirebaseConnection;

public class ModifyStaffFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // btnUpdate();
        // btnDelete();
        return inflater.inflate(R.layout.fragment_modify_staff_fragement, container, false);
    }

    private void updateStaff(String staffId, Staff staff) {
        try {
            DatabaseReference dbRef = FirebaseConnection.getDatabaseRef().child("staffs").child(staffId);
            dbRef.setValue(staff)
                    .addOnSuccessListener(aVoid -> {
                        // Handle success
                        Toast.makeText(getContext(), "Staff updated successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(getContext(), "Failed to update staff!", Toast.LENGTH_SHORT).show();
                    });

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteStaff(String staffId) {
        try {
            DatabaseReference dbRef = FirebaseConnection.getDatabaseRef().child("staffs").child(staffId);
            dbRef.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Handle success
                        Toast.makeText(getContext(), "Staff deleted successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(getContext(), "Failed to delete staff!", Toast.LENGTH_SHORT).show();
                    });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    private void loadStaff(Staff staff) {
        //TODO: loadStaff()
    }
    
    //TODO: btnAdd()
    
    
    
    
    //-------------------------------------------------TESTING WITH MOCK DATA
    private void btnUpdate() {
        String firstName = "Ajinkya";
        String lastName = "Bhintade";
        String email = "ajinkya@gmail.com";
        String phone = "4389991010";
        String photoUrl = "https://firebasestorage.googleapis.com/v0/b/superhoodcleaningfirebase.appspot.com/o/images%2Fimage_2.png?alt=media&token=70e8f9c3-586c-4e54-bce0-ea25a2913ff2";

        Staff staff = new Staff(firstName, lastName, email, phone, photoUrl);
        updateStaff("-NjQI4aGWDQxIYNQxTul", staff);
    }

    private void btnDelete() {
        deleteStaff("100");
    }
}