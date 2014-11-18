package com.energicube.eno.common.exception;

/**
 * All rights Reserved, Designed By 刘广路
 * Copyright:    Copyright(C) 2013-2014
 * Company   ZCLF Energy Technologies Inc.
 *
 * @author 刘广路
 * @version 1.0
 * @date 2014/10/19 22:40
 * @Description 自定义异常，用于异常的处理
 */
public class EnergicubeException extends RuntimeException {

    /**
     * 信息
     */
    private String message;

    /**
     * 异常的错误编码
     */
    private String errorCode;

    /**
     * 构造一个新的异常，参数为异常的信息
     * @param message 异常的信息
     */
    public EnergicubeException(String message) {
        super(message);
    }

    /**
     * 构造一个新的异常，message是异常信息，errorCode是异常的编码
     * @param message 异常信息
     * @param errorCode 异常编码
     */
    public EnergicubeException(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "EnergicubeException{" +
                "message='" + message + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
