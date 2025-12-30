package com.hjq.demo.http.api

import com.hjq.http.config.IRequestApi

class UrlCheckApi: IRequestApi{

    override fun getApi(): String {
        return "api/test_check"
    }
    class Bean{

    }
}