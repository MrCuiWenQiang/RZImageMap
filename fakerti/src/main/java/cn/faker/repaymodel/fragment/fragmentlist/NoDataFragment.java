package cn.faker.repaymodel.fragment.fragmentlist;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.faker.repaymodel.R;
import cn.faker.repaymodel.fragment.BaseFragment;

/**
 * 无数据时的展示页
 * Created by Mr.C on 2017/11/27 0027.
 */

public class NoDataFragment extends BaseFragment {

    private ImageView iv_image;
    private TextView tv_text;
    private TextView bt_renovate;

    private static final String IMAGEKEY = "IMAGEKEY";
    private static final String BTEXT = "BTEXT";
    private static final String TEXTKEY = "TEXTKEY";

    public  int iwidth = 0;
    public  int iheight = 0;


    private OnClickRenovate onClickRenovate;

    public static NoDataFragment newInstance() {
        Bundle args = new Bundle();
        NoDataFragment fragment = new NoDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static NoDataFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(TEXTKEY, text);
        NoDataFragment fragment = new NoDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static NoDataFragment newInstance(int imageid, String text) {
        Bundle args = new Bundle();
        args.putInt(IMAGEKEY, imageid);
        args.putString(TEXTKEY, text);
        NoDataFragment fragment = new NoDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static NoDataFragment newInstance(String text, String btext) {
        Bundle args = new Bundle();
        args.putString(BTEXT, btext);
        args.putString(TEXTKEY, text);
        NoDataFragment fragment = new NoDataFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fg_nodata;
    }

    @Override
    public void initview(View v) {
        iv_image = (ImageView) v.findViewById(R.id.iv_image);
        tv_text = (TextView) v.findViewById(R.id.tv_text);
        bt_renovate = (TextView) v.findViewById(R.id.bt_renovate);

        if (iwidth >0||iheight>0){
            ViewGroup.LayoutParams layoutParams = iv_image.getLayoutParams();
            layoutParams.width = iwidth;
            layoutParams.height = iheight;
        }

        if (onClickRenovate != null) {
            bt_renovate.setVisibility(View.VISIBLE);
            bt_renovate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRenovate.onRenovate();
                }
            });
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int imageid = bundle.getInt(IMAGEKEY);
        if (imageid != 0) {
            iv_image.setImageDrawable(getResources().getDrawable(imageid));
        }

        String text = bundle.getString(TEXTKEY);
        tv_text.setText(text);

        String btext = bundle.getString(BTEXT);
        if (!TextUtils.isEmpty(btext)) {
            bt_renovate.setText(btext);
        }
    }

    public void setOnClickRenovate(final OnClickRenovate onClickRenovate) {
        this.onClickRenovate = onClickRenovate;
    }

    public interface OnClickRenovate {
        void onRenovate();
    }
}
