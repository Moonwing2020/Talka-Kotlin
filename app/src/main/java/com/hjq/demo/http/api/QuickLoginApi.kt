package com.hjq.smallchat.http.api

import com.hjq.http.config.IRequestApi

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2019/12/07
 *    desc   : 用户登录
 */
class QuickLoginApi : IRequestApi {

    override fun getApi(): String {
        return "api/guest_login"
    }

    class Bean {
        private val token: String? = null
        private val headimgurl: String? = null
        private val id: String? = null
        private val nickname: String? = null
        private val status_login = 0

        fun getToken(): String? {
            return token
        }

        fun getId(): String?{
            return id
        }

        fun getNickName(): String?{
            return nickname
        }

        fun getHeadImgUrl(): String?{
            return headimgurl
        }

        fun getStatusLogin(): Int{
            return status_login
        }
    }
}