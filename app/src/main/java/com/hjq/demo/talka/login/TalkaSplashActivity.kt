package com.hjq.demo.talka.login

import com.blankj.utilcode.util.SPUtils
import com.hjq.base.ktx.startActivity
import com.hjq.demo.R
import com.hjq.demo.app.AppActivity
import com.hjq.demo.http.api.Api
import com.hjq.demo.http.api.NetCheckApi
import com.hjq.demo.http.api.UrlCheckApi
import com.hjq.demo.http.api.UserInfoApi
import com.hjq.demo.http.model.HttpData
import com.hjq.demo.ktx.toast
import com.hjq.demo.other.AppConfig
import com.hjq.demo.talka.ohter.Constant
import com.hjq.demo.talka.ohter.UserInfoBean
import com.hjq.demo.ui.activity.HomeActivity
import com.hjq.http.EasyConfig
import com.hjq.http.EasyHttp
import com.hjq.http.listener.HttpCallbackProxy
import com.hjq.smallchat.utils.UserDataUtils

class TalkaSplashActivity : AppActivity() {

    lateinit var version_name : String;
    lateinit var newest_version : String;
    var passIndex: Int = 0;

    var checkUrlArr : List<String> = ArrayList<String>()

    override fun getLayoutId(): Int {
        return R.layout.splash_activity
    }

    override fun initView() {
    }

    override fun initData() {
        if (AppConfig.isDebug()){
            checkUrl(AppConfig.getHostUrl())
        }else{
            checkNet()
        }
    }
    private fun checkNet() {
        EasyHttp.post(this)
            .server(Api.CONST_INIT_URLS)
            .api(NetCheckApi().apply {
                version_name = AppConfig.getVersionName()
            })
            .request(object : HttpCallbackProxy<HttpData<NetCheckApi.Bean?>>(this) {
                override fun onHttpSuccess(data: HttpData<NetCheckApi.Bean?>) {
                    // ✅ 核心优化1：抛弃!!断言，纯安全调用，无崩溃风险
                    // ✅ 核心优化2：let内重命名it，可读性拉满，符合Kotlin最佳实践
                    data.getData()?.getUrlLists()?.let { urlList ->
                        checkUrlArr = urlList
                        // 列表非空才执行校验，从第0个地址开始
                        if (urlList.isNotEmpty()) {
                            checkUrl(urlList[passIndex])
                        } else {
                            toast(R.string.http_network_error)
                        }
                    } ?: toast(R.string.http_network_error) // 地址列表为空时，直接提示网络错误
                }

                // ✅ 补充优化：新增全局请求失败兜底，原代码缺失，防止接口请求本身失败无响应
                override fun onHttpFail(throwable: Throwable) {
                    toast(R.string.http_network_error)
                }
            })
    }

    private fun getNextPassUrl(): String {
        // ✅ 核心优化1：判空+边界双重校验，彻底杜绝空指针、数组越界崩溃
        val urlList = checkUrlArr ?: return ""
        passIndex++
        // ✅ 核心优化2：简化边界判断，用indices更符合Kotlin语法习惯
        return if (passIndex in urlList.indices) urlList[passIndex] else ""
    }


    private fun checkUrl(url: String) {
        EasyHttp.post(this)
            .server(url)
            .api(UrlCheckApi())
            .request(object : HttpCallbackProxy<HttpData<UrlCheckApi.Bean?>>(this) {
                override fun onHttpSuccess(data: HttpData<UrlCheckApi.Bean?>) {
                    // 地址可用，全局配置+跳转首页
                    EasyConfig.getInstance().setServer(url)
                    Api.CONST_MAIN_HOST = url
                    enterHome()
                }

                override fun onHttpFail(throwable: Throwable) {
                    // 地址不可用，切换下一个地址重试
                    val nextUrl = getNextPassUrl()
                    if (nextUrl.isNotEmpty()) {
                        checkUrl(nextUrl)
                    } else {
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