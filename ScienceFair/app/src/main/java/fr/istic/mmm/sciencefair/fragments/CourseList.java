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
import fr.istic.mmm.sciencefair.adapters.CourseAdapter;
import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.adapters.EventListAdapter;

public class CourseList extends Fragment {

    private View view;
    private MainActivity activity;
    private RecyclerView list;
    private List<Event> events;
    private CourseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course_list, container, false);
        activity = ((MainActivity) getActivity());

        list = view.findViewById(R.id.course_list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CourseAdapter(activity, events);
        list.setAdapter(adapter);

        ((Button) view.findViewById(R.id.course_commit)).setOnClickListener(v -> {
            String name = ((EditText) view.findViewById(R.id.course_name)).getText().toString();
            activity.addNameToCourse(name);
        });

        return view;
    }

    public void setEventList(List<Event> eventList){
        this.events = eventList;
        if(adapter != null) { adapter.setList(eventList); }
    }
}
