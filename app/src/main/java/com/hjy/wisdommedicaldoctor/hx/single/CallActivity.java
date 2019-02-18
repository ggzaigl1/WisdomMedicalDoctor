package com.hjy.wisdommedicaldoctor.hx.single;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.WindowManager;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.hjy.wisdommedicaldoctor.R;
import com.hyphenate.chat.EMClient;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lzan13 on 2016/8/8.
 * <p>
 * 通话界面的父类，做一些音视频通话的通用操作
 */
public class CallActivity extends AppCompatActivity implements IBaseActivity{

    AppCompatActivity activity;
    // 震动器
    private Vibrator vibrator;

    /**
     * 初始化界面方法，做一些界面的初始化操作
     */
    protected void initView() {
        activity = this;

        initCallPushProvider();

        // 初始化振动器
        vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

        if (CallManager.getInstance().getCallState() == CallManager.CallState.DISCONNECTED) {
            // 收到呼叫或者呼叫对方时初始化通话状态监听
            CallManager.getInstance().setCallState(CallManager.CallState.CONNECTING);
            CallManager.getInstance().registerCallStateListener();
            CallManager.getInstance().attemptPlayCallSound();

            // 如果不是对方打来的，就主动呼叫
            if (!CallManager.getInstance().isInComingCall()) {
                CallManager.getInstance().makeCall();
            }
        }
    }

    /**
     * 初始化通话推送提供者
     */
    private void initCallPushProvider() {
        CallPushProvider pushProvider = new CallPushProvider();
        EMClient.getInstance().callManager().setPushProvider(pushProvider);
    }

    /**
     * 挂断通话
     */
    protected void endCall() {
        CallManager.getInstance().endCall();
        onFinish();
    }

    /**
     * 拒绝通话
     */
    protected void rejectCall() {
        CallManager.getInstance().rejectCall();
        onFinish();
    }

    /**
     * 接听通话
     */
    protected void answerCall() {
        CallManager.getInstance().answerCall();
    }

    /**
     * 调用系统振动，触发按钮的震动反馈
     */
    protected void vibrate() {
        vibrator.vibrate(88);
    }

    /**
     * 销毁界面时做一些自己的操作
     */
    public void onFinish() {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 判断当前通话状态，如果已经挂断，则关闭通话界面
        if (CallManager.getInstance().getCallState() == CallManager.CallState.DISCONNECTED) {
            onFinish();
        } else {
            CallManager.getInstance().removeFloatWindow();
        }
    }

    @Override
    public boolean isShowHeadView() {
        return false;
    }

    @Override
    public int setView() {
        return 0;
    }

    @StatusBar(statusColor = R.color.statusBarColor2, navColor = R.color.navigationBarColor2)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        // 设置通话界面属性，保持屏幕常亮，关闭输入法，以及解锁
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        initView();
    }

}
