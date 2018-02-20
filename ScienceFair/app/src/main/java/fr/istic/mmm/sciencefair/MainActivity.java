package fr.istic.mmm.sciencefair;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import fr.istic.mmm.sciencefair.data.Data;
import fr.istic.mmm.sciencefair.data.DataListAdapter;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private ScienceFairMap mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getFragmentManager();
        setContentView(R.layout.activity_event_list);

        AssetLoader loader = new AssetLoader(getAssets());

        ListView list = (ListView) findViewById(R.id.list_main);
        View item = findViewById(R.id.item_layout);

        List<Data> test = loader.loadData(AssetLoader.SMALL);
        ListAdapter adapter = new DataListAdapter(this, test);
        list.setAdapter(adapter);
    }

    public void test(View view){
        System.out.println("BITE");
    }
}
