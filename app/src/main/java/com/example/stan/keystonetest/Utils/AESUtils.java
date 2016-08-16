package com.example.stan.keystonetest.Utils;

import android.util.Base64;
import android.util.Log;
import com.scottyab.aescrypt.AESCrypt;
import org.json.JSONObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by stan on 16/8/12.
 */
public class AESUtils {

    public static final int ENCRYPT = 0;
    public static final int DECRYPT = 1;

    public static String handleCryptData(String data,int type){
        try {
        String[] dataArray;
        JSONObject object = new JSONObject(data);
        data = object.getString("data");
        dataArray = data.split(",");
        String  cryptoData = dataArray[0];
        String seed = dataArray[1];
        if (type==ENCRYPT){
            data = encryptData(cryptoData,seed);
        }else if (type == DECRYPT){
            data = decryptData(cryptoData,seed);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     *
     * @param data the data used for encrypt
     * @param key the seed for generate key
     * @return encrypt data with key handle with 256-32 as key 512-16 as vi
     */
    public static String encryptData(String data,String key){
        try {
            String  cryptedData = data;
            String seed = key;
            Log.d("ST","crypto Data : "+cryptedData);
            Log.d("ST","crypto seed : "+seed);
            Boolean hex = true;

            String data32 = getSHA256(seed,hex);
            String data16 =  getSHA512(seed,hex);
            SecretKeySpec secretKey = new SecretKeySpec(data32.getBytes(),"AES");
            byte[] byteDate = AESCrypt.encrypt(secretKey,data16.getBytes(),cryptedData.getBytes()); //
            data = Base64.encodeToString(byteDate,Base64.DEFAULT);

           /* Log.e("ST","  -------*******-------");
            Log.e("ST","  data16 : "+data16 );
            Log.e("ST","  data32 : "+data32 );
            Log.e("ST","  byteData : "+getString(byteDate) );
            Log.e("ST","  AESCrypt : "+data );
            Log.e("ST","  -------*******-------");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public static String decryptData(String data,String key){
        try {
            String  cryptedData = data;
            String seed = key;
            Log.d("ST","crypto Data : "+cryptedData);
            Log.d("ST","crypto seed : "+seed);
            Boolean hex = true;

            String data32 = getSHA256(seed,hex);
            String data16 =  getSHA512(seed,hex);
            SecretKeySpec secretKey = new SecretKeySpec(data32.getBytes(),"AES");
            byte[] decodeData = Base64.decode(cryptedData,Base64.DEFAULT);
            byte[] byteDate = AESCrypt.decrypt(secretKey,data16.getBytes(),decodeData);
            data = new String(byteDate,"UTF-8");


          /*  Log.e("ST","  -------*******-------");
            Log.e("ST","  data16 : "+data16 );
            Log.e("ST","  data32 : "+data32 );
            Log.e("ST","  byteData : "+getString(byteDate) );
            Log.e("ST","  AESCrypt : "+data );
            Log.e("ST","  -------*******-------");*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    private static String subTheCode(String s,int n){
        return s.substring(s.length()-n,s.length());
    }

    private static String getSHA256(String val,boolean hex) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("SHA-256");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        if (hex)
            return subTheCode(hexString(m),32); // 64 - 32
        return getString(m);
    }

    private static String getSHA512(String val,boolean hex) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("SHA-512");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        if (hex){
            return subTheCode(hexString(m),16);// 128 -16
        }
        return getString(m);
    }
    private static String hexString(byte[] bytes){
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            int val = ((int) bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();
    }

    public static String EncryptMD5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
