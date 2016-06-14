package rasalas.de.twodo;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rasalas.de.twodo.adapter.NTaskListAdapter;
import rasalas.de.twodo.model.MenuItem;
import rasalas.de.twodo.model.Todo;
import rasalas.de.twodo.model.TodoModel;

public class NewToDoActivity extends AppCompatActivity {

    private ArrayList<MenuItem> menuItems;
    private EditText task;
    private String description = "";
    Calendar calendar = Calendar.getInstance();

    ListView list;
    String[] web = {
            "Beschreibung",
            "Datum"
    } ;
    Integer[] imageId = {
            R.drawable.ic_announcement_black_24dp,
            R.drawable.ic_event_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        task = (EditText)findViewById(R.id.etTask);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       //.setAction("Action", null).show(); // TODO was macht snackbar ? :D

                Date dueDate = new Date(), createDate = new Date();
                //dueDate.setTime(dueDate.getTime()+3000);
                dueDate.setTime(calendar.getTimeInMillis());
                TodoModel.getInstance().addTodo(new Todo(task.getText().toString(), createDate, dueDate, description,1)); // TODO textfelder auslesen

                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadMenuItems();
    }


    private void loadMenuItems(){


        NTaskListAdapter adapter = new
                NTaskListAdapter(NewToDoActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                switch (position){
                    case 0: // Description
                        openDescriptionDialog();
                        break;
                    case 1: // Date
                        new DatePickerDialog(NewToDoActivity.this, date, calendar
                                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
                        break;
                }
            }
        });

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };
    private void updateLabel() {

        String myFormat = "dd.MM.yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);

        //task.setText(sdf.format(calendar.getTime()));
    }

    private void openDescriptionDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.description_dialog, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Beschreibung");
        alert.setView(promptView);

        final EditText input = (EditText) promptView
                .findViewById(R.id.etDescription);

        input.requestFocus();
        input.setTextColor(Color.BLACK);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                description = input.getText().toString();
            }

        });

        alert.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                Toast.makeText(getApplicationContext(),
                     "Cancel Clicked", Toast.LENGTH_SHORT).show();

            }
        });

        // create an alert dialog
        AlertDialog alert1 = alert.create();
        alert1.show();

    }
}



