package cn.faker.repaymodel;

import android.app.Application;
import android.content.Context;

/**
 * Created by Mr.c on 2017/11/16 0016.
 */

public class BasicApplication extends Application {


    private static Context mContext;

    public BasicApplication(){
        mContext = this;
    }

    public static Context getContext() {
        if (mContext == null){
            throw new RuntimeException("Application not to init,mContext is null");
        }
        return mContext;
    }
}
