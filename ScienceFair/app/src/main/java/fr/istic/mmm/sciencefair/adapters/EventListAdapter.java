package fr.istic.mmm.sciencefair.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;

public class EventListAdapter extends ListAdapter<Event> {

    public EventListAdapter(Context ctx, List<Event> list) {
        super(ctx, list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = list.get(position);
        int color = Color.rgb(115, 224, 65);
        if ((event.eventFirebase != null && event.eventFirebase.remaining == -1) || event.eventFirebase == null){
            color = Color.rgb(255, 142, 43);
        }else if(event.eventFirebase.remaining < 5) {
            color = Color.rgb(226, 49, 22);
        }
        ((TextView) holder.view.findViewById(R.id.titre)).setTextColor(color);
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
