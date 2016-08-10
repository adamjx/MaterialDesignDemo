package com.example.materialscaletoolbardemo;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.recyclerview_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.tb_main)
    Toolbar mToolBar;
    @BindView(R.id.tv_main_title)
    TextView mTextView;
    ImageView headerBg;
    //测量值
    @BindDimen(R.dimen.header_height)
    float headerHeight;//顶部高度
    @BindDimen(R.dimen.abc_action_bar_default_height_material)
    float minHeaderHeight;//顶部最低高度，即Bar的高度
    @BindDimen(R.dimen.float_title_left_margin)
    float floatTitleLeftMargin;//header标题文字左偏移量
    @BindDimen(R.dimen.float_title_size)
    float floatTitleSize;//header标题文字大小
    @BindDimen(R.dimen.float_title_size_large)
    float floatTitleSizeLarge;//header标题文字大小（大号）
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<String> mDatas = new ArrayList<>();
        for(int i=97;i<123;i++)
        {
            char s = (char) i;
            mDatas.add(String.valueOf(s));
        }
        RecyclerAdapter adapter = new RecyclerAdapter(this,mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
//        initMeasure();
//        initHeader();
        headerBg = adapter.img_header_bg;
        initEvent();
    }
    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        floatTitleLeftMargin = getResources().getDimension(R.dimen.float_title_left_margin);
        floatTitleSize = getResources().getDimension(R.dimen.float_title_size);
        floatTitleSizeLarge = getResources().getDimension(R.dimen.float_title_size_large);
    }
    private void initHeader() {
        View headerContainer = LayoutInflater.from(this).inflate(R.layout.header, null, false);
        headerBg = (ImageView) headerContainer.findViewById(R.id.img_header_bg);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void initEvent()
    {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                /**
                 * 获取Y轴偏移量
                 */
                View view = recyclerView.getChildAt(0);
                LinearLayoutManager layoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                int top = view.getTop();
                float mheaderHeight = 0;
                if(firstVisiblePosition>=1)
                {
                    mheaderHeight = headerHeight;
                }
                float scrollY = -top+firstVisiblePosition * view.getHeight() + mheaderHeight;
//                Log.i("xxxxxxxxx","x:  "+dx+"  y:  "+dy);
                Log.i("xxxxxxxxx","top:  "+top+"  firstVisiblePosition:  "+firstVisiblePosition+"  mheaderHeight:  "+mheaderHeight);
                Log.i("xxxxxxxxx","scrollY:  "+scrollY);
                //变化率
                float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
                float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
                //Toolbar背景色透明度
                mToolBar.setBackgroundColor(Color.argb((int) (offset * 255), 0, 0, 0));
                //header背景图Y轴偏移
                headerBg.setTranslationY(scrollY / 2);
                /*** 标题文字处理 ***/
                //标题文字缩放圆心（X轴）
                mTextView.setPivotX(mTextView.getLeft() + mTextView.getPaddingLeft());
                //标题文字缩放比例
                float titleScale = floatTitleSize / floatTitleSizeLarge;
                //标题文字X轴偏移
                mTextView.setTranslationX(floatTitleLeftMargin * offset);
                //标题文字Y轴偏移：（-缩放高度差 + 大文字与小文字高度差）/ 2 * 变化率 + Y轴滑动偏移
                mTextView.setTranslationY(
                        (-(mTextView.getHeight() - minHeaderHeight) +//-缩放高度差
                                mTextView.getHeight() * (1 - titleScale))//大文字与小文字高度差
                                / 2 * offset +
                                (headerHeight - mTextView.getHeight()) * (1 - offset));//Y轴滑动偏移
                //标题文字X轴缩放
                mTextView.setScaleX(1 - offset * (1 - titleScale));
                //标题文字Y轴缩放
                mTextView.setScaleY(1 - offset * (1 - titleScale));
                //判断标题文字的显示
                if (scrollY > headerBarOffsetY)
                {
                    mToolBar.setTitle("Toolbar Title");
                    mTextView.setVisibility(View.GONE);
                }
                else
                {
                    mToolBar.setTitle("");
                    mTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
