package com.nwf.sports.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Result«登录响应model» {
 * code (integer, optional),
 * data (登录响应model, optional),
 * msg (string, optional)
 * }
 * 登录响应model {
 * highestvalue (number, optional): 最高额度 ,
 * lowestvalue (number, optional): 最低额度 ,
 * newPayList (Array[NewPayment], optional): 支付方式 ,
 * quotaValue (string, optional): 固定额度
 * }
 * NewPayment {
 * highestvalue (number, optional): 支付的最高额度 ,
 * iconUrl (string, optional): 支付方式图标 ,
 * lowestvalue (number, optional): 支付的最低额度 ,
 * payName (string, optional): 支付名称中文 ,
 * paymentKey (string, optional): 支付方式KEY ,
 * selectIconUrl (string, optional): 选择图标 ,
 * subPaymentList (Array[支付列表«object»], optional)
 * }
 * 支付列表«object» {
 * amountList (Array[string], optional): 如果此参数有值表示玩家只能从金额列表中选择金额，不能输入，如果为空表示可以输入任意金额。例如： 100,200,500,1000 ,
 * cardList (Array[Card], optional): 点卡支付专用 ,
 * cardnum (integer, optional): 点卡数量 点卡专用 ,
 * extra (Array[Inline Model 1], optional),
 * handleFee (number, optional): 手续费 ,
 * highestvalue (string, optional),
 * index (integer, optional): 优先级 ,
 * isBigAmount (string, optional): Y:是大额支付，N:非大额 ,
 * lowestvalue (string, optional),
 * manners (string, optional),
 * noBankNetpayFlag (string, optional): BQ 为false 其他为null ,
 * payid (string, optional): 支付ID ,
 * paymannerid (string, optional): 支付类型ID ,
 * paymannername (string, optional): 支付名称 ,
 * payment (string, optional): 点卡专用 ,
 * postUrl (string, optional): 点卡专用 ,
 * serviceavailable (boolean, optional): 服务可用表示 ,
 * transferTypeList (Array[TransferType], optional): BQ 转账专用 其他支付方式忽略
 * }
 * Card {
 * cardValues (Array[string], optional),
 * code (string, optional),
 * description (string, optional),
 * descriptions (Array[string], optional),
 * flag (boolean, optional),
 * iconUrl (string, optional),
 * id (string, optional),
 * name (string, optional),
 * productid (string, optional),
 * value (string, optional),
 * values (string, optional)
 * }
 * Inline Model 1 {}
 * TransferType {
 * code (string, optional),
 * desc (string, optional),
 * icon (string, optional),
 * iconb (string, optional)
 * }
 */

/**
 * <p>类描述：
 * <p>创建人：simon
 * <p>创建时间：2019-04-03
 * <p>修改人：simon
 * <p>修改时间：2019-04-03
 * <p>修改备注：
 **/
public class DepositMannersVo implements Serializable {

