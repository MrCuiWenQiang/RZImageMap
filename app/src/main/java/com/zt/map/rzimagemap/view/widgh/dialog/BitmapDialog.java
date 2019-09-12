package com.zt.map.rzimagemap.view.widgh.dialog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zt.map.rzimagemap.R;

import cn.faker.repaymodel.util.ScreenUtil;
import cn.faker.repaymodel.widget.view.dialog.BasicDialog;

/**
 * Function :2
 * Remarks  :
 * Created by Mr.C on 2019/9/10 0010.
 */
public class BitmapDialog extends BasicDialog {

    private ImageView iv_content;

    private Bitmap bitmap;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_bitmap;
    }

    @Override
    public void initview(View v) {
        iv_content = v.findViewById(R.id.iv_content);
        if (bitmap!=null){
            iv_content.setImageBitmap(bitmap);
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    protected int getDialogWidth() {
        return ScreenUtil.getWindowWidth(getContext()) * 4 / 5;
    }

    protected int getDialogHeght() {
        return ScreenUtil.getWindowHeight(getContext()) * 10 / 11;
    }
    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
