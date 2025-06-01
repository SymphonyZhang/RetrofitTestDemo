package com.francis.retrofittestdemo.wan_android.domain

/**
 * domain 层中Banner的数据结构
 */
data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)