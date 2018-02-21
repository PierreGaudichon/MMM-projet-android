package fr.istic.mmm.sciencefair;

import android.app.FragmentManager;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import fr.istic.mmm.sciencefair.fragments.EventDetails;
import fr.istic.mmm.sciencefair.fragments.EventList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.data.EventListAdapter;

public class MainActivity extends Activity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapFragment mMapFragment;
    private FragmentManager manager;

    private EventList eventList;
    private EventDetails eventDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = getFragmentManager();
        setContentView(R.layout.activity_main);

        eventList = new EventList();
        eventDetails = new EventDetails();

        setEventList();

        /*
        ListView list = (ListView) findViewById(R.id.list_main);
        ListView list = findViewById(R.id.list_main);
        View item = findViewById(R.id.item_layout);

        List<Event> test = loader.loadData(AssetLoader.SMALL);
        ListAdapter adapter = new EventListAdapter(this, test);
        list.setAdapter(adapter);
        */
    }

    public void setEventList() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_main, eventList);
        transaction.commit();
    }

    public void setEventDetails(int pos) {
        FragmentTransaction transaction = manager.beginTransaction();
        eventDetails.setPos(pos);
        transaction.add(R.id.fragment_main, eventDetails);
        transaction.commit();
    }

    public void showMap(View view){
        if(mMapFragment == null) {
            mMapFragment = MapFragment.newInstance();
        }
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main, mMapFragment);
        mMapFragment.getMapAsync(this);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        removeMapFragment();
    }

    private void removeMapFragment(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.remove(mMapFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        System.out.println("sydney = " + sydney);
    }
}
