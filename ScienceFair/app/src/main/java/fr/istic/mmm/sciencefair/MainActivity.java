package fr.istic.mmm.sciencefair;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import fr.istic.mmm.sciencefair.fragments.EventDetails;
import fr.istic.mmm.sciencefair.fragments.EventList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AssetLoader assetLoader;
    private GoogleMap mMap;

    private MapFragment mMapFragment;
    private EventList eventList;
    private EventDetails eventDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assetLoader = new AssetLoader(getAssets(), AssetLoader.MEDIUM);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        eventList = new EventList();
        eventDetails = new EventDetails();
        mMapFragment = MapFragment.newInstance();

        showEventList();
        logEvent();
    }

    public void logEvent(){
        Bundle bundle = new Bundle();
        /*bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);*/
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void showEventList() {
        showFullFragment(eventList);
    }

    public void showEventDetails(int pos) {
        showFullFragment(eventDetails);
        eventDetails.setPos(pos);
    }

    public void showMap(){
        showFullFragment(mMapFragment);
        mMapFragment.getMapAsync(eventList);
    }

    private void showFullFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        getFragmentManager().executePendingTransactions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.toolbar_map) { showMap(); }
        return true;
    }

    public AssetLoader getAssetLoader() {
        return assetLoader;
    }
}
