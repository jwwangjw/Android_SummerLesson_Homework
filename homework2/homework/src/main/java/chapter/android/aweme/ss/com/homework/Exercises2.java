package chapter.android.aweme.ss.com.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity {
    private TextView count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);
        count=findViewById(R.id.vw);
        count.setText(""+getAllChildViewCount(findViewById(R.id.iv_avatar_header)));
    }

    public int getAllChildViewCount(View view) {
        //todo 补全你的代码
        int countNum=0;
        if(view==null){
            return 0;
        }
        if(view instanceof ViewGroup){
            countNum++;
            for(int i=0;i<((ViewGroup)view).getChildCount();i++){
                View Vw=((ViewGroup)view).getChildAt(i);
                if(view instanceof ViewGroup){
                    countNum+=getAllChildViewCount(view);
                }else{
                    countNum++;
                }
            }
        }
        return countNum;
    }
}
