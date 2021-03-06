package fr.istic.mmm.sciencefair;

import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.istic.mmm.sciencefair.data.Event;

public class AssetLoaderStatic {

    public static final String SMALL  = "fr-esr-fete-de-la-science-17.size-7.json";
    public static final String MEDIUM = "fr-esr-fete-de-la-science-17.size-100.json";
    public static final String BIG    = "fr-esr-fete-de-la-science-17.size-all.json";

    private AssetManager assets;
    private Gson gson;
    private String path;
    private List<Event> events;
    private String query;

    public AssetLoaderStatic(AssetManager assets, String path) {
        this.assets = assets;
        this.gson = new Gson();
        this.path = path;
        this.events = loadData();
    }

    private String loadString() {
        String json = null;
        try {
            InputStream stream = assets.open(path);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private List<Event> loadData() {
        List<Event> events = new ArrayList<>();
        System.out.println("Start loading JSON");
        for(Event event : Arrays.asList(gson.fromJson(loadString(), Event[].class))) {
            if(event.fields.titre_fr != null) { events.add(event); }
        }
        System.out.println("End loading JSON");
        return events;
    }

    public List<Event> getEvents() {
        if(query == null) {
            return events;
        } else {
            List<Event> result = new ArrayList<>();
            for(Event event : events) {
                String title = event.fields.titre_fr.toLowerCase();
                String q = query.toLowerCase();
                if(title.contains(q)) {
                    result.add(event);
                }
            }
            return result;
        }
    }

    public void setQuery(String query) { this.query = query; }
}
