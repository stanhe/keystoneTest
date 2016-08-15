package com.example.stan.keystonetest.Utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by stan on 16/8/12.
 */
public class AESUtils {
    public static String decryptData(String data){
        try {
        String[] dataArray = new String[2];
        JSONObject object = new JSONObject(data);
        data = object.getString("data");
        dataArray = data.split(",");
        String  cryptedData = dataArray[0];
        String seed = dataArray[1];
        Log.e("ST","crypted Data : "+cryptedData);
        Log.e("ST","crypted seed : "+seed);





//        byte[] backByte = decrypt(cryptedData.getBytes("ISO-8859-1"),"qwertyuiopasdfghqwertyuiopasdfgh".getBytes("ISO-8859-1"),"qwertyuiopasdfgh".getBytes("ISO-8859-1"));
//        data = new String(backByte,"ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }






















    //下面是一些常量
    /**
     * IV大小.
     */
    private static final int IV_SIZE = 16;

    /**
     * BC包中AES算法名.
     */
    public static final String ALGORITHM_LONG_NAME = "AES/CBC/PKCS7Padding";

    /**
     * BC包中AES算法名.
     */
    public static final String ALGORITHM_SHORT_NAME = "AES";

    /**
     * BC Provider名称.
     */
    public static final String PROVIDER_NAME = "BC";

    //获得加密器的函数
    private static Cipher generateCipher(final int mode, final byte[] key,
                                         final byte[] ivp) throws NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException {
        Cipher res = null;
        final SecretKey secretkey = new SecretKeySpec(key, ALGORITHM_SHORT_NAME);
        final IvParameterSpec ivparameter = new IvParameterSpec(ivp);
        res = Cipher.getInstance(ALGORITHM_LONG_NAME, PROVIDER_NAME);
        res.init(mode, secretkey, ivparameter);

        return res;
    }

//java安全加密的部分对随机数又要求，普通的随机数是不行的，需要特殊处理，应该是长度、算法上有区别，而且好像存储也不一样。使用的方法如下：
    /**
     * 获得密钥.
     * @return 密钥.
     */
    public static byte[] generateKey() {
        byte[] res = null;
        KeyGenerator keyGen = null;
        SecretKey key = null;
        try {
            keyGen = KeyGenerator.getInstance(ALGORITHM_SHORT_NAME, PROVIDER_NAME);
            keyGen.init(new SecureRandom());
            key = keyGen.generateKey();
            res = key.getEncoded();
        } catch (NoSuchAlgorithmException e) {

        } catch (NoSuchProviderException e) {

        }
        return res;
    }

//Java的cipher可以完成加密和解密两种功能，处理过程如下
    /**
     * 处理加密解密过程.
     * @param input
     *          输入.
     * @param cipher
     *          cipher.
     * @return 结果.
     */
    private static byte[] process(final byte[] input, final Cipher cipher) {

        byte[] res = null;
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);

        try {
            cOut.write(input);
            cOut.flush();
            cOut.close();
            res = bOut.toByteArray();
        } catch (IOException e) {

        }
        return res;
    }

//加密和解密接口

    /**
     * 加密.
     * @param data
     *          加密的数据.
     * @param key
     *          密钥.
     * @param iv
     *          CBC算法所需初始矩阵.
     * @return 加密结果.
     */
    public static byte[] encrypt(final byte[] data, final byte[] key, final byte[] iv) {
        byte[] res = null;
        try {
            res = process(data, generateCipher(Cipher.ENCRYPT_MODE, key, iv));
        } catch (Exception e) {
          e.printStackTrace();
        }
        return res;
    }

    /**
     * 解密.
     * @param data
     *          解密的数据.
     * @param key
     *          密钥.
     * @param iv
     *          CBC算法所需初始矩阵.
     * @return 解密结果.
     */
    public static byte[] decrypt(final byte[] data, final byte[] key, final byte[] iv) {
        byte[] res = null;
        try {
            res = process(data, generateCipher(Cipher.DECRYPT_MODE, key, iv));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
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
