package cn.faker.repaymodel.widget.view.edittext;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

/**
 * 用于银行卡信用卡号码格式化
 * Created by Mr.C on 2018/1/4 0004.
 */

public class BandCardEditText extends android.support.v7.widget.AppCompatEditText {

    public final String space = " ";

    private boolean isFormcat = false;
    private CardTextWatcher cardTextWatcher;

    public void setCardTextWatcher(CardTextWatcher cardTextWatcher) {
        this.cardTextWatcher = cardTextWatcher;
    }


    public BandCardEditText(Context context) {
        super(context);
        init();
    }

    public BandCardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BandCardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        format(getText().toString());
        addTextChangedListener(new BandCardWatcher());
    }

    private void format(String text) {
        if (isFormcat){
            isFormcat = false;
            return;
        }
        isFormcat = true;

        if (TextUtils.isEmpty(text)) {
            return;
        }
        String nText = text.trim().replaceAll(" ", "");
        if (TextUtils.isEmpty(nText)) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        int length = nText.length();

        if (cardTextWatcher!=null){
            cardTextWatcher.afterTextChanged(nText);
        }
        for (int i = 0; i < length; i++) {
            stringBuilder.append(nText.charAt(i));
            if (i == 3 || i == 7 || i == 11|| i == 15) {
                if (i!=length-1){
                    stringBuilder.append(space);
                }
            }
        }
        setText(stringBuilder.toString());
        setSelection(stringBuilder.length());
    }

    private class BandCardWatcher implements TextWatcher {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            format(s.toString());
        }
    }

    public String getEditText() {
        return getText().toString().trim().replaceAll(space,"");
    }

    //防止重复调用TextWatcher内的方法
    public interface CardTextWatcher{
        void afterTextChanged(String text);
    }
}
