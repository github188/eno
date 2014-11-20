package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.GroupMember;
import com.energicube.eno.monitor.model.OkcGroup;

import java.util.List;


/**
 * 用户操作接口
 */
public interface UserGroupService {

    //查询总用户组
    List<OkcGroup> getuserGroupsTotal();


    List<GroupMember> getGroupMembersByUserid(String userid);

    List<GroupMember> getGroupMembersByGroupname(String groupname);

    //根据组名查询用户组
    public List<OkcGroup> findByGroupName(String groupName);

    public List<OkcGroup> findByLangcode(String langcode);

    public void deleteGroupMembers(GroupMember groupMember);

    public void saveGroupMembers(GroupMember groupMember);

}
