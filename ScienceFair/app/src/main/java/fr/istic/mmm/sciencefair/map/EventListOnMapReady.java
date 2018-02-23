package fr.istic.mmm.sciencefair.map;

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
    private Location currentLocation;
    private int maxEvents = 100;

    public Event centerOnEvent = null;

    public EventListOnMapReady(MainActivity activity, LocationManager locationManager, EventList eventList) {
        this.locationManager = locationManager;
        this.eventList = eventList;
        this.activity = activity;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if(currentLocation == null){
                currentLocation = new Location(LocationManager.GPS_PROVIDER);
                // Rennes is default location
                currentLocation.setLatitude(48.111333);
                currentLocation.setLongitude(-1.672069);
            }
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

        if(centerOnEvent != null){
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerOnEvent.location, 15));
            centerOnEvent = null;
        }else {
            try {
                LatLngBounds bounds = builder.build();
                int padding = 50; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                googleMap.moveCamera(cu);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                Toast.makeText(activity, "No event was found closer to " + maxDist + " meters around you", Toast.LENGTH_LONG).show();
            }
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
            if(event.hasGeolocalisation()) {
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
