package fr.istic.mmm.sciencefair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import fr.istic.mmm.sciencefair.data.Course;
import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.data.EventFirebase;

public class AssetLoaderFirebase {

    public static final String eventPrefix(String recordid) {
        return "event-" + recordid;
    }
    public static final String coursePrefix(String courseid) { return "course-" + courseid; }

    public static final boolean isEvent(String id) { return id.contains("event-"); }
    public static final boolean isCourse(String id) { return id.contains("course-"); }

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
                for(DataSnapshot snap : dataSnapshot.getChildren()) {
                    if(isEvent(snap.getKey())) { onEventFirebase(snap); }
                    if(isCourse(snap.getKey())) { onCourse(snap); }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("onCancelled");
            }
        });
    }

    private void onEventFirebase(DataSnapshot snap) {
        EventFirebase efb = snap.getValue(EventFirebase.class);
        if(efb != null) {
            for (Event event : assetLoaderStatic.getEvents()) {
                if (eventPrefix(event.recordid).equals(efb.getRecordid())) {
                    event.eventFirebase = efb;
                }
            }
        } //else Database empty
    }

    private void onCourse(DataSnapshot snap) {
        System.out.println("onCourse");
        Course course = snap.getValue(Course.class);
        if(course != null) {
            System.out.println(course);
        }
    }

    public void saveEventFirebase(EventFirebase efb) {
        sciencefair.child(eventPrefix(efb.getRecordid())).setValue(efb);
    }

    public void saveCourse(Course course) {
        sciencefair.child(coursePrefix(course.getCourseid())).setValue(course.getRecordids());
    }
}
