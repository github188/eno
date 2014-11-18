package com.energicube.eno.common.feedbackmsg;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:    Copyright(C) 2013-2014
 * Company   ZCLF Energy Technologies Inc.
 *
 * @author 刘广路
 * @version 1.0
 * @date 2014/10/19 22:43
 * @Description  系统信息的反馈，用于与前端界面的信息交互，友好的提示信息
 */
public class SystemMessage implements Serializable{

    /**
     * 信息
     */
    private String msg;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 详细信息
     */
    private String detailMsg;

    /**
     * 成功
     */
    private boolean success;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "SystemMessage{" +
                "msg='" + msg + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", detailMsg='" + detailMsg + '\'' +
                ", success=" + success +
                '}';
    }
}
