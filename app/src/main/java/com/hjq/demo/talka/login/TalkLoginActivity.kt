package com.hjq.demo.talka.login

import android.view.View
import android.widget.LinearLayout
import com.hjq.demo.R
import com.hjq.demo.aop.SingleClick
import com.hjq.demo.app.AppActivity

class TalkLoginActivity: AppActivity() {
    private val quickLogin: LinearLayout? by lazy { findViewById(R.id.quick_login) }

    @SingleClick
    override fun onClick(view: View) {
        when (view) {
            quickLogin -> {

            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.talka_login_activity
    }

    override fun initView() {
    }

    override fun initData() {
    }
}