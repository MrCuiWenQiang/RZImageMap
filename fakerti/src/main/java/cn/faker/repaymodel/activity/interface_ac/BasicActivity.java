package cn.faker.repaymodel.activity.interface_ac;

import android.os.Bundle;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public interface BasicActivity {
    /**
     * 指定layout布局ID
     * @return
     */
    int getLayoutId();
    /**
     * 在此方法内初始化控件
     */
    void initView();

    /**
     * 在此方法内为控件赋值 或 开启网络线程访问
     * @param savedInstanceState
     */
    void initData(Bundle savedInstanceState);

}
