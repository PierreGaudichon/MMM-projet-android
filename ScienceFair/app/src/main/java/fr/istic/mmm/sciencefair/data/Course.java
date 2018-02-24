package fr.istic.mmm.sciencefair.data;

import android.view.ViewDebug;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Course {

    private String courseid;
    private List<Event> events;
    private List<String> recordids;
    private String name;

    public Course() {
        courseid = UUID.randomUUID().toString();
        events = new ArrayList<>();
    }

    public boolean add(Event event) {
        if(!events.contains(event)) {
            events.add(event);
            return true;
        } else {
            return false;
        }
    }

    public void remove(Event event) {
        events.remove(event);
    }

    @Exclude
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<String> getRecordids() {
        return recordids;
    }

    public void setRecordids(List<String> recordids) {
        this.recordids = recordids;
    }

    public void setCourseid(String courseid) { this.courseid = courseid; }
    public String getCourseid() {
        return courseid;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void populateEvents(List<Event> allEvents) {
        System.out.println("Course#populateEvents");
        System.out.println("recordids.length = " + recordids.size());
        System.out.println("allEvents.size() = " + allEvents.size());
        events = new ArrayList<>();
        if(recordids != null) {
            for(String recordid : recordids) {
                System.out.println("recordid = " + recordid);
                for(Event event : allEvents) {
                    if(event.recordid.equals(recordid)) {
                        System.out.println("added");
                        events.add(event);
                    }
                }
            }
        }
    }

    public void populateRecordids() {
        recordids = new ArrayList<>();
        for(Event event : events) { recordids.add(event.recordid); }
    }

    public boolean isInList(List<Course> courses) {
        for(Course course : courses) {
            if(course.courseid == courseid) {
                return true;
            }
        }
        return false;
    }
}
