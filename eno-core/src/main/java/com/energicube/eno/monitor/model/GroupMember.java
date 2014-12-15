package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 权限组中包含用户
 */
@Entity
@Table(name = "GROUPMEMBER")
public class GroupMember implements java.io.Serializable {

    private static final long serialVersionUID = 7758997238913343232L;
    private long groupmemberid;
    private String groupname;
    private String userid;

    public GroupMember() {
    }

    public GroupMember(long groupmemberid) {
        this.groupmemberid = groupmemberid;
    }

    public GroupMember(long groupmemberid, String groupname, String userid) {
        this.groupmemberid = groupmemberid;
        this.groupname = groupname;
        this.userid = userid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUPMEMBERID", unique = true, nullable = false)
    public long getGroupmemberid() {
        return this.groupmemberid;
    }

    public void setGroupmemberid(long groupmemberid) {
        this.groupmemberid = groupmemberid;
    }

    @NotEmpty
    @Column(name = "GROUPNAME", length = 30)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @NotEmpty
    @Column(name = "USERID", length = 50)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
