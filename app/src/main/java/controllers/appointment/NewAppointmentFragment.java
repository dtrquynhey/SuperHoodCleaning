package controllers.appointment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.superhoodcleaning.R;
import com.google.firebase.database.DatabaseReference;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import models.Appointment;
import models.Staff;
import services.FirebaseConnection;

public class NewAppointmentFragment extends Fragment {

    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //btnAdd();
        return inflater.inflate(R.layout.fragment_new_appointment, container, false);
    }

    private void btnAdd() {
        try {
            String customerId = "-NjPNMm_cx-cMErFFPU2";
            // For the current date
            Calendar currentDate = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(currentDate.getTime());

            // For parsing and formatting a specific time
            SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());

            // For setting a specific time, let's say 11:30 AM
            Calendar startTimeCalendar = Calendar.getInstance();
            startTimeCalendar.set(Calendar.HOUR_OF_DAY, 11);
            startTimeCalendar.set(Calendar.MINUTE, 0);
            String formattedStartTime = timeFormat.format(startTimeCalendar.getTime());

            // For setting a specific time, let's say 2:30 PM
            Calendar endTimeCalendar = Calendar.getInstance();
            endTimeCalendar.set(Calendar.HOUR_OF_DAY, 14);
            endTimeCalendar.set(Calendar.MINUTE, 0);
            String formattedEndTime = timeFormat.format(endTimeCalendar.getTime());

//            LocalDate date = LocalDate.of(2023, 12, 19);
//            LocalTime startTime = LocalTime.of(11, 30);
//            LocalTime endTime = LocalTime.of(14, 30);

            ArrayList<String> staffs = new ArrayList<>();
            staffs.add("-NjQI4aGWDQxIYNQxTul");
            staffs.add("-NjTmv4QsJYYZdUE_MNp");

            addAppointment(customerId, formattedDate , formattedStartTime, formattedEndTime, staffs);
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addAppointment(String customerId, String date, String startTime, String endTime, ArrayList<String> staffs) {
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