package controllers.appointment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.superhoodcleaning.R;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import models.Appointment;
import services.FirebaseConnection;

public class ModifyAppointmentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //btnUpdate();
        //btnDelete();
        return inflater.inflate(R.layout.fragment_modify_appointment, container, false);
    }

    private void btnUpdate() {
        String appointmentId = "-NjTsA7dEjALxq2GBmpe";
        String customerId = "-NjPNMm_cx-cMErFFPU2";
        // For the current date
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate.getTime());

        // For parsing and formatting a specific time
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());

        // For setting a specific time, let's say 11:30 AM
        Calendar startTimeCalendar = Calendar.getInstance();
        startTimeCalendar.set(Calendar.HOUR_OF_DAY, 14);
        startTimeCalendar.set(Calendar.MINUTE, 30);
        String formattedStartTime = timeFormat.format(startTimeCalendar.getTime());

        // For setting a specific time, let's say 2:30 PM
        Calendar endTimeCalendar = Calendar.getInstance();
        endTimeCalendar.set(Calendar.HOUR_OF_DAY, 17);
        endTimeCalendar.set(Calendar.MINUTE, 30);
        String formattedEndTime = timeFormat.format(endTimeCalendar.getTime());



        ArrayList<String> staffs = new ArrayList<>();
        staffs.add("-NjQI4aGWDQxIYNQxTul");
        staffs.add("-NjTmv4QsJYYZdUE_MNp");
        Appointment appointment = new Appointment(customerId, formattedDate, formattedStartTime, formattedEndTime, staffs);
        updateAppointment(appointmentId, appointment);

    }

    private void btnDelete() {
        String appointmentId = "-NjTzP-3fASiHg1ZtzyH";
        deleteAppointment(appointmentId);
    }

    //--------------------------------------------------------------------TESTING WITH MOCK DATA
    private void updateAppointment(String appointmentId, Appointment appointment) {
        try {
            DatabaseReference dbRef = FirebaseConnection.getDatabaseRef().child("appointments").child(appointmentId);
            dbRef.setValue(appointment)
                    .addOnSuccessListener(aVoid -> {
                        // Handle success
                        Toast.makeText(getContext(), "Appointment updated successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(getContext(), "Failed to update appointment!", Toast.LENGTH_SHORT).show();
                    });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteAppointment(String appointmentId) {
        try {
            DatabaseReference dbRef = FirebaseConnection.getDatabaseRef().child("appointments").child(appointmentId);
            dbRef.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        // Handle success
                        Toast.makeText(getContext(), "Appointment deleted successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(getContext(), "Failed to delete appointment!", Toast.LENGTH_SHORT).show();
                    });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadAppointment(Appointment appointment) {
        //TODO: loadAppointment()
    }

}