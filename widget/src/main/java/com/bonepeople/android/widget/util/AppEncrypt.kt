package com.bonepeople.android.widget.util

import android.util.Base64
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
 * Encryption and Decryption Utility
 * Provides methods for AES and RSA encryption and decryption.
 */
@Suppress("Unused", "MemberVisibilityCanBePrivate")
object AppEncrypt {
    /**
     * Encrypts data using AES.
     * - The returned data is not Base64-encoded.
     * - If `secret` or `salt` is less than 16 characters, it will be padded with spaces.
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
     * Decrypts data using AES.
     * - Input data should not be Base64-encoded.
     * - If `secret` or `salt` is less than 16 characters, it will be padded with spaces.
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
        val iv: IvParameterSpec? = salt?.padEnd(16)?.take(16)?.toByteArray()?.let { IvParameterSpec(it) }
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
        updateStream(inputStream, outputStream, cipher.blockSize, cipher::update, cipher::doFinal, onProgress)
        return outputStream
    }

    fun decryptByAES(data: String, secret: String, salt: String? = null, transformation: String = "AES/CBC/PKCS5Padding"): String {
        return decryptByAES(Base64.decode(data, Base64.NO_WRAP).inputStream(), ByteArrayOutputStream(), secret, salt, transformation).toString()
    }

    /**
     * Encrypts data using RSA.
     * - Supports PKCS#8.
     * - Note: "RSA" and "RSA/ECB/PKCS1Padding" are different in Android.
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
     * Decrypts data using RSA.
     * - Supports PKCS#8.
     * - Note: "RSA" and "RSA/ECB/PKCS1Padding" are different in Android.
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

    /**
     * Decodes a Base64-encoded RSA public key.
     */
    fun decodeRSAPublicKey(key: String): PublicKey {
        val factory: KeyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(Base64.decode(key, Base64.DEFAULT))
        return factory.generatePublic(keySpec)
    }

    /**
     * Decodes a Base64-encoded RSA private key.
     */
    fun decodeRSAPrivateKey(key: String): PrivateKey {
        val factory: KeyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(Base64.decode(key, Base64.DEFAULT))
        return factory.generatePrivate(keySpec)
    }

    /**
     * Generates an RSA key pair.
     * @return An array containing `[publicKey, privateKey]` in Base64-encoded strings.
     */
    fun generateRSAKeys(keySize: Int = 2048): Array<String> {
        val generator: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(keySize)
        val keyPair: KeyPair = generator.generateKeyPair()
        val public: String = Base64.encodeToString(keyPair.public.encoded, Base64.NO_WRAP)
        val private: String = Base64.encodeToString(keyPair.private.encoded, Base64.NO_WRAP)
        return arrayOf(public, private)
    }

    private fun updateStream(
        inputStream: InputStream,
        outputStream: OutputStream,
        blockSize: Int,
        onUpdate: (buffer: ByteArray, offset: Int, length: Int) -> ByteArray?,
        onFinal: (() -> ByteArray)? = null,
        onProgress: ((Long) -> Unit)? = null
    ) {
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