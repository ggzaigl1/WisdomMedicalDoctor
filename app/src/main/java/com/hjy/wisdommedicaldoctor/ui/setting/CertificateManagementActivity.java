package com.hjy.wisdommedicaldoctor.ui.setting;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.fy.baselibrary.aop.annotation.NeedPermission;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBar;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.ui.HandIdActivity;
import com.hjy.wisdommedicaldoctor.ui.ShootStandardActivity;
import com.hjy.wisdommedicaldoctor.ui.camera.CameraActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 初夏小溪 on 2018/8/6 0006.
 * 证书管理
 */
public class CertificateManagementActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    @BindView(R.id.iv_off)
    ImageView mImageViewOff;
    @BindView(R.id.iv_on)
    ImageView mImageViewOn;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_certificate_management;
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

    @OnClick({R.id.bt_next, R.id.Ll_off, R.id.Ll_on})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuLayout://菜单按钮
                JumpUtils.jump(this, ShootStandardActivity.class, null);
                break;
            case R.id.bt_next://下一步
                JumpUtils.jump(this, HandIdActivity.class, null);
                break;
            case R.id.Ll_off://Id 正面
                takePhoto(CameraActivity.TYPE_ID_CARD_FRONT);
                break;
            case R.id.Ll_on://Id 背面
                takePhotoBack(CameraActivity.TYPE_ID_CARD_BACK);
                break;
        }
    }

    /**
     * 拍摄证件照片
     *
     * @param type 拍摄证件类型
     */
    @NeedPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    private void takePhoto(int type) {
        CameraActivity.navToCamera(this, type);
    }

    @NeedPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    private void takePhotoBack(int type) {
        CameraActivity.navToCameraBack(this, type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == CameraActivity.REQUEST_CODE) {
            //获取文件路径，显示图片
            if (data != null) {
                String path = data.getStringExtra("result");
                if (!TextUtils.isEmpty(path)) {
                    mImageViewOff.setVisibility(View.VISIBLE);
                    mImageViewOff.setImageBitmap(BitmapFactory.decodeFile(path));
                }
            }
        }
        if (requestCode == CameraActivity.REQUEST_CODE_BACK) {
            //获取文件路径，显示图片
            if (data != null) {
                String path = data.getStringExtra("result");
                if (!TextUtils.isEmpty(path)) {
                    mImageViewOn.setVisibility(View.VISIBLE);
                    mImageViewOn.setImageBitmap(BitmapFactory.decodeFile(path));
                }
            }
        }
    }
}
