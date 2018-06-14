package com.lzlmall.b2b.app.vendor.framework.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.lzlmall.b2b.app.vendor.framework.R;
import com.lzlmall.b2b.app.vendor.framework.utils.StatusBarUtils;


/**
 * Created by Administrator on 2018/4/8.
 *
 * 默认的标题
 *
 */

public class DefaultNavigationBar extends BaseNavigtionBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {
    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.default_navigtionbar;
    }

    @Override
    public void apply() {
        //绑定数据
        setText(R.id.tv_title,mParams.mTitle);
        setText(R.id.tv_right_text,mParams.mRightText);
        setImage(R.id.iv_right_icon,mParams.mRightIcon);
        setOnClick(R.id.right_click,mParams.mRightClick);
        setOnClick(R.id.iv_left_icon,mParams.mLeftClick);
        viewById(R.id.root_view).setBackgroundResource(mParams.backgroundResource);
        viewById(R.id.status_bar).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,StatusBarUtils.getStatusBarHeight(mParams.mContext)));

    }


    /**
     * 默认头部的构建器
     */
    public static class Builder extends BaseNavigtionBar.Builder{
        DefaultNavigationParams defaultNavigationParams;

        public Builder(Context context) {
            super(context,null);
            defaultNavigationParams = new DefaultNavigationParams(context,null);
        }

        /**
         * 设置标题
         */
        public Builder setTitle(CharSequence text) {
            defaultNavigationParams.mTitle = text;
            return this;
        }

        /**
         * 设置右边文字
         * @param text
         * @return
         */
        public Builder setRightText(CharSequence text) {
            defaultNavigationParams.mRightText = text;
            return this;
        }

        /**
         * 设置右边图片
         */
        public Builder setRightIcon(int imagresouce) {
            defaultNavigationParams.mRightIcon = imagresouce;
            return this;
        }


        /**
         * 设置右边点击事件
         * @return
         */

        public Builder setRightClick(View.OnClickListener clickListener) {
            defaultNavigationParams.mRightClick = clickListener;
            return this;
        }

        public Builder setLeftClick(View.OnClickListener clickListener) {
            defaultNavigationParams.mLeftClick = clickListener;
            return this;
        }

        @Override
        public BaseNavigtionBar build() {
            return new DefaultNavigationBar(defaultNavigationParams);
        }

        public Builder setBackgroundResource(int backgroundResource) {
            defaultNavigationParams.backgroundResource = backgroundResource;
            return this;
        }


        /**
         * 默认头部存储器
         */

        public static class DefaultNavigationParams extends BaseNavigtionParams{
            public CharSequence mTitle;  //标题
            public CharSequence mRightText; //右边文字
            public int mRightIcon;  //设置右边图片
            public View.OnClickListener mRightClick; //设置右边点击事件
            public int backgroundResource = R.color.main;
            public View.OnClickListener mLeftClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //默认实现
                    ((Activity) mContext).finish();
                }
            };


            public DefaultNavigationParams(Context context, ViewGroup viewGroup) {
                super(context, viewGroup);
            }

        }

    }

}
