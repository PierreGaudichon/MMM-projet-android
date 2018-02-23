package fr.istic.mmm.sciencefair.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.istic.mmm.sciencefair.AssetLoaderFirebase;
import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.data.EventFirebase;

public class EventDetails extends Fragment  {

    private View view;
    private MainActivity activity;
    private int position;
    private Event event;
    private TransportMode transportMode = TransportMode.DRIVE;
    private boolean isManager = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_details, container, false);
        activity = ((MainActivity) getActivity());
        NumberPicker np = view.findViewById(R.id.remainingPlacesNumberPicker);
        np.setMinValue(0);
        np.setMaxValue(1000);
        np.setWrapSelectorWheel(false);
        view.findViewById(R.id.applyButton).setOnClickListener(
                (view) -> {
                    event.eventFirebase.remaining = np.getValue();
                    activity.getAssetLoaderFirebase().saveEventFirebase(event.eventFirebase);
                });
        view.findViewById(R.id.event_course).setOnClickListener(v -> {
            activity.addToCourse(event);
        });
        return view;
    }

    public void setPos(int position) {
        this.position = position;
        setEvent(activity.getAssetLoaderStatic().getEvents().get(position));
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
            String desc = (event.fields.description_longue_fr != null) ?
                    event.fields.description_longue_fr : event.fields.description_fr;
            ((TextView) view.findViewById(R.id.event_description)).setText(desc);
            Picasso.with(getContext()).load(event.fields.image).into((ImageView) view.findViewById(R.id.event_image));
            view.findViewById(R.id.rateButton).setEnabled(true);
            //Rating initialise with EventFirebase
            if(event.eventFirebase == null) {
                event.eventFirebase = new EventFirebase();
                event.eventFirebase.recordid = event.recordid;
                event.eventFirebase.nbVotes = 0;
                event.eventFirebase.rating = 0;
                event.eventFirebase.remaining = -1;
            }
            ((RatingBar) view.findViewById(R.id.event_rating)).setRating(event.eventFirebase.rating);
            if(event.eventFirebase.remaining != -1){
                ((NumberPicker) view.findViewById(R.id.remainingPlacesNumberPicker)).setValue(this.event.eventFirebase.remaining);
                String remaining = "" + event.eventFirebase.remaining;
                ((TextView) view
                        .findViewById(R.id.remainingPlacesTextView))
                        .setText(remaining);
            }else{
                ((NumberPicker) view.findViewById(R.id.remainingPlacesNumberPicker)).setValue(0);
                ((TextView) view.findViewById(R.id.remainingPlacesTextView)).setText("NA");
            }

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

    public void setRate(AssetLoaderFirebase assetLoaderFirebase){
        event = getEvent();
        //Mis a jour de l'eventFirebase + modifier qu'une seule fois le un click
        int nbVotesf = event.eventFirebase.nbVotes + 1;
        event.eventFirebase.rating = (((RatingBar) view.findViewById(R.id.event_rating)).getRating() + (event.eventFirebase.rating * event.eventFirebase.nbVotes)) / nbVotesf;
        event.eventFirebase.nbVotes = nbVotesf;
        //save in database
        assetLoaderFirebase.saveEventFirebase(event.eventFirebase);
        view.findViewById(R.id.rateButton).setEnabled(false);


    }

    public void setManager() {
        isManager = !isManager;
        view.findViewById(R.id.remainingPlacesNumberPicker).setVisibility(isManager ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.remainingPlacesTextView).setVisibility(isManager ? View.GONE : View.VISIBLE);
        view.findViewById(R.id.applyButton).setVisibility(isManager ? View.VISIBLE : View.GONE);
    }

    public enum TransportMode{
        DRIVE,
        WALK,
        BIKE
    }
}
