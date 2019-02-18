package com.hjy.wisdommedicaldoctor.ui.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.base.dialog.CommonDialog;
import com.fy.baselibrary.base.dialog.DialogConvertListener;
import com.fy.baselibrary.base.dialog.NiceDialog;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.retrofit.load.up.UpLoadUtils;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.T;
import com.hjy.wisdommedicaldoctor.R;
import com.hjy.wisdommedicaldoctor.adapter.ImagePickerAdapter;
import com.hjy.wisdommedicaldoctor.constant.ApiService;
import com.hjy.wisdommedicaldoctor.constant.NetCallBack;
import com.hjy.wisdommedicaldoctor.constant.NetDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * 意见反馈 Activity
 * Created by Stefan on 2018/7/9 14:58.
 */
public class FeedBackActivity extends AppCompatActivity implements IBaseActivity {
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    @BindView(R.id.iamge_recycler_view)
    RecyclerView image_recycler_view;
    @BindView(R.id.et_feedback)
    EditText et_feedback;
    @BindView(R.id.tv_inputNum)
    TextView tv_inputNum;

    ArrayList<ImageItem> images = null;
    private List<ImageItem> selImageList; //当前选择的所有图片
    private ImagePickerAdapter imageAdapter;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_feed_back;
    }

    @StatusBar(statusColor = R.color.white, navColor = R.color.navigationBarColor)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, false);

        initImagePicker();
        initImageRecycler();
        et_feedback.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        et_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_inputNum.setText(et_feedback.getText().toString().length() + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick({R.id.bt_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_commit:
                if (TextUtils.isEmpty(et_feedback.getText().toString().trim())) {
                    T.showShort("反馈内容不能为空");
                    return;
                }
                uploadImages("feedback", images);
                break;
        }
    }

    /**
     * 上传图片并提交用户反馈
     *
     * @param type  图片类型	photo(头像)、feedback(反馈)、medicalRecords(病历)
     * @param lists 文件列表
     */
    private void uploadImages(String type, List<ImageItem> lists) {
        Observable<String> observable;
        if (null != lists && lists.size() > 0) {
            observable = Observable.just(lists)
                    .observeOn(Schedulers.io())
                    .map(new Function<List<ImageItem>, List<String>>() {
                        @Override
                        public List<String> apply(List<ImageItem> imageItems) throws Exception {
                            List<String> imgPath = new ArrayList<>();
                            for (ImageItem imageItem : imageItems) {
                                imgPath.add(imageItem.getPath());
                            }
                            return imgPath;
                        }
                    }).map(new Function<List<String>, List<File>>() {
                        @Override
                        public List<File> apply(@NonNull List<String> list) throws Exception {
                            return Luban.with(FeedBackActivity.this)
                                    .load(list)
                                    .ignoreBy(100)
//                                    .setTargetDir(Environment.getDataDirectory().getPath())
                                    .get();
                        }
                    })
                    .map(new Function<List<File>, List<MultipartBody.Part>>() {
                        @Override
                        public List<MultipartBody.Part> apply(List<File> fileList) throws Exception {
                            return UpLoadUtils.fileToMultipartBodyPart(fileList);
                        }
                    }).flatMap(new Function<List<MultipartBody.Part>, ObservableSource<String>>() {
                        @Override
                        public ObservableSource<String> apply(List<MultipartBody.Part> files) throws Exception {
                            return RequestUtils.create(ApiService.class)
                                    .uploadPostFile(RequestBody.create(MediaType.parse("multipart/form-data"), type), files)
                                    .compose(RxHelper.handleResult());
                        }
                    }).flatMap(new Function<String, ObservableSource<String>>() {
                        @Override
                        public ObservableSource<String> apply(String images) throws Exception {
                            return getFeedBack(images);
                        }
                    });
        } else {
            observable = getFeedBack("");
        }

        observable.subscribe(new NetCallBack<String>(new NetDialog().init(this).setDialogMsg(R.string.upLoading)) {
            @Override
            protected void onSuccess(String objectBaseBean) {
                if (objectBaseBean != null) {
                    T.showShort("反馈提交成功");
                    JumpUtils.exitActivity(FeedBackActivity.this);
                }
            }

            @Override
            protected void updataLayout(int flag) {

            }
        });
    }

    /**
     * 获取 用户反馈 被观察者
     *
     * @param
     * @return
     */
    private Observable<String> getFeedBack(String imgUrls) {
        ArrayMap<String, Object> param = new ArrayMap<>();
        param.put("imageUrl", imgUrls);
        param.put("content", et_feedback.getText().toString().trim());

        return RequestUtils.create(ApiService.class)
                .feedback(param)
                .compose(RxHelper.handleResult());
    }

    //选择图片弹框
    private void showSelectDialog() {
        NiceDialog.init().setLayoutId(R.layout.dialog_choose_sex)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        TextView tv_top = holder.getView(R.id.tv_top);
                        TextView tv_down = holder.getView(R.id.tv_down);
                        tv_top.setText(R.string.photograph);
                        tv_down.setText(R.string.select_photo);
                        tv_top.setOnClickListener(v -> {
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(1);
                            Intent intent = new Intent(FeedBackActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
                            dialog.dismiss();
                        });
                        tv_down.setOnClickListener(v -> {
                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                            Intent intent1 = new Intent(FeedBackActivity.this, ImageGridActivity.class);
                            startActivityForResult(intent1, REQUEST_CODE_SELECT);
                            dialog.dismiss();
                        });

                        holder.setOnClickListener(R.id.tvCancel, v -> dialog.dismiss());
                    }
                }).setWidthPixels(ViewGroup.LayoutParams.MATCH_PARENT)
                .setGravity(Gravity.BOTTOM)
                .setAnim(R.style.AnimUp)
                .setHide(true)
                .show(getSupportFragmentManager());
    }

    //图片选择配置 一般在application中配置 但是不确定其他位置是否还有使用情况且选择图片数量不一定
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制

    }

    //初始化图片选择
    private int maxImgCount = 9;

    private void initImageRecycler() {
        selImageList = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(this, 4);
//        manager.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为横向布局
        image_recycler_view.setLayoutManager(manager);
        imageAdapter = new ImagePickerAdapter(FeedBackActivity.this, selImageList, maxImgCount);
        image_recycler_view.setHasFixedSize(true);
        image_recycler_view.setAdapter(imageAdapter);

        imageAdapter.setItemClickListener(position -> {
            if (position == imageAdapter.getImages().size()) {
                showSelectDialog();
            } else {
                Intent intentPreview = new Intent(FeedBackActivity.this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) imageAdapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
            }
        });
        imageAdapter.setItemLongClickListener(position -> T.showShort("长按了" + position));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    imageAdapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    imageAdapter.setImages(selImageList);
                }
            }
        }
    }


}
