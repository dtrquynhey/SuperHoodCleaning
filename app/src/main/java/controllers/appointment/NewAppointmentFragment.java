package controllers.appointment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.superhoodcleaning.R;
import com.google.firebase.database.DatabaseReference;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import models.Appointment;
import services.FirebaseConnection;

public class NewAppointmentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                                 //AndroidThreeTen.init(this);
        btnAdd();
        return inflater.inflate(R.layout.fragment_new_appointment, container, false);
    }

     private void btnAdd() {
            // For LocalDate
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy");
//            LocalDate selectedDate = LocalDate.parse("Dec 19 2023", dateFormatter);
//
//            // For LocalTime
//            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
//            LocalTime startTime = LocalTime.parse("11:30 AM", timeFormatter);
//            LocalTime endTime = LocalTime.parse("2:30 PM", timeFormatter);
//            addStaff(firstName, lastName, email, phone, photoUrl);
        }

        private void addAppointment(String customerId, LocalDate date, LocalTime startTime, LocalTime endTime, ArrayList<String> staffs) {
            try {
                Appointment appointment = new Appointment(customerId, date, startTime, endTime, staffs);
                DatabaseReference dbRef = FirebaseConnection.getDatabaseRef();
                dbRef.child("appointments").push().setValue(appointment)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getContext(), "Appointment added successfully!", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(getContext(), "Failed to add appointment!", Toast.LENGTH_SHORT).show();
                                    });
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
}