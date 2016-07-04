package rasalas.de.twodo.alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import rasalas.de.twodo.R;
import rasalas.de.twodo.model.Todo;
import rasalas.de.twodo.model.TodoModel;

/**
 * Created by Jann on 01.07.2016.
 */
public class AlarmHandler extends BroadcastReceiver
{

    private Context context;


    public AlarmHandler(Context context) {
        this.context = context;
    }

    public AlarmHandler() {

    }

    public void addTodo(Todo todo) {
        if (todo.getDueDate().before(new Date()))
            return;

        setAlarm(todo);
    }

    public void removeTodo(Todo todo) {
        cancelAlarm(todo.getId());
    }

    public void removeTodo(long todoID) {
        cancelAlarm(todoID);
    }

    private void sendNotification(Context context, String title, String text) {
        int mNotificationId = 001;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(text);

        NotificationManager mNotifyMgr = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private void setAlarm(Todo todo)
    {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmHandler.class);
        i.putExtra("title", todo.getTag());
        i.putExtra("text", todo.getDescription());
        PendingIntent pi = PendingIntent.getBroadcast(context, (int)todo.getId(), i, PendingIntent.FLAG_ONE_SHOT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, todo.getDueDate().getTime(), 1000 * 60 * 30, pi); // Millisec * Second * Minute
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");
        String text = bundle.getString("text");

        sendNotification(context, title, text);
    }

    private void cancelAlarm(long todoID)
    {
        Intent intent = new Intent(context, AlarmHandler.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, (int)todoID, intent, PendingIntent.FLAG_NO_CREATE);
        if (sender == null)
            return;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
