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

public abstract class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    protected Context ctx;
    protected List<Event> list;

    public ListAdapter(Context ctx, List<Event> list) {
        //super(ctx, R.layout.fragment_event_details, list);
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(inflateId(), parent, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Event> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public abstract int inflateId();
}
