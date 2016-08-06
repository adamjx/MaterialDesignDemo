package com.example.shawn.materialdesigntest.divideline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.shawn.materialdesigntest.R;

/**
 * Created by Shawn on 2016/8/5.
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration
{
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDividerH;
    private Drawable mDividerV;
    public GridDividerItemDecoration(Context context)
    {
        TypedArray ta = context.obtainStyledAttributes(ATTRS);
        this.mDividerH = ta.getDrawable(0);
        this.mDividerV = ResourcesCompat.getDrawable(context.getResources(), R.drawable.divider_bgvertical, null);
        ta.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        Log.i("xxxxxxxxx","onDraw");
        drawHorizontal(c,parent);
        drawVertical(c,parent);
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        Log.i("xxxxxxxxx","getItemOffsets");
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int position = parent.getChildAdapterPosition(view);

        if(isLastRow(parent,position,spanCount,childCount))     //如果是最后一行，不绘制底部padding
        {
            outRect.set(0,0,mDividerV.getIntrinsicWidth(),0);
        }
        else if(isLastColum(parent,position,spanCount,childCount)) //如果是最后一列，不绘制右边padding
        {
            outRect.set(0,0,0,mDividerH.getIntrinsicHeight());
        }
        else
        {
            outRect.set(0,0,mDividerV.getIntrinsicWidth(),mDividerH.getIntrinsicHeight());
        }
    }
    private int getSpanCount(RecyclerView parent)
    {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager)
        {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        else if(layoutManager instanceof StaggeredGridLayoutManager)
        {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }
    private void drawHorizontal(Canvas c,RecyclerView parent)
    {
        int childCount  = parent.getChildCount();
        for(int i=0;i<childCount;i++)
        {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft()-params.leftMargin;
            int right = child.getRight()+params.rightMargin;
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top+mDividerH.getIntrinsicHeight();
            mDividerH.setBounds(left,top,right,bottom);
            mDividerH.draw(c);
        }
    }
    private void drawVertical(Canvas c,RecyclerView parent)
    {
        int childCount  = parent.getChildCount();
        for(int i=0;i<childCount;i++)
        {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight()+params.rightMargin;
            int right = left+mDividerV.getIntrinsicWidth();
            int top = child.getTop()-params.topMargin;
            /**
             * 此处注意，如果不加上mDividerH.getIntrinsicHeight()，则各个item之间会留有一个白色空格。
             * 但是如果这样写的话，在最后一行会有一个空格的过度绘制（因为在getItemOffsets设置的绘制
             * 范围是最后一行item不绘制底边，并且由于decoration 的 onDraw，child view 的 onDraw，decoration
             * 的onDrawOver，这三者是依次发生）
             */
            int bottom = child.getBottom()+params.bottomMargin+mDividerH.getIntrinsicHeight();
            mDividerV.setBounds(left,top,right,bottom);
            mDividerV.draw(c);
        }
    }
    private boolean isLastColum(RecyclerView parent,int pos,int spanCount,int chidCount)
    {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager)
        {
            //若是最后一列，不需要绘制右边
            if((pos+1)%spanCount==0)
            {
                return true;
            }
        }
        else if(layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            // StaggeredGridLayoutManager 纵向滚动
            if(orientation==StaggeredGridLayoutManager.VERTICAL)
            {
                //若是最后一列，不需要绘制右边
                if((pos+1)%spanCount==0)
                {
                    return true;
                }
            }
            // StaggeredGridLayoutManager 横向滚动
            else
            {
                chidCount = chidCount - chidCount%spanCount;
                if(pos>=chidCount)
                {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isLastRow(RecyclerView parent,int pos,int spanCount,int childCount)
    {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager)
        {
            childCount = childCount - childCount%spanCount;
            //如果是最后一行，不要绘制底部
            if(pos>=childCount)
            {
                return true;
            }
        }
        else if(layoutManager instanceof StaggeredGridLayoutManager)
        {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            // StaggeredGridLayoutManager 纵向滚动
            if(orientation==StaggeredGridLayoutManager.VERTICAL)
            {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            }
            else
            // StaggeredGridLayoutManager 横向滚动
            {
                if((pos+1)%spanCount==0)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
