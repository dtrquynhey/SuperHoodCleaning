package controllers.staff;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.superhoodcleaning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import models.Staff;
import services.FirebaseConnection;
import services.IAddButton;

public class NewStaffFragment extends Fragment implements IAddButton, View.OnClickListener, OnSuccessListener, OnFailureListener
{

    private EditText edFirstNameNewStaff, edLastNameNewStaff, edEmailNewStaff, edPhoneNewStaff;
    private ImageView imageViewPhoto;
    private Button btnUploadPhotoNewStaff, btnRemovePhotoNewStaff;
    Uri photoPath;
    ProgressDialog progressDialog;
    private String uploadedPhotoUrl = null;

    StorageReference storageReference, imagesStorageRef;


    ActivityResultLauncher activityResultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
    }

    private void initialize(View view) {
        edFirstNameNewStaff = view.findViewById(R.id.edFirstNameNewStaff);
        edLastNameNewStaff = view.findViewById(R.id.edLastNameNewStaff);
        edEmailNewStaff = view.findViewById(R.id.edEmailNewStaff);
        edPhoneNewStaff = view.findViewById(R.id.edPhoneNewStaff);

        btnUploadPhotoNewStaff = view.findViewById(R.id.btnUploadPhotoNewStaff);
        btnRemovePhotoNewStaff = view.findViewById(R.id.btnRemovePhotoNewStaff);

        imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
        btnUploadPhotoNewStaff.setOnClickListener(this);
        btnRemovePhotoNewStaff.setOnClickListener(this);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Log.d("ActivityResultDebug", "onActivityResult called with result code: " + result.getResultCode());
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Log.d("ActivityResultDebug", result.toString());
                            Bitmap bitmap = convertPhotoResultToBitMap(result);
                            imageViewPhoto.setImageBitmap(bitmap);
                        } else {
                            Log.d("ActivityResultDebug", "Result not OK!");
                        }
                    }
                }
        );
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    private Bitmap convertPhotoResultToBitMap(ActivityResult result) {
        Bitmap bitmap = null;
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            photoPath = result.getData().getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), photoPath);
                Log.d("bitmap", bitmap.toString());
            } catch (Exception e) {
                Log.d("ADV_FIREBASE", e.getMessage());
            }
        }
        else {
            Toast.makeText(getContext(), "No result!", Toast.LENGTH_LONG).show();
        }
        return bitmap;
    }

    private void addStaff(String firstName, String lastName, String email, String phone, String photoUrl) {
        try {
            Staff staff = new Staff(firstName, lastName, email, phone, photoUrl);
            DatabaseReference dbRef = FirebaseConnection.getDatabaseRef();
            try {
                dbRef.child("staffs").push().setValue(staff)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), "Staff added successfully!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Failed to add staff!", Toast.LENGTH_SHORT).show();
                        });
            } catch (Exception e) {
                Throwable realException = e.getCause();
                Log.e("FirebaseError", "Real cause: ", realException);
            }

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
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading photo in progress...");
            progressDialog.show();

            imagesStorageRef = storageReference.child("images/" + UUID.randomUUID());
            imagesStorageRef.putFile(photoPath).addOnSuccessListener(this).addOnFailureListener(this);
        }
    }

    @Override
    public void addButton() {
        Log.d("MyAppTag", "btn staff clicked");
        String firstName = edFirstNameNewStaff.getText().toString().trim();
        String lastName = edLastNameNewStaff.getText().toString().trim();
        String email = edEmailNewStaff.getText().toString().trim();
        String phone = edPhoneNewStaff.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (photoPath != null) {
            uploadChosenPhotoToStorage();
        } else {
            Toast.makeText(getContext(), "Please upload a profile picture!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUploadPhotoNewStaff) {
            Log.d("MyAppTag", "btn upload photo clicked");
            browseAndChoosePhoto();
        } else if (v.getId() == R.id.btnRemovePhotoNewStaff) {

        }
    }
    private void onPhotoUploadComplete() {
        if (uploadedPhotoUrl != null) {
            String firstName = edFirstNameNewStaff.getText().toString().trim();
            String lastName = edLastNameNewStaff.getText().toString().trim();
            String email = edEmailNewStaff.getText().toString().trim();
            String phone = edPhoneNewStaff.getText().toString().trim();

            addStaff(firstName, lastName, email, phone, uploadedPhotoUrl);
            clearWidgets();
        }
    }

    @Override
    public void onSuccess(Object o) {
        Toast.makeText(getContext(), "Photo has been successfully uploaded!", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        imagesStorageRef.getDownloadUrl().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                uploadedPhotoUrl = task.getResult().toString();
                onPhotoUploadComplete();
            } else {
                Toast.makeText(getContext(), "Failed to get photo URL.", Toast.LENGTH_LONG).show();
            }
        });    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(getContext(), "Error occurred while uploading photo: " + e.getMessage(), Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    private void clearWidgets() {
        edFirstNameNewStaff.setText(null);
        edLastNameNewStaff.setText(null);
        edEmailNewStaff.setText(null);
        edPhoneNewStaff.setText(null);
        imageViewPhoto.setImageResource(R.drawable.no_one);
    }

}