    /**
     * newPayList : [{"paymentKey":"YLZF","lowestvalue":100,"highestvalue":6014,"payName":"银联支付","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/YLZF.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/YLZF.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3182","lowestvalue":"100","highestvalue":"6014","paymannerid":"21","paymannername":"银联WAP","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"ZFB","lowestvalue":1,"highestvalue":6000,"payName":"支付宝","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/ZFB.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/ZFB.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3346","lowestvalue":"1","highestvalue":"6000","paymannerid":"5","paymannername":"支付宝扫码支付","extra":null,"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":2,"isBigAmount":null,"amountList":[],"index":2,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"WX","lowestvalue":100,"highestvalue":6009,"payName":"微信","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/WX.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/WX.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3198","lowestvalue":"100","highestvalue":"2600","paymannerid":"8","paymannername":"微信支付","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":2,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null},{"serviceavailable":true,"manners":null,"payid":"385","lowestvalue":"199","highestvalue":"3999","paymannerid":"6","paymannername":"微信扫码","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":2,"isBigAmount":"N","amountList":["199","299","399","499","599","3999"],"index":2,"cardList":null,"cardnum":0,"payment":null,"postUrl":null},{"serviceavailable":true,"manners":null,"payid":"3289","lowestvalue":"100","highestvalue":"6009","paymannerid":"23","paymannername":"微信条码","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":2,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":3,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"WYKJ","lowestvalue":100,"highestvalue":6007,"payName":"网银快捷","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/WYKJ.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/WYKJ.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3254","lowestvalue":"100","highestvalue":"6007","paymannerid":"19","paymannername":"快捷支付","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"ZHHK","lowestvalue":10,"highestvalue":1000000,"payName":"转账汇款","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/ZHHK.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/ZHHK.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"","lowestvalue":"10","highestvalue":"1000000","paymannerid":"BQ","paymannername":"转账汇款","extra":[{"id":null,"bankAccountCode":"cctb","bankAccountName":"中信银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":null,"bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":[{"code":"0","desc":"银行卡","icon":"wangyinpay.png","iconb":"wangyinpay_B.png"},{"code":"2","desc":"支付宝","icon":"alipay.png","iconb":"alipay_b.png"},{"code":"1","desc":"微信支付","icon":"wechatpay.png","iconb":"wechat_b.png"}],"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":null,"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"WYZX","lowestvalue":100,"highestvalue":6012,"payName":"网银在线","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/WYZX.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/WYZX.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"331","lowestvalue":"100","highestvalue":"6012","paymannerid":"1","paymannername":"在线支付","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":[],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"QQ","lowestvalue":100,"highestvalue":6010,"payName":"QQ钱包","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/QQ.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/QQ.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3283","lowestvalue":"100","highestvalue":"6010","paymannerid":"11","paymannername":"手机QQ wap支付","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null},{"serviceavailable":true,"manners":null,"payid":"340","lowestvalue":"100","highestvalue":"6000","paymannerid":"7","paymannername":"QQ钱包","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["11","50","100","200","500","1000","2000","3000","4000","6000"],"index":2,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"JD","lowestvalue":100,"highestvalue":6013,"payName":"京东","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/JD.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/JD.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3063","lowestvalue":"100","highestvalue":"6013","paymannerid":"17","paymannername":"京东WAP","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"YSF","lowestvalue":100,"highestvalue":6015,"payName":"云闪付","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/YSF.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/YSF.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3351","lowestvalue":"100","highestvalue":"6015","paymannerid":"27","paymannername":"云闪付","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"DKZF","lowestvalue":0,"highestvalue":1000000,"payName":"点卡支付","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/DKZF.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/DKZF.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3217","lowestvalue":"0","highestvalue":"1000000","paymannerid":"2","paymannername":"点卡支付","extra":null,"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":null,"isBigAmount":null,"amountList":null,"index":1,"cardList":[{"id":"626","flag":true,"values":"5,10,15,25,30,50,100,200","productid":"E03","description":"\u203b该卡种不能使用特定游戏专属充值卡支付。 特定游戏包括大唐风云、传说、蜗牛、猫扑一卡通、九鼎、雅典娜、山河等游戏。;\u203b在此使用过的骏网一卡通，卡内剩余J点只能在富汇易达合作商家进行支付使用。\t","name":"骏网一卡通","value":"10","code":"JUNNET","iconUrl":null,"cardValues":["5","10","15","25","30","50","100","200"],"descriptions":["\u203b该卡种不能使用特定游戏专属充值卡支付。 特定游戏包括大唐风云、传说、蜗牛、猫扑一卡通、九鼎、雅典娜、山河等游戏。","\u203b在此使用过的骏网一卡通，卡内剩余J点只能在富汇易达合作商家进行支付使用。\t"]}],"cardnum":1,"payment":"yiykcard","postUrl":"http://10.66.72.77:8081/payment_web"}]}]
     * lowestvalue : 0.0
     * highestvalue : 1000000.0
     * quotaValue : 100,500,1000,5000,10000
     */

    public float highestvalue;
    public float lowestvalue;
    public String quotaValue;
    public List<DepMannersVo> newPayList;

    @Override
    public String toString() {
        return "DepositMannersVo{" +
                "highestvalue='" + highestvalue + '\'' +
                ", lowestvalue='" + lowestvalue + '\'' +
                ", quotaValue='" + quotaValue + '\'' +
                ", newPayList=" + new Gson().toJson(newPayList) +
                '}';
        // return new Gson().toJson(this);
    }

    public static class DepMannersVo {
        /**
         * paymentKey : YLZF
         * lowestvalue : 100.0
         * highestvalue : 6014.0
         * payName : 银联支付
         * iconUrl : http://10.91.6.23:8080/static/payfile/allicons/normal/YLZF.png
         * selectIconUrl : http://10.91.6.23:8080/static/payfile/allicons/white/YLZF.png
         * subPaymentList : [{"serviceavailable":true,"manners":null,"payid":"3182","lowestvalue":"100","highestvalue":"6014","paymannerid":"21","paymannername":"银联WAP","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]
         */

