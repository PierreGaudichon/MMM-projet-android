package fr.istic.mmm.sciencefair;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
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

    private boolean isSharable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assetLoader = new AssetLoader(getAssets(), AssetLoader.MEDIUM);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        toolbar = findViewById(R.id.toolbar_main);
//        setSupportActionBar(toolbar);

        eventList = new EventList();
        eventList.setEventList(getAssetLoader().getEvents());
        eventDetails = new EventDetails();
        mMapFragment = MapFragment.newInstance();

        showEventList();
        showMap();
        /*logEvent(eventDetails.getView());*/
    }

    public FirebaseAnalytics getmFirebaseAnalytics(){
        return mFirebaseAnalytics;
    }
/*    public void logEvent(View view){
        String id = "" + view.getId() ;
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putLong(FirebaseAnalytics.Param.SCORE, 5);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.POST_SCORE, bundle);
    }*/

    public void showEventList() {
        isSharable = false;
        showFullFragment(eventList);
    }

    public void showEventDetails(int pos) {
        isSharable = true;
        showFullFragment(eventDetails);
        eventDetails.setPos(pos);
    }

    public void showMap(){
        isSharable = false;
        showFullFragment(mMapFragment);
        mMapFragment.getMapAsync(eventList);
    }

    private void showFullFragment(Fragment fragment) {
        if(!fragment.isVisible()) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_main, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
            getFragmentManager().executePendingTransactions();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.toolbar_map) { showMap(); }
        if(item.getItemId() == R.id.toolbar_list) { showEventList(); }
        if(item.getItemId() == R.id.toolbar_share) { eventDetails.share(); }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.toolbar_share).setVisible(isSharable);
        return true;
    }

    public AssetLoader getAssetLoader() {
        return assetLoader;
    }
}
