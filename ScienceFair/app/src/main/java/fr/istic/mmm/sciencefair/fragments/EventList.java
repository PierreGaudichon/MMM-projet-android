package fr.istic.mmm.sciencefair.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Observer;

import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.adapters.ListAdapter;
import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.adapters.EventListAdapter;

public class EventList extends Fragment {

    private View view;
    private MainActivity activity;
    private RecyclerView list;
    private List<Event> events;
    private EventListAdapter adapter;

    public List<Event> getEvents() {
        return events;
    }

    public void setEventList(List<Event> eventList){
        this.events = eventList;
        if(adapter != null) {
            adapter.setList(eventList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_list, container, false);
        activity = ((MainActivity) getActivity());
        list = view.findViewById(R.id.event_list);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new EventListAdapter(activity, events);
        list.setAdapter(adapter);
        attachToEvents(adapter);
        return view;
    }

    private void attachToEvents(Observer observer){
        for(Event event : events){
            event.attach(observer);
        }
        System.out.println("EventList.attachToEvents");;
    }
}
