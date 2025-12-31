package com.hjq.smallchat.utils

import com.blankj.utilcode.util.SPUtils
import com.hjq.demo.talka.ohter.Constant
import com.hjq.demo.talka.ohter.UserInfoBean

object UserDataUtils {
    //缓存全局用户数据对象
    private var userInfoBean: UserInfoBean? = null

    fun getUserInfoBean(): UserInfoBean? {
        return this.userInfoBean
    }

    fun setUserInfoBean(userInfoBean: UserInfoBean?) {
        this.userInfoBean = userInfoBean
    }

    fun updateUserData(token: String?,userId: String?){
        if (token?.isNotEmpty() == true){
            SPUtils.getInstance().put(Constant.CONST_USER_TOKEN,token)
        }
        if (userId?.isNotEmpty() == true){
            SPUtils.getInstance().put(Constant.CONST_USER_ID,userId)
        }
    }
}