package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.*;

import java.util.List;

/**
 * 用户操作接口
 */
public interface UserService {

    /**
     * 获取指定类别的用户列表
     *
     * @param category 类别代码
     * @return 用户列表
     */
    public List<UserInfo> findUsersByCategory(String category);

    /**
     * 验证登陆用户
     *
     * @param userName 用户名称
     * @param password 用户密码
     * @return 查询到的用户
     */
    public Users checkUserLogin(String userName, String password);

    // 查询未注销的人员
    public List<Persons> findPersons();

    // 根据状态查询人员
    public List<Persons> findPersonsBySta(String status);

    // 根据状态查询人员
    public Persons findPersonsById(Long id);

    // 根据状态查询人员
    public Persons findPersonsByUserid(String userid);

    public Users findUsersByLoginid(String loginid);

    // 根据关联表查询
    public List<Users> findByUserid(String userid);

    // 用户更新或添加
    public void saveUsers(Users users);

    // 人员更新或添加
    public void savePersons(Persons Persons);

    // 更新状态
    public void saveOkcUserStatus(OkcUserStatus okcUserStatus);

    public List<Users> findAllUsers();

    /**
     * 根据用户ID查询组
     *
     * @param userId 用户ID
     * @return
     */
    public List<GroupMember> findGroupMemberByUserid(String userId);

    /**
     * 根据组查询授权菜单
     *
     * @param groupName 组名
     * @return
     */
    public List<AppAuth> findAppauthByGroupname(String groupName);
}
