package fr.istic.mmm.sciencefair;

import android.content.Context;
import android.content.res.AssetManager;
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

public class EventList extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        AssetLoader loader = new AssetLoader(getAssets());

        ListView list = (ListView) findViewById(R.id.list_main);
        View item = findViewById(R.id.item_layout);

        List<Data> test = loader.loadData(AssetLoader.SMALL);
        ListAdapter adapter = new DataListAdapter(this, test);
        list.setAdapter(adapter);
    }
}
