package fr.istic.mmm.sciencefair;

import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import fr.istic.mmm.sciencefair.data.Event;

public class AssetLoader {

    public static final String SMALL = "fr-esr-fete-de-la-science-17.small.json";

    private AssetManager assets;
    private Gson gson;

    public AssetLoader(AssetManager assets) {
        this.assets = assets;
        this.gson = new Gson();
    }

    private String loadString(String path) {
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

    public List<Event> loadData(String path) {
        return Arrays.asList(gson.fromJson(loadString(path), Event[].class));
    }

}
