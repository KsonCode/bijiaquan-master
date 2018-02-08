package com.lqr.bijiaquan.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lqr.bijiaquan.R;
import com.lqr.bijiaquan.app.AppConst;
import com.lqr.bijiaquan.ui.activity.MainActivity;
import com.lqr.bijiaquan.ui.base.BaseFragment;
import com.lqr.bijiaquan.ui.presenter.RecentMessageFgPresenter;
import com.lqr.recyclerview.LQRRecyclerView;
import com.lqr.bijiaquan.manager.BroadcastManager;
import com.lqr.bijiaquan.ui.view.IRecentMessageFgView;

import butterknife.Bind;

/**
 * @创建者 CSDN_LQR
 * @描述 最近会话列表界面
 */
public class RecentMessageFragment extends BaseFragment<IRecentMessageFgView, RecentMessageFgPresenter> implements IRecentMessageFgView {

    private boolean isFirst = true;
    @Bind(R.id.rvRecentMessage)
    LQRRecyclerView mRvRecentMessage;

    @Override
    public void init() {
        registerBR();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            mPresenter.getConversations();
        }
    }

    @Override
    public void initData() {
//        UIUtils.postTaskDelay(() -> {
//        mPresenter.getConversations();
//        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBR();
    }

    private void registerBR() {
        BroadcastManager.getInstance(getActivity()).register(AppConst.UPDATE_CONVERSATIONS, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mPresenter.getConversations();
                isFirst = false;
            }
        });
    }

    private void unRegisterBR() {
        BroadcastManager.getInstance(getActivity()).unregister(AppConst.UPDATE_CONVERSATIONS);
    }

    @Override
    protected RecentMessageFgPresenter createPresenter() {
        return new RecentMessageFgPresenter((MainActivity) getActivity());
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_recent_message;
    }

    @Override
    public LQRRecyclerView getRvRecentMessage() {
        return mRvRecentMessage;
    }
}
