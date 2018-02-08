package com.lqr.bijiaquan.ui.activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.lqr.bijiaquan.R;
import com.lqr.bijiaquan.ui.base.BaseActivity;
import com.lqr.bijiaquan.model.cache.UserCache;
import com.lqr.bijiaquan.ui.presenter.AddFriendAtPresenter;
import com.lqr.bijiaquan.ui.view.IAddFriendAtView;
import com.lqr.bijiaquan.util.UIUtils;

import butterknife.Bind;

/**
 * @创建者 CSDN_LQR
 * @描述 添加朋友界面
 */

public class AddFriendActivity extends BaseActivity<IAddFriendAtView, AddFriendAtPresenter> implements IAddFriendAtView {

    @Bind(R.id.llSearchUser)
    LinearLayout mLlSearchUser;
    @Bind(R.id.tvAccount)
    TextView mTvAccount;

    @Override
    public void initView() {
        setToolbarTitle(UIUtils.getString(R.string.add_friend));
        mTvAccount.setText(UserCache.getId() + "");
    }

    @Override
    public void initListener() {
        mLlSearchUser.setOnClickListener(v -> jumpToActivity(SearchUserActivity.class));
    }

    @Override
    protected AddFriendAtPresenter createPresenter() {
        return new AddFriendAtPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_add_friend;
    }
}
