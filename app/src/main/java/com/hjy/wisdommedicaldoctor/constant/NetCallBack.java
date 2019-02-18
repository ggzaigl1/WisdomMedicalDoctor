package com.hjy.wisdommedicaldoctor.constant;

import com.fy.baselibrary.retrofit.RequestBaseObserver;
import com.fy.baselibrary.retrofit.ServerException;
import com.fy.baselibrary.retrofit.dialog.IProgressDialog;
import com.fy.baselibrary.statuslayout.StatusLayoutManager;
import com.fy.baselibrary.utils.T;

/**
 * 自定义Subscribe
 * Created by fangs on 2017/8/28.
 */
public abstract class NetCallBack<V> extends RequestBaseObserver<V> {

    public NetCallBack() {
    }

    public NetCallBack(IProgressDialog dialog) {
        super(dialog);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ServerException) {
            dismissProgress();
            ServerException se = (ServerException) e;
            if (se.code == 500) {
                T.showLong(se.getMessage());
                //token 失效
            } else if (se.code == 501) {
                updataLayout(501);
            }
        } else {
            super.onError(e);
        }
    }
}
