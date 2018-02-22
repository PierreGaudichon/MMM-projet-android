package fr.istic.mmm.sciencefair;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.fragments.EventDetails;
import fr.istic.mmm.sciencefair.fragments.EventList;
import fr.istic.mmm.sciencefair.map.EventListOnMapReady;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AssetLoader assetLoader;
    private GoogleMap mMap;

    private MapFragment mMapFragment;
    private EventList eventList;
    private EventDetails eventDetails;
    private LocationManager locationManager;
    private EventListOnMapReady eventListOnMapReady;

    private boolean isSharable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        setContentView(R.layout.activity_main);

        assetLoader = new AssetLoader(getAssets(), AssetLoader.MEDIUM);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        eventList = new EventList();
        eventList.setEventList(getAssetLoader().getEvents());
        eventDetails = new EventDetails();
        mMapFragment = MapFragment.newInstance();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        eventListOnMapReady = new EventListOnMapReady(this, locationManager, eventList);

        handleIntent(getIntent());
        showEventList();
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

    public void showEventDetails(Event event){
        isSharable = true;
        showFullFragment(eventDetails);
        eventDetails.setEvent(event);
    }

    public void showMap(){
        isSharable = false;
        showFullFragment(mMapFragment);
        mMapFragment.getMapAsync(eventListOnMapReady);
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
        getMenuInflater().inflate(R.menu.toolbar, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.toolbar_search).getActionView();
        ComponentName name = new ComponentName(getApplicationContext(), MainActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(name));
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

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            assetLoader.setQuery(query);
            showEventList();
        } else {
            assetLoader.setQuery(null);
            showEventList();
        }
    }

    public AssetLoader getAssetLoader() {
        return assetLoader;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                              int[] grantResults){
        for(int i = 0; i < permissions.length; i++){
            if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[i] == -1) {
                //TODO better exit
                System.exit(2);
            }
        }
    }
}
