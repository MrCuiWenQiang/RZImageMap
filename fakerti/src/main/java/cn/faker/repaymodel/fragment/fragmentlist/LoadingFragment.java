package cn.faker.repaymodel.fragment.fragmentlist;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import cn.faker.repaymodel.R;
import cn.faker.repaymodel.fragment.BaseFragment;

/**
 * 加载
 * Created by Mr.C on 2017/11/29 0029.
 */

public class LoadingFragment extends BaseFragment {
    private TextView main_message;
    private ImageView pro_im;
    private TranslateAnimation alphaAnimation2;

    public static LoadingFragment newInstance() {
        Bundle args = new Bundle();
        LoadingFragment fragment = new LoadingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fg_loading;
    }

    @Override
    public void initview(View v) {
        main_message = (TextView) v.findViewById(R.id.main_message);
        pro_im = (ImageView) v.findViewById(R.id.pro_im);
        alphaAnimation2 = new TranslateAnimation(0f, 0F, 0F, -50F);
        alphaAnimation2.setDuration(500);
        alphaAnimation2.setRepeatCount(Animation.INFINITE);
        alphaAnimation2.setRepeatMode(Animation.REVERSE);
        alphaAnimation2.setInterpolator(new AccelerateDecelerateInterpolator());
        pro_im.setAnimation(alphaAnimation2);
        alphaAnimation2.start();
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroy() {
        alphaAnimation2.cancel();
        alphaAnimation2 = null;
        super.onDestroy();
    }
}
