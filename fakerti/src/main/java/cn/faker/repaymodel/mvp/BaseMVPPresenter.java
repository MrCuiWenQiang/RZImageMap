package cn.faker.repaymodel.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Function : 基础p层 与activity生命周期相结合
 * Remarks  :
 * Created by Mr.C on 2018/12/18 0018.
 */
public class BaseMVPPresenter<V> {

    public static final int SIZE = 10;//默认页数 供分页使用

    private Reference<V> mViewRef;


    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public V getView(){
        return mViewRef==null? null:mViewRef.get();
    }
    //生命周期回调
    public void onCreate() {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    protected String[] toList(List<String> sl) {
        if (sl == null) return null;
        return sl.toArray(new String[sl.size()]);
    }
}
