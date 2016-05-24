package rasalas.de.twodo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

import rasalas.de.twodo.adapter.TodoAdapter;
import rasalas.de.twodo.model.Todo;
import rasalas.de.twodo.model.TodoModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TodoModel.TodoModelEventListener {

    private TodoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.setTitle(R.string.inbox);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewToDoActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.setCheckedItem(R.id.nav_inbox);

        TodoModel.init(this);
        TodoModel.getInstance().setTodoModelEventListener(this);

        RecyclerView rvTodos = (RecyclerView) findViewById(R.id.mainRV);
        adapter = new TodoAdapter(new ArrayList<Todo>());
        rvTodos.setAdapter(adapter);
        rvTodos.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
            this.setTitle(R.string.inbox);

        } else if (id == R.id.nav_today) {
            this.setTitle(R.string.today);

        } else if (id == R.id.nav_next7days) {
            this.setTitle(R.string.next7days);

        } else if (id == R.id.nav_label) {
            this.setTitle(R.string.label);

        } else if (id == R.id.nav_filter) {
            this.setTitle(R.string.filter);

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_feedback) {
            sendEmailFeedback();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setActionBarTitle(String heading) {
        this.setTitle(heading);
    }

    public void sendEmailFeedback() {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@rasalas.de"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "[2do] Feedback");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter.updateTodos(TodoModel.getInstance().getTodos());
    }

    @Override
    public void onModelChanged(final List<Todo> todos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO in den recycler view packen
                adapter.updateTodos(todos);
            }
        });
    }

    @Override
    public void onTodoExpired(Todo todo) {

    }
}
