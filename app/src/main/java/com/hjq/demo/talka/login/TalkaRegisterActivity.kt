package com.hjq.demo.talka.login

import android.net.Uri
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.net.toUri
import com.blankj.utilcode.util.GsonUtils
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.gyf.immersionbar.ImmersionBar
import com.hjq.base.BaseDialog
import com.hjq.base.ktx.lazyFindViewById
import com.hjq.base.ktx.startActivity
import com.hjq.demo.R
import com.hjq.demo.aop.SingleClick
import com.hjq.demo.app.AppActivity
import com.hjq.demo.http.api.RegisterApi
import com.hjq.demo.http.glide.GlideApp
import com.hjq.demo.http.model.HttpData
import com.hjq.demo.ktx.toast
import com.hjq.demo.ui.activity.HomeActivity
import com.hjq.demo.ui.activity.common.ImageSelectActivity
import com.hjq.demo.ui.dialog.common.DateDialog
import com.hjq.http.EasyConfig
import com.hjq.http.EasyHttp
import com.hjq.http.config.IRequestApi
import com.hjq.http.listener.HttpCallbackProxy
import com.hjq.smallchat.http.api.QuickLoginApi
import com.hjq.smallchat.utils.AESEncrypt
import com.hjq.smallchat.utils.BaseUtils
import com.hjq.smallchat.utils.BitmapHelper
import com.hjq.smallchat.utils.UserDataUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.getValue

class TalkaRegisterActivity : AppActivity(){

    private val avatarView: ImageView by lazyFindViewById(R.id.img)
    private val sexRadioButton : RadioGroup? by lazyFindViewById(R.id.mSexRadioGroup)

    private val sexText: TextView by lazyFindViewById(R.id.edt_sex)

    private val nameEdit : EditText by lazyFindViewById(R.id.edit_nick_name)

    private val birthdayText : TextView by lazyFindViewById(R.id.birthday_value)

    private val btSend: TextView by lazyFindViewById(R.id.btn_ok)

    var sexIndex : Int  = 0; //  1 男 2 女

    private var nameStr : String = ""

    private var avatarUrl: Uri? = null

    private var birthdayDate: Long = 0

    private var imgPath: String = ""

    private var googleAccount: String? = ""

    override fun getLayoutId(): Int {
        return R.layout.register_activity_talka
    }

    override fun initView() {
        ImmersionBar.setTitleBar(this, findViewById(R.id.title))

        setOnClickListener(avatarView , birthdayText , btSend)

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
                    if(avatarUrl == null){
                        avatarView.setImageResource(R.drawable.gender_zhuce_moren_boy)
                    }
                }
                R.id.btnFeMale->{
                    sexIndex = 2
                    sexText.setText(R.string.female)
                    if(avatarUrl == null) {
                        avatarView.setImageResource(R.drawable.gender_zhuce_moren_girl)
                    }
                }
            }
        }
    }

    @SingleClick
    override fun onClick(view: View) {
        super.onClick(view)
       when(view){
           avatarView ->{
               startChooseHead()
           }

           birthdayText->{
               showDatePickerDialog()
           }

           btSend ->{
               startRegister()
           }
       }
    }

    private fun startRegister() {
        val nickName = nameEdit.text.toString().replace(" ", "").trim { it <= ' ' }
        if(sexIndex == 0){
            toast(getString(R.string.text_choice_sex))
            return
        }else if(birthdayDate <= 0){
            toast(getString(R.string.choose_birthday))
            return
        }else if(TextUtils.isEmpty(nickName)) {
            toast(getString(R.string.nickname))
            return
        }

        loginApp(nickName)
    }

    private fun loginApp(nickName: String) {
        var finalPath = ""
        val imgFile: File?
        if (imgPath != "") {
            imgFile = BitmapHelper.scal(this , imgPath)
            finalPath = "data:image/png;base64," + BaseUtils.file2Base64(imgFile)
        }

        showLoadingDialog()
        EasyHttp.post(this)
            .api(RegisterApi().apply {
                setSex(sexIndex.toString())
                setBirthday(birthdayDate)
                setNickname(nickName)
                setHeadimgurl(finalPath)
                setSystem("android")
                setGoogleAccount(googleAccount)
            })
            .request(object : HttpCallbackProxy<HttpData<String?>>(this) {
                override fun onHttpSuccess(data: HttpData<String?>) {
                    val tmpData: String = AESEncrypt.decrypt(data.getData())
                    val beans: QuickLoginApi.Bean? = GsonUtils.fromJson(tmpData, QuickLoginApi.Bean::class.java)
                    //全局添加请求头
                    EasyConfig.getInstance().addHeader("token",beans?.getToken())
                    UserDataUtils.updateUserData(beans?.getToken(),beans?.getId())
                    startActivity(HomeActivity::class.java)
                    finish()
                }

                override fun onHttpFail(throwable: Throwable) {

                }

                override fun onHttpEnd(api: IRequestApi) {
                    super.onHttpEnd(api)
                    hideLoadingDialog()
                }
            })

    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        var currentTime = calendar.time.time
        if (birthdayDate != 0L){
            currentTime = birthdayDate
        }
        // 日期选择对话框
        DateDialog.Builder(this,
            Calendar.getInstance(Locale.CHINA)[Calendar.YEAR] - 100,
            Calendar.getInstance(Locale.CHINA)[Calendar.YEAR] - 18)
            .setTitle(getString(R.string.date_title))
            // 确定按钮文本
            .setConfirm(getString(R.string.common_confirm))
            // 设置 null 表示不显示取消按钮
            .setCancel(null)
            .setDate(currentTime)
            .setListener(object : DateDialog.OnListener {
                override fun onSelected(dialog: BaseDialog, year: Int, month: Int, day: Int) {
                    // 如果不指定时分秒则默认为现在的时间
                    calendar.set(Calendar.YEAR, year)
                    // 月份从零开始，所以需要减 1
                    calendar.set(Calendar.MONTH, month - 1)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    birthdayDate = calendar.timeInMillis/1000
                    birthdayText.text = formatCalendarTime(calendar)
                }

                override fun onCancel(dialog: BaseDialog) {
                    toast("取消了")
                }
            })
            .show()
    }

    fun formatCalendarTime(calendar: Calendar): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }


    // 开始选择图片
    private fun startChooseHead() {
        ImageSelectActivity.start(this, object : ImageSelectActivity.OnImageSelectListener {
            override fun onSelected(data: MutableList<String>) {

                imgPath = data[0];
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
        avatarView.let {
            GlideApp.with(this)
                .load(imagePath)
                .transform(MultiTransformation(CenterCrop(), CircleCrop()))
                .into(it)
        }
    }
}
