package com.hjq.demo.http.api

import com.hjq.http.config.IRequestApi

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2019/12/07
 *    desc   : 用户注册
 */
class RegisterApi : IRequestApi {

    override fun getApi(): String {
        return "api/registered"
    }

    //性别
    private var sex: String? = null
    //生日
    private var birthday: Long? = 0
    //昵称
    private var nickname: String? = null
    //头像
    private var headimgurl: String? = null
    //系统
    private var system: String? = null
    //谷歌账号
    private var google_account: String? = null

    fun setSex(sex: String?):RegisterApi = apply {
        this.sex = sex
    }

    fun setBirthday(birthday: Long?): RegisterApi = apply {
        this.birthday = birthday
    }

    fun setNickname(nickname: String?):RegisterApi = apply {
        this.nickname = nickname
    }

    fun setHeadimgurl(headimgurl: String?):RegisterApi = apply {
        this.headimgurl = headimgurl
    }

    fun setSystem(system: String?):RegisterApi = apply {
        this.system = system
    }

    fun setGoogleAccount(googleAccount: String?):RegisterApi = apply {
        this.google_account = googleAccount
    }

    class Bean {

    }
}