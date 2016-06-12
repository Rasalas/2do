package rasalas.de.twodo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

import rasalas.de.twodo.adapter.NewTaskAdapter;
import rasalas.de.twodo.model.MenuItem;
import rasalas.de.twodo.model.Todo;
import rasalas.de.twodo.model.TodoModel;

public class NewToDoActivity extends AppCompatActivity {

    private ArrayList<MenuItem> menuItems;
    private EditText task;


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
                dueDate.setTime(dueDate.getTime()+3000);

                TodoModel.getInstance().addTodo(new Todo(task.getText().toString(), createDate, dueDate, "Zusatzinfo",1)); // TODO textfelder auslesen
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //loadMenuItems();
    }

    private void loadMenuItems() {
        menuItems = new ArrayList<>();
        RecyclerView rvMenuItems = (RecyclerView) findViewById(R.id.newTaskRV);

        menuItems = MenuItem.createMenuList();

        NewTaskAdapter adapter = new NewTaskAdapter(menuItems);

        rvMenuItems.setAdapter(adapter);

        rvMenuItems.setLayoutManager(new LinearLayoutManager(this));
    }


}
