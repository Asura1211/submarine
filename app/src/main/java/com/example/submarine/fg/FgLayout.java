package com.example.submarine.fg;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.submarine.R;
import com.example.submarine.bg.BarView;
import com.example.submarine.bg.BgLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FgLayout extends FrameLayout {

    Context context;
    AttributeSet attrs;

    BgLayout bgLayout;



    public BoatView boatView;
    boolean isStart = false;
    // 动画集合 让所有动画一起播放（together）
    AnimatorSet set;

    public FgLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
    }

    public void setBgLayout(BgLayout bgLayout){
        this.bgLayout=bgLayout;
    }

    public void start(){
        removeAllViews();
        boatView = new BoatView(context,attrs);

        //初始位置
        addView(boatView,200,200);
        boatView.setY(0);
        isStart = true;
        //开碰撞检测
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        coll();
                    }
            });
        }
    },500,500);
    }

    //游戏结束
    public void stop(){
        isStart = false;
        System.out.println("停");
        bgLayout.stop();
        // 潜艇撞柱子时，停止动画 否则仍会运动
        if(set != null)
            set.cancel();
        boatView.isDead=true;

    }

    public void moveTo(float x, float y){
        if(!isStart)
            return;

        float currX = boatView.getX();
        float currY = boatView.getY();

        float dy = y-currY;

        // 动画效果
        set = new AnimatorSet();

        ObjectAnimator animatorA = ObjectAnimator.ofFloat(boatView,"rotation",boatView.getRotation(),(float)(Math.toDegrees(Math.atan(dy/100))));

        Path path = new Path();
        path.moveTo(currX,currY);
        path.lineTo(x,y);
        ObjectAnimator animatorB = ObjectAnimator.ofFloat(boatView,"x", "y",path);

        set.setDuration(100L);
        set.playTogether(animatorA, animatorB);
        set.start();
    }

    //碰撞检测
    public void coll(){
        if (!isStart){return ;}
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<BgLayout.barViews.size();i++){
                            BarView temp=BgLayout.barViews.get(i);
                            if (boatView.isCollsionWithRect(temp.x,temp.y,
                                    temp.barWidth,temp.barHeight)){
                                stop();
                                break;
                            }
                            if (boatView.isCollsionWithRect(temp.x,temp.y2,
                                    temp.barWidth,temp.barHeight)){
                                stop();
                                break;
                            }
                        }
                    }
                });
            }
        },100,100);
        return;
    }
}
