package com.hjy.wisdommedicaldoctor.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.utils.L;
import com.fy.baselibrary.utils.NotificationUtils;
import com.fy.baselibrary.utils.T;
import com.hjy.wisdommedicaldoctor.R;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements IBaseActivity {

    @BindView(R.id.bottomNavigationBar)
    BottomNavigationBar bottomNavigationBar;

    private List<BaseFragment> mFragments = new ArrayList<>();
    private boolean mBooleanExtra;
    private int mServiceId = 0;
    private int serviceId;

    @Override
    public boolean isShowHeadView() {
        return false;
    }

    @Override
    public int setView() {
        return R.layout.activity_main;
    }

    @StatusBar(statusColor = R.color.transparent, navColor = R.color.navigationBarColor, applyNav = false, statusOrNavModel = 1)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        initFragment();
        setDefaultFragment();
        initBottomNavigation();

        //注册接收消息 监听
        EMClient.getInstance().chatManager().addMessageListener(mEMMessageListener);
    }

    private void initFragment() {
        mFragments.add(HomeFragment.newInstance(""));
        mFragments.add(ConsultingRoomFragment.newInstance(0));
        mFragments.add(PersonalCenterFragment.newInstance(""));
    }

    private void setDefaultFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.flFragment, mFragments.get(0));
        ft.commit();
    }

    private void initBottomNavigation() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavigationBar.setActiveColor(R.color.colorPrimary)
                .addItem(new BottomNavigationItem(R.drawable.svg_home, R.string.home))
                .addItem(new BottomNavigationItem(R.drawable.svg_online_inquiry, R.string.enterConsultingRoom))
                .addItem(new BottomNavigationItem(R.drawable.svg_personal, R.string.personalCenter))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (position <= mFragments.size()) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment currentFragment = fm.findFragmentById(R.id.flFragment);
                    BaseFragment nextFragment = mFragments.get(position);

                    //判断当前的Fragment是否为空，不为空则隐藏
                    if (null != currentFragment) ft.hide(currentFragment);

                    //判断此Fragment是否已经添加到FragmentTransaction事物中
                    if (!nextFragment.isAdded()) {
                        ft.add(R.id.flFragment, nextFragment, position + "nextFragment");
                    } else {
                        ft.show(nextFragment);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("serviceId", mServiceId);
                    nextFragment.setArguments(bundle);
                    ft.commitAllowingStateLoss();
                }


            }

            @Override
            public void onTabUnselected(int position) {
                if (position < mFragments.size()) {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment nextFragment = mFragments.get(position);
                    ft.hide(nextFragment);
                    ft.commitAllowingStateLoss();
                }
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); 	不要调用父类的方法
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    /**
     * 解决 activity 启动模式为 singleTask时，intent传值 接收不到问题
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        //获取 Intent而不是用 方法传递的Intent，是因为在 Application 中 设置了activity 生命周期回调时候 用到Intent 缓存了配置信息
        Intent intent1 = getIntent();
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            intent1 = intent1.putExtras(bundle);
            super.onNewIntent(intent1);
            setIntent(intent1);//intent传值 接收不到问题，关键在这句
        } else {
            super.onNewIntent(intent);
        }


        mBooleanExtra = getIntent().getBooleanExtra("main", false);
        if (mBooleanExtra) {
            mServiceId = getIntent().getIntExtra("serviceId", 0);
            L.d("接受到其他页面跳转过来的");
            bottomNavigationBar.selectTab(1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(mEMMessageListener);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String str = (String) msg.obj;
                    T.showLong(str);
                    break;
                case 1:
                    EMMessage emMessage = (EMMessage) msg.obj;
                    String userName = emMessage.getStringAttribute("username", "");
                    boolean isVideoMsg = emMessage.getBooleanAttribute("isVideoMsg", false);//是否视频问诊
//                    Class actClass = isVideoMsg ? VideoCallActivity.class : VoiceCallActivity.class;

                    NotificationUtils.FyBuild.init()
                            .setChannel(12, "10086")
                            .setIcon(R.mipmap.icon_logo, R.color.mainColor)
                            .setMsgTitle("等待接诊")
                            .setMsgContent(userName + " " + "等待候诊")
                            .sendNotify(MainActivity.this, null);
                    break;
            }
        }
    };

    EMMessageListener mEMMessageListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> list) {
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> list) {
            EMMessage emMessage = list.get(0);
            EMMessageBody body = emMessage.getBody();
            Message msg = handler.obtainMessage(0);
            msg.what = 1;
            msg.obj = emMessage;
            handler.sendMessage(msg);
        }

        @Override
        public void onMessageRead(List<EMMessage> list) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> list) {
        }

        @Override
        public void onMessageRecalled(List<EMMessage> list) {
        }

        @Override
        public void onMessageChanged(EMMessage emMessage, Object o) {
        }
    };
}
