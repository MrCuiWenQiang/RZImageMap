package cn.faker.repaymodel.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

import cn.faker.repaymodel.activity.BaseToolBarActivity;
import cn.faker.repaymodel.fragment.BaseFragment;
import cn.faker.repaymodel.fragment.BaseViewPagerFragment;

/**
 * Function : MVP模式下的基础类
 * Remarks  :
 * Created by Mr.C on 2018/12/18 0018.
 */
public abstract class BaseMVPFragment<V, T extends BaseMVPPresenter<V>> extends BaseViewPagerFragment {
    protected T mPresenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void initView() {
        mPresenter = createPresenter(this, 1);
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }

    @Override
    public void onDestroy() {
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
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
