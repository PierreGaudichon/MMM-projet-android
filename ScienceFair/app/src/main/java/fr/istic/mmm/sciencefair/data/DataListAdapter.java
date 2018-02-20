package fr.istic.mmm.sciencefair.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.istic.mmm.sciencefair.EventList;
import fr.istic.mmm.sciencefair.R;

public class DataListAdapter extends ArrayAdapter<Data> {

    private Context ctx;
    private List<Data> list;

    public DataListAdapter(Context ctx, List<Data> list) {
        super(ctx, R.layout.item, list);
        this.ctx = ctx;
        this.list = list;
    }

    public View getView(int pos, View view, ViewGroup parent) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item, parent, false);
        }
        Data data = list.get(pos);
        ((TextView) view.findViewById(R.id.item_title)).setText(data.fields.titre_fr);
        ((TextView) view.findViewById(R.id.item_description)).setText(data.fields.description_fr);
        return view;
    }

}
