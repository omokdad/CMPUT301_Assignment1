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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class LogListActivity extends Activity {

    private static final String FILENAME = "log.json";
    private ListView loglist;
    private TextView total;

    private ArrayAdapter<Fillup> adapter;
    private float totalCost;

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

                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
                Menu edit_menu = popup.getMenu();
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.edit_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(LogListActivity.this, EditEntryActivity.class);

                        // learned from https://stackoverflow.com/questions/2405120/how-to-start-an-intent-by-passing-some-parameters-to-it on 31/01/2016
                        intent.putExtra("entry_pos",position);

                        startActivity(intent);
                        return true;
                    }
                });

                popup.show();

                return true;
            }
        });

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

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new LogListAdapter();
        loglist.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html 01-29 2016
            Type listType = new TypeToken<ArrayList<Fillup>>() {}.getType();
            FillupLog.fillups = gson.fromJson(in, listType);

            totalCost = 0;

            for (Fillup item : FillupLog.fillups){
                totalCost = totalCost + item.getCost();
            }

            total.setText(String.format("$ %.2f", totalCost));

        } catch (FileNotFoundException e) {
           FillupLog.fillups = new ArrayList<Fillup>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public class LogListAdapter extends ArrayAdapter<Fillup> {
        public LogListAdapter(){
            super(LogListActivity.this, R.layout.listview_item, FillupLog.fillups);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Fillup currentFillup = FillupLog.fillups.get(position);

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
