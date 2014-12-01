package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Persons;
import com.energicube.eno.monitor.model.Users;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {

//    @Query("select a from Persons a where a.userid in (select b.userid from Users b where b.status='0' and b.type='1') order by a.jobcode,a.userid")
//    public List<Persons> selectUserorderBy() throws DataAccessException;

    public List<Users> findByUserid(String userid);
    
    @Query("select u from Users u where u.userid=?1 ")
    public Users findUserid(String userid);

    @Query("select u from Users u where u.department=?1 ")
    public List<Users> findDepartmentList(String department);
    
    /**
     * 查找指定类别的用户
     *
     * @param category 类别名称
     * @return 用户列表
     */
//    @Query("select p,u from Persons p,Users u where p.userid=u.userid and u.category=?1")
//    public List<Object[]> findByCategory(String category) throws DataAccessException;


    public List<Users> findByLoginid(String loginid);


    @Query("select u from Users u where u.loginid=?1 and u.status != '2'")
    public List<Users> findByLoginidAndStatusBy(String loginid);


    @Query("select u from Users u where u.loginid=?1 and u.password=?2")
    public Users findByLoginidAndPwd(String loginId, String password);
}
