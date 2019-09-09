package cn.faker.repaymodel.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.faker.repaymodel.activity.manager.ActivityManager;
import cn.faker.repaymodel.net.okhttp3.HttpManager;

/**
 * 此处实现栈管理 以及请求管理
 * Created by Mr.C on 2017/11/18 0018.
 */

public class BaseManagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
        HttpManager.cleanCall();
    }

    protected void exit(){
        ActivityManager.exit(this);
    }
}
