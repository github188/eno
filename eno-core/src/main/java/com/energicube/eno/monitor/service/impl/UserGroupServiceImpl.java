package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.GroupMember;
import com.energicube.eno.monitor.model.OkcGroup;
import com.energicube.eno.monitor.repository.GroupMemberRepository;
import com.energicube.eno.monitor.repository.OkcGroupRepository;
import com.energicube.eno.monitor.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private OkcGroupRepository okcGroupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public List<OkcGroup> getuserGroupsTotal() {
        List<OkcGroup> userGroupsTotal = okcGroupRepository.findAll();
        return userGroupsTotal;
    }

    public List<GroupMember> getGroupMembersByUserid(String userid) {
        List<GroupMember> groupMembers = groupMemberRepository.findByUserid(userid);
        return groupMembers;
    }

    public List<GroupMember> getGroupMembersByGroupname(String groupname) {
        List<GroupMember> groupMembers = groupMemberRepository.findByGroupname(groupname);
        return groupMembers;
    }

    public List<OkcGroup> findByGroupName(String groupName) {
        List<OkcGroup> userGroups = okcGroupRepository.findByGroupname(groupName);
        return userGroups;
    }

    public List<OkcGroup> findByLangcode(String langcode) {
        List<OkcGroup> userGroups = okcGroupRepository.findByLangcode(langcode);
        return userGroups;
    }

    public void deleteGroupMembers(GroupMember groupMember) {
        groupMemberRepository.delete(groupMember);
    }

    public void saveGroupMembers(GroupMember groupMember) {
        groupMemberRepository.save(groupMember);
    }

}
