package com.hjq.demo.talka.login

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.gyf.immersionbar.ImmersionBar
import com.hjq.base.ktx.lazyFindViewById
import com.hjq.demo.R
import com.hjq.demo.aop.SingleClick
import com.hjq.demo.app.AppActivity
import com.hjq.demo.http.glide.GlideApp
import com.hjq.demo.ui.activity.common.ImageSelectActivity
import kotlin.getValue

class TalkaRegisterActivity : AppActivity(){

    private val avatarView: ImageView? by lazyFindViewById(R.id.img)

    private var avatarUrl: Uri? = null

    override fun getLayoutId(): Int {
        return R.layout.register_activity_talka
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, findViewById(R.id.title))

        setOnClickListener(avatarView)
    }

    override fun initData() {
    }


    @SingleClick
    override fun onClick(view: View) {
        super.onClick(view)
       when(view){
           avatarView ->{
               avatarView?.let {
                   startChooseHead()
               }
           }
       }
    }

    // 开始选择图片
    private fun startChooseHead() {
        ImageSelectActivity.start(this, object : ImageSelectActivity.OnImageSelectListener {
            override fun onSelected(data: MutableList<String>) {
                // 裁剪头像
                cropImageFile(data[0])
            }
        })
    }

    /**
     * 裁剪图片
     */
    private fun cropImageFile(imagePath: String) {
        avatarUrl = imagePath.toUri()
        avatarView?.let {
            GlideApp.with(this)
                .load(imagePath)
                .transform(MultiTransformation(CenterCrop(), CircleCrop()))
                .into(it)
        }
    }
}