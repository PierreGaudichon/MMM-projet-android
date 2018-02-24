package fr.istic.mmm.sciencefair;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.UUID;

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

    private MainActivity activity;
    private AssetLoaderStatic assetLoaderStatic;
    private FirebaseDatabase database;
    private DatabaseReference sciencefair;

    public AssetLoaderFirebase(MainActivity activity, AssetLoaderStatic assetLoaderStatic) {
        this.activity = activity;
        this.assetLoaderStatic = assetLoaderStatic;
        this.database = FirebaseDatabase.getInstance();
        this.sciencefair = database.getReference();

        sciencefair.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("AssetLoaderFirebase.onDataChange");
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
                if (event.recordid.equals(efb.recordid)) {
                    event.setEventFirebase(efb);
                }
            }
        } //else Database empty
    }

    private void onCourse(DataSnapshot snap) {
        System.out.println("AssetLoaderFirebase#onCourse");
        Course course = snap.getValue(Course.class);
        if(course != null) {
            activity.addCourse(course);
        }
    }

    public void saveEventFirebase(EventFirebase efb) {
        sciencefair.child(eventPrefix(efb.recordid)).setValue(efb);
    }

    public void saveCourse(Course course) {
        System.out.println("Save course.");
        System.out.println(course.getCourseid());
        course.populateRecordids();
        sciencefair.child(coursePrefix(course.getCourseid())).setValue(course);
    }
}
