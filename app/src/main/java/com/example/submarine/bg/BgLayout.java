package com.example.submarine.bg;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.submarine.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BgLayout extends FrameLayout {

    Context context;
    AttributeSet attrs;


    List<ValueAnimator> animatorList = new ArrayList<>();
    public static List<BarView> barViews=new ArrayList<>();
    Timer timer = null;

    public BgLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
    }

    public void start(){
        removeAllViews();
        barViews.removeAll(barViews);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 把createBar()添加到消息队列
                post(new Runnable() {
                    @Override
                    public void run() {
                        createBar();
                    }
                });
            }
        },1000L,2500L);
    }

    public void stop(){
        if( timer != null){
            timer.cancel();
        }

        for(ValueAnimator animator: animatorList){
            if(animator != null){
                animator.cancel(); // 停止动画
            }
        }
    }

    public void createBar(){
        BarView barView = new BarView(context, attrs);
        addView(barView);
        barViews.add(barView);

        // 动画效果
        ValueAnimator animator = ValueAnimator.ofFloat(ScreenUtil.getScreenWidth(context), -barView.barWidth);
        // 速度变化时间
        animator.setDuration(5000L);
        // 线性加速
        animator.setInterpolator(new LinearInterpolator());
        // 对坐标赋值
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                barView.x = (Float)animation.getAnimatedValue();
                barView.postInvalidate();
                // 离开就移除
                if(barView.x <= -barView.barWidth){
                    removeView(barView);
                    barViews.remove(barView);
                    // 删除动画效果
                    animatorList.remove(animator);
                }
            }
        });

        animator.start();
        // 加入list
        animatorList.add(animator);
    }

}
// BgLayout 是容器（背景），放n个柱子