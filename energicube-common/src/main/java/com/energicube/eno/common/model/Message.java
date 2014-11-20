package com.energicube.eno.common.model;

import org.springframework.validation.BindingResult;

public class Message {
	private boolean success;
	private String msg;

    private String patternId;

	public Message() {
	}
	public Message(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}
	
	
	public Message(boolean success, BindingResult result) {
		this.success = success;
		this.msg = result.toString();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

    public String getPatternId() {
        return patternId;
    }

    public void setPatternId(String patternId) {
        this.patternId = patternId;
    }
}
