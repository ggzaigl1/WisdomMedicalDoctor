package com.hjy.wisdommedicaldoctor.hx.single;

import android.util.Log;

import com.fy.baselibrary.utils.T;
import com.hyphenate.chat.EMCallStateChangeListener;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lzan13 on 2016/10/18.
 * 通话状态监听类，用来监听通话过程中状态的变化
 */
public class CallStateListener implements EMCallStateChangeListener {

    @Override public void onCallStateChanged(CallState callState, CallError callError) {
        CallEvent event = new CallEvent();
        event.setState(true);
        event.setCallError(callError);
        event.setCallState(callState);
        EventBus.getDefault().post(event);
        switch (callState) {
            case CONNECTING: // 正在呼叫对方
                Log.i("CallStateListener", "正在呼叫对方" + callError);
                CallManager.getInstance().setCallState(CallManager.CallState.CONNECTING);
                break;
            case CONNECTED: // 正在等待对方接受呼叫申请（对方申请与你进行通话）
                Log.i("CallStateListener", "正在连接" + callError);
                CallManager.getInstance().setCallState(CallManager.CallState.CONNECTED);
                break;
            case ACCEPTED: // 通话已接通
                Log.i("CallStateListener", "通话已接通");
                CallManager.getInstance().stopCallSound();
                CallManager.getInstance().startCallTime();
                CallManager.getInstance().setEndType(CallManager.EndType.NORMAL);
                CallManager.getInstance().setCallState(CallManager.CallState.ACCEPTED);
                break;
            case DISCONNECTED: // 通话已中断
                String msgT = "通话已结束";
                Log.i("CallStateListener", "通话已结束" + callError);
                // 通话结束，重置通话状态
                if (callError == CallError.ERROR_UNAVAILABLE) {
                    msgT = "对方不在线";
                    Log.i("CallStateListener", "对方不在线" + callError);
                    CallManager.getInstance().setEndType(CallManager.EndType.OFFLINE);
                } else if (callError == CallError.ERROR_BUSY) {
                    msgT = "对方正忙";
                    Log.i("CallStateListener", "对方正忙" + callError);
                    CallManager.getInstance().setEndType(CallManager.EndType.BUSY);
                } else if (callError == CallError.REJECTED) {
                    msgT = "对方已拒绝";
                    Log.i("CallStateListener", "对方已拒绝" + callError);
                    CallManager.getInstance().setEndType(CallManager.EndType.REJECTED);
                } else if (callError == CallError.ERROR_NORESPONSE) {
                    msgT = "对方未响应，可能手机不在身边";
                    Log.i("CallStateListener", "对方未响应，可能手机不在身边" + callError);
                    CallManager.getInstance().setEndType(CallManager.EndType.NORESPONSE);
                } else if (callError == CallError.ERROR_TRANSPORT) {
                    msgT = "连接建立失败";
                    Log.i("CallStateListener", "连接建立失败" + callError);
                    CallManager.getInstance().setEndType(CallManager.EndType.TRANSPORT);
                } else if (callError == CallError.ERROR_LOCAL_SDK_VERSION_OUTDATED) {
                    Log.i("CallStateListener", "双方通讯协议不同" + callError);
                    CallManager.getInstance().setEndType(CallManager.EndType.DIFFERENT);
                } else if (callError == CallError.ERROR_REMOTE_SDK_VERSION_OUTDATED) {
                    Log.i("CallStateListener", "双方通讯协议不同" + callError);
                    CallManager.getInstance().setEndType(CallManager.EndType.DIFFERENT);
                } else if (callError == CallError.ERROR_NO_DATA) {
                    Log.i("CallStateListener", "没有通话数据" + callError);
                } else {
                    Log.i("通话已结束 %s", callError + "");
                    if (CallManager.getInstance().getEndType() == CallManager.EndType.CANCEL) {
                        CallManager.getInstance().setEndType(CallManager.EndType.CANCELLED);
                    }
                }

                T.showLong(msgT);
                // 通话结束，保存消息 todo 业务要求不保存通话信息
//                CallManager.getInstance().saveCallMessage();
                CallManager.getInstance().reset();
                break;
            case NETWORK_DISCONNECTED:
                Log.i("CallStateListener", "对方网络不可用");
                break;
            case NETWORK_NORMAL:
                Log.i("CallStateListener", "网络正常");
                break;
            case NETWORK_UNSTABLE:
                if (callError == EMCallStateChangeListener.CallError.ERROR_NO_DATA) {
                    Log.i("CallStateListener", "没有通话数据" + callError);
                } else {
                    Log.i("CallStateListener", "网络不稳定" + callError);
                }
                break;
            case VIDEO_PAUSE:
                Log.i("CallStateListener", "视频传输已暂停");
                break;
            case VIDEO_RESUME:
                Log.i("CallStateListener", "视频传输已恢复");
                break;
            case VOICE_PAUSE:
                Log.i("CallStateListener", "语音传输已暂停");
                break;
            case VOICE_RESUME:
                Log.i("CallStateListener", "语音传输已恢复");
                break;
        }
    }
}
