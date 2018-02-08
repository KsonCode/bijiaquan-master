package com.lqr.bijiaquan.ui.presenter;

import android.text.TextUtils;

import com.lqr.bijiaquan.R;
import com.lqr.bijiaquan.api.ApiRetrofit;
import com.lqr.bijiaquan.app.AppConst;
import com.lqr.bijiaquan.model.cache.UserCache;
import com.lqr.bijiaquan.model.exception.ServerException;
import com.lqr.bijiaquan.ui.activity.MainActivity;
import com.lqr.bijiaquan.ui.base.BaseActivity;
import com.lqr.bijiaquan.ui.base.BasePresenter;
import com.lqr.bijiaquan.util.LogUtils;
import com.lqr.bijiaquan.util.UIUtils;
import com.lqr.bijiaquan.ui.view.ILoginAtView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginAtPresenter extends BasePresenter<ILoginAtView> {

    public LoginAtPresenter(BaseActivity context) {
        super(context);
    }

    public void login() {
        String phone = getView().getEtPhone().getText().toString().trim();
        String pwd = getView().getEtPwd().getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            UIUtils.showToast(UIUtils.getString(R.string.phone_not_empty));
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            UIUtils.showToast(UIUtils.getString(R.string.password_not_empty));
            return;
        }

        mContext.showWaitingDialog(UIUtils.getString(R.string.please_wait));
        ApiRetrofit.getInstance().login(AppConst.REGION, phone, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    int code = loginResponse.getCode();
                    mContext.hideWaitingDialog();
                    if (code == 200) {
                        UserCache.save(loginResponse.getResult().getId(), phone, loginResponse.getResult().getToken());
                        mContext.jumpToActivityAndClearTask(MainActivity.class);
                        mContext.finish();
                    } else {
                        loginError(new ServerException(UIUtils.getString(R.string.login_error) + code));
                    }
                }, this::loginError);
    }

    private void loginError(Throwable throwable) {
        LogUtils.e(throwable.getLocalizedMessage());
        UIUtils.showToast(throwable.getLocalizedMessage());
        mContext.hideWaitingDialog();
    }
}
