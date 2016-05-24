package rasalas.de.twodo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jann on 20.05.2016.
 */
public class ToDoDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TODO_DB";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table TODO (todo_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                                                                     "tag TEXT," +
                                                                     "duedate INTEGER NOT NULL," +
                                                                     "createdate INTEGER NOT NULL," +
                                                                     "description TEXT," +
                                                                     "priority INTEGER NOT NULL);";


    public ToDoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(ToDoDatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS TODO");
        onCreate(database);
    }
}
