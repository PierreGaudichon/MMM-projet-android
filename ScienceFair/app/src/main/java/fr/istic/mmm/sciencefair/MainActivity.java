package fr.istic.mmm.sciencefair;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import fr.istic.mmm.sciencefair.fragments.EventDetails;
import fr.istic.mmm.sciencefair.fragments.EventList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends Activity {

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

    public void showMap(View view){
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

    public AssetLoader getAssetLoader() {
        return assetLoader;
    }
}
