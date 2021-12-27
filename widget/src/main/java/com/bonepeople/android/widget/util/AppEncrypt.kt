package com.bonepeople.android.widget.util

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 加解密工具类
 */
object AppEncrypt {

    /**
     * 计算给定字符串的MD5值并将结果转换为字符串返回
     */
    fun encryptByMD5(content: String): String {
        val messageDigest = MessageDigest.getInstance("MD5")
        val resultBytes = messageDigest.digest(content.toByteArray())
        val buffer = StringBuffer()
        resultBytes.map {
            val hexStr = Integer.toHexString(it.toInt() and 0xFF)
            if (hexStr.length < 2) {
                buffer.append(0)
            }
            buffer.append(hexStr)
        }
        return buffer.toString()
    }

    /**
     * 根据传入的密钥对数据进行AES加密
     *
     * 模式：AES/CBC/PKCS5Padding
     */
    fun encryptByAES(data: String, secret: String, salt: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(secret.toByteArray(), "AES")
        val iv = IvParameterSpec(salt.toByteArray()) //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
        val encrypted = cipher.doFinal(data.toByteArray())

        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    /**
     * 根据传入的密钥对数据进行AES解密
     *
     * 模式：AES/CBC/PKCS5Padding
     */
    fun decryptByAES(data: String, secret: String, salt: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(secret.toByteArray(), "AES")
        val iv = IvParameterSpec(salt.toByteArray()) //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
        val decrypted = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))

        return String(decrypted)
    }
}