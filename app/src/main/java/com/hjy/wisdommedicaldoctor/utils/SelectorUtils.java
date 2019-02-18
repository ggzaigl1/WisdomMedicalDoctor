package com.hjy.wisdommedicaldoctor.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.fy.baselibrary.ioc.ConfigUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;


/**
 * 代码生成选择器工具类 扩展TintUtils
 * Created by fangs on 2018/8/29 15:39.
 */
public class SelectorUtils {

    private SelectorUtils() {}

    /**
     * 根据传递的 drawable 和 颜色资源 生成选择器
     * @param Drawable
     * @param selectedColor
     * @param nomalColor
     * @return
     */
    public static Drawable getSelector(Drawable Drawable, @ColorRes int selectedColor, @ColorRes int nomalColor){

        int[][] states = new int[5][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{android.R.attr.state_checked};
        states[3] = new int[]{android.R.attr.state_activated};
        states[4] = new int[]{};

        Context ctx = ConfigUtils.getAppCtx();
        int color = ContextCompat.getColor(ctx, selectedColor);
        int[] colors = new int[]{color, color, color, color,
                ContextCompat.getColor(ctx, nomalColor)};

        return TintUtils.tintSelector(Drawable, colors, states);
    }

    /**
     * 获取 指定 ID的 drawable，生成的 选择器
     * @param draId1
     * @param draId2
     * @return
     */
    public static Drawable getSelector(@DrawableRes int draId1, @DrawableRes int draId2, int drawableType){

        int[][] states = new int[2][];
//        states[0] = new int[]{android.R.attr.state_pressed};
        states[0] = new int[]{android.R.attr.state_selected};
//        states[2] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};

        Drawable[] drawables = new Drawable[2];
        drawables[0] = TintUtils.getDrawable(draId1, drawableType);
        drawables[1] = TintUtils.getDrawable(draId2, drawableType);

        return TintUtils.getStateListDrawable(drawables, states);
    }

    public static Drawable getPressed(Drawable Drawable, @ColorRes int selectedColor, @ColorRes int nomalColor){
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
//        states[0] = new int[]{android.R.attr.state_selected};
//        states[2] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};

        Context ctx = ConfigUtils.getAppCtx();
        int[] colors = new int[]{ContextCompat.getColor(ctx, selectedColor),
                ContextCompat.getColor(ctx, nomalColor)};

        return TintUtils.tintSelector(Drawable, colors, states);
    }
}
