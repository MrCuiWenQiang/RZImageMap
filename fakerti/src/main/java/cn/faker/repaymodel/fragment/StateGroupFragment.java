package cn.faker.repaymodel.fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import cn.faker.repaymodel.fragment.fragmentlist.LoadingFragment;
import cn.faker.repaymodel.fragment.fragmentlist.LoadingProgressFragment;
import cn.faker.repaymodel.fragment.fragmentlist.NoDataFragment;
import cn.faker.repaymodel.util.CustomUtility;

/**
 * 状态组  实现一个页面有多个状态切换
 * Created by Mr.C on 2017/12/26 0026.
 */

public abstract class StateGroupFragment extends BaseViewPagerFragment {

    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();//存放已设置状态的VIEWGROUP

    private HashMap<Integer, ArrayList<View>> childHashMap = new HashMap<>();//存放该容器下的所有子view

    /**
     * 加载无数据的Fragment
     */
    protected void showNoData(@IdRes int containerViewId, String title) {
        NoDataFragment fragment = NoDataFragment.newInstance(title);
        fragment.iwidth = CustomUtility.getWindowmin(getContext())/5;
        fragment.iheight = CustomUtility.getWindowmin(getContext())/5;
        setFrameLayout(containerViewId, fragment);
    }

    /**
     * 加载无数据的Fragment
     */
    protected void showNoData(@IdRes int containerViewId, NoDataFragment.OnClickRenovate onClickRenovate) {
        NoDataFragment fragment = NoDataFragment.newInstance();
        fragment.setOnClickRenovate(onClickRenovate);
        setFrameLayout(containerViewId, fragment);
    }

    /**
     * 加载无数据的Fragment
     */
    protected void showNoData(@IdRes int containerViewId, String title, NoDataFragment.OnClickRenovate onClickRenovate) {
        NoDataFragment fragment = NoDataFragment.newInstance(title);
        fragment.setOnClickRenovate(onClickRenovate);
        setFrameLayout(containerViewId, fragment);
    }

    /**
     * 加载无数据的Fragment
     */
    protected void showNoData(@IdRes int containerViewId, String title, String buttontext, NoDataFragment.OnClickRenovate onClickRenovate) {
        NoDataFragment fragment = NoDataFragment.newInstance(title, buttontext);
        fragment.setOnClickRenovate(onClickRenovate);
        setFrameLayout(containerViewId, fragment);
    }

/*    *//**
     * 加载正在加载中fragment
     *//*
    protected void showLoading(@IdRes int containerViewId) {
        LoadingFragment fragment = LoadingFragment.newInstance();
        setFrameLayout(containerViewId, fragment);
    }*/
    /**
     * 加载正在加载中fragment
     */
    protected void showLoading(@IdRes int containerViewId){
        LoadingProgressFragment fragment =  LoadingProgressFragment.newInstance();
        setFrameLayout(containerViewId,fragment);
    }

    private void setFrameLayout(@IdRes int containerViewId, Fragment fragment) {
        if (fragment == null && !this.isAdded()) {
            return;
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment oldFragment = getStateFragment(containerViewId);
        if (oldFragment == null) {
            fragmentTransaction.add(containerViewId, fragment, "fragment1");
        } else {
            fragmentTransaction.remove(oldFragment);
            clearStateFrament(containerViewId);
            fragmentTransaction.add(containerViewId, fragment, "fragment2");
        }
        cleanChildView(containerViewId);
        setStateFragment(containerViewId, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }


    /**
     * 清理该容器下 childview
     */
    private void cleanChildView(@IdRes int viewid) {
        if (childHashMap.get(viewid) != null && childHashMap.get(viewid).size() > 0) {
            return;
        }
        ViewGroup frameLayout = (ViewGroup) getView().findViewById(viewid);

        if (frameLayout == null) {
            throw new IllegalStateException("not found" + viewid);
        }

        int count = frameLayout.getChildCount();

        if (count < 1) {
            return;
        }
        ArrayList<View> mChildren = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            mChildren.add(frameLayout.getChildAt(i));
        }
        childHashMap.put(viewid, mChildren);
        frameLayout.removeAllViews();
    }

    /**
     * 恢复该容器下 childview
     */
    protected void recoveryChildView(@IdRes int viewid) {
        clearFrameLayout(viewid);
        ArrayList<View> mChildren = childHashMap.get(viewid);
        ViewGroup frameLayout = (ViewGroup) getView().findViewById(viewid);
        if (mChildren != null && mChildren.size() > 0) {
            for (View v : mChildren) {
                frameLayout.addView(v);
            }
            mChildren.clear();
        }
    }

    /**
     * 清理之前的fragment
     */
    protected void clearFrameLayout(@IdRes int viewid) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment mfragment = getStateFragment(viewid);
        if (mfragment != null) {
            fragmentTransaction.remove(mfragment);
            clearStateFrament(viewid);
            fragmentTransaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }

    private Fragment getStateFragment(@IdRes int id) {
        return fragmentHashMap.get(id);
    }

    private void clearStateFrament(@IdRes int id) {
        fragmentHashMap.remove(id);
    }

    private void setStateFragment(@IdRes int id, Fragment stateFragment) {
        fragmentHashMap.put(id, stateFragment);
    }
}
