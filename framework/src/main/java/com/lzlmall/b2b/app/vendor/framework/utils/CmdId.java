/**
 * Project Name:lzlmall-b2b-common
 * File Name:CmdId.java
 * Package Name:com.lzlmall.b2b.common.cmd.code
 * Date:2018-5-22下午5:39:22
 * Copyright (c) 2018, 上海凌天信息科技有限公司 All Rights Reserved.
 */

package com.lzlmall.b2b.app.vendor.framework.utils;

/**
 * ClassName:CmdId
 *
 * @Title:
 * @Description :命令模式的命令编码
 * @author 杨金勇
 * @version
 * @since JDK 1.7 Date: 2018-5-22 下午5:39:22
 * @see
 */
public interface CmdId {
    String BaseUrl = "http://VQXWEANSF2RGPS7:9999";
//    String BaseUrl = "http://192.168.0.157:9999";

    /**
     * 检测消息
     */
    short CHECK_MESSAGE = 10000;

    /**
     * 检查版本更新
     */
    short CHECK_VERSION = 10001;

    /**
     * 启动页 Leader
     */
    short QUERY_LEADER = 10002;

    /**
     * 系统首页轮播图
     */
    short HOME_PAGE_AD_LIST = 10003;

    // -------------11 供应商信息接口编码 开始---------------------//

    /**
     * 注册第一步 手机号码 发送验证码
     */
    short MERCHANT_REGISTERED_FIRST_SENDCODE = 11001;

    /**
     * 注册第二步 校验验证码 然后保存密码
     */
    short MERCHANT_REGISTERED_TWO = 11002;

    /**
     * 注册第三步 公司信息注册
     */
    short MERCHANT_REGISTERED_THREE = 11003;

    /**
     * 公司信息注册 审核查询 Auditing
     */
    short MERCHANT_REGISTERED_AUDITING_QUERY = 11004;

    /**
     * 供应商登录
     */
    short MERCHANT_LOGIN_MOBILE = 11005;

    /**
     * 供应商密码找回
     */
    short MERCHANT_PASSWORD_RECOVERY = 11006;

    /**
     * 工作平台--供应商个人信息查询
     */
    short MERCHANT_INFO_QUERY = 11007;

    /**
     * 工作平台--供应商个人信息修改
     */
    short MERCHANT_INFO_MODIFY = 11008;

    /**
     * 工作平台--供应商 修改手机号码 发送验证码
     */
    short MERCHANT_MOBILE_MODIFY_SENDSMS = 11009;

    /**
     * 工作平台--供应商 修改手机号码
     */
    short MERCHANT_MOBILE_MODIFY = 11010;

    /**
     * 工作平台--供应商 修改密码
     */
    short MERCHANT_PASSWORD_MODIFY = 11011;

    /**
     * 工作平台--供应商企业信息 enterprise
     */
    short MERCHANT_ENTERPRISE_INFO_QUERY = 11012;

    /**
     * 供应商密码找回时发送验证码
     */
    short MERCHANT_PASSWORD_RECOVERY_SENDSMS = 11013;

    short CHANGE_QIYE_INFO = 11015;



    // -------------11供应商信息接口编码 结束---------------------//

    // -------------12工作平台 供应商商品接口编码开始 --------------//
    /**
     * 供应商商品--在售 sale
     */
    short MERCHANT_PRODUCT_SALE_LIST = 12001;

    /**
     * 供应商商品--仓库 store
     */
    short MERCHANT_PRODUCT_STORE_LIST = 12002;

    /**
     * 供应商商品--审核 auditing
     */
    short MERCHANT_PRODUCT_AUDITING_LIST = 12003;

    /**
     * 供应商商品--审核 修改
     */
    short MERCHANT_PRODUCT_AUDITING_MODIFY = 12004;

    /**
     * 供应商商品--审核 取消（撤销）cancel
     */
    short MERCHANT_PRODUCT_AUDITING_CANCEL = 12005;

    /**
     * 供应商商品--根据条码查询
     */
    short MERCHANT_PRODUCT_CODE_QUERY = 12006;

