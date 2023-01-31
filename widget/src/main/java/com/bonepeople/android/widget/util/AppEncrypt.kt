package com.bonepeople.android.widget.util

import android.util.Base64
import androidx.annotation.Size
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 加解密工具类
 */
@Suppress("UNUSED")
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
        var length: Int
        var count = 0L
        inputStream.buffered().use { input ->
            while (input.read(buffer, 0, buffer.size).also { length = it } != -1) {
                messageDigest.update(buffer, 0, length)
                count += length
                onProgress?.invoke(count)
            }
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
    fun encryptByAES(data: String, @Size(16) secret: String, @Size(16) salt: String, base64Flag: Int = Base64.NO_WRAP): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(secret.toByteArray(), "AES")
        val iv = IvParameterSpec(salt.toByteArray()) //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
        val encrypted = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encrypted, base64Flag)
    }

    /**
     * 根据传入的密钥对数据进行AES加密
     * + 模式：AES/CBC/PKCS5Padding
     * + 返回的数据并未进行Base64编码
     */
    fun <T : OutputStream> encryptByAES(inputStream: InputStream, @Size(16) secret: String, @Size(16) salt: String, outputStream: T, onProgress: ((Long) -> Unit)? = null): T {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(secret.toByteArray(), "AES")
        val iv = IvParameterSpec(salt.toByteArray()) //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
        inputStream.buffered().use { input ->
            val output = outputStream.buffered()
            val buffer = ByteArray(cipher.blockSize)
            var length: Int
            var count = 0L
            while (input.read(buffer, 0, buffer.size).also { length = it } != -1) {
                output.write(cipher.update(buffer, 0, length))
                count += length
                onProgress?.invoke(count)
            }
            output.write(cipher.doFinal())
            output.flush()
        }
        return outputStream
    }

    /**
     * 根据传入的密钥对数据进行AES解密
     * + 模式：AES/CBC/PKCS5Padding
     */
    fun decryptByAES(data: String, @Size(16) secret: String, @Size(16) salt: String, base64Flag: Int = Base64.NO_WRAP): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(secret.toByteArray(), "AES")
        val iv = IvParameterSpec(salt.toByteArray()) //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
        val decrypted = cipher.doFinal(Base64.decode(data, base64Flag))
        return String(decrypted)
    }

    /**
     * 根据传入的密钥对数据进行AES解密
     * + 模式：AES/CBC/PKCS5Padding
     * + 传入的数据不需要进行Base64编码
     */
    fun <T : OutputStream> decryptByAES(inputStream: InputStream, @Size(16) secret: String, @Size(16) salt: String, outputStream: T, onProgress: ((Long) -> Unit)? = null): T {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val keySpec = SecretKeySpec(secret.toByteArray(), "AES")
        val iv = IvParameterSpec(salt.toByteArray()) //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
        inputStream.buffered().use { input ->
            val output = outputStream.buffered()
            val buffer = ByteArray(cipher.blockSize)
            var length: Int
            var count = 0L
            while (input.read(buffer, 0, buffer.size).also { length = it } != -1) {
                output.write(cipher.update(buffer, 0, length))
                count += length
                onProgress?.invoke(count)
            }
            output.write(cipher.doFinal())
            output.flush()
        }
        return outputStream
    }

    /**
     * 使用RSA算法对传入的数据进行加密
     * + 模式：PKCS#8
     * @param data 需要加密的数据
     * @param key 加密所需的公钥或私钥，可以通过[decodeRSAPublicKey]或[decodeRSAPrivateKey]方法生成
     * @return 将加密的数据进行Base64编码后返回
     */
    fun encryptByRSA(data: String, key: Key, base64Flag: Int = Base64.NO_WRAP): String {
        val outputStream = ByteArrayOutputStream()
        data.byteInputStream().use { input ->
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val buffer = ByteArray(cipher.blockSize)
            var length: Int
            while (input.read(buffer, 0, buffer.size).also { length = it } != -1) {
                outputStream.write(cipher.doFinal(buffer, 0, length))
            }
        }
        return Base64.encodeToString(outputStream.toByteArray(), base64Flag)
    }

    /**
     * 使用RSA算法对传入的数据进行加密
     * + 模式：PKCS#8
     */
    fun <T : OutputStream> encryptByRSA(inputStream: InputStream, key: Key, outputStream: T, onProgress: ((Long) -> Unit)? = null): T {
        inputStream.buffered().use { input ->
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val output = outputStream.buffered()
            val buffer = ByteArray(cipher.blockSize)
            var length: Int
            var count = 0L
            while (input.read(buffer, 0, buffer.size).also { length = it } != -1) {
                output.write(cipher.doFinal(buffer, 0, length))
                count += length
                onProgress?.invoke(count)
            }
            output.flush()
        }
        return outputStream
    }

    /**
     * 使用RSA算法对传入的数据进行解密
     * + 模式：PKCS#8
     * @param data 已经通过Base64编码的待解密数据
     * @param key 解密所需的公钥或私钥，可以通过[decodeRSAPublicKey]或[decodeRSAPrivateKey]方法生成
     * @return 解密得到的数据
     */
    fun decryptByRSA(data: String, key: Key, base64Flag: Int = Base64.NO_WRAP): String {
        val outputStream = ByteArrayOutputStream()
        Base64.decode(data, base64Flag).inputStream().use { input ->
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.DECRYPT_MODE, key)
            val buffer = ByteArray(cipher.blockSize)
            var length: Int
            while (input.read(buffer, 0, buffer.size).also { length = it } != -1) {
                outputStream.write(cipher.doFinal(buffer, 0, length))
            }
        }
        return outputStream.toString()
    }

    /**
     * 使用RSA算法对传入的数据进行解密
     * + 模式：PKCS#8
     */
    fun <T : OutputStream> decryptByRSA(inputStream: InputStream, key: Key, outputStream: T, onProgress: ((Long) -> Unit)? = null): T {
        inputStream.buffered().use { input ->
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.DECRYPT_MODE, key)
            val output = outputStream.buffered()
            val buffer = ByteArray(cipher.blockSize)
            var length: Int
            var count = 0L
            while (input.read(buffer, 0, buffer.size).also { length = it } != -1) {
                output.write(cipher.doFinal(buffer, 0, length))
                count += length
                onProgress?.invoke(count)
            }
            output.flush()
        }
        return outputStream
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