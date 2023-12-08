package services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.superhoodcleaning.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import models.Staff;

public class StaffAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Staff> staffs;

    public StaffAdapter(Context context, ArrayList<Staff> staffs) {
        this.context = context;
        this.staffs = staffs;
    }

    View view;
    LayoutInflater inflater;

    @Override
    public int getCount() {
        return staffs.size();
    }

    @Override
    public Object getItem(int position) {
        return staffs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Staff staff = (Staff) getItem(position);
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            view = new View(context);
            view = inflater.inflate(R.layout.grid_item_layout, parent, false);

            ImageView imageView = view.findViewById(R.id.ivAvatar);
            TextView textView = view.findViewById(R.id.tvStaffName);

            Glide.with(context).load(staff.getPhotoUrl()).transform(new CircleCrop()).into(imageView);
            textView.setText(String.format("%s %s", staff.getFirstName(), staff.getLastName()));
            int radius = 50;
//            Glide.with(context)
//                    .load(imgURL[position])
//                    .transform(new CircleCrop()) // This will make the image circular
//                    .into(imageView);
//            textView.setText(staffName[position]);
        }
        return view;
    }
}
