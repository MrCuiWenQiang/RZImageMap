package cn.faker.repaymodel.net.okhttp3.callback;

import java.io.IOException;

import cn.faker.repaymodel.net.okhttp3.HttpManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public abstract class BasicCallback implements Callback {
    protected HttpResponseCallback.OnFailedAll onFailedAll;

    private String url;

    public void setUrl(String url) {
        if (url==null)throw new NullPointerException("BasicCallback url is null!");
        this.url = url;
    }

    public void cleanUrl (){
        HttpManager.remove(url);
    }
    @Override
    public void onFailure(Call call, IOException e) {
        cleanUrl();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        cleanUrl();
    }

    public void setOnFailedAll(HttpResponseCallback.OnFailedAll onFailedAll) {
        this.onFailedAll = onFailedAll;
    }
}
