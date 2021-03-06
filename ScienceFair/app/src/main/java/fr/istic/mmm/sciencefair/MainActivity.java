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
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.*;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.istic.mmm.sciencefair.data.Course;
import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.data.EventFirebase;
import fr.istic.mmm.sciencefair.fragments.CourseList;
import fr.istic.mmm.sciencefair.fragments.EventDetails;
import fr.istic.mmm.sciencefair.fragments.EventList;
import fr.istic.mmm.sciencefair.map.EventListOnMapReady;

public class MainActivity extends AppCompatActivity {

    /*
     * ------------------------------------------------------------------------
     *
     * ATTRIBUTES
     *
     * ------------------------------------------------------------------------
     */

    private Toolbar toolbar;
    private AssetLoaderStatic assetLoaderStatic;
    private AssetLoaderFirebase assetLoaderFirebase;

    private MapFragment mMapFragment;
    private EventList eventList;
    private CourseList courseList;
    private EventDetails eventDetails;
    private LocationManager locationManager;
    private EventListOnMapReady eventListOnMapReady;

    private boolean isFirst = true;
    private boolean isSharable;

    private Course course;
    private List<Course> courses;


    /*
     * ------------------------------------------------------------------------
     *
     * ON_CREATE
     *
     * ------------------------------------------------------------------------
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        setContentView(R.layout.activity_main);

        assetLoaderStatic = new AssetLoaderStatic(getAssets(), AssetLoaderStatic.BIG);
        assetLoaderFirebase = new AssetLoaderFirebase(this, assetLoaderStatic);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        course = new Course();
        courses = new ArrayList<>();

        eventList = new EventList();
        eventList.setEventList(getAssetLoaderStatic().getEvents());
        courseList = new CourseList();
        courseList.setEventList(course.getEvents());
        eventDetails = new EventDetails();
        mMapFragment = MapFragment.newInstance();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        eventListOnMapReady = new EventListOnMapReady(this, locationManager, eventList);

        showEventList();

    }


    /*
     * ------------------------------------------------------------------------
     *
     * SHOW_FRAGMENTS
     *
     * ------------------------------------------------------------------------
     */

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        isSharable = eventDetails.isVisible();
    }

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

    public void showCourseList() {
        isSharable = false;
        showFullFragment(courseList);
    }

    private void showFullFragment(Fragment fragment) {
        if(!fragment.isVisible()) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_main, fragment);
            if(isFirst) {
                isFirst = false;
            }else{
                transaction.addToBackStack(null);
            }
            transaction.commit();
            getFragmentManager().executePendingTransactions();
        }
    }


    /*
     * ------------------------------------------------------------------------
     *
     * MENU
     *
     * ------------------------------------------------------------------------
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.toolbar_search).getActionView();
        ComponentName name = new ComponentName(getApplicationContext(), MainActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(name));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                hideKeyboard();
                if(mMapFragment.isVisible()){
                    eventListOnMapReady.handleSearchSubmit();
                }
                return true;
            }
            @Override public boolean onQueryTextChange(String query) {
                if(query.isEmpty()) {
                    assetLoaderStatic.setQuery(null);
                    if (mMapFragment.isVisible()) {
                        eventListOnMapReady.handleSearchSubmit();
                    }
                }else {
                    assetLoaderStatic.setQuery(query);
                }
                eventList.setEventList(getAssetLoaderStatic().getEvents());
                return true;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.toolbar_map) { showMap(); }
        if(item.getItemId() == R.id.toolbar_list) { showEventList(); }
        if(item.getItemId() == R.id.toolbar_share) { eventDetails.share(); }
        if(item.getItemId() == R.id.toolbar_manager) { eventDetails.setManager(); }
        if(item.getItemId() == R.id.toolbar_course) { showCourseList(); }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.toolbar_share).setVisible(isSharable);
        menu.findItem(R.id.toolbar_manager).setVisible(eventDetails.isVisible());
        return true;
    }

    public void hideKeyboard(){
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }


    /*
     * ------------------------------------------------------------------------
     *
     * INTENT - SEARCH
     *
     * ------------------------------------------------------------------------
     */

    //DEPRECIATED, do not remove from codebase.
    @Override protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    // DEPRECIATED, do not remove from codebase.
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            assetLoaderStatic.setQuery(query);
            showEventList();
        } else {
            assetLoaderStatic.setQuery(null);
            showEventList();
        }
    }


    /*
     * ------------------------------------------------------------------------
     *
     * MAP
     *
     * ------------------------------------------------------------------------
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        for(int i = 0; i < permissions.length; i++){
            if(permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[i] == -1) {
                //TODO better exit
                System.exit(2);
            }
        }
    }

    public void showOnMap(View view) {
        showMap();
        eventListOnMapReady.centerOnEvent = eventDetails.getEvent();
    }

    public void intentGMRoute(View view){

        char transportMode;
        switch(eventDetails.getTransportMode()){
            case BIKE: transportMode = 'b'; break;
            case WALK: transportMode = 'w'; break;
            case DRIVE:
            default:transportMode = 'd';
        }
        Event event = eventDetails.getEvent();
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+event.fields.geolocalisation[0]+","+event.fields.geolocalisation[1]+"&mode="+transportMode);

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

    public void onRateClicked(View view) {
        eventDetails.setRate(assetLoaderFirebase);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.driveRadioButton:
                if (checked)
                    eventDetails.setTransportMode(EventDetails.TransportMode.DRIVE);
                break;
            case R.id.walkRadioButton:
                if (checked)
                    eventDetails.setTransportMode(EventDetails.TransportMode.WALK);
                break;
            case R.id.bikeRadioButton:
                if (checked)
                    eventDetails.setTransportMode(EventDetails.TransportMode.BIKE);
                break;
        }
    }


    /*
     * ------------------------------------------------------------------------
     *
     * COURSES
     *
     * ------------------------------------------------------------------------
     */

    // When an event is added to current course.
    public void addToCourse(Event event) {
        System.out.println("AddToCourse : " + event.recordid);
        if(course.add(event)) {
            Toast.makeText(this, "Event added to course.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Event already in course.", Toast.LENGTH_LONG).show();
        }
        courseList.setEventList(course.getEvents());
    }

    public void removeFromCourse(Event event) {
        System.out.println("RemoveFromCourse : " + event.recordid);
        course.remove(event);
        courseList.setEventList(course.getEvents());
    }

    public boolean publishCourse(String name) {
        System.out.println("PublishCourse : " + name);
        System.out.println(course.getEvents().size() > 0);
        System.out.println(name != "");
        if((course.getEvents().size() > 0) && (name != "")) {
            Toast.makeText(this, "Course published.", Toast.LENGTH_LONG).show();
            course.setName(name);
            assetLoaderFirebase.saveCourse(course);
            course = new Course();
            courseList.setEventList(course.getEvents());
            return true;
        } else {
            Toast.makeText(this, "Course not published (add at least an event and a name).", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    // when a course is retrieved from server.
    public void addCourse(Course course) {
        System.out.println("MainActivity#addCourse");
        if(!course.isInList(courses)) {
            course.populateEvents(getAssetLoaderStatic().getEvents());
            courses.add(course);
            courseList.setCourseList(courses);
            System.out.println(courses.size());
        }
    }


    /*
     * ------------------------------------------------------------------------
     *
     * GETTERS - SETTERS
     *
     * ------------------------------------------------------------------------
     */

    public AssetLoaderStatic getAssetLoaderStatic() {
        return assetLoaderStatic;
    }

    public AssetLoaderFirebase getAssetLoaderFirebase() {
        return assetLoaderFirebase;
    }
}
