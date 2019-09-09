package cn.faker.repaymodel.activity;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.faker.repaymodel.R;
import cn.faker.repaymodel.util.StatusBarUtils;

/**
 * Function :带有标题栏的基类
 * Remarks  :
 * Created by Mr.C on 2018/12/18 0018.
 */
public abstract class BaseToolBarActivity extends BaseActivity {

    protected Toolbar toolbar;
    protected TextView btnRight;
    protected TextView toolbar_tv_left_title;
    protected FrameLayout frameLayout;
    protected TextView toolbar_tv_title;
    protected View v_title_d;
    protected ImageView backicon;

    public static final int SUCCESSCODE = 200;

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_tv_left_title = (TextView) findViewById(R.id.toolbar_tv_left_title);
        btnRight = (TextView) findViewById(R.id.btnRight);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        toolbar_tv_title = (TextView) findViewById(R.id.toolbar_tv_title);
        v_title_d = findViewById(R.id.v_title_d);
        backicon = (ImageView) findViewById(R.id.backicon);

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backOnClickListener();
            }
        });

        setSupportActionBar(toolbar);
        settingToolBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater.from(BaseToolBarActivity.this).inflate(getLayoutContentId(), frameLayout, true);
        initContentView();
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected void setStatusBar(@DrawableRes int color) {
        StatusBarUtils.setWindowStatusBarColor(this, color);
    }


    /**
     * true状态栏透明且黑色字体
     *
     * @param setDark
     */
    public void changStatusIconCollor(boolean setDark) {
        getWindow()
                .getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtils.MIUISetStatusBarLightMode(this.getWindow(), true);
        StatusBarUtils.FlymeSetStatusBarLightMode(this.getWindow(), true);

    }

    /**
     * 是否显示标题栏
     *
     * @param isshow
     */
    public void isShowToolView(boolean isshow) {
        if (!isshow) {
            toolbar.setVisibility(View.GONE);
            v_title_d.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示返回键 默认显示
     *
     * @param isshow
     */
    public void isShowBackButton(boolean isshow) {
        if (!isshow) {
            backicon.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示分割线
     *
     * @param isshow
     */
    public void isShowCut(boolean isshow) {
        if (!isshow) {
            v_title_d.setVisibility(View.GONE);
        }
    }

    /**
     * 设置返回键图片
     *
     * @param id
     */
    protected void setBackBackground(int id) {
        backicon.setBackgroundResource(id);
    }

    /**
     * 设置返回键点击事件 默认关掉当前activity
     */
    protected void backOnClickListener() {
//        finish();
        supportFinishAfterTransition();
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitle(String title) {
        toolbar_tv_title.setText(title);
    }

    /**
     * 同时设置标题和标题颜色
     *
     * @param title
     * @param titlecolor
     */
    protected void setTitle(String title, int titlecolor) {
        toolbar_tv_title.setText(title);
        toolbar_tv_title.setTextColor(getResources().getColor(titlecolor));
    }

    /**
     * 设置左标题
     *
     * @param title
     */
    protected void setLeftTitle(String title) {
        toolbar_tv_left_title.setVisibility(View.VISIBLE);
        toolbar_tv_left_title.setText(title);
    }

    /**
     * 同时设置左标题和标题颜色
     *
     * @param title
     * @param titlecolor
     */
    protected void setLeftTitle(String title, int titlecolor) {
        toolbar_tv_left_title.setVisibility(View.VISIBLE);
        toolbar_tv_left_title.setText(title);
        toolbar_tv_left_title.setTextColor(getResources().getColor(titlecolor));
    }

    protected void setRightBtn(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        setRightBtn(text, -1, null);
    }

    protected void setRightBtn(String text, int titlecolor) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        setRightBtn(text, titlecolor, null);
    }

    protected void setRightBtn(String text, View.OnClickListener l) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        setRightBtn(text, -1, l);
    }

    protected void setRightBtn(String text, int titlecolor, View.OnClickListener l) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText(text);
        if (titlecolor != -1) {
            btnRight.setTextColor(getResources().getColor(titlecolor));
        }
        btnRight.setOnClickListener(l);
    }

    protected void setToolBarBackgroundColor(@DrawableRes int color) {
        toolbar.setBackgroundResource(color);
    }


    @Override
    public int getLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.ac_basetoolbar;
    }

    //子类可在此设置标题栏的配置
    protected void settingToolBar(Toolbar toolbar) {
    }

    /**
     * 设置布局id
     *
     * @return
     */
    protected abstract int getLayoutContentId();

    /**
     * 初始化布局控件
     *
     * @return
     */
    protected abstract void initContentView();
}
