package fr.istic.mmm.sciencefair.data;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;


public class Event {

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

    public LatLng location;
}
