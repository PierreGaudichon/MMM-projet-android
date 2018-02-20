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

import fr.istic.mmm.sciencefair.data.Data;
import fr.istic.mmm.sciencefair.data.DataListAdapter;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ListView list = (ListView) findViewById(R.id.list_main);
        View item = findViewById(R.id.item_layout);

        Gson gson = new Gson();
        List<Data> test = Arrays.asList(gson.fromJson(loadJSONFromAsset(), Data[].class));
        ListAdapter adapter = new DataListAdapter(this, test);
        list.setAdapter(adapter);
    }
}
