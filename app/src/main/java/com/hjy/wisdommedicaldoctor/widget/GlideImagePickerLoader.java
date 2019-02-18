package com.hjy.wisdommedicaldoctor.widget;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hjy.wisdommedicaldoctor.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by QKun on 2018/7/5.
 */
public class GlideImagePickerLoader implements ImageLoader {


    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_default_image)    //加载成功之前占位图
                .error(R.drawable.ic_default_image)    //加载错误之后的错误图
                .override(400, 400)    //指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                .fitCenter()
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop()
//            .circleCrop()//指定图片的缩放类型为 centerCrop （圆形）
                .skipMemoryCache(true)    //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)    //跳过磁盘缓存
                ;
        Glide.with(activity)
                .load(Uri.fromFile(new File(path)))
                .apply(options)
                .into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)    //跳过磁盘缓存
                ;
        Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
