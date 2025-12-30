package com.hjq.demo.http.api

import com.hjq.http.config.IRequestApi

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject-Kotlin
 *    time   : 2019/12/07
 *    desc   : 获取用户信息
 */
class UserInfoApi : IRequestApi {

    override fun getApi(): String {
        return "api/get_user_info"
    }

    private var user_id: String? = null

    fun setUid(userId: String?){
        this.user_id = userId
    }

    class Bean {
        private val headimgurl: String? = null
        private val nickname: String? = null

        fun getNickName(): String?{
            return nickname
        }

        fun getHeadImgUrl(): String?{
            return headimgurl
        }
    }
}