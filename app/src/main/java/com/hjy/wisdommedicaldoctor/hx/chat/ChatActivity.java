package com.hjy.wisdommedicaldoctor.hx.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.ioc.ConfigUtils;
import com.fy.baselibrary.utils.ResUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hyphenate.easeui.EaseConstant;

import butterknife.BindView;

/**
 * 聊天 activity
 * Created by fangs on 2018/9/3.
 */
public class ChatActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    protected int chatType;
    String toChatId = "";
    String toChatUsername = "";
    private EaseChatFragment chatFragment;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.chat_act;
    }

    @StatusBar(statusColor = R.color.statusBarColor, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        toChatId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        toChatUsername = getIntent().getStringExtra(EaseConstant.EXTRA_USER_NAME);
        chatType = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        //user or group id
        initTitle();
        Bundle fmBundle = new Bundle();
        fmBundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        fmBundle.putString(EaseConstant.EXTRA_USER_ID, toChatId);
        fmBundle.putString(EaseConstant.EXTRA_USER_NAME, toChatUsername);

        chatFragment = new EaseChatFragment();
        //set arguments
        chatFragment.setArguments(fmBundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, chatFragment)
                .commit();
    }

    private void initTitle() {
        Drawable backImg = TintUtils.getTintDrawable(ConfigUtils.getBackImg(), 1, R.color.white);
        toolbar.setNavigationIcon(backImg);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setBackgroundColor(ResUtils.getColor(R.color.titleBar));
        toolbarTitle.setTextColor(ResUtils.getColor(R.color.white));
        toolbarTitle.setText(toChatUsername);//设置 聊天对象昵称

//        if (chatType == EaseConstant.CHATTYPE_SINGLE) {
//            // set title
//            if (EaseUserUtils.getUserInfo(toChatId) != null) {
//                EaseUser user = EaseUserUtils.getUserInfo(toChatId);
//                if (user != null) toolbarTitle.setText(user.getNick());
//            }
//        } else {
//            if (chatType == EaseConstant.CHATTYPE_GROUP) {
//                //group chat
//                EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatId);
//                if (group != null) toolbarTitle.setText(group.getGroupName());
//            }
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        View menuLayout = menu.findItem(R.id.menuSchedule).getActionView();
        menuLayout.setOnClickListener(this);

        AppCompatImageView imgMenu = menuLayout.findViewById(R.id.imgMenu);
        imgMenu.setImageResource(R.drawable.svg_delete);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuLayout://菜单按钮
                chatFragment.emptyHistory();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        Bundle bundle = intent.getExtras();
        Intent intent1 = getIntent();
        if (null != bundle) {
            intent1 = intent1.putExtras(bundle);
            super.onNewIntent(intent1);
            setIntent(intent1);//intent传值 接收不到问题，关键在这句

            String username = bundle.getString(EaseConstant.EXTRA_USER_ID, "");
            if (toChatId.equals(username))
                super.onNewIntent(intent);
            else {
                finish();
                startActivity(intent);
            }
        } else {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

//    public String getToChatUsername(){
//        return toChatUsername;
//    }

}
