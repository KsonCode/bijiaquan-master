package com.lqr.bijiaquan.model.exception;

import com.lqr.bijiaquan.R;
import com.lqr.bijiaquan.util.UIUtils;

/**
 * @创建者 CSDN_LQR
 * @描述 服务器异常
 */
public class ServerException extends Exception {

    public ServerException(int errorCode) {
        this(UIUtils.getString(R.string.error_code) + errorCode);
    }

    public ServerException(String message) {
        super(message);
    }

}
