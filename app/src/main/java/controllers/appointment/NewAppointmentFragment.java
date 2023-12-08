package controllers.appointment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import com.example.superhoodcleaning.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import controllers.TabBarActivity;
import controllers.staff.ModifyStaffFragment;
import models.Appointment;
import models.Staff;
import services.FirebaseConnection;
import services.StaffAdapter;
import services.StaffListAdapter;

public class NewAppointmentFragment extends Fragment {

    ArrayList<Staff> staffs;
    StaffListAdapter staffListAdapter;
    private DatabaseReference databaseReference;
    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //btnAdd();
        return inflater.inflate(R.layout.fragment_new_appointment, container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        View searchBar = getActivity().findViewById(R.id.search_bar);
        if (searchBar != null) {
            searchBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        View searchBar = getActivity().findViewById(R.id.search_bar);
        if (searchBar != null) {
            searchBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button datePicker = view.findViewById(R.id.btnDatePicker);
        Button startTime = view.findViewById(R.id.btnStartTime);
        Button endTime = view.findViewById(R.id.btnEndTime);

        datePicker.setOnClickListener(new setOnClickListener());
        startTime.setOnClickListener(new setOnClickListener());
        endTime.setOnClickListener(new setOnClickListener());

        staffs = new ArrayList<Staff>();
        staffListAdapter = new StaffListAdapter(getActivity(), staffs);
        databaseReference = FirebaseConnection.getDatabaseRef();
        fetchStaffs();

        ListView listView = view.findViewById(R.id.lvStaffList);
        listView.setAdapter(staffListAdapter);

    }

    private void openDatePicker(final Button button) {
        // Existing code...
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Setting the button text to the selected date
                button.setText(String.format("%d.%02d.%02d", year, month + 1, day)); // month + 1 because month index starts at 0
            }
        }, 2023, 01, 20);
        datePickerDialog.show();
    }

    private void openTimePicker(final Button button) {
        // Existing code...
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                // Setting the button text to the selected time
                button.setText(String.format("%02d:%02d", hour, minute));
            }
        }, 15, 30, false);
        timePickerDialog.show();
    }

//    private void openDatePicker(){
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme , new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//
//                //Showing the picked value in the textView
//                datePicker.setText(String.valueOf(year)+ "."+String.valueOf(month)+ "."+String.valueOf(day));
//
//            }
//        }, 2023, 01, 20);
//
//        datePickerDialog.show();
//    }


//    private void openTimePicker(){
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
//
//
//                //Showing the picked value in the textView
//                textView.setText(String.valueOf(hour)+ ":"+String.valueOf(minute));
//
//            }
//        }, 15, 30, false);
//
//        timePickerDialog.show();
//    }

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
    private void fetchStaffs() {
        databaseReference.child("staffs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Staff staff = snapshot.getValue(Staff.class);
                    staff.setStaffId(snapshot.getKey());
                    staffs.add(staff);
                }
                staffListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private class setOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnDatePicker) {
                openDatePicker((Button) v);
            } else if (id == R.id.btnStartTime || id == R.id.btnEndTime) {
                openTimePicker((Button) v);
            }
        }
    }
}