package cn.faker.repaymodel.widget.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import cn.faker.repaymodel.util.ScreenUtil;
import cn.faker.repaymodel.widget.view.dialog.interface_dl.BasicDialogView;

/**
 * dialog对话框的基类
 * Created by Mr.C on 2017/11/13 0013.
 */


public abstract class BasicDialog extends DialogFragment implements BasicDialogView {
    private boolean isLoad = true;

    protected OnDialogState onDialogState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.dg_background));
        View view = inflater.inflate(getLayoutId(), container);
        return view;
    }

    //http://blog.csdn.net/o279642707/article/details/55205411
//java.lang.IllegalStateException: Fragment already added: TimerDialog
    //这样治标不治本 问题在于状态值问题
    @Override
    public void show(FragmentManager manager, String tag) {
        if (!isAdded()) {
            try {
                super.show(manager, tag);

            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        getDialog().getWindow().setLayout(-1,-2 );
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);
        initData(savedInstanceState);
        initListener();
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (onDialogState != null && isLoad) {
                    isLoad = false;
                    onDialogState.onStart();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WindowManager.LayoutParams attributes = getDialog().getWindow().getAttributes();
        attributes.height = getDialogHeght();
        attributes.width = getDialogWidth();
//        attributes.height = (25 * ScreenUtil.getWindowHeight(getContext()) / 32);//获取屏幕的宽度，定义自己的宽度
//        attributes.width = (8 * ScreenUtil.getWindowWidth(getContext()) / 9);
        initLayoutParams(attributes);
        getDialog().getWindow().setAttributes(attributes);
    }

    @Deprecated
    protected int getDialogWidth() {
        return ScreenUtil.getWindowWidth(getContext()) * 4 / 9;
    }

    @Deprecated
    protected int getDialogHeght() {
        if (ScreenUtil.isCreenOriatationPortrait(getContext())) {
            return ScreenUtil.getWindowHeight(getContext()) / 3;
        } else {
            return ScreenUtil.getWindowHeight(getContext()) * 2 / 3;
        }
    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isLoad = true;
    }

    protected void initLayoutParams(WindowManager.LayoutParams attributes) {

    }

    protected void initListener() {

    }

    public void setOnDialogState(OnDialogState onDialogState) {
        this.onDialogState = onDialogState;
    }

    public interface OnDialogState {
        void onStart();
    }
}
