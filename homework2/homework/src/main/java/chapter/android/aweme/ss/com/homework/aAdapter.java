package chapter.android.aweme.ss.com.homework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.widget.CircleImageView;

public class aAdapter extends RecyclerView.Adapter<aAdapter.ViewH> {
    private Context ct;
    private List<Message> data;
    public aAdapter(Context ct1, List<Message> texts) {
        this.data=texts;
        this.ct=ct1;
    }

    @NonNull
    @Override
    public aAdapter.ViewH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=View.inflate(ct,R.layout.im_list_item,null);
        return new ViewH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull aAdapter.ViewH vh, int i) {
        vh.title.setText(String.valueOf(data.get(i).getTitle()));
        vh.description.setText(String.valueOf(data.get(i).getDescription()));
        vh.time.setText(String.valueOf(data.get(i).getTime()));
        String iconType = String.valueOf(data.get(i).getIcon());
        vh.imageView.setImageResource(R.drawable.icon_girl);
        if(iconType.equals("TYPE_ROBOT")) {
            vh.imageView.setImageResource(R.drawable.session_robot);
        } else if(iconType.equals("TYPE_GAME")) {
            vh.imageView.setImageResource(R.drawable.icon_micro_game_comment);
        } else if(iconType.equals("TYPE_SYSTEM")) {
            vh.imageView.setImageResource(R.drawable.session_system_notice);
        } else if(iconType.equals("TYPE_USER")){
            vh.imageView.setImageResource(R.drawable.icon_girl);
        }else if(iconType.equals("TYPE_STRANGER")){
            vh.imageView.setImageResource(R.drawable.session_stranger);
        }

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item 点击事件
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class ViewH extends RecyclerView.ViewHolder{
        public final TextView title;
        public final TextView description;
        public final TextView time;
        CircleImageView imageView;

        public ViewH(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_title);
            description = (TextView) v.findViewById(R.id.tv_description);
            time = (TextView) v.findViewById(R.id.tv_time);
            imageView = itemView.findViewById(R.id.iv_avatar);
        }
    }

}
