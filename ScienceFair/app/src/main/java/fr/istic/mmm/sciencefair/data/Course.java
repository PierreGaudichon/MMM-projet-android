package fr.istic.mmm.sciencefair.data;

import android.view.ViewDebug;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Course {

    private String courseid;
    private List<Event> events;
    private String name;

    public Course() {
        courseid = UUID.randomUUID().toString();
        events = new ArrayList<>();
    }

    public void add(Event event) {
        if(!events.contains(event)) {
            events.add(event);
        }
    }

    public void remove(Event event) {
        events.remove(event);
    }

    @Exclude
    public List<Event> getEvents() {
        return events;
    }

    public List<String> getRecordids() {
        List<String> result = new ArrayList<>();
        for(Event event : events) { result.add(event.recordid); }
        return result;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
