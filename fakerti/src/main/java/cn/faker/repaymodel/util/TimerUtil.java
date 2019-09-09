package cn.faker.repaymodel.util;

import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class TimerUtil {

    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String dateString = format.format(date);
        return dateString;
    }

    public static String getDateYear() {
        Format f = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        String dateString = f.format(c.getTime());
        return dateString;
    }
    public static String getDateMonth() {
        Format m = new SimpleDateFormat("MM");
        Calendar dc = Calendar.getInstance();
        String dateString = m.format(dc.getTime());
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 当前年份增加
     */
    public static String addYear(int number){
        Format f = new SimpleDateFormat("yyyy");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, number);
        return f.format(c.getTime());
    }

    /*
   * 将时间戳转换为时间
   */
    public static String stampToDate(String s){
        return stampToDate(s,"yyyy-MM-dd HH:mm:ss");
    }
    /*
   * 将时间戳转换为时间
   */
    public static String stampToDate(String s,String pattern){
        if (s==null||s.length()<=0){
            return null;
        }
        try {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;

        }catch (Exception e){
            LogUtil.e("test_error",e.toString());
            return null;
        }

    }

    /*
 * 将时间转换为时间戳
 */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
}
