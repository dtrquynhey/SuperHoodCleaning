package services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.superhoodcleaning.R;

import java.util.List;

import models.Staff;

public class StaffListAdapter extends ArrayAdapter<Staff> {
    public StaffListAdapter(@NonNull Context context, @NonNull List<Staff> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Staff staff = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.staff_list_layout, parent, false);

            ImageView imageView = convertView.findViewById(R.id.ivStaffImage);
            TextView textView = convertView.findViewById(R.id.tvStaffName1);

            if (!staff.getPhotoUrl().isEmpty()) {
                Glide.with(convertView).load(staff.getPhotoUrl()).transform(new CircleCrop()).into(imageView);
            }  else {
                Glide.with(convertView).load(R.drawable.no_one).transform(new CircleCrop()).into(imageView);
            }

            textView.setText(String.format("%s %s", staff.getFirstName(), staff.getLastName()));
        }
        return convertView;
    }
}
