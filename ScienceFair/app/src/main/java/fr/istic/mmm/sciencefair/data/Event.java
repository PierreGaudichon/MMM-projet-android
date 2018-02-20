package fr.istic.mmm.sciencefair.data;

import java.util.Date;


public class Event {

    public String datasetid;
    public String recordid;
    public Date record_timestamp;
    public Event.Fields fields;

    public class Fields {
        public String titre_fr;
        public String description_fr;
    }

}
