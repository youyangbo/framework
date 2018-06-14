package com.lzlmall.b2b.app.vendor.framework.utils;

import android.view.Gravity;
import android.view.ViewGroup;

import com.lzlmall.b2b.app.vendor.framework.R;
import com.lzlmall.b2b.app.vendor.framework.dialog.AlertDialog;
import com.lzlmall.b2b.app.vendor.framework.widget.FrameAnimationImageView;

/**
 * Created by Administrator on 2018/6/7.
 */

public class LoadingDialogUtils {

    private static final LoadingDialogUtils mLoadingDialogUtils;

    static {
        mLoadingDialogUtils = new LoadingDialogUtils();
    }

    private AlertDialog mDialog;
    private FrameAnimationImageView fiv_loading;

    private LoadingDialogUtils() {
    }


    public static LoadingDialogUtils getInstance() {
        return mLoadingDialogUtils;
    }

    public void show() {
//        if (mDialog == null) {
            mDialog = new AlertDialog.Builder(AppManagerUtil.instance().currentActivity(), R.style.dialog_white)
                    .setContentView(R.layout.loading_dialog)
                    .setGravity(Gravity.CENTER)
                    .setWidthAndHeight((int) (ScreenUtils.getScreenWidth(AppManagerUtil.instance().currentActivity()) * 0.5), ViewGroup.LayoutParams.WRAP_CONTENT)
                    .create();

//            fiv_loading = mDialog.getView(R.id.fiv_loading);
//            fiv_loading.setFrameAnimationResouce(R.drawable.loading_drawable);

//        }

//        if (!mDialog.isShowing()) {
//            fiv_loading.startAnimation();
            mDialog.show();
//        }


    }


    public void dismiss() {
        if (mDialog != null) {
//            fiv_loading.stopAnimation();
            mDialog.dismiss();
        }
    }

}
