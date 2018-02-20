package fr.istic.mmm.sciencefair;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EventList extends AppCompatActivity {

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream stream = getAssets().open("fr-esr-fete-de-la-science-17.small.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public class Data {
        public String datasetid;
        public String recordid;
        public Date record_timestamp;
        public EventList.Data.Fields fields;
        public class Fields {
            public String titre_fr;
            public String description_fr;
        }
    }

    public class DataListAdapter extends ArrayAdapter<EventList.Data> {
        private Context ctx;
        private List<EventList.Data> list;
        public DataListAdapter(Context ctx, List<EventList.Data> list) {
            super(ctx, R.layout.item, list);
            this.ctx = ctx;
            this.list = list;
        }
        public View getView(int pos, View view, ViewGroup parent) {
            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item, parent, false);
            }
            EventList.Data data = list.get(pos);
            ((TextView) view.findViewById(R.id.item_title)).setText(data.fields.titre_fr);
            ((TextView) view.findViewById(R.id.item_description)).setText(data.fields.description_fr);
            return view;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ListView list = (ListView) findViewById(R.id.list_main);
        View item = findViewById(R.id.item_layout);

        Gson gson = new Gson();
        List<EventList.Data> test = Arrays.asList(gson.fromJson(loadJSONFromAsset(), EventList.Data[].class));
        ListAdapter adapter = new EventList.DataListAdapter(this, test);
        list.setAdapter(adapter);
    }
}
