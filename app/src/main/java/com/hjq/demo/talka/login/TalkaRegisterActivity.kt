package com.hjq.demo.talka.login

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
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
    private val sexRadioButton : RadioGroup? by lazyFindViewById(R.id.mSexRadioGroup)

    private val sexText: TextView by lazyFindViewById(R.id.edt_sex)

    private val nameEdit : EditText by lazyFindViewById(R.id.edit_nick_name)

    var sexIndex : Int  = 1; //  1 男 2 女

    private var nameStr : String = ""

    private var avatarUrl: Uri? = null

    override fun getLayoutId(): Int {
        return R.layout.register_activity_talka
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, findViewById(R.id.title))

        setOnClickListener(avatarView)

        addEditTextListener()
    }

    private fun addEditTextListener() {
        nameEdit.addTextChangedListener(object  : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                nameStr = s?.toString()?.trim() ?: ""
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })
    }

    override fun initData() {
        sexRadioButton?.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.btnMale -> {
                    sexIndex = 1
                    sexText.setText(R.string.male)
                }
                R.id.btnFeMale->{
                    sexIndex = 2
                    sexText.setText(R.string.female)
                }
            }
        }
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
