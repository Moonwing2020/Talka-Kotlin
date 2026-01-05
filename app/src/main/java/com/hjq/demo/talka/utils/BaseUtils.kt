package com.hjq.smallchat.utils

import android.text.TextUtils
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

object BaseUtils {

    /**
     * 文件转Base64.
     *
     * @param filePath
     * @return
     */
    fun file2Base64(filePath: String?): String {
        var objFileIS: FileInputStream? = null
        try {
            objFileIS = FileInputStream(filePath)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val objByteArrayOS = ByteArrayOutputStream()
        val byteBufferString = ByteArray(1024)
        try {
            var readNum: Int
            while ((objFileIS!!.read(byteBufferString).also { readNum = it }) != -1) {
                objByteArrayOS.write(byteBufferString, 0, readNum)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val videodata = Base64.encodeToString(objByteArrayOS.toByteArray(), Base64.DEFAULT)
        return videodata
    }

    /**
     * 文件转Base64.
     *
     * @param file
     * @return
     */
    fun file2Base64(file: File?): String {
        var objFileIS: FileInputStream? = null
        try {
            objFileIS = FileInputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val objByteArrayOS = ByteArrayOutputStream()
        val byteBufferString = ByteArray(1024)
        try {
            var readNum: Int
            while ((objFileIS!!.read(byteBufferString).also { readNum = it }) != -1) {
                objByteArrayOS.write(byteBufferString, 0, readNum)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val videodata = Base64.encodeToString(objByteArrayOS.toByteArray(), Base64.DEFAULT)
        return videodata
    }


    //文件转换base64
    fun fileToBase64(file: File?): String? {
        var base64: String? = null
        var `in`: InputStream? = null
        try {
            `in` = FileInputStream(file)
            val bytes = ByteArray(`in`.available())
            val length = `in`.read(bytes)
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                `in`?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return base64
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    fun imageToBase64(path: String?): String? {
        if (TextUtils.isEmpty(path)) {
            return null
        }
        var `is`: InputStream? = null
        var data: ByteArray? = null
        var result: String? = null
        try {
            `is` = FileInputStream(path)
            //创建一个字符流大小的数组。
            data = ByteArray(`is`.available())
            //写入数组
            `is`.read(data)
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (null != `is`) {
                try {
                    `is`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return result
    }

//    fun getJson(type: String?): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        val str: String = JSON.toJSONString(msg)
//        return str
//    }
//
//
//    fun getJson(type: String?, message: String?, nickname: String?, id: String?): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        msg.setMessage(message)
//        msg.setNickName(nickname)
//        msg.setUser_id(id)
//        val str: String = JSON.toJSONString(msg)
//        return str
//    }
//
//    fun getJson(
//        type: String?,
//        message: String?,
//        nickname: String?,
//        id: String?,
//        vip_img: String,
//        hz_img: String
//    ): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        msg.setMessage(message)
//        msg.setNickName(nickname)
//        msg.setUser_id(id)
//        if (TextUtils.isEmpty(vip_img)) {
//            msg.vip_img = ""
//        } else {
//            msg.vip_img = vip_img
//        }
//        if (TextUtils.isEmpty(hz_img)) {
//            msg.hz_img = ""
//        } else {
//            msg.hz_img = hz_img
//        }
//        val str: String = JSON.toJSONString(msg)
//        return str
//    }
//
//    fun getJson(
//        type: String?,
//        message: String?,
//        nickname: String?,
//        id: String?,
//        vip_img: String,
//        hz_img: String,
//        nick_color: String,
//        bean: MessageBean?
//    ): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        msg.setMessage(message)
//        msg.setNickName(nickname)
//        msg.setUser_id(id)
//        msg.nick_color = nick_color
//        if (TextUtils.isEmpty(vip_img)) {
//            msg.vip_img = ""
//        } else {
//            msg.vip_img = vip_img
//        }
//        if (TextUtils.isEmpty(hz_img)) {
//            msg.hz_img = ""
//        } else {
//            msg.hz_img = hz_img
//        }
//
//        if (bean != null) {
//            msg.atName = bean.atName
//            msg.atColor = bean.atColor
//            msg.atId = bean.atId
//        }
//        msg.official_host_img = AdminHomeActivity.official_host_img
//        val str: String = JSON.toJSONString(msg)
//        return str
//    }
//
//    fun getJson(
//        type: String?,
//        message: String?,
//        nickName: String?,
//        userId: String?,
//        dataBean: EnterRoom.RoomInfoBean
//    ): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        msg.setMessage(message)
//        msg.setNickName(nickName)
//        msg.setUser_id(userId)
//
//        msg.setRoom_name(dataBean.getRoom_name()) //名字
//        msg.room_typeHanZi = dataBean.getName() //类型
//        msg.back_img = dataBean.getBack_img() //背景
//        msg.setRoom_intro(dataBean.getRoom_intro()) //公告
//        msg.room_cover = dataBean.getRoom_cover() //封面
//        msg.setRoom_type(dataBean.getRoom_type()) ////类型汉字
//
//        val str: String = JSON.toJSONString(msg)
//        return str
//    }
//
//    fun getJson(
//        type: String?,
//        message: String?,
//        nickName: String?,
//        userId: String?,
//        dataBean: MessageBean
//    ): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        msg.setMessage(message)
//        msg.setNickName(nickName)
//        msg.setUser_id(userId)
//
//        msg.setRoom_name(dataBean.getRoom_name()) //名字
//        msg.room_typeHanZi = dataBean.getRoom_typeHanZi() //类型
//        msg.room_attr = dataBean.getRoom_attr() //类型
//        msg.back_img = dataBean.getBack_img() //背景
//        msg.setRoom_intro(dataBean.getRoom_intro()) //公告
//        msg.room_cover = dataBean.getRoom_cover() //封面
//        msg.setRoom_type(dataBean.getRoom_type()) ////类型汉字
//        msg.setMessage(dataBean.getMessage())
//        msg.official_host_img = AdminHomeActivity.official_host_img
//        val str: String = JSON.toJSONString(msg)
//        return str
//    }
//
//
//    fun getJson(
//        type: String?,
//        message: String?,
//        nickName: String?,
//        userId: String?,
//        dataBean: VipBean.DataBean
//    ): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        msg.setMessage(message)
//        msg.setNickName(nickName)
//        msg.setUser_id(userId)
//        msg.nick_color = dataBean.getNick_color()
//        if (TextUtils.isEmpty(dataBean.getVip_img())) {
//            msg.vip_img = ""
//        } else {
//            msg.vip_img = dataBean.getVip_img()
//        }
//        if (TextUtils.isEmpty(dataBean.getHz_img())) {
//            msg.hz_img = ""
//        } else {
//            msg.hz_img = dataBean.getHz_img()
//        }
//        if (TextUtils.isEmpty(dataBean.getVip_tx())) {
//            msg.vip_tx = ""
//        } else {
//            msg.vip_tx = dataBean.getVip_tx()
//        }
//
//        if (TextUtils.isEmpty(dataBean.getGz_jczq())) {
//            msg.gz_jczq = ""
//        } else {
//            msg.gz_jczq = dataBean.getGz_jczq()
//            msg.setJczq_name(dataBean.getJczq_name())
//        }
//        msg.gz_img = dataBean.getGz_img()
//
//        val str: String = JSON.toJSONString(msg)
//        return str
//    }
//
//    fun getJson(
//        type: String?,
//        message: String?,
//        nickname: String?,
//        id: String?,
//        vipBeans: List<HonourBean?>?,
//        hz_img: String,
//        nick_color: String,
//        pet_img: String,
//        dataBean: VipBean.DataBean?,
//        bean: MessageBean?
//    ): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        msg.setMessage(message)
//        msg.setNickName(nickname)
//        msg.setUser_id(id)
//        msg.nick_color = nick_color
//        msg.pet_img = pet_img
//
//        //        if(TextUtils.isEmpty(vip_img)){
////            msg.vip_img = "";
////        }else {
////            msg.vip_img = vip_img;
////        }
//        if (vipBeans != null && vipBeans.size > 0) {
//            msg.levelInfo = vipBeans
//        }
//
//        if (TextUtils.isEmpty(hz_img)) {
//            msg.hz_img = ""
//        } else {
//            msg.hz_img = hz_img
//        }
//        if (dataBean != null) {
//            msg.ltk = dataBean.getLtk()
//            msg.ltk_left = dataBean.getLtk_left()
//            msg.ltk_right = dataBean.getLtk_right()
//            msg.ltk_down = dataBean.getLtk_down()
//            msg.ltk_top = dataBean.getLtk_top()
//            msg.gz_status = dataBean.getGz_status()
//        }
//
//        if (bean != null) {
//            msg.atName = bean.atName
//            msg.atColor = bean.atColor
//            msg.atId = bean.atId
//
//            msg.gz_img = dataBean.getGz_img()
//            msg.ml_img = dataBean.getMl_img()
//            msg.gx_img = dataBean.getGx_img()
//            msg.headimgurl = dataBean.getHeadimgurl()
//            msg.setHead_txk(dataBean.getHead_txk())
//        }
//        msg.official_host_img = AdminHomeActivity.official_host_img
//
//        val str: String = JSON.toJSONString(msg)
//
//        return str
//    }
//
//    fun getJson(
//        type: String?,
//        message: String?,
//        nickname: String?,
//        id: String?,
//        vipBeans: List<HonourBean?>?,
//        hz_img: String,
//        nick_color: String,
//        headimg: String,
//        chatType: String,
//        pet_img: String,
//        dataBean: VipBean.DataBean?,
//        bean: MessageBean?
//    ): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        msg.setMessage(message)
//        msg.setNickName(nickname)
//        msg.setUser_id(id)
//        msg.pet_img = pet_img
//        msg.nick_color = nick_color
//
//        if (vipBeans != null && vipBeans.size > 0) {
//            msg.levelInfo = vipBeans
//        }
//
//        if (TextUtils.isEmpty(hz_img)) {
//            msg.hz_img = ""
//        } else {
//            msg.hz_img = hz_img
//        }
//        if (dataBean != null) {
//            msg.ltk = dataBean.getLtk()
//            msg.ltk_left = dataBean.getLtk_left()
//            msg.ltk_right = dataBean.getLtk_right()
//            msg.ltk_down = dataBean.getLtk_down()
//            msg.ltk_top = dataBean.getLtk_top()
//
//            msg.gz_status = dataBean.getGz_status()
//            msg.ml_img = dataBean.getMl_img()
//            msg.gx_img = dataBean.getGx_img()
//        }
//
//        if (bean != null) {
//            msg.atName = bean.atName
//            msg.atColor = bean.atColor
//            msg.atId = bean.atId
//            msg.setHead_txk(dataBean.getHead_txk())
//        }
//        msg.chatMessage_type = chatType
//        msg.headimgurl = headimg
//        msg.official_host_img = AdminHomeActivity.official_host_img
//
//        val str: String = JSON.toJSONString(msg)
//
//        return str
//    }
//
//    /**
//     * 房间设置
//     *
//     * @param type
//     * @param message
//     * @param nickname
//     * @param id
//     * @return
//     */
//    fun getJson(
//        type: String?, message: String?,
//        nickname: String?, id: String?, room_name: String?,
//        room_type: String?, room_background: String?,
//        room_intro: String?
//    ): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        msg.setMessage(message)
//        msg.setNickName(nickname)
//        msg.setUser_id(id)
//        msg.setRoom_name(room_name)
//        msg.setRoom_type(room_type)
//        msg.setRoom_background(room_background)
//        msg.setRoom_intro(room_intro)
//
//
//        val str: String = JSON.toJSONString(msg)
//        return str
//    }
//
//    /**
//     * 表情
//     */
//    fun getJson(
//        type: String?,
//        message: String?,
//        nickname: String?,
//        id: String?,
//        is_answer: String?,
//        emoji: String?,
//        t_length: String?,
//        dataBean: VipBean.DataBean,
//        emojiName: String,
//        emojiIndex: Int,
//        imgId: Int
//    ): String {
//        val msg: MessageBean = MessageBean(MessageTypeUtil.getMsgType(type))
//        msg.setMessageType(type)
//        msg.setMessage(message)
//        msg.setNickName(nickname)
//        msg.setUser_id(id)
//        msg.setIs_answer(is_answer)
//        msg.setEmoji(emoji)
//        msg.setT_length(t_length)
//        msg.nick_color = dataBean.getNick_color()
//        msg.emojiName = emojiName
//        msg.emojiIndex = emojiIndex
//        msg.imgId = imgId
//        msg.pet_img = dataBean.getPet_img()
//        msg.levelInfo = dataBean.getLevelInfo()
//
//        if (TextUtils.isEmpty(dataBean.getVip_img())) {
//            msg.vip_img = ""
//        } else {
//            msg.vip_img = dataBean.getVip_img()
//        }
//        if (TextUtils.isEmpty(dataBean.getHz_img())) {
//            msg.hz_img = ""
//        } else {
//            msg.hz_img = dataBean.getHz_img()
//        }
//
//        if (TextUtils.isEmpty(dataBean.getLtk())) {
//            msg.ltk = ""
//        } else {
//            msg.ltk = dataBean.getLtk()
//        }
//
//        msg.gz_img = dataBean.getGz_img()
//        msg.ml_img = dataBean.getMl_img()
//        msg.gx_img = dataBean.getGx_img()
//        msg.headimgurl = dataBean.getHeadimgurl()
//        msg.setHead_txk(dataBean.getHead_txk())
//        val str: String = JSON.toJSONString(msg)
//        return str
//    }
//
//    fun getMessageBean(json: String?): MessageBean {
//        val data: MessageBean = JSON.parseObject(json, MessageBean::class.java)
//        //        LogUtils.debugInfo("sgm","====得到的对象：" + data.toString());
//        return data
//    }
//
//
//    fun getMessageBeanWithAES(json: String): MessageBean {
//        var json = json
//        json = AESEncrypt.decrypt(json)
//        val data: MessageBean = JSON.parseObject(json, MessageBean::class.java)
//
//        LogUtils.debugInfo("sgm", "====得到消息：$json")
//        return data
//    }
//
//    fun getRandom(size: Int): Int {
//        val min = 0
//        val max = size
//        val random = Random()
//        val num = random.nextInt(max) % (max - min + 1) + min
//        LogUtils.debugInfo("====随机数：$num")
//        return num
//    }
//
//
//    /***
//     * 将指定路径的图片转uri
//     * @param context
//     * @param path ，指定图片(或文件)的路径
//     * @return
//     */
//    fun getMediaUriFromPath(context: Context, path: String): Uri? {
//        val mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        val cursor = context.contentResolver.query(
//            mediaUri,
//            null,
//            MediaStore.Images.Media.DISPLAY_NAME + "= ?",
//            arrayOf(path.substring(path.lastIndexOf("/") + 1)),
//            null
//        )
//
//        var uri: Uri? = null
//        if (cursor!!.moveToFirst()) {
//            uri = ContentUris.withAppendedId(
//                mediaUri,
//                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
//            )
//        }
//        cursor.close()
//        return uri
//    }
//
//    fun getNumber(str: String?): String {
//        val regEx = "[^0-9]"
//        val p = Pattern.compile(regEx)
//        val m = p.matcher(str)
//        return m.replaceAll("").trim { it <= ' ' }
//    }
//
//    fun isEmpty(obj: Any?): Boolean {
//        if (obj == null) {
//            return true
//        }
//        if (obj is CharSequence && obj.toString().length == 0) {
//            return true
//        }
//        if (obj.javaClass.isArray && Array.getLength(obj) == 0) {
//            return true
//        }
//        if (obj is Collection<*> && obj.isEmpty()) {
//            return true
//        }
//        if (obj is Map<*, *> && obj.isEmpty()) {
//            return true
//        }
//        if (obj is SimpleArrayMap<*, *> && obj.isEmpty) {
//            return true
//        }
//        if (obj is SparseArray<*> && obj.size() == 0) {
//            return true
//        }
//        if (obj is SparseBooleanArray && obj.size() == 0) {
//            return true
//        }
//        if (obj is SparseIntArray && obj.size() == 0) {
//            return true
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            if (obj is SparseLongArray && obj.size() == 0) {
//                return true
//            }
//        }
//        if (obj is LongSparseArray<*> && obj.size() == 0) {
//            return true
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            if (obj is android.util.LongSparseArray<*>
//                && obj.size() == 0
//            ) {
//                return true
//            }
//        }
//        return false
//    }
//
//    /**
//     * bitmap转为base64
//     *
//     * @return
//     * @parambitmap
//     */
//    fun bitmapToBase64(bitmap: Bitmap?): String? {
//        var result: String? = null
//        var baos: ByteArrayOutputStream? = null
//        try {
//            if (bitmap != null) {
//                baos = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//
//                baos.flush()
//                baos.close()
//
//                val bitmapBytes = baos.toByteArray()
//                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } finally {
//            try {
//                if (baos != null) {
//                    baos.flush()
//                    baos.close()
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//        return result
//    }
//
//
//    fun getDataString(context: Context): String? {
//        val key = "CHANNEL_NAME_KEY"
//        var value = "guanfang"
//        try {
//            val appInfo = context.packageManager.getApplicationInfo(
//                context.packageName, PackageManager.GET_META_DATA
//            )
//            if (appInfo.metaData != null) {
//                value = appInfo.metaData.getString(key)
//                //   Log.i("xxxx", key + "=" + value);
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//            //Log.i("xxxx",e.getLocalizedMessage());
//            return value
//        }
//
//        return value
//    }
}