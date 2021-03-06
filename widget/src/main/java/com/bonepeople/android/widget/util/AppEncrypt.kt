package com.bonepeople.android.widget.util

import android.util.Base64
import java.io.InputStream
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
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
        return convertByteArrayToString(resultBytes)
    }

    /**
     * 计算输入流中数据的MD5值并将结果转换为字符串返回
     * + 待完善：添加终止计算的逻辑
     * @param inputStream 输入流
     * @param blockSize 缓冲区大小，通常配合[onProgress]使用，控制回调频率
     * @param onProgress 计算进度的回调，返回当前已处理内容的大小
     */
    fun encryptByMD5(inputStream: InputStream, blockSize: Int = 1024, onProgress: ((Long) -> Unit)? = null): String {
        val messageDigest = MessageDigest.getInstance("MD5")
        val buffer = ByteArray(blockSize)
        var i: Int
        var count = 0L
        while (inputStream.read(buffer, 0, buffer.size).also { i = it } != -1) {
            count += i
            messageDigest.update(buffer, 0, i)
            onProgress?.invoke(count)
        }
        return convertByteArrayToString(messageDigest.digest())
    }

    fun convertByteArrayToString(bytes: ByteArray): String {
        val buffer = StringBuffer()
        bytes.map {
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
     * + 模式：AES/CBC/PKCS5Padding
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
     * + 模式：AES/CBC/PKCS5Padding
     */
    fun decryptByAES(data: String, secret: String, salt: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(secret.toByteArray(), "AES")
        val iv = IvParameterSpec(salt.toByteArray()) //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
        val decrypted = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))
        return String(decrypted)
    }

    /**
     * 使用RSA算法对传入的数据进行加密
     * + 模式：PKCS#8
     * @param data 需要加密的数据
     * @param key 加密所需的公钥或私钥，可以通过[decodeRSAPublicKey]或[decodeRSAPrivateKey]方法生成
     * @return 将加密的数据进行Base64编码后返回
     */
    fun encryptByRSA(data: String, key: Key): String {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encrypted = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    /**
     * 使用RSA算法对传入的数据进行解密
     * + 模式：PKCS#8
     * @param data 已经通过Base64编码的待解密数据
     * @param key 解密所需的公钥或私钥，可以通过[decodeRSAPublicKey]或[decodeRSAPrivateKey]方法生成
     * @return 解密得到的数据
     */
    fun decryptByRSA(data: String, key: Key): String {
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decrypted = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))
        return String(decrypted)
    }

    /**
     * 获取RSA公钥
     * @param key 经过Base64编码的公钥
     */
    fun decodeRSAPublicKey(key: String): PublicKey {
        val factory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(Base64.decode(key, Base64.DEFAULT))
        return factory.generatePublic(keySpec)
    }

    /**
     * 获取RSA私钥
     * @param key 经过Base64编码的私钥
     */
    fun decodeRSAPrivateKey(key: String): PrivateKey {
        val factory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(Base64.decode(key, Base64.DEFAULT))
        return factory.generatePrivate(keySpec)
    }

    /**
     * 生成RSA密钥对
     * @return 生成的密钥对会放到字符串数组中返回，`[publicKey,privateKey]`
     */
    fun generateRSAKeys(): Array<String> {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.generateKeyPair()
        val public = Base64.encodeToString(keyPair.public.encoded, Base64.DEFAULT)
        val private = Base64.encodeToString(keyPair.private.encoded, Base64.DEFAULT)
        return arrayOf(public, private)
    }
}