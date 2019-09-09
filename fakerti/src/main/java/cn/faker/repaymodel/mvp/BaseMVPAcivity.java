package cn.faker.repaymodel.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.lang.reflect.ParameterizedType;

import cn.faker.repaymodel.activity.BaseToolBarActivity;

/**
 * Function : MVP模式下的基础类
 * Remarks  :
 * Created by Mr.C on 2018/12/18 0018.
 */
public abstract class BaseMVPAcivity<V, T extends BaseMVPPresenter<V>> extends BaseToolBarActivity {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        mPresenter = createPresenter(this, 1);
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

    //自动获取泛型的实例对象
    private T createPresenter(Object o, int i) {
        try {
            ParameterizedType pType = (ParameterizedType) o.getClass().getGenericSuperclass();
            Class<T> ct = (Class<T>) pType.getActualTypeArguments()[i];
            return ct.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
