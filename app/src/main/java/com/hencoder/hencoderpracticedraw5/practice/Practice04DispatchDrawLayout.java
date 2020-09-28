package com.hencoder.hencoderpracticedraw5.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class Practice04DispatchDrawLayout extends LinearLayout {
    Pattern pattern = new Pattern();

    public Practice04DispatchDrawLayout(Context context) {
        super(context);
    }

    public Practice04DispatchDrawLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice04DispatchDrawLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**{
    *    setWillNotDraw(false);
    }*/

     /**
      * 伪代码：
      *   public void draw(Canvas canvas) {
      *     ...
      *
      *     drawBackground(Canvas); // 绘制背景（不能重写）
      *     onDraw(Canvas); // 绘制主体
      *     dispatchDraw(Canvas); // 绘制子 View
      *     onDrawForeground(Canvas); // 绘制滑动相关和前景
      *
      *     ...
      *  }​
      */
    // 把 onDraw() 换成 dispatchDraw()，让绘制内容可以盖住子 View
    // 另外，在改完之后，上面的 setWillNotDraw(false) 也可以删了

     @Override
     protected void dispatchDraw(Canvas canvas) {

         pattern.draw(canvas);
         super.dispatchDraw(canvas);
     }




    private class Pattern {
        private static final float PATTERN_RATIO = 5f / 6;

        Paint patternPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Spot[] spots;

        private Pattern() {
            spots = new Spot[4];
            spots[0] = new Spot(0.14f, 0.3f, 0.026f);
            spots[1] = new Spot(0.59f, 0.25f, 0.067f);
            spots[2] = new Spot(0.22f, 0.6f, 0.067f);
            spots[3] = new Spot(0.52f, 0.78f, 0.083f);
        }

        private Pattern(Spot[] spots) {
            this.spots = spots;
        }

        {
            patternPaint.setColor(Color.parseColor("#A0E91E63"));
        }

        private void draw(Canvas canvas) {
            int repitition = (int) Math.ceil((float) getWidth() / getHeight());
            for (int i = 0; i < spots.length * repitition; i++) {
                Spot spot = spots[i % spots.length];
                canvas.drawCircle(i / spots.length * getHeight() * PATTERN_RATIO + spot.relativeX * getHeight(), spot.relativeY * getHeight(), spot.relativeSize * getHeight(), patternPaint);
            }
        }

         private class Spot {
            private float relativeX;
            private float relativeY;
            private float relativeSize;

            private Spot(float relativeX, float relativeY, float relativeSize) {
                this.relativeX = relativeX;
                this.relativeY = relativeY;
                this.relativeSize = relativeSize;
            }
        }
    }
}
