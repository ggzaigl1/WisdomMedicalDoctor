package com.hjy.wisdommedicaldoctor.hx;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.fy.baselibrary.aop.annotation.NeedPermission;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.L;
import com.hjy.wisdommedicaldoctor.hx.conference.ConferenceActivity;
import com.hjy.wisdommedicaldoctor.hx.single.CallManager;
import com.hjy.wisdommedicaldoctor.hx.single.VideoCallActivity;
import com.hjy.wisdommedicaldoctor.hx.single.VoiceCallActivity;
import com.hjy.wisdommedicaldoctor.main.MainActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * 环信请求工具类
 * Created by fangs on 2018/9/4 10:49.
 */
public class EMUtils {

    /**
     * 环信 登录
     * @param userName
     * @param password
     */
    public static void loginEM(AppCompatActivity act, String userName, String password) {

        EMClient.getInstance().login(userName, password, new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();

                        L.d("main", "登录聊天服务器成功！");
//                        JumpUtils.jump(act, SessionActivity.class, null);
                        act.finish();
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {
                        L.d("main", "登录聊天服务器失败！");
                    }
                });
    }

    /**
     * 环信 退出登录
     */
    public static void outLoginEM() {
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                L.d("main", "退出登录成功！");
            }

            @Override
            public void onError(int i, String s) {
                L.d("main", "退出登录失败！");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    /**
     * 视频呼叫
     */
//    @NeedPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO})
    public static void callVideo(AppCompatActivity act, String toChatUsername) {
        Intent intent = new Intent(act, VideoCallActivity.class);
        CallManager.getInstance().setChatId(toChatUsername);
        CallManager.getInstance().setInComingCall(false);
        CallManager.getInstance().setCallType(CallManager.CallType.VIDEO);
        act.startActivity(intent);
    }

    /**
     * 语音呼叫
     * @param act
     * @param toChatUsername 对方的环信id
     */
    @NeedPermission({Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO})
    public static void callVoice(AppCompatActivity act, String toChatUsername) {
        Intent intent = new Intent(act, VoiceCallActivity.class);
        CallManager.getInstance().setChatId(toChatUsername);
        CallManager.getInstance().setInComingCall(false);
        CallManager.getInstance().setCallType(CallManager.CallType.VOICE);
        act.startActivity(intent);
    }


    /**
     * 发起视频会议
     */
    public void videoConference(AppCompatActivity act, String toChatUsername, boolean isCreator) {
        Intent intent = new Intent(act, ConferenceActivity.class);
        intent.putExtra("isCreator", isCreator);
        intent.putExtra("username", toChatUsername);
        act.startActivity(intent);
    }
}
