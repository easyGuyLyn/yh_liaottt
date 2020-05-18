package com.nwf.sports.utils;

/**
 * <p>类描述：
 * <p>创建人：Simon
 * <p>创建时间：2019-07-12
 * <p>修改人：Simon
 * <p>修改时间：2019-07-12
 * <p>修改备注：
 **/

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 对EditText输入规则进行校验，规则可以自己定义
 */
public class LimitInputTextWatcher implements TextWatcher {

    public static String LETTER_FIGURE = "[^0-9A-Za-z]"; //只能输入字母和数字

    public static String CHARACTER = "[^\u4E00-\u9FA5]"; //只能输入中文

    public static String CHARACTER_LETTER = "[^A-Za-z\u4e00-\u9fa5]"; //只能输入中文和字母

    public static String CHARACTER_LETTER_DOT = "[^·A-Za-z\u4e00-\u9fa5]"; //只能输入中文和字母和特殊字符·


    /*** et*/
    private EditText et = null;
    /**
     * 筛选条件
     */
    private String regex;

    /***默认的筛选条件(正则:只能输入中文和字母)*/
    private String DEFAULT_REGEX = LETTER_FIGURE;

    /***构造方法** @param et*/
    public LimitInputTextWatcher(EditText et) {
        this.et = et;
        this.regex = DEFAULT_REGEX;
    }

    /***构造方法** @param etet* @param regex筛选条件*/
    public LimitInputTextWatcher(EditText et, String regex) {
        this.et = et;
        this.regex = regex;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }


    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editable.toString();
        String inputStr = clearLimitStr(regex, str);
        et.removeTextChangedListener(this);
        // et.setText方法可能会引起键盘变化,所以用editable.replace来显示内容
        editable.replace(0, editable.length(), inputStr.trim());
        et.addTextChangedListener(this);
    }

    /***清除不符合条件的内容** @param regex* @return*/
    private String clearLimitStr(String regex, String str) {
        return str.replaceAll(regex, "");
    }
}

