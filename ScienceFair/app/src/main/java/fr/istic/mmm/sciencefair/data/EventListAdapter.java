package fr.istic.mmm.sciencefair.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    private Context ctx;
    private List<Event> list;

    public EventListAdapter(Context ctx, List<Event> list) {
        //super(ctx, R.layout.fragment_event_details, list);
        this.ctx = ctx;
        this.list = list;
        for (Event event :
                list) {
            if(!event.hasGeolocalisation()){
                System.out.println("event.fields.titre_fr = " + event.fields.titre_fr);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_event_list_item, parent, false));
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
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Event> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
