package fr.istic.mmm.sciencefair;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fr.istic.mmm.sciencefair.fragments.EventList;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private ScienceFairMap mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getFragmentManager();
        setContentView(R.layout.activity_main);

        EventList eventList = new EventList();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_main, eventList);
        transaction.commit();


        /*
        ListView list = (ListView) findViewById(R.id.list_main);
        View item = findViewById(R.id.item_layout);

        List<Event> test = loader.loadData(AssetLoader.SMALL);
        ListAdapter adapter = new EventListAdapter(this, test);
        list.setAdapter(adapter);
        */
    }

    public void test(View view){
        System.out.println("BITE");
    }
}
