package rasalas.de.twodo.model;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import rasalas.de.twodo.R;

/**
 * Created by Jann on 09.06.2016.
 */
public class NotificationHandler {

    private List<Todo> notificationTodos;

    private Timer expireTimer;
    private TimerTask expireTask;

    private Context context;


    public NotificationHandler(Context context) {
        this.context = context;

        notificationTodos = new ArrayList<Todo>();
        expireTimer = new Timer();
    }

    public void addTodo(Todo todo) {
        if (todo.getDueDate().before(new Date()))
            return;

        int i;

        for (i = 0; i < notificationTodos.size(); i++) {
            if (todo.getDueDate().before(notificationTodos.get(i).getDueDate()))
                break;
        }

        notificationTodos.add(i, todo);

        if (i == 0)
            startNewTimerTask(todo);
    }

    public void removeTodo(Todo todo) {
        int i;
        for (i = 0; i < notificationTodos.size(); i++) {
            if (notificationTodos.get(i) == todo) {
                notificationTodos.remove(i);
                if (i == 0) {
                    if (!notificationTodos.isEmpty())
                        startNewTimerTask(notificationTodos.get(0));
                }
                break;
            }
        }
    }

    private void startNewTimerTask(final Todo todo) {
        if (expireTask != null)
            expireTask.cancel();

        if (todo.getDueDate().before(new Date())) {
            removeTodo(todo);
            return;
        }

        expireTimer.schedule(expireTask = new TimerTask() {
            @Override
            public void run() {
                removeTodo(todo);
                sendNotification(todo);
            }
        }, todo.getDueDate());
    }

    private void sendNotification(Todo todo) {
        int mNotificationId = 001;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(todo.getTag())
                .setContentText(todo.getDescription());

        NotificationManager mNotifyMgr = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
