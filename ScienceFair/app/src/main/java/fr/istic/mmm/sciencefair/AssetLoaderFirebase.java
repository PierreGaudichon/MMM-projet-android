package fr.istic.mmm.sciencefair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.data.EventFirebase;

public class AssetLoaderFirebase {

    public static final String eventPrefix(String recordid) {
        return "event-" + recordid;
    }

    private AssetLoaderStatic assetLoaderStatic;
    private FirebaseDatabase database;
    private DatabaseReference sciencefair;

    public AssetLoaderFirebase(AssetLoaderStatic assetLoaderStatic) {
        this.assetLoaderStatic = assetLoaderStatic;
        this.database = FirebaseDatabase.getInstance();
        this.sciencefair = database.getReference();

        sciencefair.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EventFirebase efb = dataSnapshot.getValue(EventFirebase.class);
                for(Event event : assetLoaderStatic.getEvents()) {
                    if(eventPrefix(event.recordid) == efb.recordid) {
                        event.eventFirebase = efb;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("onCancelled");
            }
        });
    }

    public void saveEventFirebase(EventFirebase efb) {
        sciencefair.child(eventPrefix(efb.recordid)).setValue(efb);
    }
}
