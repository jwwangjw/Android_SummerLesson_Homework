package com.bytedance.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btn;
    private TextView tv;
    private Switch sw;
    private ImageView im;
    private CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        btn = findViewById(R.id.button);
        sw=findViewById(R.id.sw);
        im=findViewById(R.id.im);
        cb=findViewById(R.id.cb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                tv.setText("Hello android world!");
            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    im.setVisibility(View.VISIBLE);
                }else {
                    im.setVisibility(View.INVISIBLE);
                }
            }
        });

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tv.setText("HELLO");
                } else {
                    tv.setText("BYE");
                }
            }
        });
        Log.d(TAG, "MainActivity");
    }
}
