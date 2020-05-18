package com.nwf.sports.utils.textviewlink;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ak on 2017/6/1.
 */

public class TextViewLinkUtil {
    public static void linkSpanTextView(Context context, TextView tv, String name, String toName, String content, String color, String contentColor) {
        StringBuilder actionText = new StringBuilder();

        //谁回复
        actionText.append("<a style=\"text-decoration:none;\" href='name' ><font color='" + color + "'>"
                + name + "</font> </a>");

        if (toName != null && toName.length() > 0) {
            actionText.append("回复");
            actionText.append("<font color='" + color + "'><a style=\"text-decoration:none;\" href='toName'>"
                    + toName + " " + " </a></font>");
        }
        // 内容
        actionText.append("<font color='" + contentColor + "'><a style=\"text-decoration:none;\" href='content'>"
                + ":" + content + " " + " </a></font>");

        tv.setText(Html.fromHtml(actionText.toString()));
        tv.setMovementMethod(LinkMovementMethod
                .getInstance());
        CharSequence text = tv.getText();
        int ends = text.length();
        Spannable spannable = (Spannable) tv.getText();
        URLSpan[] urlspan = spannable.getSpans(0, ends, URLSpan.class);
        SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
        stylesBuilder.clearSpans();
        for (URLSpan url : urlspan) {
            LinkSpanWrapper myURLSpan = new LinkSpanWrapper(url.getURL(),
                    context, name, toName, content, color);
            stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url),
                    spannable.getSpanEnd(url), spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(stylesBuilder);
        tv.setFocusable(false);
        tv.setClickable(false);
        tv.setLongClickable(false);
    }

    public static void textLinkClick(final Context context, String html, TextView tv) {
        CharSequence charSequence = Html.fromHtml(html);
        tv.setText(charSequence);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        Spannable sp = (Spannable) text;
        URLSpan[] urlSpans = sp.getSpans(0, text.length(), URLSpan.class);
        SpannableStringBuilder builder = new SpannableStringBuilder(
                charSequence);
        builder.clearSpans();
        for (URLSpan span : urlSpans) {
            int flag = builder.getSpanFlags(span);
            final String link = span.getURL();
            builder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    // 捕获<a>标签点击事件，及对应超链接link
                    Toast.makeText(context, link, Toast.LENGTH_SHORT).show();
                }
            }, sp.getSpanStart(span), sp.getSpanEnd(span), flag);
            builder.removeSpan(span);
        }
        tv.setText(builder);
    }


    /**
     *
     * @param context               上下文
     * @param frontString           默认字体
     * @param linkString            链接字体
     * @param behindString          是否还有下划线
     * @param colorString           链接字体颜色
     * @param linkline              是否还有下划线
     * @param tv                    TextView
     * @param NClickableSpan        监听链接的点击事件
     */
    public static void textLinkOnClick(final Context context, String frontString, final String linkString, String behindString, final String colorString, final boolean linkline, TextView tv, final TextViewLinkClickableSpan NClickableSpan) {
        CharSequence charSequence = Html.fromHtml(frontString+"<a href='http://n'>"+linkString+"</a>"+behindString);
        tv.setText(charSequence);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        Spannable sp = (Spannable) text;
        URLSpan[] urlSpans = sp.getSpans(0, text.length(), URLSpan.class);
        SpannableStringBuilder builder = new SpannableStringBuilder(
                charSequence);
        builder.clearSpans();
        for (URLSpan span : urlSpans) {
            int flag = builder.getSpanFlags(span);
            final String link = span.getURL();
            builder.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(linkline);
                    ds.setColor(Color.parseColor(colorString));
                }
                @Override
                public void onClick(View widget) {
                    // 捕获<a>标签点击事件，及对应超链接link
                    //Toast.makeText(context, link, Toast.LENGTH_SHORT).show();
                    NClickableSpan.show(widget.getId(),linkString);
                    //NClickableSpan.show(widget.getId(),link);
                }
            }, sp.getSpanStart(span), sp.getSpanEnd(span), flag);
            builder.removeSpan(span);
        }
        tv.setText(builder);
    }

    public interface TextViewLinkClickableSpan {
        void show(int position, String linkString);
    }
}
