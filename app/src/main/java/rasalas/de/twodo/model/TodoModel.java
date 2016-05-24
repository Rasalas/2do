package rasalas.de.twodo.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rasalas.de.twodo.database.ToDoDatabase;


/**
 * Created by Jann on 22.04.2016.
 */
public class TodoModel {

    private ToDoDatabase toDoDatabase;

    private List<Todo> todos;
    private TodoModelEventListener todoModelEventListener;

    private Timer expireTimer;
    private TimerTask expireTask;

    private static TodoModel instance;


    public static void init(Context context) {
        if (instance == null)
            instance = new TodoModel(context);
    }

    public static TodoModel getInstance() {
        return instance;
    }

    private TodoModel(Context context) {
        toDoDatabase = new ToDoDatabase(context);

        todos = new ArrayList<Todo>();
        expireTimer = new Timer();

        for (Todo todo : toDoDatabase.getAllToDos())
            insertTodo(todo);
    }

    private void insertTodo(Todo todo) {
        int i;

        synchronized (todos) {
            for (i = 0; i < todos.size(); i++) {
                if (todo.getDueDate().before(todos.get(i).getDueDate()))
                    break;
            }

            todos.add(i, todo);
        }

        if (i == 0)
            startNewTimerTask(todo);
    }

    public void addTodo(Todo todo) {
        insertTodo(todo);
        toDoDatabase.insertTodo(todo);
        notifyModelChanged();
    }

    public void removeTodo(Todo todo) {
        synchronized (todos) {
            int i;
            for (i = 0; i < todos.size(); i++) {
                if (todos.get(i) == todo) {
                    todos.remove(i);
                    if (i == 0) {
                        if (!todos.isEmpty())
                            startNewTimerTask(todos.get(0));
                    }
                    break;
                }
            }
        }

        toDoDatabase.deleteTodo(todo);
        notifyModelChanged();
    }

    private void startNewTimerTask(final Todo todo) {
        if (expireTask != null)
            expireTask.cancel();

        if (todo.getDueDate().before(new Date())) {
            notifyTodoExpired(todo);
            removeTodo(todo);
            return;
        }

        expireTimer.schedule(expireTask = new TimerTask() {
            @Override
            public void run() {
                notifyTodoExpired(todo);
                removeTodo(todo);
            }
        }, todo.getDueDate());
    }

    private void notifyModelChanged() {
        if (todoModelEventListener == null)
            return;

        synchronized (todos) {
            todoModelEventListener.onModelChanged(todos);
        }
    }

    private void notifyTodoExpired(Todo todo) {
        if (todoModelEventListener == null)
            return;

        todoModelEventListener.onTodoExpired(todo);
    }

    public void setTodoModelEventListener(TodoModelEventListener todoModelEventListener) {
        this.todoModelEventListener = todoModelEventListener;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public interface TodoModelEventListener {
        void onModelChanged(final List<Todo> todos);
        void onTodoExpired(Todo todo);
    }
}
