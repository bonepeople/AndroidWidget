package com.bonepeople.android.widget.util

import kotlin.random.Random

@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object AppRandom {
    val seed: Random by lazy { Random(System.currentTimeMillis()) }
    private val charSet by lazy { mutableSetOf<Char>().apply { addAll(('0'..'9'));addAll(('a'..'z'));addAll(('A'..'Z')) } }

    fun randomString(@androidx.annotation.IntRange(from = 1) length: Int): String {
        return (1..length).map { charSet.random(seed) }.toCharArray().concatToString()
    }

    fun randomInt(range: IntRange): Int {
        return if (range.isEmpty()) 0 else range.random(seed)
    }

    fun <T> shuffleList(list: Iterable<T>): MutableList<T> {
        return list.toMutableList().apply { shuffle(seed) }
    }
}