package cn.faker.repaymodel.fragment.interface_fg;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public interface BasicFragment {
    /**
     * 指定layout布局ID
     * @return
     */
    int getLayoutId();
    /**
     * 在此方法内初始化控件
     */
    void initview(View v);
    /**
     * 在此方法内为控件赋值 或 开启网络线程访问
     * @param savedInstanceState
     */
    void initData(Bundle savedInstanceState);


}
