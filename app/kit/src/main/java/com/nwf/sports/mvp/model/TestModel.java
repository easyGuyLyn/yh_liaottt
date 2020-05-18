package com.nwf.sports.mvp.model;

import java.util.List;

public class TestModel
{


    /**
     * newPayList : [{"paymentKey":"YLZF","lowestvalue":100,"highestvalue":6014,"payName":"银联支付","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/YLZF.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/YLZF.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3182","lowestvalue":"100","highestvalue":"6014","paymannerid":"21","paymannername":"银联WAP","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"ZFB","lowestvalue":1,"highestvalue":6000,"payName":"支付宝","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/ZFB.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/ZFB.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3346","lowestvalue":"1","highestvalue":"6000","paymannerid":"5","paymannername":"支付宝扫码支付","extra":null,"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":2,"isBigAmount":null,"amountList":[],"index":2,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"WX","lowestvalue":100,"highestvalue":6009,"payName":"微信","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/WX.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/WX.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3198","lowestvalue":"100","highestvalue":"2600","paymannerid":"8","paymannername":"微信支付","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":2,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null},{"serviceavailable":true,"manners":null,"payid":"385","lowestvalue":"199","highestvalue":"3999","paymannerid":"6","paymannername":"微信扫码","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":2,"isBigAmount":"N","amountList":["199","299","399","499","599","3999"],"index":2,"cardList":null,"cardnum":0,"payment":null,"postUrl":null},{"serviceavailable":true,"manners":null,"payid":"3289","lowestvalue":"100","highestvalue":"6009","paymannerid":"23","paymannername":"微信条码","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":2,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":3,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"WYKJ","lowestvalue":100,"highestvalue":6007,"payName":"网银快捷","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/WYKJ.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/WYKJ.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3254","lowestvalue":"100","highestvalue":"6007","paymannerid":"19","paymannername":"快捷支付","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"ZHHK","lowestvalue":10,"highestvalue":1000000,"payName":"转账汇款","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/ZHHK.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/ZHHK.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"","lowestvalue":"10","highestvalue":"1000000","paymannerid":"BQ","paymannername":"转账汇款","extra":[{"id":null,"bankAccountCode":"cctb","bankAccountName":"中信银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":null,"bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":[{"code":"0","desc":"银行卡","icon":"wangyinpay.png","iconb":"wangyinpay_B.png"},{"code":"2","desc":"支付宝","icon":"alipay.png","iconb":"alipay_b.png"},{"code":"1","desc":"微信支付","icon":"wechatpay.png","iconb":"wechat_b.png"}],"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":null,"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"WYZX","lowestvalue":100,"highestvalue":6012,"payName":"网银在线","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/WYZX.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/WYZX.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"331","lowestvalue":"100","highestvalue":"6012","paymannerid":"1","paymannername":"在线支付","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":[],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"QQ","lowestvalue":100,"highestvalue":6010,"payName":"QQ钱包","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/QQ.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/QQ.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3283","lowestvalue":"100","highestvalue":"6010","paymannerid":"11","paymannername":"手机QQ wap支付","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null},{"serviceavailable":true,"manners":null,"payid":"340","lowestvalue":"100","highestvalue":"6000","paymannerid":"7","paymannername":"QQ钱包","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["11","50","100","200","500","1000","2000","3000","4000","6000"],"index":2,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"JD","lowestvalue":100,"highestvalue":6013,"payName":"京东","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/JD.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/JD.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3063","lowestvalue":"100","highestvalue":"6013","paymannerid":"17","paymannername":"京东WAP","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"YSF","lowestvalue":100,"highestvalue":6015,"payName":"云闪付","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/YSF.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/YSF.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3351","lowestvalue":"100","highestvalue":"6015","paymannerid":"27","paymannername":"云闪付","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]},{"paymentKey":"DKZF","lowestvalue":0,"highestvalue":1000000,"payName":"点卡支付","iconUrl":"http://10.91.6.23:8080/static/payfile/allicons/normal/DKZF.png","selectIconUrl":"http://10.91.6.23:8080/static/payfile/allicons/white/DKZF.png","subPaymentList":[{"serviceavailable":true,"manners":null,"payid":"3217","lowestvalue":"0","highestvalue":"1000000","paymannerid":"2","paymannername":"点卡支付","extra":null,"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":null,"isBigAmount":null,"amountList":null,"index":1,"cardList":[{"id":"626","flag":true,"values":"5,10,15,25,30,50,100,200","productid":"E03","description":"\u203b该卡种不能使用特定游戏专属充值卡支付。 特定游戏包括大唐风云、传说、蜗牛、猫扑一卡通、九鼎、雅典娜、山河等游戏。;\u203b在此使用过的骏网一卡通，卡内剩余J点只能在富汇易达合作商家进行支付使用。\t","name":"骏网一卡通","value":"10","code":"JUNNET","iconUrl":null,"cardValues":["5","10","15","25","30","50","100","200"],"descriptions":["\u203b该卡种不能使用特定游戏专属充值卡支付。 特定游戏包括大唐风云、传说、蜗牛、猫扑一卡通、九鼎、雅典娜、山河等游戏。","\u203b在此使用过的骏网一卡通，卡内剩余J点只能在富汇易达合作商家进行支付使用。\t"]}],"cardnum":1,"payment":"yiykcard","postUrl":"http://10.66.72.77:8081/payment_web"}]}]
     * lowestvalue : 0.0
     * highestvalue : 1000000.0
     * quotaValue : 100,500,1000,5000,10000
     */

