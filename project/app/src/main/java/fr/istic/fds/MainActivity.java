package fr.istic.fds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("fr-esr-fete-de-la-science-17.small.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public class Data {
        public String datasetid;
        public String recordid;
        public Date record_timestamp;

        @Override
        public String toString() {
            return "Data{" +
                    "datasetid='" + datasetid + '\'' +
                    ", recordid='" + recordid + '\'' +
                    ", record_timestamp=" + record_timestamp +
                    '}';
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View list = findViewById(R.id.list_main);

        Gson gson = new Gson();
        Data[] test = gson.fromJson(loadJSONFromAsset(), Data[].class);
        System.out.println("---");
        for(Data t : test) {
            System.out.println(t.toString());
        }
        System.out.println(test[0]);
        System.out.println("---");


    }
}
