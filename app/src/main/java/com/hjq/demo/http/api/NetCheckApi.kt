package com.hjq.demo.http.api

import com.hjq.http.config.IRequestApi

class NetCheckApi: IRequestApi{


    override fun getApi(): String {
        TODO("Not yet implemented")
        return "api/android_check"
    }

    private var versionNumber: String? = null


    class Bean{
        private val url: ArrayList<String>? = null
        private val socketIp: ArrayList<String>? = null
        private val status: String? = null
        fun getUrlLists(): ArrayList<String>? {
            return url;
        }

        fun getSocketLists(): ArrayList<String>? {
            return socketIp;
        }

        fun getStatus(): String?{
            return status
        }
    }


}