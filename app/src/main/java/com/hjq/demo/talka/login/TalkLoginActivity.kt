package com.hjq.demo.talka.login

import android.view.View
import android.widget.LinearLayout
import com.blankj.utilcode.util.GsonUtils
import com.hjq.base.ktx.startActivity
import com.hjq.demo.R
import com.hjq.demo.aop.SingleClick
import com.hjq.demo.app.AppActivity
import com.hjq.demo.http.model.HttpData
import com.hjq.demo.ui.activity.HomeActivity
import com.hjq.http.EasyConfig
import com.hjq.http.EasyHttp
import com.hjq.http.listener.HttpCallbackProxy
import com.hjq.smallchat.http.api.QuickLoginApi
import com.hjq.smallchat.utils.AESEncrypt
import com.hjq.smallchat.utils.UserDataUtils

class TalkLoginActivity: AppActivity() {
    private val quickLogin: LinearLayout? by lazy { findViewById(R.id.quick_login) }


    override fun getLayoutId(): Int {
        return R.layout.talka_login_activity
    }

    override fun initView() {
        setOnClickListener(quickLogin)
    }

    override fun initData() {
    }


    @SingleClick
    override fun onClick(view: View) {
        when (view) {
            quickLogin -> {
                quickStart()
            }
        }
    }

    //快速开始
    private fun quickStart() {
        EasyHttp.post(this)
            .api(QuickLoginApi())
            .request(object :HttpCallbackProxy<HttpData<String?>>(this){
                override fun onHttpSuccess(data: HttpData<String?>) {
                    val tmpData: String = AESEncrypt.decrypt(data.getData())
                    val beans: QuickLoginApi.Bean? = GsonUtils.fromJson(tmpData,QuickLoginApi.Bean::class.java)
                    if (beans?.getStatusLogin() == 2){
                        //进入到注册界面
                        //RegisterActivity.start(mContext?.applicationContext,"")
                        startActivity(TalkaRegisterActivity::class.java)
                        finish()
                    }else{
                        //全局添加请求头
                        EasyConfig.getInstance().addHeader("token",beans?.getToken())
                        //更新用户缓存信息
                        UserDataUtils.updateUserData(beans?.getToken(),beans?.getId())
                        startActivity(HomeActivity::class.java)
                        finish()
                    }
                }
                override fun onHttpFail(throwable: Throwable) {

                }
            })
    }
}