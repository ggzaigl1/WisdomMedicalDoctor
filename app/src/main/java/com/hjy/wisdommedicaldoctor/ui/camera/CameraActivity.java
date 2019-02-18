package com.hjy.wisdommedicaldoctor.ui.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.utils.FileUtil;


/**
 * Created by gxj on 2018/2/18 11:46.
 * 拍照界面
 */
public class CameraActivity extends Activity implements View.OnClickListener {

    /**
     * 身份证正面
     */
    public final static int TYPE_ID_CARD_FRONT = 1;
    /**
     * 身份证反面
     */
    public final static int TYPE_ID_CARD_BACK = 2;

    public final static int REQUEST_CODE = 0X13;
    public final static int REQUEST_CODE_BACK = 0X14;

    private CustomCameraPreview customCameraPreview;
    private ImageView cropView;
    private View optionView;

    /**
     * 跳转到拍照页面
     */
    public static void navToCamera(Context context, int type) {
        Intent intent = new Intent(context, CameraActivity.class);
        intent.putExtra("type", type);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
    }

    public static void navToCameraBack(Context context, int type) {
        Intent intent = new Intent(context, CameraActivity.class);
        intent.putExtra("type", type);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_BACK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra("type", 0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_camera);

        customCameraPreview = findViewById(R.id.camera_surface);
        View containerView = findViewById(R.id.camera_crop_container);
        cropView = findViewById(R.id.camera_crop);
        optionView = findViewById(R.id.camera_option);

        //获取屏幕最小边，设置为cameraPreview较窄的一边
        float screenMinSize = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        //根据screenMinSize，计算出cameraPreview的较宽的一边，长宽比为标准的16:9
        float maxSize = screenMinSize / 9.0f * 16.0f;
        RelativeLayout.LayoutParams layoutParams;

        layoutParams = new RelativeLayout.LayoutParams((int) maxSize, (int) screenMinSize);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        customCameraPreview.setLayoutParams(layoutParams);

        float height = (int) (screenMinSize * 0.75);
        float width = (int) (height * 75.0f / 47.0f);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams cropParams = new LinearLayout.LayoutParams((int) width, (int) height);
        containerView.setLayoutParams(containerParams);
        cropView.setLayoutParams(cropParams);
        switch (type) {
            case TYPE_ID_CARD_FRONT:
                cropView.setImageResource(R.mipmap.camera_front);
                break;
            case TYPE_ID_CARD_BACK:
                cropView.setImageResource(R.mipmap.camera_back);
                break;
        }

        customCameraPreview.setOnClickListener(this);
        findViewById(R.id.camera_close).setOnClickListener(this);
        findViewById(R.id.camera_take).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_surface:
                customCameraPreview.focus();
                break;
            case R.id.camera_close:
                finish();
                break;
            case R.id.camera_take:
                takePhoto();
        }
    }

    private void takePhoto() {
        optionView.setVisibility(View.GONE);
        customCameraPreview.setEnabled(false);
        customCameraPreview.takePhoto(new Camera.PictureCallback() {
            public void onPictureTaken(final byte[] data, final Camera camera) {
                //子线程处理图片，防止ANR
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = null;
                        if (data != null) {
                            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            camera.stopPreview();
                        }

                        if (bitmap != null) {
                            //计算裁剪位置
                            Bitmap resBitmap = Bitmap.createBitmap(bitmap, 0, cropView.getTop(), cropView.getWidth(), cropView.getHeight());
                            FileUtil.saveBitmap(resBitmap);
                            if (!bitmap.isRecycled()) {
                                bitmap.recycle();
                            }
                            if (!resBitmap.isRecycled()) {
                                resBitmap.recycle();
                            }
                            //拍照完成，返回对应图片路径
                            Intent intent = new Intent();
                            intent.putExtra("result", FileUtil.getImgPath());
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        return;
                    }
                }).start();
            }
        });
    }
}
