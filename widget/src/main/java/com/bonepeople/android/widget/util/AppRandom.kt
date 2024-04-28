package com.bonepeople.android.widget.util

import kotlin.random.Random

/**
 * Utility class for generating random numbers
 * + Provides a random seed and several convenient methods
 */
@Suppress("Unused", "MemberVisibilityCanBePrivate")
object AppRandom {
    /**
     * Seed used for generating random numbers.
     * + The seed is initialized with the current system timestamp.
     */
    val seed: Random by lazy { Random(System.currentTimeMillis()) }
    private val charSet by lazy { mutableSetOf<Char>().apply { addAll(('0'..'9'));addAll(('a'..'z'));addAll(('A'..'Z')) } }

    /**
     * Generates a random string of the specified length.
     * + The string consists of uppercase and lowercase English letters and digits.
     */
    fun randomString(@androidx.annotation.IntRange(from = 1) length: Int): String {
        return (1..length).map { charSet.random(seed) }.toCharArray().concatToString()
    }

    /**
     * Generates a random number within the specified range.
     */
    fun randomInt(range: IntRange): Int {
        return if (range.isEmpty()) 0 else range.random(seed)
    }

    /**
     * Shuffles the elements of a collection using the Fisher-Yates algorithm.
     * @return A new list with elements shuffled.
     * @see Iterable.shuffled
     */
    fun <T> shuffleList(list: Iterable<T>): MutableList<T> {
        return list.toMutableList().apply { shuffle(seed) }
    }
}