package services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.superhoodcleaning.R;

import java.util.List;

import models.Appointment;
import models.Customer;

public class CustomerAdapter extends ArrayAdapter<Customer> {

    private List<Appointment> appoint;

    public CustomerAdapter(@NonNull Context context, @NonNull List<Customer> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Customer item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }

        // Lookup views in the layout
        TextView customerName = convertView.findViewById(R.id.txtCustomerName);
        TextView managerName = convertView.findViewById(R.id.txtManagerName);
        TextView appointment = convertView.findViewById(R.id.txtAppointment);
        TextView address1 = convertView.findViewById(R.id.txtAddress1);
        TextView address2 = convertView.findViewById(R.id.txtAddress2);

        // Populate the data into the template view using the data object
        customerName.setText(item.getName());
        managerName.setText(item.getManager());
        address1.setText(item.getAddress().getStreet());
        address2.setText(String.format("%s, %s %s", item.getAddress().getCity(), item.getAddress().getState(), item.getAddress().getZip()));



        // Return the completed view
        return convertView;
    }
}
