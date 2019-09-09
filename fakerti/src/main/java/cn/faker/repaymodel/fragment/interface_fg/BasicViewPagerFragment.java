package cn.faker.repaymodel.fragment.interface_fg;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Mr.C on 2017/11/1 0001.
 */

public interface BasicViewPagerFragment {
    /**
     * 网络访问等 只需加载一次的操作放在此方法内
     * @return 如果返回true则下一次到次Fragment时仍然会加载此方法内的数据
     */
    boolean requestData();
}
