package com.jc.demo.moretextdemo;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class TextUtil {
    /**
     * 设置TextView 显示更多
     * 使用方法：
     * textview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
     *  @Override
     *  public boolean onPreDraw() {
     *  mTvInfo.getViewTreeObserver().removeOnPreDrawListener(this);
     *  TextUtil.setEllipsize(...)
     *  return true;
     *   }
     *   });
     * @param textView
     * @param originalText 原始行数
     * @param ellipsizeLine 更多状态下显示的最多行数
     */
    public static void setEllipsize(final Context context, final TextView textView, final String originalText, final int ellipsizeLine){
        if(TextUtils.isEmpty(originalText) || TextUtils.isEmpty(textView.getText().toString())){
            return;
        }
        String more = context.getResources().getString(R.string.info_more);
        String ellipsized = TextUtils.ellipsize(textView.getText(), textView.getPaint(), (float) textView.getWidth() * ellipsizeLine, TextUtils.TruncateAt.END).toString();
        final String ellipsis = context.getResources().getString(R.string.ellipsis);
        if(!ellipsized.endsWith(ellipsis) && !ellipsized.endsWith("...")) {
            textView.setText(originalText);
            return;
        }

        String[] nextLine = ellipsized.split("\n");
        if(nextLine.length > ellipsizeLine){
            Matcher slashMatcher = Pattern.compile("\n").matcher(ellipsized);
            int mIdx = 0;
            while(slashMatcher.find()) {
                mIdx++;
                //当换行符第ellipsizeLine出现的位置
                if(mIdx == ellipsizeLine){
                    break;
                }
            }
            int ellipPos = slashMatcher.start();
            ellipsized = ellipsized.substring(0, ellipPos);
        }
        //替换[更多]的位置点
        int changePos = ellipsized.length() - (ellipsis).length() - more.length() - 2;
        String endStr = ellipsized.substring(changePos < 0 ? 0:changePos, ellipsized.length() - 1);
        String result;
        //如果要截取替换[更多]的字符串包括换行符，则直接把[more]加在文字末尾
        if(endStr.contains("\n")){
            result = ellipsized + more;
        }
        else {
            String tmp = ellipsized.substring(0, changePos < 0 ? 0:changePos);
            result = tmp + (ellipsis) + more;
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spannable = new SpannableString(result);
        ClickableSpan click = new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                if(textView.getLineCount() == ellipsizeLine){
                    String less = context.getResources().getString(R.string.info_less);
                    String newText = originalText + less;
                    SpannableString spannable = new SpannableString(newText);
                    ClickableSpan fullClick = new ClickableSpan() {
                        @Override
                        public void onClick(View widget) {
                            setEllipsize(context, textView, originalText, ellipsizeLine);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            ds.setUnderlineText(false);
                            ds.clearShadowLayer();
                        }
                    };
                    spannable.setSpan(fullClick, 0, newText.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
                    spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_more)),
                            newText.length() - less.length(), newText.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
                    textView.setText(spannable);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
                ds.clearShadowLayer();
            }
        };
        spannable.setSpan(click, 0, result.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.text_more)),
                result.length() - more.length(), result.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannable);
    }
}