        public String paymentKey;
        public String payName;
        public float highestvalue;
        public float lowestvalue;
        public List<SubPaymentVo> subPaymentList; //同一支付方式下的子存款方式
        public String iconUrl;
        public String selectIconUrl;

        public DepMannersVo() {
        }

        @Override
        public DepMannersVo clone() {
            //return super.clone();
            DepMannersVo vo = new DepMannersVo();
            vo.paymentKey = this.paymentKey;
            vo.payName = this.payName;
            vo.highestvalue = this.highestvalue;
            vo.lowestvalue = this.lowestvalue;
            vo.subPaymentList = this.subPaymentList;

            return vo;
        }

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getSelectIconUrl() {
            return selectIconUrl;
        }

        public void setSelectIconUrl(String selectIconUrl) {
            this.selectIconUrl = selectIconUrl;
        }

        public String getPaymentKey() {
            return paymentKey;
        }

        public void setPaymentKey(String paymentKey) {
            this.paymentKey = paymentKey;
        }

        public String getPayName() {
            return payName;
        }

        public void setPayName(String payName) {
            this.payName = payName;
        }

        public float getHighestvalue() {
            return highestvalue;
        }

        public void setHighestvalue(float highestvalue) {
            this.highestvalue = highestvalue;
        }

        public float getLowestvalue() {
            return lowestvalue;
        }

        public void setLowestvalue(float lowestvalue) {
            this.lowestvalue = lowestvalue;
        }

        public List<SubPaymentVo> getSubPaymentList() {
            return subPaymentList;
        }

        public void setSubPaymentList(List<SubPaymentVo> subPaymentList) {
            this.subPaymentList = subPaymentList;
        }
    }

    public static class SubPaymentVo implements Serializable {

        /**
         * serviceavailable : true
         * manners : null
         * payid : 3182
         * lowestvalue : 100
         * highestvalue : 6014
         * paymannerid : 21
         * paymannername : 银联WAP
         * extra : [{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}]
         * transferTypeList : null
         * noBankNetpayFlag : null
         * handleFee : 0
         * isBigAmount : N
         * amountList : ["50","100","200","500","1000","2000","2100","2500","2600"]
         * index : 1
         * cardList : null
         * cardnum : 0
         * payment : null
         * postUrl : null
         */

        public SubPaymentVo(){

        }

        public String payid;
        public String manners;
        public String paymannerid;
        public float handleFee;
        public float highestvalue;
        public float lowestvalue;
        public String index;
        public String isBigAmount;
        public String paymannername;
        public String serviceavailable;
        public ArrayList<BankVo> extra=new ArrayList<>();
        public ArrayList<TransferTypeVo> transferTypeList=new ArrayList<>();
        public List<String> amountList=new ArrayList<>();
        public ArrayList<PointCardVo> cardList=new ArrayList<>(); // 点卡支付列表
        public String postUrl; // 点卡支付用的

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }

        public float getHandleFee() {
            return handleFee;
        }

        public void setHandleFee(float handleFee) {
            this.handleFee = handleFee;
        }

        public float getHighestvalue() {
            return highestvalue;
        }

        public void setHighestvalue(float highestvalue) {
            this.highestvalue = highestvalue;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getIsBigAmount() {
            return isBigAmount;
        }

        public void setIsBigAmount(String isBigAmount) {
            this.isBigAmount = isBigAmount;
        }

        public float getLowestvalue() {
            return lowestvalue;
        }

        public void setLowestvalue(float lowestvalue) {
            this.lowestvalue = lowestvalue;
        }

        public String getPayid() {
            return payid;
        }

        public void setPayid(String payid) {
            this.payid = payid;
        }

        public String getPaymannerid() {
            return paymannerid;
        }

        public void setPaymannerid(String paymannerid) {
            this.paymannerid = paymannerid;
        }

        public String getPaymannername() {
            return paymannername;
        }

        public void setPaymannername(String paymannername) {
            this.paymannername = paymannername;
        }

        public String getServiceavailable() {
            return serviceavailable;
        }

        public void setServiceavailable(String serviceavailable) {
            this.serviceavailable = serviceavailable;
        }

        public ArrayList<BankVo> getExtra() {
            return extra;
        }

        public void setExtra(ArrayList<BankVo> extra) {
            this.extra = extra;
        }

        public ArrayList<TransferTypeVo> getTransferTypeList() {
            return transferTypeList;
        }

        public void setTransferTypeList(ArrayList<TransferTypeVo> transferTypeList) {
            this.transferTypeList = transferTypeList;
        }

        public List<String> getAmountList() {
            return amountList;
        }

        public void setAmountList(List<String> amountList) {
            this.amountList = amountList;
        }

        public ArrayList<PointCardVo> getCardList() {
            return cardList;
        }

        public void setCardList(ArrayList<PointCardVo> cardList) {
            this.cardList = cardList;
        }

        public String getPostUrl() {
            return postUrl;
        }

        public void setPostUrl(String postUrl) {
            this.postUrl = postUrl;
        }

        public String getManners() {
            return manners;
        }

        public void setManners(String manners) {
            this.manners = manners;
        }
    }

