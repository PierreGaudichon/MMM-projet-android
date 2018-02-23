package fr.istic.mmm.sciencefair.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return view;
    }
}
