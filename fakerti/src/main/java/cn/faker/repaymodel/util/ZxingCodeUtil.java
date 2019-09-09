package cn.faker.repaymodel.util;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Function :二维码工具  现集成生成二维码功能  后期加入扫描
 * Remarks  :
 * Created by Mr.C on 2018/12/19 0019.
 */
public class ZxingCodeUtil {

    /**
     * 生成一维码
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createOneCode(String content, int width, int height) {
        try {
            return createCodeBitmap(BarcodeFormat.CODE_128, content, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成二维码
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createTwoCode(String content, int width, int height) {
        try {
            return createCodeBitmap(BarcodeFormat.QR_CODE, content, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static synchronized Bitmap createCodeBitmap(BarcodeFormat format, String content, int width, int height) throws WriterException {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        BitMatrix matrix = new MultiFormatWriter().encode(content, format, width, height);
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
