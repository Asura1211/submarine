package com.example.submarine.bg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.submarine.R;
import com.example.submarine.utils.ScreenUtil;

public class BarView extends View{
    // bmp
    Bitmap bitmap, bitmap2;
    Paint paint;

    public float barWidth,barHeight;
    public float x,y,y2;
    Context context;
    Integer distance = 500;
    Integer num;

    public BarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        num = (int) (Math.random()*800);
        this.context = context;
        // 加载图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bar);
        barWidth = bitmap.getWidth();
        barHeight = bitmap.getHeight();
        // 初始状态，柱子出现在屏幕外
        x = -barWidth;
        y = -barHeight/2-num;

        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.bar2);
        // 初始状态，柱子出现在屏幕外
        y2 = barHeight/2+distance-num;

        // 创建画笔
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // left 左 top 上
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.drawBitmap(bitmap2, x, y2, paint);
    }

//     todo 下面的

}
