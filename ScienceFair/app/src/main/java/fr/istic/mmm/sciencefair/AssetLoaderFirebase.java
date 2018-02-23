package fr.istic.mmm.sciencefair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import fr.istic.mmm.sciencefair.data.Event;

public class AssetLoaderFirebase {
    private FirebaseDatabase database;
    private DatabaseReference sciencefair;

    public AssetLoaderFirebase() {
        database = FirebaseDatabase.getInstance();
        sciencefair = database.getReference();
        // Read from the database
        sciencefair.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                System.out.println("onDataChange");
                System.out.println(event);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("onCancelled");
            }
        });
    }

    private void loadData() {

    }
}
