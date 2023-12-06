package models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superhoodcleaning.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Staff {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String photoUrl;
    private List<Staff> staffList;

    public Staff(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public Staff(String firstName, String lastName, String email, String phone, String photoUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.photoUrl = photoUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhoto(String photoUrl) {
        this.photoUrl = photoUrl;
    }
/*
    @NonNull
    @Override
    public Staff.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Staff.ViewHolder holder, int position) {
        Staff staff = staffList.get(position);
        Picasso.get().load(staff.getPhotoUrl()).into(holder.imageView);
        holder.textView.setText(staff.getFirstName()+staff.getLastName());
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }*/
}
