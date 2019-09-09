package cn.faker.repaymodel.util;

import android.util.Base64;
import android.util.Log;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Mr.C on 2017/9/15 0015.
 */

public class AESUtil {
    //16位的英數組合位元
//32位的英數組合Key欄位
    public final static String IvAES = "12b8c4590a637def" ;
    public final static String KeyAES = "45012389036789845679012345671258";

    public static String EncryptAES(byte[] iv, byte[] key,byte[] text)
    {
        try
        {
            AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = null;
            mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            mCipher.init(Cipher.ENCRYPT_MODE,mSecretKeySpec,mAlgorithmParameterSpec);
            byte[] textbyte = mCipher.doFinal(text);

            return Base64.encodeToString(textbyte, Base64.DEFAULT);
        }
        catch(Exception ex)
        {
            Log.e("ss","sss"+ex.toString());
            return null;
        }
    }

    //AES解密，帶入byte[]型態的16位英數組合文字、32位英數組合Key、需解密文字
    public static String DecryptAES(byte[] iv,byte[] key,byte[] text)
    {
        try
        {
            AlgorithmParameterSpec mAlgorithmParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, "AES");
            Cipher mCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            mCipher.init(Cipher.DECRYPT_MODE,
                    mSecretKeySpec,
                    mAlgorithmParameterSpec);
            byte[] ss =Base64.decode(text, Base64.DEFAULT);

            return new String(mCipher.doFinal(ss),"UTF-8");
        }
        catch(Exception ex)
        {
            return null;
        }
    }
}
