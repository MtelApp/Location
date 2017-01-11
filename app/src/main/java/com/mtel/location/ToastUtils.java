package com.mtel.location;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mtel.location.base.AppApplication;


/**
 * By action on 2017/1/11
 *
 * Explain:Toast工具类
 */
public class ToastUtils {

	private ToastUtils() {
		throw new UnsupportedOperationException("不能实例化工具类");
	}

	private static Toast mToast;

	static {
		mToast = Toast.makeText(AppApplication.getAppContext(), "", Toast.LENGTH_SHORT);
	}

	/** 显示弹窗 */
	public static void showToast(String str) {
		try {
			mToast.setText(str);
			mToast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showToast(Context context, String str) {
		showToast(str);
	}

	static boolean canShowTestToast = true;// 显示测试toast

	/** 显示测试弹窗 */
	public static void showTestToast(String str) {
		if (canShowTestToast == false)
			return;
		showToast(str);
	}

	/** 带图片消息提示 */
	public static void ImageToast(Context context, int ImageResourceId, String text, int duration) {
		
		// 创建一个Toast提示消息
		Toast ImageToast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);
		// 设置Toast提示消息在屏幕上的位置
		ImageToast.setGravity(Gravity.CENTER, 0, 0);
		// 获取Toast提示消息里原有的View
		View toastView = mToast.getView();
		// 创建一个ImageView
		ImageView img = new ImageView(context.getApplicationContext());
		img.setImageResource(ImageResourceId);
		// 创建一个LineLayout容器
		LinearLayout ll = new LinearLayout(context.getApplicationContext());
		// 向LinearLayout中添加ImageView和Toast原有的View
		ll.addView(img);
		ll.addView(toastView);
		// 将LineLayout容器设置为toast的View
		ImageToast.setView(ll);
		// 显示消息
		ImageToast.show();
	}
}
