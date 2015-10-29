package com.jc.demo.moretextdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.TextView;

public class TextActivity extends Activity {

    private TextView mTvMore = null;

    private String mText = "Android（读音：英 ['ændrɔɪd] 美 [ˈænˌdrɔɪd]），" +
            "中文俗称安卓，是一个以Linux为基础的开放源代码移动设备操作系统，主要用于移动设备，" +
            "由Google成立的Open Handset Alliance（OHA，开放手持设备联盟）持续领导与开发中。" +
            "Android系统最初由安迪·鲁宾（Andy Rubin）等人开发制作，" +
            "最初开发这个系统的目的是创建一个数码相机的先进操作系统；" +
            "但是后来发现市场需求不够大，加上智能手机市场快速成长，" +
            "于是Android被改造为一款面向智能手机的操作系统。";

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