    public static class BankVo implements Serializable, Parcelable {

        /**
         * id : PSBC
         * bankAccountCode : psb
         * bankAccountName : 邮政储蓄
         * bankAccountNo : null
         * bankName : null
         * bankCity : null
         * branchName : null
         * trustLevel : null
         * cusLevel : null
         * limitAmount : null
         * province : null
         * lastDepostFlag : null
         * remarks : null
         * depositAmount : null
         * isShow : null
         * currency : null
         */

        public String id;
        public String bankAccountCode;
        public String bankAccountName;

        protected BankVo(Parcel in) {
            id = in.readString();
            bankAccountCode = in.readString();
            bankAccountName = in.readString();
        }

        @Override
        public String toString() {
            return "BankVo{" +
                    "id='" + id + '\'' +
                    ", bankAccountCode='" + bankAccountCode + '\'' +
                    ", bankAccountName='" + bankAccountName + '\'' +
                    '}';
        }

        public static final Creator<BankVo> CREATOR = new Creator<BankVo>() {
            @Override
            public BankVo createFromParcel(Parcel in) {
                return new BankVo(in);
            }

            @Override
            public BankVo[] newArray(int size) {
                return new BankVo[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBankAccountCode() {
            return bankAccountCode;
        }

        public void setBankAccountCode(String bankAccountCode) {
            this.bankAccountCode = bankAccountCode;
        }

        public String getBankAccountName() {
            return bankAccountName;
        }

        public void setBankAccountName(String bankAccountName) {
            this.bankAccountName = bankAccountName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(bankAccountCode);
            dest.writeString(bankAccountName);
        }
    }

    public static class TransferTypeVo implements Serializable, Parcelable {
        /**
         * "code":"0",
         * "desc":"银行卡",
         * "icon":"wangyinpay.png",
         * "iconb":"wangyinpay_B.png"
         */
        public String code;
        public String desc;
        public String icon;
        public String iconb;
        public ArrayList<BankVo> extra;
        public String typeBank = "";

        @Override
        public String toString() {
            return "TransferTypeVo{" +
                    "code='" + code + '\'' +
                    ", desc='" + desc + '\'' +
                    ", icon='" + icon + '\'' +
                    ", iconb='" + iconb + '\'' +
                    ", extra=" + extra.toString() +
                    ", typeBank='" + typeBank + '\'' +
                    '}';
        }

        protected TransferTypeVo(Parcel in) {
            code = in.readString();
            desc = in.readString();
            icon = in.readString();
            iconb = in.readString();
            extra = in.createTypedArrayList(BankVo.CREATOR);
            typeBank = in.readString();
        }

        public static final Creator<TransferTypeVo> CREATOR = new Creator<TransferTypeVo>() {
            @Override
            public TransferTypeVo createFromParcel(Parcel in) {
                return new TransferTypeVo(in);
            }

            @Override
            public TransferTypeVo[] newArray(int size) {
                return new TransferTypeVo[size];
            }
        };

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIconb() {
            return iconb;
        }

        public void setIconb(String iconb) {
            this.iconb = iconb;
        }

        public ArrayList<BankVo> getExtra() {
            return extra;
        }

        public void setExtra(ArrayList<BankVo> extra) {
            this.extra = extra;
        }

        public String getTypeBank() {
            return typeBank;
        }

        public void setTypeBank(String typeBank) {
            this.typeBank = typeBank;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(code);
            dest.writeString(desc);
            dest.writeString(icon);
            dest.writeString(iconb);
            dest.writeTypedList(extra);
            dest.writeString(typeBank);
        }
    }

}
