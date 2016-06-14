package rasalas.de.twodo.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import rasalas.de.twodo.database.ToDoDatabase;


/**
 * Created by Jann on 22.04.2016.
 */
public class TodoModel {

    private ToDoDatabase toDoDatabase;

    private NotificationHandler notificationHandler;

    private List<Todo> todos;
    private TodoModelEventListener todoModelEventListener;

    private Context context;

    private static TodoModel instance;


    public static void init(Context context) {
        if (instance == null)
            instance = new TodoModel(context);
        else
            instance.setContext(context);
    }

    public static TodoModel getInstance() {
        return instance;
    }

    private TodoModel(Context context) {
        toDoDatabase = new ToDoDatabase(context);

        notificationHandler = new NotificationHandler(context);

        todos = new ArrayList<Todo>();

        for (Todo todo : toDoDatabase.getAllToDos())
            insertTodo(todo);
    }

    // Called for adding / loading existing Todos
    private void insertTodo(Todo todo) {
        todos.add(todo);
        notificationHandler.addTodo(todo);
    }

    // Called for adding / saving new Todos
    public void addTodo(Todo todo) {
        insertTodo(todo);
        toDoDatabase.insertTodo(todo);
        notifyModelChanged();
    }

    public void removeTodoById(int pos){
        int s = 0;
        for (Todo d: todos){
            s++;
            if(d.equals(todos.get(pos))){

                //todos.remove(d);

                notificationHandler.removeTodo(d);
                toDoDatabase.deleteTodo(d);
                //notifyModelChanged();
            }
        }
        //todos.remove(pos);
    }
    public void removeTodo(Todo todo) {
        todos.remove(todo);
        notificationHandler.removeTodo(todo);

        toDoDatabase.deleteTodo(todo);
        notifyModelChanged();
    }

    private void notifyModelChanged() {
        if (todoModelEventListener == null)
            return;

        synchronized (todos) {
            todoModelEventListener.onModelChanged(todos);
        }
    }

    public void setTodoModelEventListener(TodoModelEventListener todoModelEventListener) {
        this.todoModelEventListener = todoModelEventListener;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public interface TodoModelEventListener {
        void onModelChanged(final List<Todo> todos);
    }

    public void setContext(Context context) {
        this.context = context;
        notificationHandler.setContext(context);
    }
}
