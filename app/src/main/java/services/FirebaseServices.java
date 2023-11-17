package services;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class FirebaseServices {
        @Provides
        @Singleton
        public DatabaseReference provideDatabaseReference() {
            return FirebaseDatabase.getInstance().getReference();
        }

        @Provides
        @Singleton
        public StorageReference provideStorageReference() {
            return FirebaseStorage.getInstance().getReference();
        }
}
