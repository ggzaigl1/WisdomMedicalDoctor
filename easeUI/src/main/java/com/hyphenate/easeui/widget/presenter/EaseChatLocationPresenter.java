package com.hyphenate.easeui.widget.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.fy.baselibrary.utils.JumpUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowLocation;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by zhangsong on 17-10-12.
 */

public class EaseChatLocationPresenter extends EaseChatRowPresenter {
    @Override
    protected EaseChatRow onCreateChatRow(Context cxt, EMMessage message, int position, BaseAdapter adapter) {
        return new EaseChatRowLocation(cxt, message, position, adapter);
    }

    @Override
    protected void handleReceiveMessage(EMMessage message) {
        if (!message.isAcked() && message.getChatType() == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBubbleClick(EMMessage message) {
        EMLocationMessageBody locBody = (EMLocationMessageBody) message.getBody();

        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", locBody.getLatitude());
        bundle.putDouble("longitude", locBody.getLongitude());
        bundle.putString("address", locBody.getAddress());
        JumpUtils.jump((Activity) getContext(), EaseBaiduMapActivity.class, bundle);
    }
}
