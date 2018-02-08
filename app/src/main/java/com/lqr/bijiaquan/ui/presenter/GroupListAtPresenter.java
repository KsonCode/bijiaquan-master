package com.lqr.bijiaquan.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.bijiaquan.R;
import com.lqr.bijiaquan.ui.activity.SessionActivity;
import com.lqr.bijiaquan.ui.base.BaseActivity;
import com.lqr.bijiaquan.ui.view.IGroupListAtView;
import com.lqr.ninegridimageview.LQRNineGridImageView;
import com.lqr.ninegridimageview.LQRNineGridImageViewAdapter;
import com.lqr.bijiaquan.db.DBManager;
import com.lqr.bijiaquan.db.model.GroupMember;
import com.lqr.bijiaquan.db.model.Groups;
import com.lqr.bijiaquan.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;


public class GroupListAtPresenter extends BasePresenter<IGroupListAtView> {

    private List<Groups> mData = new ArrayList<>();
    private LQRAdapterForRecyclerView<Groups> mAdapter;
    private LQRNineGridImageViewAdapter mNgivAdapter = new LQRNineGridImageViewAdapter<GroupMember>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, GroupMember groupMember) {
            Glide.with(context).load(groupMember.getPortraitUri()).centerCrop().into(imageView);
        }
    };

    public GroupListAtPresenter(BaseActivity context) {
        super(context);
    }

    public void loadGroups() {
        loadData();
        setAdapter();
    }

    private void loadData() {
        List<Groups> groups = DBManager.getInstance().getGroups();
        if (groups != null && groups.size() > 0) {
            mData.clear();
            mData.addAll(groups);
            setAdapter();
            getView().getLlGroups().setVisibility(View.VISIBLE);
        } else {
            getView().getLlGroups().setVisibility(View.GONE);
        }
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new LQRAdapterForRecyclerView<Groups>(mContext, mData, R.layout.item_contact) {
                @Override
                public void convert(LQRViewHolderForRecyclerView helper, Groups item, int position) {
                    LQRNineGridImageView ngvi = helper.setViewVisibility(R.id.ngiv, View.VISIBLE)
                            .setViewVisibility(R.id.ivHeader, View.GONE)
                            .setText(R.id.tvName, item.getName())
                            .getView(R.id.ngiv);
                    ngvi.setAdapter(mNgivAdapter);
                    List<GroupMember> groupMembers = DBManager.getInstance().getGroupMembers(item.getGroupId());
                    ngvi.setImagesData(groupMembers);
                }
            };
            mAdapter.setOnItemClickListener((lqrViewHolder, viewGroup, view, i) -> {
                Intent intent = new Intent(mContext, SessionActivity.class);
                intent.putExtra("sessionId", mData.get(i).getGroupId());
                intent.putExtra("sessionType", SessionActivity.SESSION_TYPE_GROUP);
                mContext.jumpToActivity(intent);
                mContext.finish();
            });
            getView().getRvGroupList().setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChangedWrapper();
        }
    }
}
