package com.example.recyclerviewstaggeredgriddemo.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.recyclerviewstaggeredgriddemo.R;

import java.util.List;

/**
 * Created by GourdBoy on 2016/8/6.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder>
{
    private Context mContext;
    private List<String> mDatas;
    private OnItemClickLitener mOnItemClickLitener;
    public RecyclerViewAdapter(Context context, List<String> datas)
    {
        this.mContext = context;
        this.mDatas = datas;
    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.itemview,parent,false));
    }
    @Override
    public void onBindViewHolder(final MyHolder holder, int position)
    {
        holder.tv.setText(mDatas.get(position));
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    removeData(pos);
                    return false;
                }
            });
        }
    }
    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
    public void addData(int position)
    {
        mDatas.add(position,"Insert One");
        notifyItemInserted(position);
    }
    public void removeData(int position)
    {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
    class MyHolder extends RecyclerView.ViewHolder
    {
        TextView tv;

        public MyHolder(View itemView)
        {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.id_num);
        }
    }
}
