package cn.faker.repaymodel.activity.manager;

import android.app.Application;
import android.content.Context;

import java.util.Stack;

import cn.faker.repaymodel.activity.BaseManagerActivity;

/**
 * 栈管理类
 * Created by Mr,C on 2017/11/18 0018.
 */

public class ActivityManager {
    private static Stack<BaseManagerActivity> activityStack = new Stack<>();

    /**
     * 入栈
     *
     * @param appCompatActivity
     */
    public static void addActivity(BaseManagerActivity appCompatActivity) {
        activityStack.push(appCompatActivity);
    }

    /**
     * 获取最后一个ac 即当前ac
     *
     * @return
     */
    public static BaseManagerActivity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前ac（堆栈中最后一个压入的）
     */
    public static void finishCurrentActivity() {
        BaseManagerActivity activity = activityStack.pop();
        activity.finish();
    }

    /**
     * 结束指定的ac
     */
    public static void finishActivity(BaseManagerActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 移除栈中指定ac
     */
    public static void removeActivity(BaseManagerActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (BaseManagerActivity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (BaseManagerActivity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public static void exit(Context context) {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
