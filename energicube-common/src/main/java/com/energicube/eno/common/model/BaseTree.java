package com.energicube.eno.common.model;


public abstract class BaseTree implements java.io.Serializable {

	private static final long serialVersionUID = -5492250996531388470L;
	/**
	 * 图标,不指定则为默认图标
	 * */
	private String iconCls;
	/**
	 * 状态 open|close
	 * */
	private State state;
	/**
	 * 是否被选中
	 * */
	private boolean checked;

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
