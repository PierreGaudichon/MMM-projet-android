package fr.istic.mmm.sciencefair.data;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;


public class Event implements OnMapReadyCallback{

    public String datasetid;
    public String recordid;
    public Date record_timestamp;
    public Event.Fields fields;

    public class Fields {
        public String titre_fr;
        public String description_fr;
        public String description_longue_fr;
        public String lien;
        public double[] geolocalisation;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(fields.geolocalisation[0], fields.geolocalisation[1]);
        googleMap.addMarker(new MarkerOptions().position(location).title(fields.titre_fr));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
