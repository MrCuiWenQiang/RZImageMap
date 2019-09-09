package cn.faker.repaymodel.util;

/**
 * Created by Administrator on 2018/1/10 0010.
 */

public class StringUtil {

    //加密中间三个文字
    public static String encryption(String text) {
        if (text == null || text.length() < 3) {
            return null;
        }
        int index_middle = 0;
        if (text.length() == 3) {

        } else if ((text.length() % 2) == 0) {
            index_middle = text.length() / 2 - 2;
        } else {
            index_middle = Math.round(text.length() / 2);
        }

        StringBuilder mString = new StringBuilder(text);
        try {
            mString.replace(index_middle, index_middle + 3, "***");

        }catch (Exception e){
            LogUtil.e("test-error",e.toString());
            return text;
        }
        return mString.toString();
    }
}
