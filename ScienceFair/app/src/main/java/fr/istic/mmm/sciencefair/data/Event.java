package fr.istic.mmm.sciencefair.data;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import java.util.Observer;


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
        public String image;
        public String apercu;
    }

    private Observer observer;

    public void attach(Observer observer) {
        this.observer = observer;
    }

    public LatLng location;

    private EventFirebase eventFirebase;

    public EventFirebase getEventFirebase() {
        return eventFirebase;
    }

    public void setEventFirebase(EventFirebase eventFirebase) {
        this.eventFirebase = eventFirebase;
        if(observer != null){
            observer.update(null, this);
        }
    }

    public boolean hasGeolocalisation() {
        return (fields.geolocalisation != null) && (fields.geolocalisation.length >= 2);
    }
}
