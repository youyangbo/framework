package com.lzlmall.b2b.app.vendor.framework.utils;

/**
 * Created by Administrator on 2018/4/18.
 */

public interface Constant {

    /**
     * url
     */
    //account 4567
    //192.168.0.121
    //merchant 7777

    String BaseUrl = "http://192.168.0.124:9999";
    String merchantBaseUrl = "http://192.168.0.121:7777";
    String OderBaseUrl = "http://192.168.0.155:8899";
    String ProductBaseUrl = "http://192.168.0.121:8866";
    //获取验证码
    String MESCODE = BaseUrl + "/api/account/message";
    //注册
    String REGISTERURL = BaseUrl + "/api/account/signup";
    //登录
    String LOGIN = BaseUrl + "/api/account/login";
    //获取用户信息
    String GETUESRINFO = BaseUrl + "/api/account/getInfo";
    //退出登录
    String LOGOUT = BaseUrl + "/api/account/signout";
    //编辑用户信息
    String UPDATEINFO = BaseUrl + "/api/account/edit";
    //获取banner
    String BANNER = BaseUrl + "/api/account/banner";
    //申请入驻
    String APPLICATIONINURL = merchantBaseUrl + "/api/merchant/apply";
    //申请入驻
    String APPLICATIONINURL2 = merchantBaseUrl + "/api/merchant/checkMerchant";
    //查询仓库
    String GETRESPOTORY = merchantBaseUrl + "/api/merchantStore/merchant";
    //删除仓库
    String DELETEREPOTITRY = merchantBaseUrl + "/api/merchantStore";
    //编辑仓库
    String EDITREPOSITORY = merchantBaseUrl + "/api/merchantStore";

    //更新电话号码
    String UPDATEPHONE = BaseUrl + "/api/account/changePhoneNumber";
    //修改密码
    String UPDATEPWD = BaseUrl + "/api/account/changePassword";
    //查询订单
    String SEACHORDE = OderBaseUrl + "/order";
    //商品查询
    String SEACHSHOPURL = ProductBaseUrl + "/api/product/page/conditions";
    //商品级别查询
    String LEVELURL1 = ProductBaseUrl + "/api/product/getByLevel";
    //商品二级
    String LEVELURL2 = ProductBaseUrl + "/api/product/getByParentId";


    String ACTIVTYPUTBUNDLEKEY = "跳转Activity传递的bundleKey";

    String NETWORKERRO_KEY = "NETWORKERRO_KEY";
    String TIMEOUTEXCEPTION = "网络超时";
    String OTHRENETWORKEXCEPTION = "网络异常";
    String NONETWORKEXCEPTION = "当前无网络";

    int REQUESTCODE = 0x0001;
    int NETWORKRESULTCODE = 0x0002;


    String ACCOUNTID_KEY = "用户id";

    String QIYENAMEKEY = "企业名称key";
    String TOYIXINGYONGCODE_KEY = "统一信用codekey";
    String QIYERENZHENGBEANKEY = "企业认证beanKey";

    String QIYEJIANJIEDATA = "企业简介所有数据";

    String PHONE_KEY = "电话号码_key";

    String ADRRESSINFO_KEY = "详细地址";

    String ACCOUNT_INFOR_KEY = "用户信息";

    String TOKEN = "TOKEN";
    String MOBILE_KEY = "電話號碼";


    String UESRNAMEKEY = "用户名字_key";
    String UESRINFO_KEY = "用户信息";
    String JOBNAMEKEY = "职位";


    String UNIT_KEY = "单位";

    String REPOSITORYINFOR_KEY = "仓库信息";

    String CURRENT_LOCATION = "当前城市";
    String ADRRESS_KEY = "选择地址信息";

    String LATITUDE_KEY = "纬度";
    String LONGITUDE_KEY = "经度";
    String POI_TYPE = "高德地图兴趣点类型—key";
    String LONGPHOEN_KEY = "登录电话";
    String m_id = "m_id_key";
    String mc_id = "mc_id_key";
    String CHUSHI_KUCUN_KEY = "初始库存——key";
    String SET_KUCUN_KEY = "设置库存_key";
    String SHOPID_KEY = "商品id_key";
    String KUNCUN_TYPE_KEY = "区别设置库存时是修改进来还是添加"; //"1"  ;  //1代表修改  0 代表添加

    String KUCUN_KEY = "库存key";
    String SHOPID_TYPE_KEY = "判断是审核商品还是非审核商品";   //0 是非审核商品    1 是审核商品
    String QIYE_INFO_KEY = "企业信息_key";
    String ORDE_INFO_KEY = "订单详情——key";
    String ORDE_INFO_TYPE_KEY = "订单详情的类别";  //0 待发货的详情
    String CONSULT_HISTORY_KEY = "协商历史——key";
    String REPOSITORYINFOR_PEISONG_LIST_KEY = "配送区域集合——key";
    String IMAGE_URL_KEY = "图片url地址——key";
    String SCANSHOP_KEY = "扫描结果——key";
}