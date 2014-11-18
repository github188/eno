package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.repository.*;
import com.energicube.eno.monitor.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Log logger = LogFactory.getLog(UserServiceImpl.class);

    @Resource
    private UsersRepository usersRepository;

    @Resource
    private PersonsRepository personsRepository;

    @Resource
    private OkcUserStatusRepository okcUserStatusRepository;

    @Resource
    private GroupMemberRepository groupMemberRepository;

    @Resource
    private AppAuthRepository appAuthRepository;

    @Transactional(readOnly = true)
    public List<UserInfo> findUsersByCategory(String category) {
        List<Object[]> list = usersRepository.findByCategory(category);
        List<UserInfo> userinfos = getUserinfoList(list);
        return userinfos;
    }

    private List<UserInfo> getUserinfoList(List<Object[]> list) {
        List<UserInfo> userinfos = new ArrayList<UserInfo>();
        for (Object[] objs : list) {
            UsersSet usersSet = getUsersSet(objs);
            UserInfo userinfo = new UserInfo();
            userinfo.setCategory(usersSet.getUsers().getCategory());
            userinfo.setLoginid(usersSet.getUsers().getLoginid());
            userinfo.setPwd(usersSet.getUsers().getPwd());
            userinfo.setStatus(usersSet.getUsers().getStatus());
            userinfo.setUserface(usersSet.getPersons().getUserface());
            userinfo.setFullname(usersSet.getPersons().getFirstname()
                    + usersSet.getPersons().getLastname());
            userinfos.add(userinfo);
        }
        return userinfos;
    }

    /**
     * 获取人员登陆相关信息
     */
    private UsersSet getUsersSet(Object[] arrObject) {
        UsersSet usersSet = new UsersSet();
        Object[] obj = arrObject;
        for (Object o : obj) {
            if (o instanceof Persons) {
                usersSet.setPersons((Persons) o);
            }
            if (o instanceof Users) {
                usersSet.setUsers((Users) o);
            }
        }
        return usersSet;
    }

    @Transactional(readOnly = true)
    public Users checkUserLogin(String loginUserid, String loginPwd) {
        Users users = usersRepository.findByLoginidAndPwd(loginUserid, loginPwd);
        return users;
    }

    // 查询未注销的人员名单
    public List<Persons> findPersons() {
        List<Persons> personses = personsRepository.findPersons();
        return personses;


    }

    // 查询未注销的人员名单
    public List<Persons> findPersonsBySta(String status) {
        List<Persons> personses = personsRepository.findPersonsBySta(status);
        return personses;
    }

    // 根据ID查询人员
    public Persons findPersonsById(Long id) {
        Persons personses = personsRepository.findOne(id);
        return personses;
    }

    // 根据USERID查询人员
    public Persons findPersonsByUserid(String userid) {
        Persons persons = personsRepository.findByUserid(userid);
        return persons;
    }

    // 根据ID查询人员
    public List<Users> findByUserid(String userid) {
        List<Users> users = usersRepository.findByUserid(userid);
        return users;
    }

    // 根据ID查询人员
    public Users findUsersByLoginid(String loginid) {
        Users users = new Users();
        List<Users> usersList = usersRepository.findByLoginid(loginid);
        for (int i = 0; i < usersList.size(); i++) {
            if (usersList.get(i).getStatus().equals("0") && usersList.get(i).getType().equals("1"))
                users = usersList.get(i);
        }
        return users;
    }

    // 根据ID查询人员
    public void savePersons(Persons persons) {
        personsRepository.save(persons);
    }

    // 根据ID查询人员
    public void saveUsers(Users users) {
        usersRepository.save(users);
    }

    // 更新状态
    public void saveOkcUserStatus(OkcUserStatus okcUserStatus) {
        okcUserStatusRepository.save(okcUserStatus);
    }

    public List<Users> findAllUsers() {
        List<Users> users = usersRepository.findAll();
        return users;
    }


    public List<GroupMember> findGroupMemberByUserid(String userId) {
        return groupMemberRepository.findByUserid(userId);
    }

    @Override
    public List<AppAuth> findAppauthByGroupname(String groupName) {
        return appAuthRepository.findByGroupname(groupName);
    }
}
