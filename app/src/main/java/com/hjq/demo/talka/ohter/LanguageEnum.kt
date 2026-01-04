package com.hjq.smallchat.utils

/**
 * created by mcw on 2024/9/29
 * desc : 多语言枚举实例
 */
enum class LanguageEnum (private val language: String){
    ENGLISH("en"),
    INDONESIA("in"),
    ARAB("ar");

    fun getLangCode(): String {
        return language
    }
}