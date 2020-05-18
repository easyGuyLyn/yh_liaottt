package com.nwf.sports.net.util;


import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESHelper {

    private final static String DES = "DES";
    private final static String KEY = "63^rt(#)F*Ukg49GK04GB_I09$KG_$4g";

    public DESHelper() {
    }

    public static String encrypt(String pliantext) throws Exception {
        return encodeBase64(encryptDES(pliantext, KEY));
    }

    public static String encrypt(String pliantext, String key) throws Exception {
        return encodeBase64(encryptDES(pliantext, key));
    }

    public static String decrypt(String ciphertext) throws Exception {
        return decryptDES(decodeBase64(ciphertext.getBytes()), KEY);
    }

    public static String decrypt(String ciphertext, String key) throws Exception {
        return decryptDES(decodeBase64(ciphertext.getBytes()), key);
    }

    /**
     * base64编码
     *
     * @param binaryData
     * @return
     * @throws Exception
     */
    private static String encodeBase64(byte[] binaryData) throws Exception {
        try {

//            return Base64.getEncoder().encodeToString(binaryData);
            return android.util.Base64.encodeToString(binaryData, android.util.Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("BASE64编码失败!");
        }
    }

    /**
     * Base64解码
     *
     * @param binaryData
     * @return
     */
    private static byte[] decodeBase64(byte[] binaryData) {
        try {
//            return Base64.getDecoder().decode(binaryData);
            return android.util.Base64.decode(binaryData, android.util.Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("BASE64解码失败！");
        }
    }

    public static byte[] encryptDES(String data, String key) {

        try {
            // 生成一个可信任的随机数源 ,  SHA1PRNG: 仅指定算法名称
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec deskey = new DESKeySpec(key.getBytes("UTF-8"));

            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey secretKey = keyFactory.generateSecret(deskey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES);
            //用密匙初始化Cipher对象,
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
            //现在，获取数据并加密
            //正式执行加密操作
            return cipher.doFinal(data.getBytes("UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptDES(byte[] data, String key) {

        try {
            // 算法要求有一个可信任的随机数源,  SHA1PRNG: 仅指定算法名称
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            // 创建一个DESKeySpec对象
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, random);
            // 真正开始解密操作
            return new String(cipher.doFinal(data), "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        // String enString = encrypt("{\"pageEntity\":{\"pageSize\":\"20\",\"pageIndex\":\"0\"},\"instance\":{\"status\":\"7\"}}");
/*        String enString = "{\"msg\":\"success\",\"code\":0,\"expire\":43199985,\"token\":\"c4d179442983455bb3ae328bce298db6\"}";
        enString = encrypt(enString);
        System.out.println("加密后的字串是：" + enString);*/
        String enString = "+HwIQ145mT+Nm41q4/HVaGUucv8JRoVRJzii/CTeVMft3wpOyi31VO1Vad9MBmd4p9FLpySXqG9jnxs/DlgKaWD4Wn/C524ayg5b3fP6Ays8SY18v/sdJm6MofzJWaW5Bf/jvqaznceA3zCW9vpIBSXqv3OblWR+kcMauAyzqj4Ooru6v3nuYmh90xEqq1s6";
        String deString = decrypt(enString);
        System.out.println("解密后的字串是：" + deString);

    }


}