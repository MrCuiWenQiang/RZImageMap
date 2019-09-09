package cn.faker.repaymodel.activity;

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
 * 状态切换activity
 * Created by Mr.C on 2017/11/29 0029.
 */
@Deprecated
public abstract class StateToolbarActivity extends BaseToolBarActivity {
    private ViewGroup frameLayout;
    private ArrayList<View> mChildren ;
    private Fragment mfragment;
    private boolean isStart = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        frameLayout = (ViewGroup) findViewById(R.id.ac_state);
        if (frameLayout == null) {
            throw new RuntimeException("We not get this view!");
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
    protected void showNoData(String title,String buttontext, NoDataFragment.OnClickRenovate onClickRenovate) {
        NoDataFragment fragment = NoDataFragment.newInstance(title,buttontext);
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
    protected void showLoading(){
        if (mfragment instanceof LoadingProgressFragment){
            return;
        }
        LoadingProgressFragment fragment =  LoadingProgressFragment.newInstance();
        setFrameLayout(fragment);
    }

    private void setFrameLayout(Fragment fragment) {
        if (fragment == null||isStart) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mfragment == null) {
            fragmentTransaction.add(R.id.ac_state, fragment, "fragment1");
        } else {
            fragmentTransaction.remove(mfragment);
            fragmentTransaction.add(R.id.ac_state, fragment, "fragment2");
        }
        cleanChildView();
        mfragment = fragment;
        if (!isStart){
            fragmentTransaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }

    /**
     * 清理之前的fragment
     */
    private void clearFrameLayout() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mfragment != null) {
            fragmentTransaction.remove(mfragment);
        }
        mfragment = null;
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    /**
     * 清理该容器下 childview
     */
    private void cleanChildView() {
        if (mChildren == null){
            mChildren = new ArrayList<View>();
        }
        if (mChildren.size() > 0) {
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
    protected void onDestroy() {
        super.onDestroy();
        isStart = !isStart;
        if (mChildren != null) {
            mChildren.clear();
            mChildren = null;
        }
        if (mfragment != null) {
            mfragment = null;
        }
    }
}
