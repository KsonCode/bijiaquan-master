package com.lqr.bijiaquan.ui.presenter;

import com.lqr.bijiaquan.R;
import com.lqr.bijiaquan.api.ApiRetrofit;
import com.lqr.bijiaquan.ui.base.BaseActivity;
import com.lqr.bijiaquan.ui.base.BasePresenter;
import com.lqr.bijiaquan.util.LogUtils;
import com.lqr.bijiaquan.util.UIUtils;
import com.lqr.bijiaquan.ui.view.IPostScriptAtView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PostScriptAtPresenter extends BasePresenter<IPostScriptAtView> {

    public PostScriptAtPresenter(BaseActivity context) {
        super(context);
    }

    public void addFriend(String userId) {
        String msg = getView().getEtMsg().getText().toString().trim();
        ApiRetrofit.getInstance().sendFriendInvitation(userId, msg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(friendInvitationResponse -> {
                    if (friendInvitationResponse.getCode() == 200) {
                        UIUtils.showToast(UIUtils.getString(R.string.rquest_sent_success));
                        mContext.finish();
                    } else {
                        UIUtils.showToast(UIUtils.getString(R.string.rquest_sent_fail));
                    }
                }, this::loadError);
    }

    private void loadError(Throwable throwable) {
        LogUtils.sf(throwable.getLocalizedMessage());
        UIUtils.showToast(throwable.getLocalizedMessage());
    }
}
