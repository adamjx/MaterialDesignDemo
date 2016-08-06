package com.example.shawn.materialdesigntest;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shawn.materialdesigntest.divideline.DividerItemDecoration;
import com.example.shawn.materialdesigntest.divideline.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity
{
    private List<String> mDatas;
    private RecyclerView rvToDoList;
    private HomeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        rvToDoList = (RecyclerView) findViewById(R.id.rvToDoList);
//        rvToDoList.setLayoutManager(new LinearLayoutManager(this));
        rvToDoList.setLayoutManager(new GridLayoutManager(this,4));
//        rvToDoList.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        rvToDoList.setAdapter(mAdapter);
//        rvToDoList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        rvToDoList.addItemDecoration(new GridDividerItemDecoration(this));
    }
    private void initData()
    {
        mDatas = new ArrayList<>();
        mAdapter = new HomeAdapter();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }
    private class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.itemview,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView tv;
            public MyViewHolder(View itemView)
            {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.id_num);
            }
        }
    }
}
