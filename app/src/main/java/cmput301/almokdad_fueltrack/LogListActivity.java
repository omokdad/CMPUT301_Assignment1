package cmput301.almokdad_fueltrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

// The first and main activity in the app
// It shows the log of all the inputted entries
// It also provides a method for the entry of new entries or editing old ones
// It also computes and shows the running total of money spent on gas.



public class LogListActivity extends Activity {

    private static final String FILENAME = "log.json";
    private ListView loglist;
    private TextView total;

    private ArrayAdapter<Fillup> adapter;
    private float totalCost;
    private FillupLog log = new FillupLog();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_list_activity);
        Button new_entry_btn = (Button) findViewById(R.id.button_new_entry);

        loglist = (ListView) findViewById(R.id.log_list);
        total = (TextView) findViewById(R.id.total);

        loglist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                //setup of edit menu to show on long-click of entries.
                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                Menu edit_menu = popup.getMenu();
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.edit_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(LogListActivity.this, EditEntryActivity.class);

                        // learned from https://stackoverflow.com/questions/2405120/how-to-start-an-intent-by-passing-some-parameters-to-it on 31/01/2016
                        // it is a way to send parameters through activities in order to know which entry to edit.
                        intent.putExtra("entry_pos",position);

                        startActivity(intent);
                        return true;
                    }
                });

                popup.show();

                return true;
            }
        });

        // setup for add new  entry button.
        new_entry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogListActivity.this,
                        NewEntryActivity.class);
                startActivity(intent);

                adapter.notifyDataSetChanged();
            }
        });


    }

    // setup to read the savefile and load the data onto the listlayout at every start of the activity
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new LogListAdapter();
        loglist.setAdapter(adapter);
    }

    // code to read from json file mostly from in-lab lonelyTwitter app
    // this also calculates the running total of money spent
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-29 2016
            Type listType = new TypeToken<ArrayList<Fillup>>() {}.getType();
            log.setFillups ( (ArrayList<Fillup>) gson.fromJson(in, listType));

            totalCost = 0;

            for (Fillup item : log.getFillups()){
                totalCost = totalCost + item.getCost();
            }

            total.setText(String.format("$ %.2f", totalCost));

        } catch (FileNotFoundException e) {
           log = new FillupLog();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    // this private sub-class takes the layout from listview_item.xml
    // and uses it to provide the structure of the display of the arrayList onto the listviewer in the Activity.
    private class LogListAdapter extends ArrayAdapter<Fillup> {
        public LogListAdapter(){
            super(LogListActivity.this, R.layout.listview_item, log.getFillups());
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Fillup currentFillup = log.getFillup(position);

            TextView loc_Date = (TextView) view.findViewById(R.id.loc_date);
            loc_Date.setText(currentFillup.getDate());
            TextView loc_Station = (TextView) view.findViewById(R.id.loc_station);
            loc_Station.setText(currentFillup.getStation());
            TextView loc_Odometer = (TextView) view.findViewById(R.id.loc_odometer);
            loc_Odometer.setText(currentFillup.getOdometer());
            TextView loc_Grade = (TextView) view.findViewById(R.id.loc_grade);
            loc_Grade.setText(currentFillup.getGrade());
            TextView loc_Amount = (TextView) view.findViewById(R.id.loc_amount);
            loc_Amount.setText(currentFillup.getAmount());
            TextView loc_Unit = (TextView) view.findViewById(R.id.loc_unit);
            loc_Unit.setText(currentFillup.getUnit());
            TextView loc_Cost = (TextView) view.findViewById(R.id.loc_cost);
            loc_Cost.setText(String.format ("$%.2f",currentFillup.getCost()));

            return view;
        }
    }

}
