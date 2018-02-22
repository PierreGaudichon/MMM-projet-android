package fr.istic.mmm.sciencefair.map;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.SortedMap;
import java.util.TreeMap;

import fr.istic.mmm.sciencefair.MainActivity;
import fr.istic.mmm.sciencefair.data.Event;
import fr.istic.mmm.sciencefair.fragments.EventList;

public class EventListOnMapReady implements OnMapReadyCallback {

    private final LocationManager locationManager;
    private final EventList eventList;
    private final MainActivity activity;
    private double maxDist = 10000000; // in meters
    private Location currentLocation = new Location(LocationManager.GPS_PROVIDER);
    private int maxEvents = 100;

    public EventListOnMapReady(MainActivity activity, LocationManager locationManager, EventList eventList) {
        this.locationManager = locationManager;
        this.eventList = eventList;
        this.activity = activity;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            currentLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnCameraIdleListener(() ->
                handleIdleCamera(googleMap)
            );
        }catch(SecurityException e){
            e.printStackTrace();
        }

        googleMap.setOnMarkerClickListener(this::handleInfoWindowClose);
//        googleMap.setOnInfoWindowClickListener(this::handleInfoWindowClick);
//        googleMap.setOnInfoWindowCloseListener(this::handleInfoWindowClose);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        SortedMap<Double, Event> distanceMap = getSortedDistanceMap(currentLocation);

        addToGoogleMap(googleMap, distanceMap, builder);

        try {
            LatLngBounds bounds = builder.build();
            int padding = 50; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            googleMap.moveCamera(cu);
        }catch (IllegalStateException e){
            e.printStackTrace();
            Toast.makeText(activity, "No event was found closer to " + maxDist + " meters around you", Toast.LENGTH_LONG).show();
        }
    }

    private boolean handleInfoWindowClose(Marker marker) {
        activity.showEventDetails((Event) marker.getTag());
        return true;
    }

    private boolean handleMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    private boolean handleInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
        return true;
    }

    private SortedMap<Double, Event> getSortedDistanceMap(Location locationTo){
        SortedMap<Double, Event> sortedMap = new TreeMap<>();

        Location location = new Location(LocationManager.GPS_PROVIDER);
        for(Event event : eventList.getEvents()){
            if(event.fields.geolocalisation != null) {
                LatLng latLng = new LatLng(event.fields.geolocalisation[0], event.fields.geolocalisation[1]);
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                double distance = location.distanceTo(locationTo);
                event.location = latLng;
                if(distance < maxDist){
                    sortedMap.put(distance, event);
                }
            }
        }
        return sortedMap;
    }

    private void addToGoogleMap(GoogleMap googleMap, SortedMap<Double, Event> distanceMap, LatLngBounds.Builder builder){
        int maxEventsTemp = maxEvents;
        for(Event event : distanceMap.values()){
            if(maxEventsTemp-- <= 0){
                return;
            }
            Marker marker = googleMap.addMarker(new MarkerOptions().position(event.location).title(event.fields.titre_fr));
            marker.setTag(event);
            if(builder != null){
                builder.include(event.location);
            }
        }
    }

    private void handleIdleCamera(GoogleMap googleMap) {
        Location locationTarget = new Location(LocationManager.GPS_PROVIDER);
        googleMap.clear();
        LatLng latLngTarget = googleMap.getCameraPosition().target;
        locationTarget.setLatitude(latLngTarget.latitude);
        locationTarget.setLongitude(latLngTarget.longitude);
        SortedMap<Double, Event> distanceMap = getSortedDistanceMap(locationTarget);
        addToGoogleMap(googleMap, distanceMap, null);
    }
}
