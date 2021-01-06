package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.byted.camp.todolist.beans.Note;
import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoDbHelper;
import com.byted.camp.todolist.operation.activity.DatabaseActivity;
import com.byted.camp.todolist.operation.activity.DebugActivity;
import com.byted.camp.todolist.operation.activity.SettingActivity;
import com.byted.camp.todolist.ui.NoteListAdapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 1002;

    private RecyclerView recyclerView;
    private NoteListAdapter notesAdapter;
    private SQLiteDatabase database;
    private TodoDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(MainActivity.this, NoteActivity.class),
                        REQUEST_CODE_ADD);
            }
        });
        this.dbHelper = new TodoDbHelper((Context)this);
        this.database = this.dbHelper.getWritableDatabase();
        recyclerView = findViewById(R.id.list_todo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        notesAdapter = new NoteListAdapter(new NoteOperator() {
            @Override
            public void deleteNote(Note note) {
                MainActivity.this.deleteNote(note);
            }

            @Override
            public void updateNote(Note note) {
                MainActivity.this.updateNode(note);
            }
        });
        recyclerView.setAdapter(notesAdapter);

        notesAdapter.refresh(loadNotesFromDatabase());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            case R.id.action_debug:
                startActivity(new Intent(this, DebugActivity.class));
                return true;
            case R.id.action_database:
                startActivity(new Intent(this, DatabaseActivity.class));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD
                && resultCode == Activity.RESULT_OK) {
            notesAdapter.refresh(loadNotesFromDatabase());
        }
    }

    private List<Note> loadNotesFromDatabase() {
        // TODO 从数据库中查询数据，并转换成 JavaBeans
        if (this.database == null)
            return Collections.emptyList();
        LinkedList<Note> linkedList = new LinkedList();
        Cursor cursor = null;
        try {
            Cursor cursor1 = this.database.query("note", null, null, null, null, null, "date DESC");
            while (true) {
                cursor = cursor1;
                if (cursor1.moveToNext()) {
                    cursor = cursor1;
                    long l1 = cursor1.getLong(cursor1.getColumnIndex("_id"));
                    cursor = cursor1;
                    String str = cursor1.getString(cursor1.getColumnIndex("content"));
                    cursor = cursor1;
                    long l2 = cursor1.getLong(cursor1.getColumnIndex("date"));
                    cursor = cursor1;
                    int i = cursor1.getInt(cursor1.getColumnIndex("state"));
                    cursor = cursor1;
                    Note note = new Note(l1);
                    cursor = cursor1;
                    note.setContent(str);
                    cursor = cursor1;
                    note.setDate(new Date(l2));
                    cursor = cursor1;
                    note.setState(State.from(i));
                    cursor = cursor1;
                    linkedList.add(note);
                    continue;
                }
                break;
            }
            return linkedList;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    private void deleteNote(Note note) {
        // TODO 删除数据
        if (this.database == null)
            return;
        if (this.database.delete("note", "_id=?", new String[] { String.valueOf(note.id) }) > 0)
            this.notesAdapter.refresh(loadNotesFromDatabase());
    }

    private void updateNode(Note note) {
        // 更新数据
        if (this.database == null)
            return;
        ContentValues contentValues = new ContentValues();
        contentValues.put("state", Integer.valueOf((note.getState()).intValue));
        if (this.database.update("note", contentValues, "_id=?", new String[] { String.valueOf(note.id) }) > 0)
            this.notesAdapter.refresh(loadNotesFromDatabase());
    }

}
