package com.byted.camp.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.byted.camp.todolist.beans.State;
import com.byted.camp.todolist.db.TodoContract;
import com.byted.camp.todolist.db.TodoDbHelper;

public class NoteActivity extends AppCompatActivity {

    private EditText editText;
    private Button addBtn;
    private TodoDbHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.take_a_note);
        this.dbHelper = new TodoDbHelper((Context)this);
        this.database = this.dbHelper.getWritableDatabase();
        editText = findViewById(R.id.edit_text);
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.showSoftInput(editText, 0);
        }

        addBtn = findViewById(R.id.btn_add);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence content = editText.getText();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(NoteActivity.this,
                            "No content to add", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean succeed = saveNote2Database(content.toString().trim());
                if (succeed) {
                    Toast.makeText(NoteActivity.this,
                            "Note added", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                } else {
                    Toast.makeText(NoteActivity.this,
                            "Error", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean saveNote2Database(String content) {
        // TODO 插入一条新数据，返回是否插入成功
        SQLiteDatabase sQLiteDatabase = this.database;
        boolean bool = false;
        if (sQLiteDatabase != null) {
            if (TextUtils.isEmpty(content))
                return false;
            ContentValues contentValues = new ContentValues();
            contentValues.put("content", content);
            contentValues.put("state", Integer.valueOf(State.TODO.intValue));
            contentValues.put("date", Long.valueOf(System.currentTimeMillis()));
            if (this.database.insert("note", null, contentValues) != -1L)
                bool = true;
            return bool;
        }
        return false;
    }
}
