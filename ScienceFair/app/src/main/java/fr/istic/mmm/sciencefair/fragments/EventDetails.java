package fr.istic.mmm.sciencefair.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import fr.istic.mmm.sciencefair.AssetLoader;
import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;

public class EventDetails extends Fragment {

    private View view;
    private MainActivity activity;
    private Event event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_details, container, false);
        activity = ((MainActivity) getActivity());
        return view;
    }

    public void setPos(int position) {
        event = activity.getAssetLoader().getEvents().get(position);
        ((TextView) view.findViewById(R.id.event_title)).setText(event.fields.titre_fr);
        ((TextView) view.findViewById(R.id.event_description)).setText(event.fields.description_longue_fr);
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

}
