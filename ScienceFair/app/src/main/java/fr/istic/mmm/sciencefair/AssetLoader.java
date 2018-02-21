package fr.istic.mmm.sciencefair;

import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import fr.istic.mmm.sciencefair.data.Event;

public class AssetLoader {

    public static final String SMALL  = "fr-esr-fete-de-la-science-17.small.json";
    public static final String MEDIUM = "fr-esr-fete-de-la-science-17.medium.json";
    public static final String BIG    = "fr-esr-fete-de-la-science-17.json";

    private AssetManager assets;
    private Gson gson;
    private String path;
    private List<Event> events;

    public AssetLoader(AssetManager assets, String path) {
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
        System.out.println("Start loading JSON");
        List<Event> events = Arrays.asList(gson.fromJson(loadString(), Event[].class));
        System.out.println("End loading JSON");
        return events;
    }

    public List<Event> getEvents() {
        return events;
    }

}
