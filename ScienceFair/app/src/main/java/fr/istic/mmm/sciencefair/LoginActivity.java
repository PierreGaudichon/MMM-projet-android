package fr.istic.mmm.sciencefair;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import fr.istic.mmm.sciencefair.fragments.EventDetails;
import fr.istic.mmm.sciencefair.fragments.EventList;


import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.R;
import fr.istic.mmm.sciencefair.data.Event;

/**
 * Created by paget on 21/02/18.
 */

public class LoginActivity extends Activity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeFirebase();

    }

        /**
         * Méthode d'initialisation de Firebase.
         * Ici, on redirige directement l'utilisateur vers l'accueil s'il est déjà connecté via FirebaseAuth.
         */
        private void initializeFirebase(){
            mAuth = FirebaseAuth.getInstance();
            if(mAuth.getCurrentUser() != null){
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }



    /* Recup de MainActivity
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
        setSupportActionBar(toolbar);

        eventList = new EventList();
        eventDetails = new EventDetails();
        mMapFragment = MapFragment.newInstance();

        showEventList();
        *//*logEvent(eventDetails.getView());*//*
    }

    public FirebaseAnalytics getmFirebaseAnalytics(){
        return mFirebaseAnalytics;
    }
*//*    public void logEvent(View view){
        String id = "" + view.getId() ;
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putLong(FirebaseAnalytics.Param.SCORE, 5);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.POST_SCORE, bundle);
    }*//*

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
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //SearchView searchView = (SearchView) menu.findItem(R.id.toolbar_search).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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
    }*/
}
