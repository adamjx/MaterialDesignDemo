package com.example.imageareaclickdemo;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.papperindex_img)
    ImageView papperindex;
    @BindView(R.id.root_rl)
    RelativeLayout mRoot;
    String json = "{\"width\":\"312\",\"height\":\"54\",\"left\":\"8\",\"top\":\"84\",\"snapshotWidth\":\"330\"" +
            ",\"snapshotHeight\":\"519\"}";
    Bean papperinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        papperindex.setImageResource(R.drawable.a01_s);
        papperinfo = new Gson().fromJson(json,Bean.class);
        papperindex.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                handler.sendEmptyMessage(1);
                papperindex.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    float scale = (float)papperindex.getWidth()/papperinfo.snapshotWidth;
                    Log.i("xxxxxxxxx","width is:   "+papperindex.getWidth()+"  scale:  "+scale);
                    initClickView(papperinfo.left,papperinfo.top,papperinfo.width,papperinfo.height,scale);
                    break;
            }
        }
    };
    private void initClickView(int left,int top,int width,int height,float scale)
    {
        final View view = new View(this);
        int left1 = (int) (left*scale);
        int top1 = (int) (top*scale);
        int width1 = (int)(width*scale);
        int height1 = (int)(height*scale);
        Log.i("xxxxxxxxx",left1+","+top1+","+width1+","+height1);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width1,height1);
        params.setMargins(left1,top1,0,0);
        view.setLayoutParams(params);
        mRoot.addView(view);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(MainActivity.this,"bingo!",Toast.LENGTH_SHORT).show();
                view.setBackgroundColor(Color.argb((int) (0.3 * 255), 0, 0, 0));
                TimerTask task = new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        Runnable runnable = new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                view.setBackgroundColor(Color.argb(0, 255, 255, 255));
                            }
                        };
                        runOnUiThread(runnable);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task,500);
            }
        });
    }
}
