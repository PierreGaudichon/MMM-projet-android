package fr.istic.mmm.sciencefair.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;

public class EventListAdapter extends ListAdapter<Event> implements Observer{

    private Map<Event, ViewHolder> eventToViewHolder = new HashMap<>();

    public EventListAdapter(Context ctx, List<Event> list) {
        super(ctx, list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = list.get(position);
        applyColor(event, holder);
        ((TextView) holder.view.findViewById(R.id.titre)).setText(event.fields.titre_fr);
        ((TextView) holder.view.findViewById(R.id.description)).setText(event.fields.description_fr);
        if(event.fields.apercu != null) {
            Picasso.with(ctx).load(event.fields.apercu).into((ImageView) holder.view.findViewById(R.id.card_image));
        }else{
            holder.view.findViewById(R.id.card_image).setVisibility(View.GONE);
        }
        holder.view.setOnClickListener(l -> ((MainActivity) ctx).showEventDetails(position));
        eventToViewHolder.put(event, holder);
    }

    @Override
    public int inflateId() {
        return R.layout.fragment_event_list_item;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Event){
            ViewHolder holder = eventToViewHolder.get(arg);
            if(holder != null) {
               applyColor((Event) arg, holder);
            }
        }
    }

    private void applyColor(Event event, ViewHolder holder){
        int color = Color.rgb(115, 224, 65); // GREEN
        if ((event.getEventFirebase() != null && event.getEventFirebase().remaining == -1) || event.getEventFirebase() == null) {
            color = Color.rgb(114, 114, 114); // GREY
        }else if(event.getEventFirebase().remaining <= 0){
            color = Color.rgb(226, 49, 22); // RED
        } else if (event.getEventFirebase().remaining < 5) {
            color = Color.rgb(255, 142, 43); // ORANGE
        }
        ((TextView) holder.view.findViewById(R.id.titre)).setTextColor(color);
    }
}
