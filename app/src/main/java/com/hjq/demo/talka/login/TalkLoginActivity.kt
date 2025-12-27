package com.hjq.demo.talka.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import com.hjq.demo.R
import com.hjq.demo.aop.SingleClick
import com.hjq.demo.app.AppActivity

class TalkLoginActivity: AppActivity() {

    companion object{
        fun start(context: Context){
            var intent = Intent(context, TalkLoginActivity::class.java)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        }
    }

    private val quickLogin: LinearLayout? by lazy { findViewById(R.id.quick_login) }



    @SingleClick
    override fun onClick(view: View) {
        when (view) {
            quickLogin -> {

            }
        }
    }


    override fun getLayoutId(): Int {
        TODO("Not yet implemented")
        return R.layout.talka_login_activity
    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }
}