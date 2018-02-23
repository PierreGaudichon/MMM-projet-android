package fr.istic.mmm.sciencefair.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.List;

import fr.istic.mmm.sciencefair.AssetLoader;
import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;

public class EventDetails extends Fragment  { /*implements View.OnClickListener*/

    private View view;
    private MainActivity activity;
    private Event event;
    private TransportMode transportMode = TransportMode.DRIVE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_details, container, false);
        activity = ((MainActivity) getActivity());
        /*//Firebase
        view.findViewById(R.id.event_score).setOnClickListener(this);*/
        return view;
    }

    public void setPos(int position) {
        setEvent(activity.getAssetLoader().getEvents().get(position));
    }

    public void setEvent(Event event) {
        this.event = event;
        if(!event.hasGeolocalisation()) {
            view.findViewById(R.id.routeLinearLayout).setVisibility(View.GONE);
            view.findViewById(R.id.warningText).setVisibility(View.VISIBLE);
        }
        onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(event != null) {
            ((TextView) view.findViewById(R.id.event_title)).setText(event.fields.titre_fr);
            ((TextView) view.findViewById(R.id.event_description)).setText(event.fields.description_longue_fr);
            Picasso.with(getContext()).load(event.fields.image).into((ImageView) view.findViewById(R.id.event_image));
        }
    }

    public void share() {
        String name = getResources().getString(R.string.app_name);
        String subject = name + " : " + event.fields.titre_fr;
        String body = subject + "\n" + event.fields.lien;
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(intent, "Share using"));
    }

    public Event getEvent() {
        return event;
    }

    public TransportMode getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TransportMode transportMode) {
        this.transportMode = transportMode;
    }

    /*//Firebase
    public void logEvent(View view){
        String id = "" + view.getId() ;
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);

        // Inserer un champ text pour que l'utilisateur rentre une note autre que 5
        bundle.putLong(FirebaseAnalytics.Param.SCORE, 5);

        activity.getmFirebaseAnalytics().logEvent(FirebaseAnalytics.Event.POST_SCORE, bundle);
    }

    @Override
    public void onClick(View view) {
        //Firebase
        if(view.getId() == R.id.event_score) { logEvent(view); }
    }*/

    public enum TransportMode{
        DRIVE,
        WALK,
        BIKE
    }
}