    /**
     * 供应商商品--新增ADD
     */
    short MERCHANT_PRODUCT_ADD = 12007;

    /**
     * 供应商商品--修改modify
     */
    short MERCHANT_PRODUCT_MODIFY = 12008;

    /**
     * 供应商商品--删除 del
     */
    short MERCHANT_PRODUCT_DEL = 12009;

    /**
     * 供应商商品--下架 down
     */
    short MERCHANT_PRODUCT_DOWN = 12010;

    /**
     * 供应商商品--上架 up
     */
    short MERCHANT_PRODUCT_UP = 12011;

    /**
     * 获取商品详
     */
    short MERCHANT_PRODUCT_GETSHOPINFO1= 12012;
    /**
     * 获取商品详情
     * (审核商品的详情)
     */
    short MERCHANT_PRODUCT_GETSHOPINFO= 12013;
    /**
     * delete 删除审核商品
     */
    short MERCHANT_PRODUCT_DELETE_SHENHE_SHOP= 12014;

    // -------------12工作平台 供应商商品接口编码 结束 --------------//

    // -------------13工作平台 供应商订单接口编码 开始 --------------//
    /**
     * 供应商订单--待发货 Pending order
     */
    short MERCHANT_ORDER_PENDING_LIST = 13001;

    /**
     * 供应商订单--已发货 Already
     */
    short MERCHANT_ORDER_ALREADY_LIST = 13002;

    /**
     * 供应商订单 --已完成 Completed
     */
    short MERCHANT_ORDER_COMPLETED_LIST = 13003;

    /**
     * 供应商订单 --售后/退货 after-sales
     */
    short MERCHANT_ORDER_AFTERSALES_LIST = 13004;

    /**
     * 供应商订单 --详情 details
     */
    short MERCHANT_ORDER_DETAILS = 13005;

    /**
     * 供应商订单 --发货 send
     */
    short MERCHANT_ORDER_SEND = 13006;

    /**
     * 供应商订单 --详情 (售后/退货) after-sales details
     */
    short MERCHANT_ORDER_AFTERSALES_DETAILS = 13007;


    /**
     * 供应商订单 --同意 (售后/退货) after-sales Agree
     */
    short MERCHANT_ORDER_AFTERSALES_AGREE = 13008;

    /**
     * 供应商订单 --拒绝 (售后/退货) after-sales refuse
     */
    short MERCHANT_ORDER_AFTERSALES_REFUSE = 13009;
    /**
     * 供应商订单 --发货前获取供应商所有仓库
     */
    short MERCHANT_ORDER_SEND_STORE = 13010;
    /**
     * 供应商订单 --删除完成订单
     */
    short MERCHANT_ORDER_DELETE_COMPLTE = 13011;

    short MERCHANT_ORDER_DELETE_COMPLTE1312  = 13012;




    // -------------13供应商订单接口编码 结束 --------------//

    // -------------14供应商仓库接口编码 开始 --------------//
    /**
     * 供应商仓库--列表
     */
    short MERCHANT_STORE_LIST = 14001;

    /**
     * 供应商仓库--新增add
     */
    short MERCHANT_STORE_ADD = 14002;

    /**
     * 供应商仓库--修改modify
     */
    short MERCHANT_STORE_MODIFY = 14003;

    /**
     * 供应商仓库--删除 del
     */
    short MERCHANT_STORE_DEL = 14004;

    /**
     * 供应商仓库--配送区域 delivery 删除 del
     */
    short MERCHANT_STORE_DELIVERY_DEL = 14005;

    // -------------14供应商仓库接口编码 结束 --------------//

    // -------------15平台数据接口编码 开始 ---------------//
    /**
     * 平台商品单位查询 plat_product_unit
     */
    short PLAT_PRODUCT_UNIT_LIST = 15001;

    /**
     * 平台商品类别 查询 plat_product_category
     */
    short PLAT_PRODUCT_CATEGORY_LIST = 15002;
    // -------------15平台数据接口编码 结束 ---------------//

}
