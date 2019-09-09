package cn.faker.repaymodel.net.okhttp3;


import android.support.v4.util.ArrayMap;

import cn.faker.repaymodel.net.okhttp3.callback.BasicCallback;
import cn.faker.repaymodel.util.LogUtil;
import okhttp3.Call;

/**
 * 请求管理类
 * Created by Administrator on 2017/12/4 0004.
 */

public class HttpManager {

    private static final ArrayMap<String, Call> calllist = new ArrayMap<>();

    public static void addCall(String url, Call call) {
        try {
            calllist.put(url, call);
        } catch (Exception e) {
            LogUtil.e("test-ERROR", e.toString());
        }
    }

    public static void cleanCall() throws NullPointerException {
        if (calllist.size() > 0) {
            for (int i = 0; i < calllist.size(); i++) {
                String key = calllist.keyAt(i);
                Call call = calllist.get(key);
                if (call != null) {
                    call.cancel();
                }
                calllist.remove(key);
            }
        }
    }

    public static void cleanCall(String url) {
        if (calllist != null && calllist.size() > 0 && url != null) {
            Call call = calllist.get(url);
            if (call != null) {
                call.cancel();
            }
        }
    }

    public static void remove(String url) {
        try {
            if (calllist != null && calllist.size() > 0 && url != null) {
                if (calllist.get(url) == null) return;
                calllist.remove(url);
            }
        } catch (Exception e) {
            LogUtil.e("remove Http", e.toString());
        }
    }
}
