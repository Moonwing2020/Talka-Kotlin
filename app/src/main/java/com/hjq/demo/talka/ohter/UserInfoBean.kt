package com.hjq.demo.talka.ohter


class UserInfoBean(
    var add_fans_num: Int,//新增粉丝数量
    var add_visit_num: Int,//新增访客数量
    var fans_num: Int,//粉丝数量
    var visit: Int,//访客数量
    var follows_num: Int,//关注数量
    var age: Int,//年龄
    var birthday: String,//生日
    var carousels: ArrayList<Carousel>,//我的轮播图
    var city: String,
    var constellation: String,//星座
    var country: String,
    var headimgurl: String,//头像
    var id: Int,//用户id
    var images: ArrayList<String>,//形象照
    var isUploadGif: Int,//是否可以上传gif头像
    var mibi: String,//金币
    var mizuan: Int,//钻石
    var nickname: String,//昵称
    var sex: Int,//性别
    var sign_text: String,//签名
    var gold_level: Int,//贡献等级
    var star_level: Int,//魅力等级
    var task_reward: Int,
    var vip_level: Int//vip等级
){
    inner class Carousel(
        var img: String,//轮播图
        var target: Int,//跳转类型(1webview,其他跳对应界面)
        var url: String//跳转地址
    )
}

