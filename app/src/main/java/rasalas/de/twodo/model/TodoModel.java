package rasalas.de.twodo.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jann on 22.04.2016.
 */
public class TodoModel {

    private List<Todo> todos;
    private TodoModelEventListener todoModelEventListener;

    private Timer expireTimer;
    private TimerTask expireTask;


    public TodoModel() {
        todos = new ArrayList<Todo>();
        expireTimer = new Timer();
    }

    public void addTodo(Todo todo) {
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

        notifyModelChanged();
    }

    private void startNewTimerTask(final Todo todo) {
        if (expireTask != null)
            expireTask.cancel();

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


    public interface TodoModelEventListener {
        void onModelChanged(final List<Todo> todos);
        void onTodoExpired(Todo todo);
    }
}

