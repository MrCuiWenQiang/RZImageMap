package cn.faker.repaymodel.net.okhttp3.cookie;

import java.util.ArrayList;
import java.util.List;

import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Cookie持久化类，可以将cookie保存到本地.
 * Created by Mr.C on 2017/3/31 0031.
 */

public class Cookie implements CookieJar {
    private  List<okhttp3.Cookie> cookies;
    @Override
    public void saveFromResponse(HttpUrl url, List<okhttp3.Cookie> cookies) {
        if ( cookies!= null && cookies.size()>0){
            this.cookies = cookies;
        }
    }

    @Override
    public List<okhttp3.Cookie> loadForRequest(HttpUrl url) {
        return cookies ==null?  new ArrayList<okhttp3.Cookie>():cookies ;
    }
}
