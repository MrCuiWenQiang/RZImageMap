package cn.faker.repaymodel.fragment.fragmentlist;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.faker.repaymodel.R;
import cn.faker.repaymodel.fragment.BaseFragment;

/**
 * 加载
 * Created by Mr.C on 2017/11/29 0029.
 */

public class LoadingProgressFragment extends BaseFragment {
    private ProgressBar pb_circle;

    public static LoadingProgressFragment newInstance() {
        Bundle args = new Bundle();
        LoadingProgressFragment fragment = new LoadingProgressFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fg_progress_loading;
    }

    @Override
    public void initview(View v) {
        pb_circle = (ProgressBar) v.findViewById(R.id.pb_circle);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
