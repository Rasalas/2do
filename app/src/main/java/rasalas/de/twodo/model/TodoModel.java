package rasalas.de.twodo.model;

import android.content.Context;

import java.util.List;

import rasalas.de.twodo.alarm.AlarmHandler;
import rasalas.de.twodo.database.ToDoDatabase;


/**
 * Created by Jann on 22.04.2016.
 */
public class TodoModel {

    private ToDoDatabase toDoDatabase;

    private AlarmHandler alarmHandler;

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

        alarmHandler = new AlarmHandler(context);
        alarmHandler.setContext(context);

        for (Todo todo : toDoDatabase.getAllToDos()) {
            alarmHandler.addTodo(todo);
        }
    }

    // Called for adding new Todos
    public void addTodo(Todo todo) {
        alarmHandler.addTodo(todo);
        toDoDatabase.insertTodo(todo);
        notifyModelChanged();
    }

    public void removeTodo(Todo todo) {
        toDoDatabase.deleteTodo(todo);
        alarmHandler.removeTodo(todo);
        notifyModelChanged();
    }

    public void removeTodo(long todoID) {
        toDoDatabase.deleteTodo(todoID);
        alarmHandler.removeTodo(todoID);
        notifyModelChanged();
    }

    public void removeTodo(int index) {
        List<Todo> todos = toDoDatabase.getAllToDos();
        if (index < 0 || index >= todos.size())
            return;

        Todo todo = todos.get(index);
        removeTodo(todo);
    }

    private void notifyModelChanged() {
        if (todoModelEventListener == null)
            return;

        todoModelEventListener.onModelChanged(toDoDatabase.getAllToDos());
    }

    public void setTodoModelEventListener(TodoModelEventListener todoModelEventListener) {
        this.todoModelEventListener = todoModelEventListener;
    }

    public List<Todo> getTodos() {
        return toDoDatabase.getAllToDos();
    }

    public interface TodoModelEventListener {
        void onModelChanged(final List<Todo> todos);
    }

    public void setContext(Context context) {
        this.context = context;
        alarmHandler.setContext(context);
    }

    public AlarmHandler getAlarmHandler() {
        return alarmHandler;
    }

    public ToDoDatabase getToDoDatabase() {
        return toDoDatabase;
    }
}