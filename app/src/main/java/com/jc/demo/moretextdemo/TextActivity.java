package com.jc.demo.moretextdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class TextActivity extends Activity {

    private TextView mTvMore = null;

    private String mText = "设置TextView 显示更多\n" +
            "    使用方法：\n" +
            "     textview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {\n" +
            "     @Override\n" +
            "     public boolean onPreDraw() {\n" +
            "     textview.getViewTreeObserver().removeOnPreDrawListener(this);\n" +
            "     TextUtil.setEllipsize(...)\n" +
            "     return true;\n" +
            "     }\n" +
            "     });";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        mTvMore = (TextView) findViewById(R.id.tv_more);
        mTvMore.setText(mText);
        mTvMore.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mTvMore.getViewTreeObserver().removeOnPreDrawListener(this);
                TextUtil.setEllipsize(TextActivity.this, mTvMore, mText, 2);
                return true;
            }
        });
    }

}
