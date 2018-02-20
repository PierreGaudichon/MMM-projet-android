package fr.istic.fds;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("fr-esr-fete-de-la-science-17.small.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
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
        public Fields fields;
        public class Fields {
            public String titre_fr;
            public String description_fr;
        }
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (ListView) findViewById(R.id.list_main);
        View item = findViewById(R.id.item_layout);

        Gson gson = new Gson();
        List<Data> test = Arrays.asList(gson.fromJson(loadJSONFromAsset(), Data[].class));
        ListAdapter adapter = new DataListAdapter(this, test);
        list.setAdapter(adapter);
    }
}
