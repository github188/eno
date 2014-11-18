package com.energicube.eno.monitor.model;

import java.util.List;


public class UserGroupTo {

    private static final long serialVersionUID = -5492250996531388470L;

    private long groupId;

    private String groupName;

    private String groupComment = "";

    private String langcode;

    public String getLangcode() {
        return langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    private List appAuthList;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupComment() {
        return groupComment;
    }

    public void setGroupComment(String groupComment) {
        this.groupComment = groupComment;
    }

    public List getAppAuthList() {
        return appAuthList;
    }

    public void setAppAuthList(List appAuthList) {
        this.appAuthList = appAuthList;
    }
}
