package com.energicube.eno.common.model;

import java.util.List;


public class ListTotal {
	private static final long serialVersionUID = -5606895454250797400L;
	
	private List  userGroupsTotal;
	private List  userGroupsUser;
	private String  stat;

	public List getUserGroupsUser() {
		return userGroupsUser;
	}

	public void setUserGroupsUser(List userGroupsUser) {
		this.userGroupsUser = userGroupsUser;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public List getUserGroupsTotal() {
		return userGroupsTotal;
	}

	public void setUserGroupsTotal(List userGroupsTotal) {
		this.userGroupsTotal = userGroupsTotal;
	}
}
