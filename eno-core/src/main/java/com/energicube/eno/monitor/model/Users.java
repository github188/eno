package com.energicube.eno.monitor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 */
@Entity
@Table(name = "USERS")
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 6864670836615506994L;
	/** 用户Id */
	private String userid;
	/** 用户 */
	private String firstname;
	/** 用户 */
	private String lastname;
	/** 登录Id */
	private String loginid;
	/** 密码 */
	private String password;
	/** 状态 */
	private String status;
	/** 错误次数 */
	private int errortimes;
	/** 描述 */
	private String description;
	/** 性别 */
	private String sex;
	/** 出生日期 */
	private Date birthday;
	/** 手机号码 */
	private String phone;
	/** 入职时间 */
	private Date workdate;
	/** 部门 */
	private String department;

	public Users() {
	}

	public Users(String userid, String firstname, String lastname,
			String loginid, String password, String status, int errortimes,
			String description, String sex, Date birthday, String phone,
			Date workdate, String department) {
		this.userid = userid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.loginid = loginid;
		this.password = password;
		this.status = status;
		this.errortimes = errortimes;
		this.description = description;
		this.sex = sex;
		this.birthday = birthday;
		this.phone = phone;
		this.workdate = workdate;
		this.department = department;
	}

	@Id
	@Column(name = "USERID", unique = true, nullable = false)
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "FIRSTNAME", length = 50)
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "LASTNAME", length = 50)
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Column(name = "LOGINID", length = 30)
	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	@Column(name = "PASSWORD", length = 150)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "ERRORTIMES", length = 50)
	public int getErrortimes() {
		return errortimes;
	}

	public void setErrortimes(int errortimes) {
		this.errortimes = errortimes;
	}

	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "SEX", length = 5)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY", length = 23)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "WORKDATE", length = 23)
	public Date getWorkdate() {
		return workdate;
	}

	public void setWorkdate(Date workdate) {
		this.workdate = workdate;
	}

	@Column(name = "DEPARTMENT", length = 20)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
