package cn.faker.repaymodel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.faker.repaymodel.R;
import cn.faker.repaymodel.fragment.fragmentlist.LoadingFragment;
import cn.faker.repaymodel.fragment.fragmentlist.LoadingProgressFragment;
import cn.faker.repaymodel.fragment.fragmentlist.NoDataFragment;

/**
 * 用于管理Fragment状态切换   （是不是用Manger管理好点~ 烦躁 ）
 * （2017年11月29日 17:24:04 activity用manager试试好了）
 * <p>
 * Created by Mr.C on 2017/11/27 0027.
 */

public abstract class StateFragment extends BaseViewPagerFragment {
    private ViewGroup frameLayout;
    private Fragment mfragment;

    private ArrayList<View> mChildren;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = (ViewGroup) view.findViewById(R.id.fg_state);
        if (frameLayout == null) {
            throw new RuntimeException("We not get this view!");
        }
        if (mChildren == null) {
            mChildren = new ArrayList<View>();
        }
    }

    /**
     * 加载无数据的Fragment
     */
    protected void showNoData(String title) {
        NoDataFragment fragment = NoDataFragment.newInstance(title);
        setFrameLayout(fragment);
    }

    /**
     * 加载无数据的Fragment
     */
    protected void showNoData(NoDataFragment.OnClickRenovate onClickRenovate) {
        NoDataFragment fragment = NoDataFragment.newInstance();
        fragment.setOnClickRenovate(onClickRenovate);
        setFrameLayout(fragment);
    }

    /**
     * 加载无数据的Fragment
     */
    protected void showNoData(String title, NoDataFragment.OnClickRenovate onClickRenovate) {
        NoDataFragment fragment = NoDataFragment.newInstance(title);
        fragment.setOnClickRenovate(onClickRenovate);
        setFrameLayout(fragment);
    }

    /**
     * 加载无数据的Fragment
     */
    protected void showNoData(String title, String buttontext, NoDataFragment.OnClickRenovate onClickRenovate) {
        NoDataFragment fragment = NoDataFragment.newInstance(title, buttontext);
        fragment.setOnClickRenovate(onClickRenovate);
        setFrameLayout(fragment);
    }

/*    *//**
     * 加载正在加载中fragment
     *//*
    protected void showLoading(){
        if (mfragment instanceof LoadingFragment){
            return;
        }
        LoadingFragment fragment =  LoadingFragment.newInstance();
        setFrameLayout(fragment);
    }*/

    /**
     * 加载正在加载中fragment
     */
    protected void showLoading() {
        if (mfragment instanceof LoadingProgressFragment) {
            return;
        }
        LoadingProgressFragment fragment = LoadingProgressFragment.newInstance();
        setFrameLayout(fragment);
    }

    private void setFrameLayout(Fragment fragment) {
        if (fragment == null && !this.isAdded()) {
            return;
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mfragment == null) {
            fragmentTransaction.add(R.id.fg_state, fragment, "fragment1");
        } else {
            fragmentTransaction.remove(mfragment);
            fragmentTransaction.add(R.id.fg_state, fragment, "fragment2");
        }
        cleanChildView();
        mfragment = fragment;
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    /**
     * 清理之前的fragment
     */
    protected void clearFrameLayout() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mfragment != null) {
            fragmentTransaction.remove(mfragment);
            mfragment = null;
            fragmentTransaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }

    /**
     * 清理该容器下 childview
     */
    private void cleanChildView() {
        if (mChildren==null||mChildren.size() > 0) {
            return;
        }
        int count = frameLayout.getChildCount();

        if (count < 1) {
            return;
        }
        for (int i = 0; i < count; i++) {
            mChildren.add(frameLayout.getChildAt(i));
        }
        frameLayout.removeAllViews();
    }

    /**
     * 恢复该容器下 childview
     */
    protected void recoveryChildView() {
        clearFrameLayout();
        if (mChildren != null && mChildren.size() > 0) {
            for (View v : mChildren) {
                frameLayout.addView(v);
            }
            mChildren.clear();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChildren != null) {
            mChildren.clear();
            mChildren = null;
        }
        if (mfragment != null) {
            mfragment = null;
        }
    }
}
