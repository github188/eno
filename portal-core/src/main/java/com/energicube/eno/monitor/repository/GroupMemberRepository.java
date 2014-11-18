package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    public List<GroupMember> findByUserid(String userid);

    public List<GroupMember> findByGroupname(String groupname);

}
