package fr.istic.mmm.sciencefair.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fr.istic.mmm.sciencefair.AssetLoader;
import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.data.EventListAdapter;

public class EventList extends Fragment implements OnMapReadyCallback{

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for(Event event : events){
            if(event.fields.geolocalisation != null) {
                LatLng location = new LatLng(event.fields.geolocalisation[0], event.fields.geolocalisation[1]);
                Marker marker = googleMap.addMarker(new MarkerOptions().position(location).title(event.fields.titre_fr));
                builder.include(marker.getPosition());
            }
        }

        LatLngBounds bounds = builder.build();
        int padding = 50; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cu);
    }
}
