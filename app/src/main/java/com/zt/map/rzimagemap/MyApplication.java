package com.zt.map.rzimagemap;

import android.support.multidex.MultiDex;

import cn.faker.repaymodel.BasicApplication;
import cn.faker.repaymodel.util.ToastUtility;

public class MyApplication extends BasicApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        ToastUtility.setToast(getApplicationContext());
    }
}
