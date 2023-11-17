package services;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import models.Address;
import models.Customer;

public class CustomerServices {

//    static FirebaseServices firebaseServices = new FirebaseServices();
//    @Inject
//    static
//    DatabaseReference databaseReference;

    public static Address generateAddress(String street, String city, String state, String zip ){
        return new Address(street, city, state, zip);
    }

//    public static String addCustomer(String name, String manager, Address address, String phone) {
//       try {
//           Customer customer = new Customer(name, manager, address, phone);
//
//           //databaseReference = firebaseServices.provideDatabaseReference();
//           //Add customer to the database
//           DatabaseReference dbRef = FirebaseConnection.getDatabaseRef();
//           dbRef.child("customers").push().setValue(customer)
//                   .addOnSuccessListener(aVoid -> {
//                       // Handle success
//                       return "Customer added successfully";
//                   })
//                   .addOnFailureListener(e -> {
//                       // Handle failure
//                       return "Failed to add customer!";
//                   });
//       } catch (Exception e) {
//           return  e.getMessage();
//       };
//    }
}
