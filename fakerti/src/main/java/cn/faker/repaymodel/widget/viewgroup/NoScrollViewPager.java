package cn.faker.repaymodel.widget.viewgroup;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止滚动的ViewPager 且切换不经过中间页
 * 因业务逻辑开启滚动
 * Created by Administrator on 2017/9/11 0011.
 */

public class NoScrollViewPager extends CurrentViewPager{
    public NoScrollViewPager(Context context) {
        super(context);
    }
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
