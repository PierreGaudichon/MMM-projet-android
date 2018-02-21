package fr.istic.mmm.sciencefair.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import fr.istic.mmm.sciencefair.AssetLoader;
import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.data.EventListAdapter;

public class EventList extends Fragment {

    private View view;
    private MainActivity activity;
    private RecyclerView list;
    private List<Event> events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_list, container, false);
        activity = ((MainActivity) getActivity());
        list = view.findViewById(R.id.event_list);
        events = activity.getAssetLoader().getEvents();
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(new EventListAdapter(activity, events));
        return view;
    }

}
