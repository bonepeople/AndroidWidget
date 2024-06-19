package com.bonepeople.android.widget.util.event

data class EventInfo<T> (
    val name: String,
    val data: T, //json
)