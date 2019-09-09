package cn.faker.repaymodel.widget.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import cn.faker.repaymodel.R;

/**
 * 实现了点击事件的Recycleview Adapter
 * 需要继承 BaseRecycleAdapter和 BaseViewHolder  并在onBingViewHolder调用 BaseViewHolder(View itemView, final OnItemListener onItemListener) 方可使用
 * Created by Mr.C on 2017/11/10 0010.
 */

public class BaseRecycleView extends RecyclerView {

    //是否执行入场动画
    private boolean isAnimation;

    private Context mContext;

    public BaseRecycleView(Context context) {
        this(context, null, 0);
    }

    public BaseRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }


    public void setisAnimation(boolean animation) {
        isAnimation = animation;
        if (animation) {
            LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_animation_fall_down);
            this.setLayoutAnimation(animationController);
        }
    }

    public static abstract class BaseRecycleAdapter<T extends ViewHolder> extends RecyclerView.Adapter<T> {

        protected OnItemClickListener onItemListener;

        public void setOnItemListener(OnItemClickListener onItemListener) {
            this.onItemListener = onItemListener;
        }


    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, T data, int position);
    }

}
