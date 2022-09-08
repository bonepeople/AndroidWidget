package com.bonepeople.android.widget.util

import kotlin.random.Random

/**
 * 随机数工具类
 * + 提供一个随机数种子和一些封装好的快捷方法
 */
object AppRandom {
    /**
     * 用于生成随机数的种子
     * + 该种子使用系统当前时间的时间戳初始化
     */
    val seed: Random by lazy { Random(System.currentTimeMillis()) }
    private val charSet by lazy { mutableSetOf<Char>().apply { addAll(('0'..'9'));addAll(('a'..'z'));addAll(('A'..'Z')) } }

    /**
     * 生成指定长度的随机字符串
     * + 字符串由26个英文字母的大小写字符及数字组成
     */
    fun randomString(@androidx.annotation.IntRange(from = 1) length: Int): String {
        return (1..length).map { charSet.random(seed) }.toCharArray().concatToString()
    }

    /**
     * 从指定的数字区间中生成一个随机的数字
     */
    fun randomInt(range: IntRange): Int {
        return if (range.isEmpty()) 0 else range.random(seed)
    }
}