package cmput301.almokdad_fueltrack;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class LogListActivity extends Activity {

    private static final String FILENAME = "log.json";
    private ListView loglist;

    private ArrayList<Fillup> fillups = new ArrayList<Fillup>();
    private ArrayAdapter<Fillup> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);

        loglist = (ListView) findViewById(R.id.log_list);

    }

    @Override
    protected void onStart() {
        super.onStart();;
        loadFromFile();
        adapter = new ArrayAdapter<Fillup>(this,
                R.layout.activity_log_list, fillups);
        loglist.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-29 2016
            Type listType = new TypeToken<ArrayList<Fillup>>() {}.getType();
            fillups = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            fillups = new ArrayList<Fillup>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(fillups, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
