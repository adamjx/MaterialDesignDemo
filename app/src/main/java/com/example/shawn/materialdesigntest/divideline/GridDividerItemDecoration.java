package com.example.shawn.materialdesigntest.divideline;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by Shawn on 2016/8/5.
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration
{
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    public GridDividerItemDecoration(Context context)
    {
        TypedArray ta = context.obtainStyledAttributes(ATTRS);
        this.mDivider = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        drawHorizontal(c,parent);
        drawVertical(c,parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        int position = parent.getChildAdapterPosition(view);
        if(isLastRow(parent,position,spanCount,childCount))     //如果是最后一行，不绘制底部，
        {
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }
        else if(isLastColum(parent,position,spanCount,childCount))
        {
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }
        else
        {
            outRect.set(0,0,mDivider.getIntrinsicWidth(),mDivider.getIntrinsicHeight());
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
            int right = child.getRight()+params.rightMargin+mDivider.getIntrinsicWidth();
            int top = child.getTop()+params.topMargin;
            int bottom = top+mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
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
            int right = left+mDivider.getIntrinsicWidth();
            int top = child.getTop()-params.topMargin;
            int bottom = child.getBottom()+params.bottomMargin;
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
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