    private double lowestvalue;
    private double highestvalue;
    private String quotaValue;
    private List<NewPayListBean> newPayList;

    public double getLowestvalue() {
        return lowestvalue;
    }

    public void setLowestvalue(double lowestvalue) {
        this.lowestvalue = lowestvalue;
    }

    public double getHighestvalue() {
        return highestvalue;
    }

    public void setHighestvalue(double highestvalue) {
        this.highestvalue = highestvalue;
    }

    public String getQuotaValue() {
        return quotaValue;
    }

    public void setQuotaValue(String quotaValue) {
        this.quotaValue = quotaValue;
    }

    public List<NewPayListBean> getNewPayList() {
        return newPayList;
    }

    public void setNewPayList(List<NewPayListBean> newPayList) {
        this.newPayList = newPayList;
    }

    public static class NewPayListBean {
        /**
         * paymentKey : YLZF
         * lowestvalue : 100.0
         * highestvalue : 6014.0
         * payName : 银联支付
         * iconUrl : http://10.91.6.23:8080/static/payfile/allicons/normal/YLZF.png
         * selectIconUrl : http://10.91.6.23:8080/static/payfile/allicons/white/YLZF.png
         * subPaymentList : [{"serviceavailable":true,"manners":null,"payid":"3182","lowestvalue":"100","highestvalue":"6014","paymannerid":"21","paymannername":"银联WAP","extra":[{"id":"PSBC","bankAccountCode":"psb","bankAccountName":"邮政储蓄","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ABC","bankAccountCode":"abc","bankAccountName":"中国农业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CCB","bankAccountCode":"ccb","bankAccountName":"建设银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"GDB","bankAccountCode":"gddb","bankAccountName":"广发银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"BOC","bankAccountCode":"bofc","bankAccountName":"中国银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CMB","bankAccountCode":"cmb","bankAccountName":"招商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"ICBC","bankAccountCode":"icbc","bankAccountName":"工商银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"SPDB","bankAccountCode":"shpdb","bankAccountName":"上海浦东发展银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null},{"id":"CIB","bankAccountCode":"inb","bankAccountName":"兴业银行","bankAccountNo":null,"bankName":null,"bankCity":null,"branchName":null,"trustLevel":null,"cusLevel":null,"limitAmount":null,"province":null,"lastDepostFlag":null,"remarks":null,"depositAmount":null,"isShow":null,"currency":null}],"transferTypeList":null,"noBankNetpayFlag":null,"handleFee":0,"isBigAmount":"N","amountList":["50","100","200","500","1000","2000","2100","2500","2600"],"index":1,"cardList":null,"cardnum":0,"payment":null,"postUrl":null}]
         */

        private String paymentKey;
        private double lowestvalue;
        private double highestvalue;
        private String payName;
        private String iconUrl;
        private String selectIconUrl;
        private List<SubPaymentListBean> subPaymentList;

        public String getPaymentKey() {
            return paymentKey;
        }

        public void setPaymentKey(String paymentKey) {
            this.paymentKey = paymentKey;
        }

        public double getLowestvalue() {
            return lowestvalue;
        }

        public void setLowestvalue(double lowestvalue) {
            this.lowestvalue = lowestvalue;
        }

        public double getHighestvalue() {
            return highestvalue;
        }

        public void setHighestvalue(double highestvalue) {
            this.highestvalue = highestvalue;
        }

        public String getPayName() {
            return payName;
        }

