package com.hjq.demo.talka.login

import com.gyf.immersionbar.ImmersionBar
import com.hjq.demo.R
import com.hjq.demo.app.AppActivity

class RegisterActivity : AppActivity(){




    override fun getLayoutId(): Int {
        return R.layout.register_activity_talka
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, findViewById(R.id.title))
    }

    override fun initData() {
    }


}