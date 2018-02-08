package com.lqr.bijiaquan.ui.presenter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.lqr.bijiaquan.R;
import com.lqr.bijiaquan.app.AppConst;
import com.lqr.bijiaquan.model.response.GetUserInfoByPhoneResponse;
import com.lqr.bijiaquan.ui.base.BaseActivity;
import com.lqr.bijiaquan.ui.view.ISearchUserAtView;
import com.lqr.bijiaquan.util.UIUtils;
import com.lqr.bijiaquan.api.ApiRetrofit;
import com.lqr.bijiaquan.model.response.GetUserInfoByIdResponse;
import com.lqr.bijiaquan.ui.activity.UserInfoActivity;
import com.lqr.bijiaquan.ui.base.BasePresenter;
import com.lqr.bijiaquan.util.LogUtils;
import com.lqr.bijiaquan.util.RegularUtils;

import io.rong.imlib.model.UserInfo;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchUserAtPresenter extends BasePresenter<ISearchUserAtView> {

    public SearchUserAtPresenter(BaseActivity context) {
        super(context);
    }

    public void searchUser() {
        String content = getView().getEtSearchContent().getText().toString().trim();

        if (TextUtils.isEmpty(content)) {
            UIUtils.showToast(UIUtils.getString(R.string.content_no_empty));
            return;
        }

        mContext.showWaitingDialog(UIUtils.getString(R.string.please_wait));
        if (RegularUtils.isMobile(content)) {
            ApiRetrofit.getInstance().getUserInfoFromPhone(AppConst.REGION, content)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getUserInfoByPhoneResponse -> {
                        mContext.hideWaitingDialog();
                        if (getUserInfoByPhoneResponse.getCode() == 200) {
                            GetUserInfoByPhoneResponse.ResultEntity result = getUserInfoByPhoneResponse.getResult();
                            UserInfo userInfo = new UserInfo(result.getId(), result.getNickname(), Uri.parse(result.getPortraitUri()));
                            Intent intent = new Intent(mContext, UserInfoActivity.class);
                            intent.putExtra("userInfo", userInfo);
                            mContext.jumpToActivity(intent);
                        } else {
                            getView().getRlNoResultTip().setVisibility(View.VISIBLE);
                            getView().getLlSearch().setVisibility(View.GONE);
                        }
                    }, this::loadError);
        } else {
            ApiRetrofit.getInstance().getUserInfoById(content)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getUserInfoByIdResponse -> {
                        mContext.hideWaitingDialog();
                        if (getUserInfoByIdResponse.getCode() == 200) {
                            GetUserInfoByIdResponse.ResultEntity result = getUserInfoByIdResponse.getResult();
                            UserInfo userInfo = new UserInfo(result.getId(), result.getNickname(), Uri.parse(result.getPortraitUri()));
                            Intent intent = new Intent(mContext, UserInfoActivity.class);
                            intent.putExtra("userInfo", userInfo);
                            mContext.jumpToActivity(intent);
                        } else {
                            getView().getRlNoResultTip().setVisibility(View.VISIBLE);
                            getView().getLlSearch().setVisibility(View.GONE);
                        }
                    }, this::loadError);
        }
    }

    private void loadError(Throwable throwable) {
        mContext.hideWaitingDialog();
        LogUtils.sf(throwable.getLocalizedMessage());
    }
}
