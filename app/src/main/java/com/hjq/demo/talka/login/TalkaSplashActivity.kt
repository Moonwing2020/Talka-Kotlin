package com.hjq.demo.talka.login

import com.blankj.utilcode.util.SPUtils
import com.hjq.demo.R
import com.hjq.demo.app.AppActivity
import com.hjq.demo.http.api.Api
import com.hjq.demo.http.api.NetCheckApi
import com.hjq.demo.http.api.UrlCheckApi
import com.hjq.demo.http.model.HttpData
import com.hjq.demo.ktx.toast
import com.hjq.demo.other.AppConfig
import com.hjq.demo.other.Constant
import com.hjq.http.EasyConfig
import com.hjq.http.EasyHttp
import com.hjq.http.listener.HttpCallbackProxy

class TalkaSplashActivity : AppActivity() {

    lateinit var version_name : String;
    lateinit var newest_version : String;
    var passIndex: Int = 0;

    override fun getLayoutId(): Int {
        TODO("Not yet implemented")
        return R.layout.splash_activity
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }


    private fun checkNet(){
        EasyHttp.post(this).server(Api.CONST_INIT_URLS)
            .api(NetCheckApi().apply {
                version_name = AppConfig.getVersionName();
            })
            .request(object : HttpCallbackProxy<HttpData<NetCheckApi.Bean?>>(this) {
                override fun onHttpSuccess(data: HttpData<NetCheckApi.Bean?>) {
                    val tmpUrls:ArrayList<String>? = data.getData()?.getUrlLists()
                    if (tmpUrls != null) {
                        checkUrl(tmpUrls[passIndex],tmpUrls)
                    }
                }
            })
    }

    private fun getNextPassUrl(urlList:ArrayList<String>): String {
        passIndex++
        if (passIndex >= urlList.size) {
            return ""
        }
        return urlList[passIndex]
    }



    private fun checkUrl(url: String,tmpUrls: ArrayList<String>){
        EasyHttp.post(this).server(url)
            .api(UrlCheckApi())
            .request(object : HttpCallbackProxy<HttpData<UrlCheckApi.Bean?>>(this) {
                override fun onHttpSuccess(data: HttpData<UrlCheckApi.Bean?>) {
                    //地址可以正常访问，将地址设置为全局
                    EasyConfig.getInstance().setServer(url)
                    Api.CONST_MAIN_HOST = url
//                    HomeActivity.start(this@SplashActivity)
//                    finish()
                    enterHome()
                }

                override fun onHttpFail(throwable: Throwable?) {
                    val nextUrl: String = getNextPassUrl(tmpUrls)
                    if (nextUrl.isNotEmpty()){
                        checkUrl(nextUrl,tmpUrls)
                    }else{
                        toast(R.string.http_network_error)
                    }
                }
            })
    }

    fun enterHome(){
        val userToken: String = SPUtils.getInstance().getString(Constant.CONST_USER_TOKEN)
        if (userToken.isNotEmpty()){//token不为空则拉去用户信息然后进入到首页
            val userId: String = SPUtils.getInstance().getString(Constant.CONST_USER_ID)
            EasyConfig.getInstance().addHeader("token",userToken)
            // 获取用户信息
            EasyHttp.post(this)
                .api(UserInfoApi().apply {
                    setUid(userId)
                })
                .request(object : HttpCallbackProxy<HttpData<UserInfoBean?>>(this) {
                    override fun onHttpSuccess(data: HttpData<UserInfoBean?>) {
                        //更新缓存信息
                        UserDataUtils.setUserInfoBean(data.getData())
                        startActivity(HomeActivity::class.java)
                        finish()
                    }
                })
        }else{//进到登录界面
            startActivity(TalkLoginActivity::class.java)
            finish()
        }
    }

}