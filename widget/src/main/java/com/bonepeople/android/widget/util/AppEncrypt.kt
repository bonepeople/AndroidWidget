package com.bonepeople.android.widget.util

import android.util.Base64
import androidx.annotation.Size
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.security.Key
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
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
    @Deprecated("此方法已迁移至新的工具类", ReplaceWith("AppMessageDigest.md5(inputStream, blockSize, onProgress)"))
    fun encryptByMD5(inputStream: InputStream, blockSize: Int = 1024, onProgress: ((Long) -> Unit)? = null): String = AppMessageDigest.md5(inputStream, blockSize, onProgress)

    @Deprecated("此方法已迁移至新的工具类", ReplaceWith("AppMessageDigest.md5(content)"))
    fun encryptByMD5(content: String): String = AppMessageDigest.md5(content)

    /**
     * 根据传入的密钥对数据进行AES加密
     * + 返回的数据并未进行Base64编码
     * + secret，salt不足16个字符的时候会在后面补充空格
     */
    fun <T : OutputStream> encryptByAES(
        inputStream: InputStream,
        outputStream: T,
        secret: String,
        salt: String? = null,
        transformation: String = "AES/CBC/PKCS5Padding",
        onProgress: ((Long) -> Unit)? = null
    ): T {
        val cipher: Cipher = Cipher.getInstance(transformation)
        val keySpec = SecretKeySpec(secret.padEnd(16).take(16).toByteArray(), "AES")
        val iv: IvParameterSpec? = salt?.padEnd(16)?.take(16)?.toByteArray()?.let { IvParameterSpec(it) }
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
        updateStream(inputStream, outputStream, cipher.blockSize, cipher::update, cipher::doFinal, onProgress)
        return outputStream
    }

    fun encryptByAES(data: String, secret: String, salt: String? = null, transformation: String = "AES/CBC/PKCS5Padding"): String {
        return Base64.encodeToString(encryptByAES(data.byteInputStream(), ByteArrayOutputStream(), secret, salt, transformation).toByteArray(), Base64.NO_WRAP)
    }

    /**
     * 根据传入的密钥对数据进行AES解密
     * + 传入的数据不需要进行Base64编码
     * + secret，salt不足16个字符的时候会在后面补充空格
     */
    fun <T : OutputStream> decryptByAES(
        inputStream: InputStream,
        outputStream: T,
        secret: String,
        salt: String? = null,
        transformation: String = "AES/CBC/PKCS5Padding",
        onProgress: ((Long) -> Unit)? = null
    ): T {
        val cipher: Cipher = Cipher.getInstance(transformation)
        val keySpec = SecretKeySpec(secret.padEnd(16).take(16).toByteArray(), "AES")
        val iv: IvParameterSpec? = salt?.padEnd(16)?.take(16)?.toByteArray()?.let { IvParameterSpec(it) }  //使
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
        updateStream(inputStream, outputStream, cipher.blockSize, cipher::update, cipher::doFinal, onProgress)
        return outputStream
    }

    fun decryptByAES(data: String, secret: String, salt: String? = null, transformation: String = "AES/CBC/PKCS5Padding"): String {
        return decryptByAES(Base64.decode(data, Base64.NO_WRAP).inputStream(), ByteArrayOutputStream(), secret, salt, transformation).toString()
    }

    /**
     * 使用RSA算法对传入的数据进行加密
     * + 模式：PKCS#8
     * + 安卓环境下"RSA"与"RSA/ECB/PKCS1Padding"并不相同，需要注意区分
     * @param key 加密所需的公钥或私钥，可以通过[decodeRSAPublicKey]或[decodeRSAPrivateKey]方法生成
     * @param blockSize 分片加密时单片大小，不可超过RSA加密上限
     */
    fun <T : OutputStream> encryptByRSA(
        inputStream: InputStream,
        outputStream: T,
        key: Key,
        transformation: String = "RSA/ECB/PKCS1Padding",
        blockSize: Int = 0,
        onProgress: ((Long) -> Unit)? = null
    ): T {
        val cipher: Cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val size: Int = if (blockSize > 0) {
            blockSize
        } else if (cipher.blockSize > 0) {
            cipher.blockSize
        } else {
            245
        }
        updateStream(inputStream, outputStream, size, cipher::doFinal, null, onProgress)
        return outputStream
    }

    fun encryptByRSA(data: String, key: Key, transformation: String = "RSA/ECB/PKCS1Padding", blockSize: Int = 0): String {
        return Base64.encodeToString(encryptByRSA(data.byteInputStream(), ByteArrayOutputStream(), key, transformation, blockSize).toByteArray(), Base64.NO_WRAP)
    }

    /**
     * 使用RSA算法对传入的数据进行解密
     * + 模式：PKCS#8
     * + 安卓环境下"RSA"与"RSA/ECB/PKCS1Padding"并不相同，需要注意区分
     * @param key 解密所需的公钥或私钥，可以通过[decodeRSAPublicKey]或[decodeRSAPrivateKey]方法生成
     * @param blockSize 分片加密时单片大小，不可超过RSA加密上限
     */
    fun <T : OutputStream> decryptByRSA(
        inputStream: InputStream,
        outputStream: T,
        key: Key,
        transformation: String = "RSA/ECB/PKCS1Padding",
        blockSize: Int = 0,
        onProgress: ((Long) -> Unit)? = null
    ): T {
        val cipher: Cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val size: Int = if (blockSize > 0) {
            blockSize
        } else if (cipher.blockSize > 0) {
            cipher.blockSize
        } else {
            256
        }
        updateStream(inputStream, outputStream, size, cipher::doFinal, null, onProgress)
        return outputStream
    }

    fun decryptByRSA(data: String, key: Key, transformation: String = "RSA/ECB/PKCS1Padding", blockSize: Int = 0): String {
        return decryptByRSA(Base64.decode(data, Base64.NO_WRAP).inputStream(), ByteArrayOutputStream(), key, transformation, blockSize).toString()
    }

    @Deprecated("请使用新版加密方法", ReplaceWith("AppEncrypt.encryptByAES(inputStream, outputStream, secret, salt, onProgress = onProgress)"))
    fun <T : OutputStream> encryptByAES(inputStream: InputStream, @Size(16) secret: String, @Size(16) salt: String, outputStream: T, onProgress: ((Long) -> Unit)? = null): T = encryptByAES(inputStream, outputStream, secret, salt, onProgress = onProgress)

    @Deprecated("请使用新版解密方法", ReplaceWith("AppEncrypt.decryptByAES(inputStream, outputStream, secret, salt, onProgress = onProgress)"))
    fun <T : OutputStream> decryptByAES(inputStream: InputStream, @Size(16) secret: String, @Size(16) salt: String, outputStream: T, onProgress: ((Long) -> Unit)? = null): T = decryptByAES(inputStream, outputStream, secret, salt, onProgress = onProgress)

    @Deprecated("请使用新版加密方法", ReplaceWith("AppEncrypt.encryptByRSA(inputStream, outputStream, key, \"RSA\", onProgress = onProgress)"))
    fun <T : OutputStream> encryptByRSA(inputStream: InputStream, key: Key, outputStream: T, onProgress: ((Long) -> Unit)? = null): T = encryptByRSA(inputStream, outputStream, key, "RSA", onProgress = onProgress)

    @Deprecated("请使用新版解密方法", ReplaceWith("AppEncrypt.decryptByRSA(inputStream, outputStream, key, \"RSA\", onProgress = onProgress)"))
    fun <T : OutputStream> decryptByRSA(inputStream: InputStream, key: Key, outputStream: T, onProgress: ((Long) -> Unit)? = null): T = decryptByRSA(inputStream, outputStream, key, "RSA", onProgress = onProgress)

    /**
     * 获取RSA公钥
     * @param key 经过Base64编码的公钥
     */
    fun decodeRSAPublicKey(key: String): PublicKey {
        val factory: KeyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(Base64.decode(key, Base64.DEFAULT))
        return factory.generatePublic(keySpec)
    }

    /**
     * 获取RSA私钥
     * @param key 经过Base64编码的私钥
     */
    fun decodeRSAPrivateKey(key: String): PrivateKey {
        val factory: KeyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(Base64.decode(key, Base64.DEFAULT))
        return factory.generatePrivate(keySpec)
    }

    /**
     * 生成RSA密钥对
     * @return 生成的密钥对会放到字符串数组中返回，`[publicKey,privateKey]`
     */
    fun generateRSAKeys(keySize: Int = 2048): Array<String> {
        val generator: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(keySize)
        val keyPair: KeyPair = generator.generateKeyPair()
        val public: String = Base64.encodeToString(keyPair.public.encoded, Base64.NO_WRAP)
        val private: String = Base64.encodeToString(keyPair.private.encoded, Base64.NO_WRAP)
        return arrayOf(public, private)
    }

    private fun updateStream(inputStream: InputStream, outputStream: OutputStream, blockSize: Int, onUpdate: (buffer: ByteArray, offset: Int, length: Int) -> ByteArray?, onFinal: (() -> ByteArray)? = null, onProgress: ((Long) -> Unit)? = null) {
        inputStream.buffered().let { input: BufferedInputStream ->
            val output: BufferedOutputStream = outputStream.buffered()
            val buffer = ByteArray(blockSize)
            var length: Int
            var count = 0L
            while (input.read(buffer, 0, buffer.size).also { length = it } != -1) {
                onUpdate(buffer, 0, length)?.let { output.write(it) }
                count += length
                onProgress?.invoke(count)
            }
            onFinal?.invoke()?.let { output.write(it) }
            output.flush()
        }
    }
}