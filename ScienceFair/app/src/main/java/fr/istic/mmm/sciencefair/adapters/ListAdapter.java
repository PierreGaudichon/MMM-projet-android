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

public abstract class ListAdapter<T> extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    protected Context ctx;
    protected List<T> list;

    public ListAdapter(Context ctx, List<T> list) {
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
        return (list == null) ? 0 : list.size();
    }

    public void setList(List<T> list) {
        this.list = list;
        System.out.println(list);
        notifyDataSetChanged();
    }

    public abstract int inflateId();
}
