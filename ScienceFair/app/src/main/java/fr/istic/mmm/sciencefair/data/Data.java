package fr.istic.mmm.sciencefair.data;

import java.util.Date;

import fr.istic.mmm.sciencefair.EventList;


public class Data {

    public String datasetid;
    public String recordid;
    public Date record_timestamp;
    public Data.Fields fields;

    public class Fields {
        public String titre_fr;
        public String description_fr;
    }

}
