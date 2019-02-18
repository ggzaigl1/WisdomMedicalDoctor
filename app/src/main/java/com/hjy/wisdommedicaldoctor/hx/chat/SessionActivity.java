package com.hjy.wisdommedicaldoctor.hx.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.adapter.EMAConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

/**
 * 回话列表 activity
 * Created by fangs on 2018/9/4 10:04.
 */
public class SessionActivity extends AppCompatActivity implements IBaseActivity{

    @Override
    public boolean isShowHeadView() {
        return false;
    }

    @Override
    public int setView() {
        return R.layout.chat_act;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);
        EaseConversationListFragment sessionListFragment = new EaseConversationListFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, sessionListFragment)
                .commit();

        sessionListFragment.setConversationListItemClickListener(conversation -> {
            Bundle bundle = new Bundle();
            bundle.putString(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
            bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, getChatType(conversation.getType()));

            JumpUtils.jump(SessionActivity.this, ChatActivity.class, bundle);
        });
    }

    /**
     * 比对枚举 返回 聊天类型
     * @param chatType
     * @return
     */
    private int getChatType(EMConversation.EMConversationType chatType){
        if (chatType == EMConversation.EMConversationType.Chat) {
            return EaseConstant.CHATTYPE_SINGLE;
        } else if (chatType == EMConversation.EMConversationType.GroupChat) {
            return EaseConstant.CHATTYPE_GROUP;
        } else if (chatType == EMConversation.EMConversationType.ChatRoom) {
            return EaseConstant.CHATTYPE_CHATROOM;
        }

        return EaseConstant.CHATTYPE_SINGLE;
    }

}
