package rasalas.de.twodo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rasalas.de.twodo.model.Todo;


/**
 * Created by Jann on 20.05.2016.
 */
public class ToDoDatabase {

    private ToDoDatabaseHelper dbHelper;
    private SQLiteDatabase database;


    public ToDoDatabase(Context context){
        dbHelper = new ToDoDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void insertTodo(Todo todo) {
        ContentValues values = new ContentValues();
        values.put("tag", todo.getTag());
        values.put("duedate", todo.getDueDate().getTime());
        values.put("createdate", todo.getCreateDate().getTime());
        values.put("description", todo.getDescription());
        values.put("priority", todo.getPriority());
        todo.setId(database.insert("TODO", null, values));
    }

    public void deleteTodo(Todo todo) {
        database.delete("TODO", "todo_id = " + todo.getId(), null);
    }

    public void deleteTodo(long todoID) {
        database.delete("TODO", "todo_id = " + todoID, null);
    }

    public List<Todo> getAllToDos() {
        List<Todo> todos = new ArrayList<Todo>();

        String[] projection = {"todo_id", "tag", "duedate", "createdate", "description", "priority"};
        Cursor c = database.query("TODO", projection, null, null, null, null, null);
        if (c != null) {
            String tag;
            Date dueDate, createDate;
            String description;
            int priority;
            long id;
            Todo todo;

            c.moveToFirst();
            while (c.moveToNext()) {
                id = c.getInt(c.getColumnIndex("todo_id"));
                tag = c.getString(c.getColumnIndex("tag"));
                dueDate = new Date(c.getLong(c.getColumnIndex("duedate")));
                createDate = new Date(c.getLong(c.getColumnIndex("createdate")));
                description = c.getString(c.getColumnIndex("description"));
                priority = c.getInt(c.getColumnIndex("priority"));

                todo = new Todo(tag, createDate, dueDate, description, priority);
                todo.setId(id);
                todos.add(todo);
            }
        }

        return todos;
    }
}
