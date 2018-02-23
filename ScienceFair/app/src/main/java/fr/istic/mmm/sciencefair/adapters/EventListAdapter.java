package fr.istic.mmm.sciencefair.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;

public class EventListAdapter extends ListAdapter {

    public EventListAdapter(Context ctx, List<Event> list) {
        super(ctx, list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = list.get(position);
        ((TextView) holder.view.findViewById(R.id.titre)).setText(event.fields.titre_fr);
        ((TextView) holder.view.findViewById(R.id.description)).setText(event.fields.description_fr);
        holder.view.setOnClickListener(l -> {
            ((MainActivity) ctx).showEventDetails(position);
        });
    }

    @Override
    public int inflateId() {
        return R.layout.fragment_event_list_item;
    }
}
