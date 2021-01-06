package com.byted.camp.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 2019/1/22.
 *  *
 *  * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class TodoDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "todo.db";

    private static final int DB_VERSION = 1;

    public TodoDbHelper(Context paramContext) { super(paramContext, "todo.db", null, 1); }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase) { paramSQLiteDatabase.execSQL("CREATE TABLE note(_id INTEGER PRIMARY KEY AUTOINCREMENT, date INTEGER, state INTEGER, content TEXT)"); }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
        while (paramInt1 < paramInt2)
            paramInt1++;
    }

}
