package fr.istic.mmm.sciencefair.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;

public class EventListAdapter extends ArrayAdapter<Event> {

    private Context ctx;
    private List<Event> list;

    public EventListAdapter(Context ctx, List<Event> list) {
        super(ctx, R.layout.fragment_event_details, list);
        this.ctx = ctx;
        this.list = list;
    }

    public View getView(int pos, View view, ViewGroup parent) {
        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_event_list_item, parent, false);
        }
        Event event = list.get(pos);
        ((TextView) view.findViewById(R.id.titre)).setText(event.fields.titre_fr);
        ((TextView) view.findViewById(R.id.description)).setText(event.fields.description_fr);
        view.setOnClickListener(l -> {
            ((MainActivity) ctx).setEventDetails(pos);
        });
        return view;
    }

}
