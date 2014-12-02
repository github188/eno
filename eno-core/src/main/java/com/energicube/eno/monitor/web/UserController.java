package com.energicube.eno.monitor.web;

import com.energicube.eno.common.Constants;
import com.energicube.eno.common.PageProperties;
import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.repository.*;
import com.energicube.eno.monitor.service.DictionaryService;
import com.energicube.eno.monitor.service.UserGroupService;
import com.energicube.eno.monitor.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户登陆处理控制
 *
 * @author CHENPING
 */
@Controller
@RequestMapping("/okcsys")
public class UserController extends BaseController {

	private static final Log logger = LogFactory.getLog(UserController.class);
	
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ShiftsRepository shiftsRepository;
    @Autowired
    private ShiftworkRepository shiftworkRepository;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private AppAuthRepository appAuthRepository;
    @Autowired
    private OkcMenuRepository okcMenuRepository;

    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
    private final static Format dateFormat = new SimpleDateFormat("MMddHHmmssS");
    private final static NumberFormat numberFormat = new DecimalFormat("0000");
    private static int seq = 0;
    private static final int MAX = 9999;

    
    
    /**
     * 用户管理用户查询
     * 
     * @return  userList List<Users>
     */
    @RequestMapping(value = "/findUserList", method = RequestMethod.GET)
    @ResponseBody
    public List<Users> findUserList() {
    	List<Users> userList = new ArrayList<Users>();
    	userList = userService.findAllUsers();
    	return userList;
    }
    
