package com.hjy.wisdommedicaldoctor.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.AppUtils;
import com.fy.baselibrary.utils.JumpUtils;
import com.hjy.wisdommedicaldoctor.R;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 初夏小溪 on 2018/8/7 0007.
 * 手持身份证
 */

public class HandIdActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    @BindView(R.id.iv_hand)
    ImageView mImageView;
    File file;
    public final static int REQUEST_CODE = 0X13;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_hand_id;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_certificate, menu);
        View menuLayout = menu.findItem(R.id.menuSchedule).getActionView();
        menuLayout.setOnClickListener(this);

        return true;
    }

    @OnClick({R.id.bt_next, R.id.Ll_hand})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuLayout://菜单按钮
                JumpUtils.jump(this, ShootStandardActivity.class, null);
                break;
            case R.id.bt_next://下一步
                JumpUtils.jump(this, PractisingCertificateActivity.class, null);
                break;
            case R.id.Ll_hand://手持身份证
                useCamera();
                break;
        }
    }

    /**
     * 使用相机
     */
    private void useCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test/" + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
        Uri uri = FileProvider.getUriForFile(this, AppUtils.getFileProviderName(), file);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mImageView.setVisibility(View.VISIBLE);
            mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

            //在手机相册中显示刚拍摄的图片
//            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//            Uri contentUri = Uri.fromFile(file);
//            mediaScanIntent.setData(contentUri);
//            sendBroadcast(mediaScanIntent);
        }
    }
}
