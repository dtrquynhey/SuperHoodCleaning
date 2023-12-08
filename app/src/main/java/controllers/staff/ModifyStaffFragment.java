package controllers.staff;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.superhoodcleaning.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.UUID;

import controllers.TabBarActivity;
import models.Staff;
import services.FirebaseConnection;

public class ModifyStaffFragment extends Fragment implements OnSuccessListener, OnFailureListener, DialogInterface.OnClickListener {

    EditText edFirstNameModifyStaff, edLastNameModifyStaff, edEmailModifyStaff, edPhoneModifyStaff;
    Button btnUpload, btnRemove, btnUpdate, btnDelete;
    ImageView imPhotoModifyStaff;
    Staff staffToModify;


    ActivityResultLauncher activityResultLauncher;
    Uri photoPath;
    String uploadedPhotoUrl = null;
    StorageReference storageReference, imagesStorageRef;
    AlertDialog.Builder deleteConfirmAlertDialog;
    AlertDialog.Builder updateWithoutPhotoAlertDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((TabBarActivity)getActivity()).tvTitle.setText("Modify Staff");

        View view = inflater.inflate(R.layout.fragment_modify_staff, container, false);

        Serializable data = getArguments().getSerializable("dataKey");
        if (data instanceof Staff) {
            staffToModify = (Staff) data;
            initialize(view);
        }
        return view;
    }
    public void onResume() {
        super.onResume();
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        BottomAppBar bottomAppBar = getActivity().findViewById(R.id.bottomAppBar);

        fab.hide();
        bottomAppBar.performShow(); // Request layout pass

    }

    private void initialize(View view) {
        edFirstNameModifyStaff = view.findViewById(R.id.edFirstNameModifyStaff);
        edLastNameModifyStaff = view.findViewById(R.id.edLastNameModifyStaff);
        edEmailModifyStaff = view.findViewById(R.id.edEmailModifyStaff);
        edPhoneModifyStaff = view.findViewById(R.id.edPhoneModifyStaff);
        imPhotoModifyStaff = view.findViewById(R.id.imPhotoModifyStaff);

        btnUpload = view.findViewById(R.id.btnUpload);
        btnRemove = view.findViewById(R.id.btnRemove);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnDelete = view.findViewById(R.id.btnDelete);

        edFirstNameModifyStaff.setText(staffToModify.getFirstName());
        edLastNameModifyStaff.setText(staffToModify.getLastName());
        edEmailModifyStaff.setText(staffToModify.getEmail());
        edPhoneModifyStaff.setText(staffToModify.getPhone());

        String photoUrl = staffToModify.getPhotoUrl();

        if (photoUrl != null && !photoUrl.isEmpty()) {
            Glide.with(view).load(staffToModify.getPhotoUrl()).transform(new CircleCrop()).into(imPhotoModifyStaff);
        } else {
            imPhotoModifyStaff.setImageResource(R.drawable.no_one);
        }


        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("ActivityResultDebug", "onActivityResult called with result code: " + result.getResultCode());
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Log.d("ActivityResultDebug", result.toString());
                            Bitmap bitmap = convertPhotoResultToBitMap(result);
                            Glide.with(requireContext())
                                    .load(bitmap)
                                    .transform(new CircleCrop()) // This will make the image circular
                                    .into(imPhotoModifyStaff);
                            Toast.makeText(getContext(), "Profile picture uploaded!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("ActivityResultDebug", "Result not OK!");
                        }
                    }
                }
        );
        storageReference = FirebaseStorage.getInstance().getReference();


        deleteConfirmAlertDialog = new AlertDialog.Builder(getContext());
        deleteConfirmAlertDialog.setTitle("Delete a record");
        deleteConfirmAlertDialog.setMessage("Are you sure you want to delete this staff record?");
        deleteConfirmAlertDialog.setPositiveButton("Yes", this);
        deleteConfirmAlertDialog.setNegativeButton("No", this);

        updateWithoutPhotoAlertDialog = new AlertDialog.Builder(getContext());
        updateWithoutPhotoAlertDialog.setTitle("No profile picture uploaded");
        updateWithoutPhotoAlertDialog.setMessage("Are you sure you want to proceed updating this staff record?");

        updateWithoutPhotoAlertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onPhotoUploadComplete();
            }
        });
        updateWithoutPhotoAlertDialog.setNegativeButton("No", this);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseAndChoosePhoto();
                btnRemove.setEnabled(true);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUpdateClicked();
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imPhotoModifyStaff.setImageResource(R.drawable.no_one);
                Toast.makeText(getContext(), "Profile picture removed!", Toast.LENGTH_SHORT).show();
                uploadedPhotoUrl = null; // Clear the uploaded photo URL
                staffToModify.setPhotoUrl("");
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteConfirmAlertDialog.show();
            }
        });
    }

    private void btnUpdateClicked() {
        String firstName = edFirstNameModifyStaff.getText().toString();
        String lastName = edLastNameModifyStaff.getText().toString();
        String email = edEmailModifyStaff.getText().toString();
        String phone = edPhoneModifyStaff.getText().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (photoPath != null) {
            uploadChosenPhotoToStorage();
        } else if (isDefaultImageSet() && (staffToModify.getPhotoUrl() == null || staffToModify.getPhotoUrl().isEmpty())) {
            updateWithoutPhotoAlertDialog.show();
        } else {
            onPhotoUploadComplete();
        }
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
            backToManageStaffs();
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
                        Toast.makeText(getContext(), "Staff record successfully deleted!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(getContext(), "Failed to delete staff!", Toast.LENGTH_SHORT).show();
                    });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void browseAndChoosePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent,"Select image"));
    }

    private void uploadChosenPhotoToStorage() {
        if (photoPath != null) {

            imagesStorageRef = storageReference.child("images/" + UUID.randomUUID());
            imagesStorageRef.putFile(photoPath).addOnSuccessListener(this).addOnFailureListener(this);
        }
    }

    private Bitmap convertPhotoResultToBitMap(ActivityResult result) {
        Bitmap bitmap = null;
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            photoPath = result.getData().getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoPath);
            } catch (Exception e) {
                Log.d("ModifyStaffFragment", e.getMessage());
            }
        }
        else {
            Toast.makeText(getContext(), "No result!", Toast.LENGTH_LONG).show();
        }
        return bitmap;
    }


    private void onPhotoUploadComplete() {
            String firstName = edFirstNameModifyStaff.getText().toString().trim();
            String lastName = edLastNameModifyStaff.getText().toString().trim();
            String email = edEmailModifyStaff.getText().toString().trim();
            String phone = edPhoneModifyStaff.getText().toString().trim();
            String photoUrl = (uploadedPhotoUrl != null) ? uploadedPhotoUrl : staffToModify.getPhotoUrl();

            updateStaff(staffToModify.getStaffId(), new Staff(firstName, lastName, email, phone, photoUrl));
            clearWidgets();

    }
    @Override
    public void onSuccess(Object o) {
        Toast.makeText(getContext(), "Photo has been successfully uploaded!", Toast.LENGTH_SHORT).show();
        imagesStorageRef.getDownloadUrl().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                uploadedPhotoUrl = task.getResult().toString();
            } else {
                Toast.makeText(getContext(), "Failed to get photo URL.", Toast.LENGTH_LONG).show();
            }
        });    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(getContext(), "Error occurred while uploading photo: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }


    private void clearWidgets() {
        edFirstNameModifyStaff.setText(null);
        edLastNameModifyStaff.setText(null);
        edEmailModifyStaff.setText(null);
        edPhoneModifyStaff.setText(null);
        imPhotoModifyStaff.setImageResource(R.drawable.no_one);
        edFirstNameModifyStaff.setCursorVisible(true);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                deleteStaff(staffToModify.getStaffId());
                Toast.makeText(getContext(), "The staff record has been successfully deleted!" , Toast.LENGTH_LONG).show();
                clearWidgets();
                backToManageStaffs();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }
    private boolean isDefaultImageSet() {
        Drawable currentDrawable = imPhotoModifyStaff.getDrawable();
        Drawable defaultDrawable = getResources().getDrawable(R.drawable.no_one, null);
        return currentDrawable.getConstantState().equals(defaultDrawable.getConstantState());
    }



    private void backToManageStaffs() {
        // Implement the logic to replace the current fragment with another
        // This is typically done by interacting with the MainActivity
        if (getActivity() instanceof TabBarActivity) {
            ((TabBarActivity) getActivity()).replaceFragmentWith(new ManageStaffFragment(), null);
        }
    }
}