    /**
     * 用户管理部门用户查询
     * 
     * @return  userList List<Users>
     */
    @RequestMapping(value = "/finddepartmentList", method = RequestMethod.GET)
    @ResponseBody
    public List<Users> findDepartmentList(HttpServletRequest request) {
		String department=null;
		try {
			department = new String(request.getParameter("department").getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		 List<Users> userlist = userService.findDepartmentList(department);
    	return userlist;
    }
    
    /**
     * 用户管理用户查询
     * 
     * @return  Users users
     */
    @RequestMapping(value = "/findUserid", method = RequestMethod.GET)
    @ResponseBody
    public Users findByUserid(HttpServletRequest request) {
		String userid = request.getParameter("userid");
		Users users = userService.findUserid(userid);
    	return users;
    }
    
    /**
     * 用户管理用户密码修改
     *
     * @param user
     */
    @RequestMapping(value = "/updateUsersPassword", method = RequestMethod.GET)
    @ResponseBody
    public String updateUsersPassword(HttpServletRequest request) {
    	//用户信息
    	Users user= new Users();
    	//存放userid
    	user.setUserid(request.getParameter("useruid"));
    	user.setPassword(Constants.getMdPassword(request.getParameter("passwordnew")));
    	userService.updateUsersPassword(user);
    	return "success";
    }
    
    /**
     * 用户管理用户新建&修改
     *
     * @param user
     */
    @RequestMapping(value = "/saveUser", method = RequestMethod.GET)
    @ResponseBody
    public String saveUser(HttpServletRequest request) {
    	String flg="";
    	//定义日期格式
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	//用户信息
    	Users user= new Users();
    	//存放userid
    	String userid=request.getParameter("useruid");
    	//判断userid是否为空， 空：新增用户，不为空：修改用户
    	if(userid==null || "".equals(userid)){
    		//自动生成userid
    		user.setUserid(UUID.randomUUID().toString());
    		flg="save";
    	}else{
    		user.setUserid(userid);
    		flg="update";
    	}
    	user.setFirstname(request.getParameter("firstname"));
    	user.setLoginid(request.getParameter("loginid"));
    	user.setPassword(Constants.getMdPassword(request.getParameter("password")));
    	user.setSex(request.getParameter("sex"));
    	Date date;
		try {
			date = sdf.parse(request.getParameter("birthday"));
			user.setBirthday(date);
			date = sdf.parse(request.getParameter("workdate"));
			user.setWorkdate(date);
		} catch (ParseException e) {
			logger.error(e);
		}
    	user.setPhone(request.getParameter("phone"));
    	String department;
		try {
			department = new String(request.getParameter("department").getBytes("ISO-8859-1"), "UTF-8");
			user.setDepartment(department);
		} catch (UnsupportedEncodingException e) {
			 logger.error(e);
		}
    	userService.saveUsers(user);
    	return flg;
    }
    
  
    /**
     * 用户管理用户删除
     *
     * @param user
     */
    @RequestMapping(value = "/delUsers", method = RequestMethod.GET)
    @ResponseBody
    public String delUsers(HttpServletRequest request){
    	userService.delUsers(request.getParameter("userid"));
    	return "del";
    }
    
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String inituserList(Model model) {
        List<Persons> Personses = userService.findPersons();
        String sex = "";
        String employeetype = "";
        for (int i = 0; i < Personses.size(); i++) {
            Persons persons = Personses.get(i);
            sex = persons.getSex();
            employeetype = persons.getEmployeetype();
            if (sex.equals("0")) {
                persons.setSex("男");
            }
            if (sex.equals("1")) {
                persons.setSex("女");
            }
            if (employeetype.equals("0")) {
                persons.setEmployeetype("全职员工");
            }
            if (employeetype.equals("1")) {
                persons.setEmployeetype("兼职合同工");
            }
            if (employeetype.equals("2")) {
                persons.setEmployeetype("临时合同工");
            }
            if (employeetype.equals("3")) {
                persons.setEmployeetype("外包人员");
            }
            Personses.set(i, persons);
        }
        List<Persons> PersonsPageList = new ArrayList<Persons>();
        int pageSize = PageProperties.User_Page;
        if (Personses.size() <= pageSize) {
            PersonsPageList = Personses;
        } else {
            for (int k = 0; k < pageSize; k++) {
                PersonsPageList.add(Personses.get(k));
            }
        }
        int endIndex = 0;
        if (Personses.size() > 0) {
            endIndex = Personses.size() / PageProperties.User_Page;
            if (Personses.size() % PageProperties.User_Page != 0) {
                endIndex = endIndex + 1;
            }
        }
        model.addAttribute("endIndex", endIndex);
        model.addAttribute("userStaIndex", "3");
        model.addAttribute("Persons", PersonsPageList);
        return "user/userList";
    }

    // 分页
    @RequestMapping(value = "/user/selectUsersListView", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Persons> selectUsersListView(Model model, HttpServletRequest request,
                                      HttpServletResponse response) {
        String sta = request.getParameter("sta");
        List<Persons> personses = new ArrayList<Persons>();
        if (sta.equals("3"))
            personses = userService.findPersons();
        else {
            personses = userService.findPersonsBySta(sta);
        }
        String sex = "";
        String employeetype = "";
        for (int i = 0; i < personses.size(); i++) {
            Persons Persons = personses.get(i);
            sex = Persons.getSex();
            employeetype = Persons.getEmployeetype();
            if (sex.equals("0")) {
                Persons.setSex("男");
            }
            if (sex.equals("1")) {
                Persons.setSex("女");
            }
            if (employeetype.equals("0")) {
                Persons.setEmployeetype("全职员工");
            }
            if (employeetype.equals("1")) {
                Persons.setEmployeetype("兼职合同工");
            }
            if (employeetype.equals("2")) {
                Persons.setEmployeetype("临时合同工");
            }
            if (employeetype.equals("3")) {
                Persons.setEmployeetype("外包人员");
            }
            personses.set(i, Persons);
        }
        List<Persons> PersonsPageList = new ArrayList<Persons>();
        int pageSize = PageProperties.User_Page;
        int pageNumber = Integer.valueOf(request.getParameter("userpagef"));
        if (personses.size() <= pageSize) {
            PersonsPageList = personses;
        } else {
            for (int k = PageProperties.User_Page * (pageNumber - 1); k < PageProperties.User_Page * (pageNumber - 1)
                    + pageSize
                    && k < personses.size(); k++) {
                PersonsPageList.add(personses.get(k));
            }
        }
        int endIndex = 0;
        if (personses.size() > 0) {
            endIndex = personses.size() / PageProperties.User_Page;
            if (personses.size() % PageProperties.User_Page != 0) {
                endIndex = endIndex + 1;
            }
        }
        return PersonsPageList;
    }

    /**
     * 角色权限
     *
     * @return
     */
    @RequestMapping(value = "/user/roleList", method = RequestMethod.GET)
    public String initroleList(Model model) {
        return "user/roleList";
    }

    // 根据状态查询
    @RequestMapping(value = "/user/selectBystatusx", method = RequestMethod.GET)
    public String selectBystatusx(HttpServletRequest request,
                                  HttpServletResponse response, Model model) {
        List<Persons> Personses = new ArrayList<Persons>();
        try {
            String status = request.getParameter("sta");
            if (status.equals("3")) {
                Personses = userService.findPersons();
            } else {
                Personses = userService.findPersonsBySta(status);
            }
            String sex = "";
            String employeetype = "";
            for (int i = 0; i < Personses.size(); i++) {
                Persons Persons = Personses.get(i);
                sex = Persons.getSex();
                employeetype = Persons.getEmployeetype();
                if (sex.equals("0")) {
                    Persons.setSex("男");
                }
                if (sex.equals("1")) {
                    Persons.setSex("女");
                }
                if (employeetype.equals("0")) {
                    Persons.setEmployeetype("全职员工");
                }
                if (employeetype.equals("1")) {
                    Persons.setEmployeetype("兼职合同工");
                }
                if (employeetype.equals("2")) {
                    Persons.setEmployeetype("临时合同工");
                }
                if (employeetype.equals("3")) {
                    Persons.setEmployeetype("外包人员");
                }
                Personses.set(i, Persons);
            }
            List<Persons> PersonsPageList = new ArrayList<Persons>();
            int pageSize = PageProperties.User_Page;
            if (Personses.size() <= pageSize) {
                PersonsPageList = Personses;
            } else {
                for (int k = 0; k < pageSize; k++) {
                    PersonsPageList.add(Personses.get(k));
                }
            }
            int endIndex = 0;
            if (Personses.size() > 0) {
                endIndex = Personses.size() / PageProperties.User_Page;
                if (Personses.size() % PageProperties.User_Page != 0) {
                    endIndex = endIndex + 1;
                }
            }
            model.addAttribute("endIndex", endIndex);
            model.addAttribute("Persons", PersonsPageList);
            model.addAttribute("userStaIndex", status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user/userList";
    }

    /**
     * 根据Id查询
     *
     * @return
     */
    @RequestMapping(value = "/user/PersonsUpdate", method = RequestMethod.GET)
    public
    @ResponseBody
    List PersonsUpdate(HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        Persons Persons = new Persons();
        List<Users> users = new ArrayList<Users>();
        List list = new ArrayList();
        try {
            String Personsid = request.getParameter("Personsid");
            Persons = userService.findPersonsById(Long.valueOf(Personsid));
            Date birthdate = Persons.getBirthdate();
            Date hiredate = Persons.getHiredate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            birthdate = format.parse(format.format(birthdate));
            hiredate = format.parse(format.format(hiredate));
            Persons.setBirthdate(birthdate);
            Persons.setHiredate(hiredate);
            users = userService.findByUserid(Persons.getUserid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(0, Persons);
        if (users.size() == 0) {
            Users usersMap = new Users();
            list.add(1, usersMap);
        } else {
            list.add(1, users.get(0));
        }
        return list;
    }

    /**
     * 根据Id查询
     *
     * @return
     */
    @RequestMapping(value = "/user/PersonsDelete", method = RequestMethod.GET)
    public String PersonsDelete(HttpServletRequest request,
                                HttpServletResponse response, Model model) {
        Persons PersonsInit = new Persons();
        String sta = request.getParameter("sta");
        List<Users> usersList = new ArrayList<Users>();
        try {
            String Personsid = request.getParameter("Personsid");
            PersonsInit = userService.findPersonsById(Long.valueOf(Personsid));
            usersList = userService.findByUserid(PersonsInit.getUserid());
            Users users = usersList.get(0);
            users.setStatus("2");
            userService.saveUsers(users);
            List<GroupMember> groupMembers = groupMemberRepository
                    .findByUserid(users.getUserid());
            for (int i = 0; i < groupMembers.size(); i++) {
                groupMemberRepository.delete(groupMembers.get(i));
            }
            OkcUserStatus okcUserStatus = new OkcUserStatus();
            okcUserStatus.setChangeby(PersonsInit.getFirstname()
                    + PersonsInit.getLastname());
            Date changedate = new Date();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            okcUserStatus.setChangedate(sdf1.parse(sdf1.format(changedate)));
            okcUserStatus.setStatus("DELETED");
            okcUserStatus.setUserid(PersonsInit.getUserid());
            userService.saveOkcUserStatus(okcUserStatus);
            List<Shifts> shiftsList = shiftsRepository.findByName(PersonsInit
                    .getUserid());
            for (int i = 0; i < shiftsList.size(); i++) {
                shiftsRepository.delete(shiftsList.get(i));
            }
            List<Shiftwork> shiftsstartList = shiftworkRepository
                    .findByShiftstartingbyid(Long.valueOf(PersonsInit
                            .getUserid()));
            List<Shiftwork> shiftsendList = shiftworkRepository
                    .findByShiftendingbyid(Long.valueOf(PersonsInit.getUserid()));
            if (shiftsstartList.size() > 0) {
                for (int i = 0; i < shiftsstartList.size(); i++) {
                    Shiftwork shiftsstart = shiftsstartList.get(i);
                    if (shiftsstart.getShiftendingbyid() == null)
                        shiftworkRepository.delete(shiftsstart);
                    else {
                        shiftsstart.setShiftstartingby(null);
                        shiftsstart.setShiftstartingbyid(null);
                        shiftworkRepository.save(shiftsstart);
                    }
                }
            }
            if (shiftsendList.size() > 0) {
                for (int j = 0; j < shiftsendList.size(); j++) {
                    Shiftwork shiftsend = shiftsendList.get(j);
                    if (shiftsend.getShiftstartingbyid() == null)
                        shiftworkRepository.delete(shiftsend);
                    else {
                        shiftsend.setShiftendingby(null);
                        shiftsend.setShiftendingbyid(null);
                        shiftworkRepository.save(shiftsend);
                    }
                }
            }
            List<Persons> personses = new ArrayList<Persons>();
            if (sta.equals("3")) {
                personses = userService.findPersons();
            } else {
                personses = userService.findPersonsBySta(sta);
            }
            String sex = "";
            String employeetype = "";
            for (int i = 0; i < personses.size(); i++) {
                Persons Persons = personses.get(i);
                sex = Persons.getSex();
                employeetype = Persons.getEmployeetype();
                if (sex.equals("0")) {
                    Persons.setSex("男");
                }
                if (sex.equals("1")) {
                    Persons.setSex("女");
                }
                if (employeetype.equals("0")) {
                    Persons.setEmployeetype("全职员工");
                }
                if (employeetype.equals("1")) {
                    Persons.setEmployeetype("兼职合同工");
                }
                if (employeetype.equals("2")) {
                    Persons.setEmployeetype("临时合同工");
                }
                if (employeetype.equals("3")) {
                    Persons.setEmployeetype("外包人员");
                }
                personses.set(i, Persons);
            }
            model.addAttribute("persons", personses);
            model.addAttribute("userStaIndex", sta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:";
    }

    /**
     * 根据Id查询
     *
     * @return
     */
    @RequestMapping(value = "/user/changeUser", method = RequestMethod.GET)
    public
    @ResponseBody
    List changeUser(HttpServletRequest request, HttpServletResponse response,
                    Model model) {
        Persons Persons = new Persons();
        List<Users> users = new ArrayList<Users>();
        List list = new ArrayList();
        List<OkcGroup> userGroupsTotalZs = new ArrayList<OkcGroup>();
        List<OkcGroup> userGroupsTotal = new ArrayList<OkcGroup>();
        List<OkcGroup> userGroupsUserbe = new ArrayList<OkcGroup>();
        List<OkcGroup> userGroupsUser = new ArrayList<OkcGroup>();
        List<GroupMember> groupMembers = new ArrayList<GroupMember>();
        ListTotal listTotal = new ListTotal();
        try {
            userGroupsTotalZs = userGroupService.getuserGroupsTotal();
            if (userGroupsTotalZs.size() > 0) {
                String Personsid = request.getParameter("Personsid");
                Persons = userService.findPersonsById(Long.valueOf(Personsid));
                users = userService.findByUserid(Persons.getUserid());
                if (users.get(0).getLoginid().equals("-1")) {
                    listTotal.setStat("false");
                } else {
                    listTotal.setStat("true");
//                    userGroupsTotal = userGroupService.findByLangcode(users
//                            .get(0).getClientip());
                    groupMembers = userGroupService
                            .getGroupMembersByUserid(users.get(0).getUserid());
                    for (int j = 0; j < groupMembers.size(); j++) {
                        OkcGroup userGroup = userGroupService.findByGroupName(
                                groupMembers.get(j).getGroupname()).get(0);
                        userGroupsUserbe.add(userGroup);
                    }
                    if (userGroupsUserbe.size() > 0) {
                        for (int i = 0, n = userGroupsUserbe.size(); i < n; i++) {
                            String id = userGroupsUserbe.get(i).getUgroupid()
                                    + "";
                            for (int j = userGroupsTotal.size() - 1; j >= 0; j--) {
                                OkcGroup detail = userGroupsTotal.get(j);
                                if (id.endsWith(detail.getUgroupid() + "")) {
                                    userGroupsTotal.remove(detail);
                                }
                            }
                        }
                    }
                }
            }
            listTotal.setUserGroupsTotal(userGroupsTotal);
            listTotal.setUserGroupsUser(userGroupsUserbe);
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.add(0, listTotal);
        return list;
    }

    // 用户赋予角色
    @RequestMapping(value = "/user/insertgroupMember", method = RequestMethod.GET)
    public
    @ResponseBody
    List insertgroupMember(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        List list = new ArrayList();
        try {
            List<Users> users = new ArrayList<Users>();
            List<GroupMember> groupMembers = new ArrayList<GroupMember>();
            Persons Persons = new Persons();
            String Personsid = request.getParameter("Personsid");
            String groupnames = new String(request.getParameter("groupnames")
                    .getBytes("ISO-8859-1"), "utf-8"); // java转码
            Persons = userService.findPersonsById(Long.valueOf(Personsid));
            users = userService.findByUserid(Persons.getUserid());
            groupMembers = userGroupService.getGroupMembersByUserid(users
                    .get(0).getUserid());
            for (int i = 0; i < groupMembers.size(); i++) {
                userGroupService.deleteGroupMembers(groupMembers.get(i));
            }
            if (!groupnames.equals("")) {
                String groupname[] = groupnames.split(";");
                for (int m = 0; m < groupname.length; m++) {
                    if (!groupname[m].equals("")) {
                        GroupMember groupMember = new GroupMember();
                        groupMember.setGroupname(groupname[m]);
                        groupMember.setUserid(users.get(0).getUserid());
                        userGroupService.saveGroupMembers(groupMember);
                    }
                }
            }
            list.add(0, "success");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    /**
     * 验证登录名是否重复
     *
     * @return
     */
    @RequestMapping(value = "/user/checkLoginid", method = RequestMethod.GET)
    public
    @ResponseBody
    String checkLoginid(HttpServletRequest request,
                        HttpServletResponse response, Model model) {
        String str = "cg";
        try {
            String loginid = new String(request.getParameter("loginid")
                    .getBytes("ISO-8859-1"), "utf-8"); // java转码
            String Personsid = request.getParameter("Personsid");
            if (usersRepository.findByLoginidAndStatusBy(loginid).size() > 0) {
                Users users = usersRepository.findByLoginidAndStatusBy(loginid)
                        .get(0);
                Persons Persons = userService.findPersonsByUserid(users
                        .getUserid());
                if (Personsid.equals("")) {
                    str = "sb";
                } else {
                    if (!Personsid.equals(String.valueOf(Persons.getPersonid()))) {
                        str = "sb";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 添加人员
     *
     * @return
     */
    @RequestMapping(value = "/user/doInsertPersons", method = RequestMethod.GET)
    public String doInsertPersons(HttpServletRequest request,
                                  HttpServletResponse response, Model model) {
        try {
            String generateSequenceId = this.generateSequenceNo();
            String type = request.getParameter("type");
            String roleSelect = request.getParameter("roleSelect");
            String Personsid = request.getParameter("Personsid");
            String status = request.getParameter("statusx");
            String loginid = request.getParameter("loginid");
            String pwd = request.getParameter("pwd");
            String firstname = new String(request.getParameter("firstname")
                    .getBytes("ISO-8859-1"), "utf-8"); // java转码
            String department = new String(request.getParameter("department")
                    .getBytes("ISO-8859-1"), "utf-8"); // java转码
            String sex = request.getParameter("sex");
            String birthdate = request.getParameter("birthdate");
            String hiredate = request.getParameter("hiredate");
            String employeetype = request.getParameter("employeetype");
            String jobcode = new String(request.getParameter("jobcode")
                    .getBytes("ISO-8859-1"), "utf-8"); // java转码
            String lastname = new String(request.getParameter("lastname")
                    .getBytes("ISO-8859-1"), "utf-8");
            String workemail = request.getParameter("workemail");
            String workphone = request.getParameter("workphone");
            String bgztvalue = request.getParameter("bgzt");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Persons Persons = new Persons();
            Persons.setUserid(generateSequenceId);
            if (!Personsid.equals("")) {
                Persons = userService.findPersonsById(Long.valueOf(Personsid));
            }
            Persons.setFirstname(firstname);
            Persons.setDepartment(department);
            Persons.setSex(sex);
            Persons.setBirthdate(sdf.parse(birthdate));
            Persons.setHiredate(sdf.parse(hiredate));
            Persons.setEmployeetype(employeetype);
            Persons.setJobcode(jobcode);
            Persons.setLastname(lastname);
            Persons.setWorkemail(workemail);
            Persons.setWorkphone(workphone);
            Users users = new Users();
            if (type.equals("0")) {
                if (!Personsid.equals("")) {
                    List<Users> userslist = userService.findByUserid(Persons
                            .getUserid());
                    if (userslist.size() > 0)
                        users = userslist.get(0);
                }
//                users.setType(type);
                users.setUserid(Persons.getUserid());
                users.setLoginid("-1");
                users.setPassword(Constants.getMdPassword(pwd));
                users.setStatus(status);
                userService.saveUsers(users);

            } else {
                if (!Personsid.equals("")) {
                    List<Users> userslist = userService.findByUserid(Persons
                            .getUserid());
                    if (userslist.size() > 0)
                        users = userslist.get(0);
                }
                if (users.getLoginid() == null || users.getLoginid() == "null"
                        || users.getLoginid() == "") {
                    users.setPassword(Constants.getMdPassword(pwd));
                } else {
                    if (!users.getPassword().equals(pwd)) {
                        users.setPassword(Constants.getMdPassword(pwd));
                    }
                }
//                users.setType(type);
                users.setUserid(Persons.getUserid());
                users.setLoginid(loginid);
                users.setStatus(status);
//                users.setClientip(roleSelect);
                userService.saveUsers(users);
            }
            userService.savePersons(Persons);
            if (bgztvalue.equals("") && type.equals("0")) {
            } else {
                if (type.equals("0"))
                    status = "2";
                if (!bgztvalue.equals(status)) {
                    OkcUserStatus okcUserStatus = new OkcUserStatus();
                    okcUserStatus.setChangeby(Persons.getFirstname()
                            + Persons.getLastname());
                    Date changedate = new Date();
                    okcUserStatus.setChangedate(sdf1.parse(sdf1
                            .format(changedate)));
                    if (status.equals("0")) {
                        status = "ACTIVE";
                    }
                    if (status.equals("1")) {
                        status = "INACTIVE";
                    }
                    if (status.equals("2")) {
                        status = "DELETED";
                    }
                    okcUserStatus.setStatus(status);
                    okcUserStatus.setUserid(Persons.getUserid());
                    userService.saveOkcUserStatus(okcUserStatus);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Persons> Persons = userService.findPersons();
        model.addAttribute("Personss", Persons);
        return "redirect:";
    }

    /**
     * 时间格式生成序列
     *
     * @return String
     */
    public String generateSequenceNo() {
        Calendar rightNow = Calendar.getInstance();
        StringBuffer sb = new StringBuffer();
        dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
        numberFormat.format(seq, sb, HELPER_POSITION);
        if (seq == MAX) {
            seq = 0;
        } else {
            seq++;
        }

        return sb.toString();
    }

    /**
     * 查看用户首选项信心
     *
     * @return
     */
    @RequestMapping(value = "/user/shUserDefaults", method = RequestMethod.GET)
    public
    @ResponseBody
    Persons shUserDefaults(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        String loginId = request.getParameter("loginId");
        Users Users = userService.findUsersByLoginid(loginId);
        Persons Persons = userService.findPersonsByUserid(Users.getUserid());
        return Persons;
    }

    /**
     * 查看用户权限
     *
     * @return
     */
    @RequestMapping(value = "/user/showAuthority", method = RequestMethod.GET)
    public
    @ResponseBody
    List<OkcMenu> showAuthority(HttpServletRequest request,
                                HttpServletResponse response, Model model) {
        String Personsid = request.getParameter("Personsid");
        List<OkcMenu> okcMenus = new ArrayList<OkcMenu>();
        String userid = userService.findPersonsById(Long.valueOf(Personsid))
                .getUserid();
        if (groupMemberRepository.findByUserid(userid).size() > 0) {
            GroupMember groupMember = groupMemberRepository
                    .findByUserid(userid).get(0);
            List<AppAuth> appAuthList = appAuthRepository
                    .findByGroupname(groupMember.getGroupname());
            for (int i = 0; i < appAuthList.size(); i++) {
                AppAuth appAuth = appAuthList.get(i);
                OkcMenu okcMenu = okcMenuRepository.findByElementvalue(
                        appAuth.getAppcode()).get(0);
                okcMenus.add(okcMenu);
            }
        }
        return okcMenus;
    }

    /**
     * 用户类别
     */
//    @ModelAttribute("userclasses")
//    public List<OkcDMAlphaNum> GetUserClass() {
//        // 用户类型,取字典表
//        return dictionaryService.findALNDictionaryByDictid("USERCATEGORY");
//    }


}
