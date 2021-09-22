package com.example.submarine.fg;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.submarine.R;

import java.util.Timer;
import java.util.TimerTask;

public class BoatView extends AppCompatImageView {


    private Boolean type=false;
    public Boolean isDead=false;

    private void changeFormat(){
        if (isDead){
            setImageResource(R.drawable.explode);
            return;
        }
        if (type){
            setImageResource(R.drawable.boat_000);
        }else{
            setImageResource(R.drawable.boat_002);
        }
    }

    public BoatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        isDead=false;
        // 图片默认值
        Timer time =new Timer();
        setImageResource(R.drawable.boat_000);
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        type=!type;
                        changeFormat();
                    }
                });
            }
        },500,500);
    }

    //碰撞容错
    public boolean isCollsionWithRect( float x2, float y2, float w2, float h2) {
        h2-=120;
        w2-=50;
        if (this.getX() >= x2 && this.getX() >= x2 + w2) {
            return false;
        } else if (this.getX() <= x2 && this.getX() + this.getWidth() <= x2) {
            return false;
        } else if (this.getY() >= y2 && this.getY() >= y2 + h2) {
            return false;
        } else if (this.getY() <= y2 && this.getY() + this.getHeight() <= y2) {
            return false;
        }
            return true;
        }

}
