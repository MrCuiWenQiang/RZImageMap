package cn.faker.repaymodel.activity.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 一个全局的广播
 * Created by Mr.c on 2018/1/3 0003.
 */
@Deprecated
public class AllBroadCast extends BroadcastReceiver{

    private OnAllBroadCastReceive onAllBroadCastReceive;

    public AllBroadCast(){
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (onAllBroadCastReceive!=null){
            onAllBroadCastReceive.onAllReceive(context,intent);
        }
    }

    public void setOnAllBroadCastReceive(OnAllBroadCastReceive onAllBroadCastReceive) {
        this.onAllBroadCastReceive = onAllBroadCastReceive;
    }

    public interface OnAllBroadCastReceive{
        void onAllReceive(Context context, Intent intent);
    }
}
