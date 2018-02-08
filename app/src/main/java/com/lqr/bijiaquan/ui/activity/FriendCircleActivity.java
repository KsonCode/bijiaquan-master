package com.lqr.bijiaquan.ui.activity;

import com.lqr.bijiaquan.ui.base.BaseActivity;
import com.lqr.bijiaquan.ui.presenter.FriendCircleAtPresenter;
import com.lqr.bijiaquan.ui.view.IFriendCircleAtView;

/**
 * @创建者 CSDN_LQR
 * @描述 朋友圈
 */
public class FriendCircleActivity extends BaseActivity<IFriendCircleAtView, FriendCircleAtPresenter> implements IFriendCircleAtView {

    @Override
    protected FriendCircleAtPresenter createPresenter() {
        return new FriendCircleAtPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return 0;
    }
}
