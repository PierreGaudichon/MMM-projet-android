package fr.istic.mmm.sciencefair.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.istic.mmm.sciencefair.AssetLoader;
import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;

public class EventDetails extends Fragment {

    private View view;
    private MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_details, container, false);
        activity = ((MainActivity) getActivity());
        return view;
    }

    public void setPos(int pos) {
        List<Event> events = activity.getAssetLoader().getEvents();
        ((TextView) view.findViewById(R.id.event_title)).setText(events.get(pos).fields.titre_fr);
        ((TextView) view.findViewById(R.id.event_description)).setText(events.get(pos).fields.description_fr);
    }

}
