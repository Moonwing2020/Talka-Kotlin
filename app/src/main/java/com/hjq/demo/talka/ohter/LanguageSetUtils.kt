package com.hjq.smallchat.utils

import android.content.Context
import com.blankj.utilcode.util.SPUtils
import com.hjq.demo.talka.ohter.Constant
import com.hjq.language.MultiLanguages
import java.util.Locale

object LanguageSetUtils {
    fun setDefaultLanguageCode(mContext: Context){
        // 获取手机是否保存过选择的语言
        val result = SPUtils.getInstance().getString(Constant.CONST_LANGUAGE_KEY)
        // 如果isEmpty is false 直接返回
        if (result.isNotEmpty()) {
            val locale = Locale(result)
            MultiLanguages.setAppLanguage(mContext, locale)
            Constant.CHOOSE_LANGUAGE = result
            return
        }

        // 如果都没有， 默认设置阿拉伯语
        val locale = Locale(LanguageEnum.ARAB.getLangCode())
        SPUtils.getInstance().put(Constant.CONST_LANGUAGE_KEY,LanguageEnum.ARAB.getLangCode())
        // 设置默认的语种（越早设置越好）
        MultiLanguages.setAppLanguage(mContext, locale)
        Constant.CHOOSE_LANGUAGE = LanguageEnum.ARAB.getLangCode()
    }

    // 获取当前语言包的code
    fun getLanguageCode(): String {
        // 获取手机是否保存过选择的语言
        return SPUtils.getInstance().getString(Constant.CONST_LANGUAGE_KEY)
    }

    // 设置指定的语言
    fun setLanguage(mContext: Context, language: LanguageEnum) {
        val locale = Locale(language.getLangCode())
        MultiLanguages.setAppLanguage(mContext, locale)
        SPUtils.getInstance().put(Constant.CONST_LANGUAGE_KEY,language.getLangCode())
        Constant.CHOOSE_LANGUAGE = language.getLangCode()
        //HomeActivity.start(mContext)
    }
}