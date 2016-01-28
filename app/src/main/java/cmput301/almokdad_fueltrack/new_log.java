package cmput301.almokdad_fueltrack;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class new_log extends AppCompatActivity {

    private EditText entry_date;
    private int myYear, myMonth, myDay;
    private final int dialog_id = 0;
    private  String buffer, month_s, day_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Calendar cal = Calendar.getInstance();
        myYear = cal.get(Calendar.YEAR);
        myMonth = cal.get(Calendar.MONTH) + 1;
        myDay = cal.get(Calendar.DAY_OF_MONTH);


        showDialogOnClick();
    }

    public void showDialogOnClick(){
        entry_date = (EditText)findViewById(R.id.entry_date);

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
            return new DatePickerDialog(this, dplistner, myYear, myMonth, myDay);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
