package com.lqr.bijiaquan.ui.presenter;

import com.lqr.bijiaquan.R;
import com.lqr.bijiaquan.app.AppConst;
import com.lqr.bijiaquan.db.DBManager;
import com.lqr.bijiaquan.manager.BroadcastManager;
import com.lqr.bijiaquan.model.cache.UserCache;
import com.lqr.bijiaquan.ui.base.BaseActivity;
import com.lqr.bijiaquan.ui.base.BasePresenter;
import com.lqr.bijiaquan.util.LogUtils;
import com.lqr.bijiaquan.util.UIUtils;
import com.lqr.bijiaquan.app.MyApp;
import com.lqr.bijiaquan.ui.view.IMainAtView;

import io.rong.imlib.RongIMClient;

public class MainAtPresenter extends BasePresenter<IMainAtView> {

    public MainAtPresenter(BaseActivity context) {
        super(context);
        connect(UserCache.getToken());
        //同步所有用户信息
        DBManager.getInstance().getAllUserInfo();
    }


    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {

        if (UIUtils.getContext().getApplicationInfo().packageName.equals(MyApp.getCurProcessName(UIUtils.getContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    LogUtils.e("--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    LogUtils.e("--onSuccess---" + userid);
                    BroadcastManager.getInstance(mContext).sendBroadcast(AppConst.UPDATE_CONVERSATIONS);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.e("--onError" + errorCode);
                    UIUtils.showToast(UIUtils.getString(R.string.disconnect_server));
                }
            });
        }
    }
}