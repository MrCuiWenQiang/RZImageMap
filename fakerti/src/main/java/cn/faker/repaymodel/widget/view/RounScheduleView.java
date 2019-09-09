package cn.faker.repaymodel.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import cn.faker.repaymodel.R;


/**
 * 还款圆环进度
 * Created by Mr.C on 2017/11/8 0008.
 */

public class RounScheduleView extends View {

    private int mWidth;
    private int mHeight;

    private int r;//外部圆班级
    private int nr;//内部圆半径

    private Paint mPaint;
    private Paint nPaint;
    private Paint ArcPaint;
    private Paint NArcPaint;
    private Paint EArcPaint;
    private Paint textPaint;

    private String text = "加载中";//提示文字

    public RounScheduleView(Context context) {
        this(context, null);
    }

    public RounScheduleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RounScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorh));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);

        nPaint = new Paint();
        nPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        nPaint.setAntiAlias(true);
        nPaint.setStyle(Paint.Style.FILL);
        nPaint.setStrokeWidth(3);

        ArcPaint = new Paint();
        ArcPaint.setColor(ContextCompat.getColor(getContext(), R.color.ablue));
        ArcPaint.setAntiAlias(true);
        ArcPaint.setStyle(Paint.Style.FILL);

        NArcPaint = new Paint();
        NArcPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        NArcPaint.setAntiAlias(true);
        NArcPaint.setStyle(Paint.Style.FILL);

        EArcPaint = new Paint();
        EArcPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        EArcPaint.setAntiAlias(true);
        EArcPaint.setStyle(Paint.Style.STROKE);
        PathEffect effect = new DashPathEffect(new float[]{2, 10, 0, 0}, 0);
        EArcPaint.setPathEffect(effect);

        textPaint = new Paint();
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.ablue));
        textPaint.setAntiAlias(true);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, getResources().getDisplayMetrics());
        textPaint.setTextSize(size);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2, r, mPaint);
        canvas.drawCircle(mWidth / 2, mHeight / 2, nr, nPaint);

//        RectF rectF = new RectF(getLeft(), getTop(), getRight(), getBottom());
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        ArcPaint.setStrokeWidth(nr / 3);
        canvas.drawArc(rectF, -90, sweepAnger, true, ArcPaint);

        int r_width = r - nr;
        RectF nrectF = new RectF( r_width,  r_width, mWidth - r_width, mHeight - r_width);
        canvas.drawArc(nrectF, -90, sweepAnger, true, NArcPaint);

        EArcPaint.setStrokeWidth(nr / 3);
        getArc(canvas, mWidth / 2 - 1, mHeight / 2 - 1, mHeight / 2 - 1, -90, 360);

        Rect bounds = new Rect();
        textPaint.getTextBounds(text,0,text.length(),bounds);
        canvas.drawText(text, mWidth / 2-bounds.width()/2, mHeight / 2+bounds.height()/2, textPaint);
    }


    public void getArc(Canvas canvas, float o_x, float o_y, float r,
                       float startangel, float endangel) {
        RectF rect = new RectF(o_x - r, o_y - r, o_x + r, o_y + r);
        Path path = new Path();
        path.moveTo(o_x, o_y);
        path.lineTo((float) (o_x + r * Math.cos(startangel * Math.PI / 180))
                , (float) (o_y + r * Math.sin(startangel * Math.PI / 180)));
        path.lineTo((float) (o_x + r * Math.cos(endangel * Math.PI / 180))
                , (float) (o_y + r * Math.sin(endangel * Math.PI / 180)));
        path.addArc(rect, startangel, endangel - startangel);
        canvas.clipPath(path);
        canvas.drawCircle(o_x, o_y, r, EArcPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || heightSpecMode == MeasureSpec.EXACTLY) {
            mWidth = widthSpecSize;//这里的值为实际的值的3倍
            mHeight = heightSpecSize;
        } else {
            float defaultSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getContext().getResources().getDisplayMetrics());
            mHeight = (int) defaultSize;
            mWidth = (int) defaultSize;
        }
        if (mWidth < mHeight) {
            mHeight = mWidth;
        } else {
            mWidth = mHeight;
        }

        r = mHeight / 2;
        nr = r / 2;
        setMeasuredDimension(mWidth, mHeight);
    }

    public void setText(String text) {
        this.text = text;
    }

    private double rate = 0;
    private int sweepAnger = 0;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
        sweepAnger = (int) (360*getRate());
        invalidate();
    }


}
