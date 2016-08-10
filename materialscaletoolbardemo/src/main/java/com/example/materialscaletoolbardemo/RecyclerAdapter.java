package com.example.materialscaletoolbardemo;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GourdBoy on 2016/8/10.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context mContext;
    private List<String> mDatas;
    private final int HEADER_TYPE = 1;
    private final int ITEM_TYPE = 0;
    @Nullable @BindView(R.id.img_header_bg)
    ImageView img_header_bg;
    private final View view;

    public RecyclerAdapter(Context context, List<String> datas)
    {
        this.mContext = context;
        this.mDatas = datas;
        view = LayoutInflater.from(mContext).inflate(R.layout.header, null, false);
        ButterKnife.bind(this,view);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType==ITEM_TYPE)
        {
            return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.itemview, parent, false));
        }
        else
        {
            return new MyHeadHolder(view);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if(holder instanceof MyHolder)
        {
            ((MyHolder)holder).tv.setText(mDatas.get(position-1));
        }
    }
    @Override
    public int getItemCount()
    {
        return mDatas.size()+1;
    }
    class MyHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.descr_tv)
        public TextView tv;
        public MyHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class MyHeadHolder extends RecyclerView.ViewHolder
    {
        public MyHeadHolder(View itemView)
        {
            super(itemView);
        }
    }
    @Override
    public int getItemViewType(int position)
    {
        if(position==0)
        {
            return HEADER_TYPE;
        }
        else
        {
            return ITEM_TYPE;
        }
    }
}
