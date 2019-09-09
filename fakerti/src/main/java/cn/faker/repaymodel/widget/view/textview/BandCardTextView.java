package cn.faker.repaymodel.widget.view.textview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 格式化银行卡号
 * Created by Mr.C on 2018/1/4 0004.
 */

public class BandCardTextView extends android.support.v7.widget.AppCompatTextView {

    public final String space = " ";

    public BandCardTextView(Context context) {
        super(context);
    }

    public BandCardTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BandCardTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String text) {
        super.setText(format(text), BufferType.NORMAL);
    }

    public String getText() {
        return super.getText().toString().trim().replaceAll(space,"");
    }


    private String format(String text) {

        if (TextUtils.isEmpty(text)) {
            return null;
        }
        String nText = text.trim().replaceAll(" ", "");
        if (TextUtils.isEmpty(nText)) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        int length = nText.length();


        for (int i = 0; i < length; i++) {
            stringBuilder.append(nText.charAt(i));
            if (i == 3 || i == 7 || i == 11 || i == 15) {
                if (i != length - 1) {
                    stringBuilder.append(space);
                }
            }
        }
        return stringBuilder.toString();
    }
}
