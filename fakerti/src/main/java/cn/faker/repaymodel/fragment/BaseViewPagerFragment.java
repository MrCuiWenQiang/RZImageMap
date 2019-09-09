package cn.faker.repaymodel.fragment;

import android.os.Bundle;

import cn.faker.repaymodel.fragment.interface_fg.BasicViewPagerFragment;

/**
 * 解决ViewPager多次加载fragment的情况
 * Created by Mr.C on 2017/9/23 0023.
 */

public abstract class BaseViewPagerFragment extends BaseFragment implements BasicViewPagerFragment {

    protected boolean isViewInitiated = false;
    protected boolean isDataLoaded = true;

    protected boolean isIntiiated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareRequestData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        prepareRequestData();
    }

    public void prepareRequestData() {
        if (isIntiiated||(getUserVisibleHint() && isViewInitiated && isDataLoaded)) {
            isDataLoaded = requestData();
        }
    }

    @Override
    public boolean requestData() {
        return false;
    }
}
