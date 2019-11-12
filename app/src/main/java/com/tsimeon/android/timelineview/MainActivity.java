package com.tsimeon.android.timelineview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tsimeon.android.timelineviewlib.TimeLineDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * date：2019/11/12 9:28
 * author：simeon
 * email：simeon@qq.com
 * description：
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView rvData;
    private TimeLineDecoration timeLineDecoration;
    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvData = findViewById(R.id.rv_data);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("item" + i);
        }
        timeLineDecoration = new TimeLineDecoration(this);
        adapter = new RVAdapter(data);
        rvData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rvData.addItemDecoration(timeLineDecoration);
        rvData.setAdapter(adapter);
    }

    public void typeChange(View view) {
        List<String> content = new ArrayList<>();
        List<String> title = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            title.add("物流信息" + i);
            content.add("2019年11月11日" + i);
        }
        timeLineDecoration.setContentDatas(content);
        timeLineDecoration.setTitleDatas(title);
        timeLineDecoration.setBitmapSize(15);
        //通知数据改变
        timeLineDecoration.setItemCircleColor(2, Color.parseColor("#FF0000"));
        timeLineDecoration.setItemCircleRaius(2, 6);
        timeLineDecoration.setItemCircleColor(5, Color.parseColor("#FF0000"));
        timeLineDecoration.setItemCircleRaius(5, 6);
        timeLineDecoration.setItemCircleColor(8, Color.parseColor("#FF0000"));
        timeLineDecoration.setItemCircleRaius(8, 6);
        timeLineDecoration.setItemCircleRaius(0, 8);
        timeLineDecoration.setItemBitmapRes(1, R.mipmap.ic_time_line);
        timeLineDecoration.setItemBitmapRes(3, R.mipmap.ic_time_line);
        timeLineDecoration.setItemBitmapSize(3, 25);
        adapter.notifyDataSetChanged();
    }


    private class RVAdapter extends RecyclerView.Adapter<RVAdapter.BaseViewHolder> {

        private List<String> data;

        RVAdapter(List<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view =
                    LayoutInflater.from(MainActivity.this).inflate(android.R.layout.simple_list_item_1,
                            parent, false);
            return new BaseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) holder.text.getLayoutParams();
            layoutParams.width = LayoutParams.WRAP_CONTENT;
            //            layoutParams.setMargins(30, 30, 30, 30);
            holder.text.setText(data.get(position));
            holder.text.setPadding(40, 60, 40, 60);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private class BaseViewHolder extends RecyclerView.ViewHolder {

            TextView text;

            BaseViewHolder(@NonNull View itemView) {
                super(itemView);
                text = itemView.findViewById(android.R.id.text1);
            }
        }
    }

}
