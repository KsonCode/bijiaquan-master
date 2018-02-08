package com.lqr.bijiaquan.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lqr.bijiaquan.R;
import com.lqr.recyclerview.LQRRecyclerView;
import com.lqr.bijiaquan.ui.base.BaseActivity;
import com.lqr.bijiaquan.ui.presenter.NewFriendAtPresenter;
import com.lqr.bijiaquan.ui.view.INewFriendAtView;
import com.lqr.bijiaquan.util.UIUtils;

import butterknife.Bind;

/**
 * @创建者 CSDN_LQR
 * @描述 新的朋友界面
 */

public class NewFriendActivity extends BaseActivity<INewFriendAtView, NewFriendAtPresenter> implements INewFriendAtView {

    @Bind(R.id.llToolbarAddFriend)
    LinearLayout mLlToolbarAddFriend;
    @Bind(R.id.tvToolbarAddFriend)
    TextView mTvToolbarAddFriend;

    @Bind(R.id.llNoNewFriend)
    LinearLayout mLlNoNewFriend;
    @Bind(R.id.llHasNewFriend)
    LinearLayout mLlHasNewFriend;
    @Bind(R.id.rvNewFriend)
    LQRRecyclerView mRvNewFriend;

    @Override
    public void initView() {
        mLlToolbarAddFriend.setVisibility(View.VISIBLE);
        setToolbarTitle(UIUtils.getString(R.string.new_friend));
    }

    @Override
    public void initData() {
        mPresenter.loadNewFriendData();
    }

    @Override
    public void initListener() {
        mTvToolbarAddFriend.setOnClickListener(v -> jumpToActivity(AddFriendActivity.class));
    }

    @Override
    protected NewFriendAtPresenter createPresenter() {
        return new NewFriendAtPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_new_friend;
    }

    @Override
    public LinearLayout getLlNoNewFriend() {
        return mLlNoNewFriend;
    }

    @Override
    public LinearLayout getLlHasNewFriend() {
        return mLlHasNewFriend;
    }

    @Override
    public LQRRecyclerView getRvNewFriend() {
        return mRvNewFriend;
    }
}
