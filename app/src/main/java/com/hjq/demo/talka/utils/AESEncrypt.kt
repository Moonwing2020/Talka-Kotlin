package com.hjq.smallchat.utils

import android.annotation.SuppressLint
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AESEncrypt {
    //编码方式
    private const val CODE_TYPE: String = "UTF-8"
    //填充类型
    private const val AES_TYPE: String = "AES/ECB/PKCS5Padding"
    //私钥
    private const val AES_KEY: String = "IBojRarkcKW2J525" //AES固定格式为128/192/256 bits.即：16/24/32bytes。DES固定格式为128bits，即8bytes。

    @SuppressLint("GetInstance")
    fun encrypt(cleartext: String): String {
        try {
            //两个参数，第一个为私钥字节数组， 第二个为加密方式 AES或者DES
            val key = SecretKeySpec(AES_KEY.toByteArray(), "AES")
            //实例化加密类，参数为加密方式，要写全
            val cipher = Cipher.getInstance(AES_TYPE) //PKCS5Padding比PKCS7Padding效率高，PKCS7Padding可支持IOS加解密
            //初始化，此方法可以采用三种方式，按加密算法要求来添加。（1）无第三个参数（2）第三个参数为SecureRandom random = new SecureRandom();中random对象，随机数。(AES不可采用这种方法)（3）采用此代码中的IVParameterSpec
            //加密时使用:ENCRYPT_MODE;  解密时使用:DECRYPT_MODE;
            cipher.init(Cipher.ENCRYPT_MODE, key) //CBC类型的可以在第三个参数传递偏移量zeroIv,ECB没有偏移量
            //加密操作,返回加密后的字节数组，然后需要编码。主要编解码方式有Base64, HEX, UUE,7bit等等。此处看服务器需要什么编码方式
            val encryptedData = cipher.doFinal(cleartext.toByteArray(charset(CODE_TYPE)))
            return Base64.encodeToString(encryptedData, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    /**
     * 解密
     *
     * @param encrypted
     * @return
     */
    @SuppressLint("GetInstance")
    fun decrypt(encrypted: String?): String {
        try {
            val byteMi = Base64.decode(encrypted, Base64.DEFAULT)
            val key = SecretKeySpec(
                AES_KEY.toByteArray(), "AES"
            )
            val cipher = Cipher.getInstance(AES_TYPE)
            //与加密时不同MODE:Cipher.DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, key) //CBC类型的可以在第三个参数传递偏移量zeroIv,ECB没有偏移量
            val decryptedData = cipher.doFinal(byteMi)
            return String(decryptedData, charset(CODE_TYPE))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return ""
        }
    }
}