package cn.faker.repaymodel.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * recyclerview item的间隔
 * Created by Administrator on 2017/9/21 0021.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int left;
    private int right;
    private int top;
    private int bottom;

    private float divderLeft;
    private float divderRight;
    private float divderTop;
    private float divderBottom;

    public static final int SURROUND = 3;

    public SpaceItemDecoration(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public SpaceItemDecoration(int length) {
        left = right = top = bottom = length / 2;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        int model = ((LinearLayoutManager) manager).getOrientation();
        int child_Position = parent.getChildAdapterPosition(view);
        int child_Count = parent.getChildCount();

        if (manager == null || child_Count == -1) {
            return;
        }

        switch (model) {
            case LinearLayoutManager.HORIZONTAL: {
                if (child_Position == 1) {
                    outRect.left = left + right;
                    outRect.right = right;
                } /*else if (child_Position == child_Count - 1) {
                    outRect.left = left;
                    outRect.right = left + right;
                }*/
                outRect.left = left;
                outRect.right = right;
                break;
            }
            case LinearLayoutManager.VERTICAL: {
                if (child_Position == 0) {
                    outRect.top = top + bottom;
                    outRect.bottom = bottom;
                }
                outRect.top = top;
                outRect.bottom = bottom;
                break;
            }
        }
    }

    private Paint mPaint;
    private int mOrientation = -1;
    private float mDividerHeight = 0;//分割线高度，默认为0px

    /**
     * 设置分割线
     *
     * @param divderHeight
     * @param dividerColor
     * @param mOrientation//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
     */
    public SpaceItemDecoration setDivider(float divderHeight, @ColorInt int dividerColor, int mOrientation) {
        this.mDividerHeight = divderHeight;
        this.mOrientation = mOrientation;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(divderHeight);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
        return this;
    }

    /**
     * 设置分割线 边距 单位dp
     *
     * @param context
     * @param divderLeft
     * @param divderRight
     * @param divderTop
     * @param divderBottom
     */
    public void setDividerPadding(Context context, float divderLeft, float divderRight, float divderTop, float divderBottom) {
        this.divderLeft = dptopx(context, divderLeft);
        this.divderRight = dptopx(context, divderRight);
        ;
        this.divderTop = dptopx(context, divderTop);
        ;
        this.divderBottom = dptopx(context, divderBottom);
        ;
    }

    private float dptopx(Context context, float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }


    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        if (mOrientation != -1) {
            //获得RecyclerView中总条目数量
            int childCount = parent.getChildCount();

            //遍历一下
            for (int i = 0; i < childCount; i++) {

                View childView = parent.getChildAt(i);

                float x = childView.getX();
                float y = childView.getY();
                int width = childView.getWidth();
                int height = childView.getHeight();
                if (mOrientation == LinearLayoutManager.VERTICAL) {
                    c.drawLine(x + divderLeft, y + height, x + width - divderRight, y + height, mPaint);
                } else if (mOrientation == LinearLayoutManager.HORIZONTAL) {
                    //根据这些点画条目的四周的线
                    c.drawLine(x, y, x + width, y, mPaint);
                    c.drawLine(x + width, y, x + width, y + height, mPaint);
                } else if (mOrientation == SURROUND) {
                    //根据这些点画条目的四周的线
                    c.drawLine(x, y, x + width, y, mPaint);
                    c.drawLine(x, y, x, y + height, mPaint);
                    c.drawLine(x + width, y, x + width, y + height, mPaint);
                    c.drawLine(x, y + height, x + width, y + height, mPaint);
                }

            }


        }

    }


}
