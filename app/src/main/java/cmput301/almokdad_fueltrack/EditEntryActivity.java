package cmput301.almokdad_fueltrack;

        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;

        import com.google.gson.Gson;

        import java.io.BufferedWriter;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.OutputStreamWriter;

// The entry-editing activity in the app
// It provides the space and prompts for inputted entries
// as well as shows the current unedited values in the textedits
// It also saves the changes onto the array-list (FillupLog) and the json file
// It also computes the new money spent on gas per fillup.

public class EditEntryActivity extends Activity {

        private static final String FILENAME = "log.json";

        private EditText entry_date;
        private EditText entry_station;
        private EditText entry_odometer;
        private EditText entry_grade;
        private EditText entry_amount;
        private EditText entry_unit;
        private FillupLog log = new FillupLog();
        private int myYear, myMonth, myDay;
        private final int dialog_id = 0;
        private  String buffer, month_s, day_s;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.new_entry_activity);

            Intent intent = getIntent();

            // to recieve the sent parameter from the previous activity
            // also from https://stackoverflow.com/questions/2405120/how-to-start-an-intent-by-passing-some-parameters-to-it
            final int entry_pos = intent.getIntExtra("entry_pos", -1);


            Fillup currentFillup = log.getFillup(entry_pos);

            entry_date = (EditText)findViewById(R.id.entry_date);
            entry_station = (EditText) findViewById(R.id.entry_station);
            entry_odometer = (EditText) findViewById(R.id.entry_odometer);
            entry_grade = (EditText) findViewById(R.id.entry_grade);
            entry_amount = (EditText) findViewById(R.id.entry_amount);
            entry_unit = (EditText) findViewById(R.id.entry_unit);
            Button save_entry = (Button) findViewById(R.id.button_save_entry);


            // loads the textboxes with the previous unedited data
            entry_station.setText(currentFillup.getStation());
            entry_odometer.setText(currentFillup.getOdometer().substring(0, currentFillup.getOdometer().length() - 2));
            entry_grade.setText(currentFillup.getGrade());
            entry_amount.setText(currentFillup.getAmount().substring(0, currentFillup.getAmount().length() - 1));
            entry_unit.setText(currentFillup.getUnit().substring(0, currentFillup.getUnit().length() - 3));

            myYear = Integer.parseInt(currentFillup.getDate().substring(0,4));
            myMonth = Integer.parseInt(currentFillup.getDate().substring(5,7));
            myDay = Integer.parseInt(currentFillup.getDate().substring(8,10));

            // for the datepicker
            showDialogOnClick();


            save_entry.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    setResult(RESULT_OK);

                    String new_date = entry_date.getText().toString();
                    String new_station = entry_station.getText().toString();
                    float new_odometer = Float.parseFloat(entry_odometer.getText().toString());
                    String new_grade = entry_grade.getText().toString();
                    float new_amount = Float.parseFloat(entry_amount.getText().toString());
                    float new_unit = Float.parseFloat(entry_unit.getText().toString());
                    float new_cost = new_amount * new_unit / 100;

                    String new_odometer_s = String.format("%.1f Km", new_odometer);
                    String new_amount_s = String.format("%.3f L", new_amount);
                    String new_unit_s = String.format("%.1f ¢/L", new_unit);


                    Fillup latestFillup = new Fillup(new_date, new_station, new_odometer_s, new_grade, new_amount_s, new_unit_s, new_cost);

                    // replace old entry with new, keeping the order
                    log.removeFillup(entry_pos);
                    log.addFillup(latestFillup,entry_pos);
                    saveInFile();
                    finish();
                }
            });

        }

    // showDialogOnClick, onCreateDialog, and onDateSetListner are functions heavily inspired by
    // https://stackoverflow.com/questions/18267091/open-a-datepickerdialog-on-click-of-edittext-takes-two-clicks
    // it provides the datepicker for choosing the date easier, as well as defaulting to the previously entered date
        public void showDialogOnClick(){

            month_s = (myMonth < 10 ? "0" : "") + myMonth;
            day_s = (myDay < 10 ? "0" : "") + myDay;

            buffer = String.format("%d-%s-%s",myYear,month_s,day_s);

            entry_date.setText(buffer);

            entry_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(dialog_id);
                }
            });
        }

        @Override
        protected Dialog onCreateDialog(int id){
            if (id == dialog_id)
                return new DatePickerDialog(this, dplistner, myYear, myMonth-1, myDay);
            return null;
        }

        private DatePickerDialog.OnDateSetListener dplistner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myYear = year;
                myMonth = monthOfYear + 1;
                myDay = dayOfMonth;

                month_s = (myMonth < 10 ? "0" : "") + myMonth;
                day_s = (myDay < 10 ? "0" : "") + myDay;

                buffer = String.format("%d-%s-%s",myYear,month_s,day_s);

                entry_date.setText(buffer);
            }
        };


        // Saves the current arraylist after the new addition into the json file
        // Mostly from in-lab lonelyTwitter app
        private void saveInFile() {
            try {
                FileOutputStream fos = openFileOutput(FILENAME, 0);
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
                Gson gson = new Gson();
                gson.toJson(log.getFillups(), out);
                out.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

}
