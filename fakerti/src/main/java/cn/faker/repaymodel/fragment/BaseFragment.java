package cn.faker.repaymodel.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.ArrayList;

import cn.faker.repaymodel.R;
import cn.faker.repaymodel.activity.broadcast.AllBroadCast;
import cn.faker.repaymodel.fragment.interface_fg.BasicFragment;

/**
 * 普通Fragment的基类
 * Created by Mr.C on 2017/11/1 0001.
 */

public abstract class BaseFragment extends Fragment implements BasicFragment {

    private AllBroadCast mAllBroadCast;
    private QMUITipDialog tipDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(getLayoutId(), null, false);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
    protected void toAcitvity(Class<?> cls){
        Intent intent = new Intent(getContext(),cls);
        startActivity(intent);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview(view);
        initData(savedInstanceState);
        initListener();
    }

    /**
     * 在此方法内设置控件监听
     */
    protected void initListener() {

    }

    protected void goAcitvity(Class o) {
        Intent intent = new Intent(getContext(), o);
        startActivity(intent);
    }

    //设置广播接收  只允许设置一次
    protected void setBroadCastAction(String action) {
        if (mAllBroadCast == null) {
            mAllBroadCast = new AllBroadCast();
            mAllBroadCast.setOnAllBroadCastReceive(new AllBroadCast.OnAllBroadCastReceive() {
                @Override
                public void onAllReceive(Context context, Intent intent) {
                    onReceive(context, intent);
                }
            });
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(action);
            getActivity().registerReceiver(mAllBroadCast, intentFilter);
        }
    }

    protected void showLoading() {
        dimiss();
        tipDialog = new QMUITipDialog.CustomBuilder(getContext())
                .setContent(R.layout.dialog_loading)
                .create();
        tipDialog.setCancelable(false);
        tipDialog.show();
    }

    protected void dimiss() {
        if (tipDialog != null) {
            tipDialog.dismiss();
            tipDialog = null;
        }
    }

    protected void showDialog(String msg) {
        showDialog(msg,true);
    }
    protected void showDialog(String msg,boolean iscan) {
        new QMUIDialog.MessageDialogBuilder(getContext()).setMessage(msg).setCancelable(iscan).addAction("确定", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        }).show();
    }

    protected void showDialog(String msg,boolean iscan, QMUIDialogAction.ActionListener actionListener) {
        new QMUIDialog.MessageDialogBuilder(getContext()).setMessage(msg).setCancelable(iscan).addAction("确定", actionListener).show();
    }
    protected void showDialog(String msg, QMUIDialogAction.ActionListener actionListener) {
        showDialog(msg,true,actionListener);
    }
    protected void showDialogAddClean(String msg, QMUIDialogAction.ActionListener actionListener) {
        new QMUIDialog.MessageDialogBuilder(getContext()).setMessage(msg).addAction("确定", actionListener).addAction("取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        }).show();
    }

    //设置广播接收 可设置多次
    protected void setBroadCastAction(String action, AllBroadCast.OnAllBroadCastReceive receive) {
        AllBroadCast mAllBroadCast = new AllBroadCast();
        mAllBroadCast.setOnAllBroadCastReceive(receive);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action);
        getActivity().registerReceiver(mAllBroadCast, intentFilter);
    }

    private ArrayList<AllBroadCast> list_broad;

    protected void setBroadCastAction(AllBroadCast.OnAllBroadCastReceive receive, String... actions) {
        if (actions != null && actions.length > 0) {

            if (list_broad == null) {
                list_broad = new ArrayList();
            }

            for (String action : actions) {
                AllBroadCast mAllBroadCast = new AllBroadCast();
                mAllBroadCast.setOnAllBroadCastReceive(receive);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(action);
                list_broad.add(mAllBroadCast);
                getActivity().registerReceiver(mAllBroadCast, intentFilter);
            }
        }

    }

    protected void sendMyBroadCase(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        getActivity().sendBroadcast(intent);
    }

    protected void onReceive(Context context, Intent intent) {
    }

    @Override
    public void onDestroy() {
        unregister(mAllBroadCast);
        if (list_broad != null && list_broad.size() > 0) {
            for (int i = 0; i < list_broad.size(); i++) {
                unregister(list_broad.get(i));
            }
        }
        super.onDestroy();
    }

    private void unregister(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }
}
