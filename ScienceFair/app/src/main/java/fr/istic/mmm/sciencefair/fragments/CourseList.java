package fr.istic.mmm.sciencefair.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.adapters.AllCourseAdapter;
import fr.istic.mmm.sciencefair.adapters.MyCourseAdapter;
import fr.istic.mmm.sciencefair.data.Course;
import fr.istic.mmm.sciencefair.data.Event;

public class CourseList extends Fragment {

    private View view;
    private MainActivity activity;
    private List<Event> events;
    private MyCourseAdapter eventAdapter;
    private List<Course> courses;
    private AllCourseAdapter coursesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("CourseList#onCreateView#begin");
        view = inflater.inflate(R.layout.fragment_course_list, container, false);
        activity = ((MainActivity) getActivity());

        System.out.println("CourseList#onCreateView#eventListAdapter");
        RecyclerView myList = view.findViewById(R.id.course_my_list);
        myList.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventAdapter = new MyCourseAdapter(activity, events);
        myList.setAdapter(eventAdapter);

        System.out.println("CourseList#onCreateView#button");
        ((Button) view.findViewById(R.id.course_publish)).setOnClickListener(v -> {
            String name = ((EditText) view.findViewById(R.id.course_name)).getText().toString();
            boolean published = activity.publishCourse(name);
            if(published) {
                ((EditText) view.findViewById(R.id.course_name)).getText().clear();
            }
        });

        System.out.println("CourseList#onCreateView#courseAdapter");
        System.out.println("courses.size() = " + courses.size());
        RecyclerView allList = view.findViewById(R.id.course_all_list);
        allList.setLayoutManager(new LinearLayoutManager(getActivity()));
        coursesAdapter = new AllCourseAdapter(activity, courses);
        if(courses != null) { coursesAdapter.setList(courses); }
        allList.setAdapter(coursesAdapter);

        return view;
    }

    public void setEventList(List<Event> events){
        this.events = events;
        if(eventAdapter != null) { eventAdapter.setList(events); }
    }

    public void setCourseList(List<Course> courses){
        System.out.println("CourseList#setCourseList");
        System.out.print("coursesAdapter != null = ");
        System.out.println(coursesAdapter != null);
        this.courses = courses;
        if(coursesAdapter != null) { coursesAdapter.setList(courses); }
    }

}
