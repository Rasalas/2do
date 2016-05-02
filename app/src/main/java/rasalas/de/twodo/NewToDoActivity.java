package rasalas.de.twodo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import rasalas.de.twodo.adapter.NewTaskAdapter;
import rasalas.de.twodo.model.MenuItem;

public class NewToDoActivity extends AppCompatActivity {
    ArrayList<MenuItem> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
