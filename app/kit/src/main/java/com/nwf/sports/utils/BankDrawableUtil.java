package com.nwf.sports.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.dawoo.coretool.util.Check;
import com.nwf.sports.IMApplication;
import com.ivi.imsdk.R;

import androidx.core.content.ContextCompat;

/**
 * <p>类描述：  银行统一处理
 * <p>创建人：Simon
 * <p>创建时间：2019-03-19
 * <p>修改人：Simon
 * <p>修改时间：2019-03-19
 * <p>修改备注：
 **/
public class BankDrawableUtil
{

    /**
     * @param code
     * @return 兴业银行(IBC)
     * 上海浦东银行(SPDB)
     * 中国农业银行(ABOC)
     * 交通银行(BCM)
     * 中国银行(BOC)
     * 中国建设银行(CCBC)
     * 中信银行(CCTB)
     * 中国光大银行(CEB)
     * 招商银行(CMB)
     * 中国民生银行(CMBC)
     * 华夏银行(HXB)
     * 中国工商银行(ICBC)
     * 邮政储蓄(PSBC)
     * 支付宝(ALIPAY)
     * 哈尔滨银行(HARBINBANK)
     * 浙商银行( CZBANK)
     * 平安银行( PAB)
     * <p>
     * icbc("工商银行"),
     * abc("中国农业银行"),
     * aboc("中国农业银行"),
     * ccb("建设银行"),
     * ccbc("建设银行"),
     * boco("交通银行"),
     * bcm("交通银行"),
     * cmb("招商银行"),
     * bofc("中国银行"),
     * boc("中国银行"),
     * msb("民生银行"),
     * cmbc("民生银行"),
     * hxb("华夏银行"),
     * inb("兴业银行"),
     * ibc("兴业银行"),
     * shpdb("上海浦东发展银行"),
     * spdb("上海浦东发展银行"),
     * gddb("广发银行"),
     * citic("中信银行"),
     * ebb("光大银行"),
     * ceb("光大银行"),
     * psb("邮政储蓄"),
     * psbc("邮政储蓄"),
     * bjyh("北京银行"),
     * tccb("天津银行"),
     * bos("上海银行"),
     * srcb("上海农商银行"),
     * pab("平安银行"),
     * bjrcb("北京农村商业银行"),
     * njcb("南京银行"),
     * szdb("深圳发展银行"),
     * nbcb("宁波银行"),
     * hsbank("徽商银行"),
     * hzbank("杭州银行"),
     * czbank("浙商银行"),
     * bohaibank("渤海银行"),
     * hebbank("河北银行"),
     * eab("东亚银行"),
     * inbdebit("兴业银行借记卡"),
     * inbcredit("兴业银行贷记卡"),
     * gzcb("广州银行"),
     * gnxs("广州市农村信用合作社"),
     * hfyh("恒丰银行"),
     * cdyh("成都银行"),
     * zjtlyh("浙江泰隆银行"),
     * ccbank("城市商业银行"),
     * crbank("农村商业银行"),
     * cctb("中信银行"),
     * alipay("支付宝"),
     * harbinbank("哈尔滨银行"),
     * other("其他银行")
     */

    public static Drawable getDrawable(String code)
    {
        Context applicationContext= IMApplication.getInstance().getApplicationContext();
        String codeString = code.toLowerCase();
        Drawable drawable = null;
        switch (codeString)
        {
            case "gddb":
                // drawable = context.getResources().getDrawable(R.mipmap.icon_gfyh);
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_gfyh);
                break;
            case "hxb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_hxyh);
                break;
            case "boco":
            case "bcm":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_jtyh);
                break;
            case "hzbank":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_hzyh);
                break;
            case "ebb":
            case "ceb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_gdyh);
                break;
            case "citic":
            case "cctb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_zxyh);
                break;
            case "ccb":
            case "ccbc":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_jsyh);
                break;
            case "inb":
            case "ibc":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_xyyh);
                break;
            case "abc":
            case "aboc":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_nyyh);
                break;
            case "msb":
            case "cmbc":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_msyh);
                break;
            case "bos":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_shyh);
                break;
            case "cmb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_zsyh);
                break;
            case "pab":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_payh);
                break;
            case "shpdb":
            case "spdb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_pfyh);
                break;
            case "srcb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_shnsyh);
                break;
            case "icbc":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_gsyh);
                break;
            case "nbcb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_nbyh);
                break;
            case "bofc":
            case "boc":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_zgyh);
                break;
            case "psb":
            case "psbc":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_yzyh);
                break;
            case "bjyh":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_bjyh);
                break;
            case "bjrcb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_bjns);
                break;
            case "tccb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_tjyh);
                break;
            case "njcb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_njyh);
                break;
            case "szdb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_szfzyh);
                break;
            case "hsbank":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_wsyh);
                break;
            case "czbank":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_zsyh);
                break;
            case "bohaibank":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_bhyh);
                break;
            case "hebbank":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_hbyh);
                break;
            case "eab":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_dyyh);
                break;
            case "inbcredit":
            case "inbdebit":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_xyyh);
                break;
            case "gnxs":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_nongcun);
                break;
            case "harbinbank":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.nwf_ico_hebyh);
                break;
            case "cdyh":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_cdyh);
                break;
            case "gzcb":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_gzyh);
                break;
            case "hfyh":
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.icon_hfyh);
                break;
            //暂时没有的银行
            case "zjtlyh": //浙江泰隆银行
            case "ccbank": //城市商业银行
            case "crbank": //农村商业银行
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.nwf_bank_nor);
                break;
            default:
                drawable = ContextCompat.getDrawable(applicationContext, R.mipmap.nwf_bank_nor);
                break;
        }
        /// 获取图标的大小，这一步必须要做,否则不会显示
        //drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //textView.setCompoundDrawables(drawable, null, null, null);
        return drawable;
    }

    public static void setDrawable(TextView textView, String code)
    {
        Drawable drawable = getDrawable(code);
        if (Check.isNull(drawable))
        {
            return;
        }
        /// 获取图标的大小，这一步必须要做,否则不会显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

}