        public void setPayName(String payName) {
            this.payName = payName;
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

        public List<SubPaymentListBean> getSubPaymentList() {
            return subPaymentList;
        }

        public void setSubPaymentList(List<SubPaymentListBean> subPaymentList) {
            this.subPaymentList = subPaymentList;
        }

        public static class SubPaymentListBean {
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

            private boolean serviceavailable;
            private Object manners;
            private String payid;
            private String lowestvalue;
            private String highestvalue;
            private String paymannerid;
            private String paymannername;
            private Object transferTypeList;
            private Object noBankNetpayFlag;
            private int handleFee;
            private String isBigAmount;
            private int index;
            private Object cardList;
            private int cardnum;
            private Object payment;
            private Object postUrl;
            private List<ExtraBean> extra;
            private List<String> amountList;

            public boolean isServiceavailable() {
                return serviceavailable;
            }

            public void setServiceavailable(boolean serviceavailable) {
                this.serviceavailable = serviceavailable;
            }

            public Object getManners() {
                return manners;
            }

            public void setManners(Object manners) {
                this.manners = manners;
            }

            public String getPayid() {
                return payid;
            }

            public void setPayid(String payid) {
                this.payid = payid;
            }

            public String getLowestvalue() {
                return lowestvalue;
            }

            public void setLowestvalue(String lowestvalue) {
                this.lowestvalue = lowestvalue;
            }

            public String getHighestvalue() {
                return highestvalue;
            }

            public void setHighestvalue(String highestvalue) {
                this.highestvalue = highestvalue;
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

            public Object getTransferTypeList() {
                return transferTypeList;
            }

            public void setTransferTypeList(Object transferTypeList) {
                this.transferTypeList = transferTypeList;
            }

            public Object getNoBankNetpayFlag() {
                return noBankNetpayFlag;
            }

            public void setNoBankNetpayFlag(Object noBankNetpayFlag) {
                this.noBankNetpayFlag = noBankNetpayFlag;
            }

            public int getHandleFee() {
                return handleFee;
            }

            public void setHandleFee(int handleFee) {
                this.handleFee = handleFee;
            }

            public String getIsBigAmount() {
                return isBigAmount;
            }

            public void setIsBigAmount(String isBigAmount) {
                this.isBigAmount = isBigAmount;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public Object getCardList() {
                return cardList;
            }

            public void setCardList(Object cardList) {
                this.cardList = cardList;
            }

            public int getCardnum() {
                return cardnum;
            }

            public void setCardnum(int cardnum) {
                this.cardnum = cardnum;
            }

            public Object getPayment() {
                return payment;
            }

            public void setPayment(Object payment) {
                this.payment = payment;
            }

            public Object getPostUrl() {
                return postUrl;
            }

            public void setPostUrl(Object postUrl) {
                this.postUrl = postUrl;
            }

            public List<ExtraBean> getExtra() {
                return extra;
            }

            public void setExtra(List<ExtraBean> extra) {
                this.extra = extra;
            }

            public List<String> getAmountList() {
                return amountList;
            }

            public void setAmountList(List<String> amountList) {
                this.amountList = amountList;
            }

            public static class ExtraBean {
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

                private String id;
                private String bankAccountCode;
                private String bankAccountName;
                private Object bankAccountNo;
                private Object bankName;
                private Object bankCity;
                private Object branchName;
                private Object trustLevel;
                private Object cusLevel;
                private Object limitAmount;
                private Object province;
                private Object lastDepostFlag;
                private Object remarks;
                private Object depositAmount;
                private Object isShow;
                private Object currency;

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

                public Object getBankAccountNo() {
                    return bankAccountNo;
                }

                public void setBankAccountNo(Object bankAccountNo) {
                    this.bankAccountNo = bankAccountNo;
                }

                public Object getBankName() {
                    return bankName;
                }

                public void setBankName(Object bankName) {
                    this.bankName = bankName;
                }

                public Object getBankCity() {
                    return bankCity;
                }

                public void setBankCity(Object bankCity) {
                    this.bankCity = bankCity;
                }

                public Object getBranchName() {
                    return branchName;
                }

                public void setBranchName(Object branchName) {
                    this.branchName = branchName;
                }

                public Object getTrustLevel() {
                    return trustLevel;
                }

                public void setTrustLevel(Object trustLevel) {
                    this.trustLevel = trustLevel;
                }

                public Object getCusLevel() {
                    return cusLevel;
                }

                public void setCusLevel(Object cusLevel) {
                    this.cusLevel = cusLevel;
                }

                public Object getLimitAmount() {
                    return limitAmount;
                }

                public void setLimitAmount(Object limitAmount) {
                    this.limitAmount = limitAmount;
                }

                public Object getProvince() {
                    return province;
                }

                public void setProvince(Object province) {
                    this.province = province;
                }

                public Object getLastDepostFlag() {
                    return lastDepostFlag;
                }

                public void setLastDepostFlag(Object lastDepostFlag) {
                    this.lastDepostFlag = lastDepostFlag;
                }

                public Object getRemarks() {
                    return remarks;
                }

                public void setRemarks(Object remarks) {
                    this.remarks = remarks;
                }

                public Object getDepositAmount() {
                    return depositAmount;
                }

                public void setDepositAmount(Object depositAmount) {
                    this.depositAmount = depositAmount;
                }

                public Object getIsShow() {
                    return isShow;
                }

                public void setIsShow(Object isShow) {
                    this.isShow = isShow;
                }

                public Object getCurrency() {
                    return currency;
                }

                public void setCurrency(Object currency) {
                    this.currency = currency;
                }
            }
        }
    }
}
