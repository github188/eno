package com.energicube.eno.monitor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 */
@Entity
@Table(name = "USER", schema = "dbo")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 6864670836615506994L;
    /**用户Id*/
    private String userid;
    /**用户*/
    private String firstname;
    /**用户*/
    private String lastname;
    /**登录Id*/
    private String loginid;
    /**密码*/
    private String password;
    /**状态*/
    private int status;
    /**错误次数*/
    private int errortimes;
    /**描述*/
    private String description;
    /**性别*/
    private String sex;
    /**出生日期*/
    private Date birthday;
    /**手机号码*/
    private String phone;
    /**入职时间*/
    private Date workdate;
    /**部门*/
    private String department;
    
    public User() {
    }
    
    public User( String userid, String firstname, String lastname, String loginid,
    		String password, int status, int errortimes,
    		String description, String sex, Date birthday, String phone,
    		Date workdate,String department) {
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERUID", unique = true, nullable = false)
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

    @Column(name = "PASSWORD", length = 50)
  	public String getPassword() {
  		return password;
  	}

  	public void setPassword(String password) {
  		this.password = password;
  	}

    @Column(name = "STATUS", length = 50)
  	public int getStatus() {
  		return status;
  	}

  	public void setStatus(int status) {
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

   /* private long useruid;
    private String loginid;
    private String pwd;
    private String memo;
    private String defsite;
    private Boolean querywithdefsite;
    private String status;
    private String type;
    private Boolean issysuser = false;
    private String userid;
    private String clientip;
    private Date lastactivity;*/
   

  /*  public Users(long useruid) {
        this.useruid = useruid;
    }

    public Users(long useruid, String loginid, String pwd, String memo,
                 String defsite, Boolean querywithdefsite, String status,
                 String type, Boolean issysuser, String userid, String clientip,
                 Date lastactivity) {
        this.useruid = useruid;
        this.loginid = loginid;
        this.pwd = pwd;
        this.memo = memo;
        this.defsite = defsite;
        this.querywithdefsite = querywithdefsite;
        this.status = status;
        this.type = type;
        this.issysuser = issysuser;
        this.userid = userid;
        this.clientip = clientip;
        this.lastactivity = lastactivity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERUID", unique = true, nullable = false)
    public long getUseruid() {
        return this.useruid;
    }

    public void setUseruid(long useruid) {
        this.useruid = useruid;
    }

    @NotEmpty
    @Column(name = "LOGINID", length = 50)
    public String getLoginid() {
        return this.loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    @Column(name = "PWD", length = 128)
    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Column(name = "MEMO", length = 256)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "DEFSITE", length = 8)
    public String getDefsite() {
        return this.defsite;
    }

    public void setDefsite(String defsite) {
        this.defsite = defsite;
    }

    @Column(name = "QUERYWITHDEFSITE")
    public Boolean getQuerywithdefsite() {
        return this.querywithdefsite;
    }

    public void setQuerywithdefsite(Boolean querywithdefsite) {
        this.querywithdefsite = querywithdefsite;
    }

    @Column(name = "STATUS", length = 12)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "TYPE", length = 30)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "ISSYSUSER")
    public Boolean getIssysuser() {
        return this.issysuser;
    }

    public void setIssysuser(Boolean issysuser) {
        this.issysuser = issysuser;
    }

    @NotEmpty
    @Column(name = "USERID", length = 50)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "CLIENTIP", length = 100)
    public String getClientip() {
        return this.clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LASTACTIVITY", length = 23)
    public Date getLastactivity() {
        return this.lastactivity;
    }

    public void setLastactivity(Date lastactivity) {
        this.lastactivity = lastactivity;
    }

    @Column(name = "CATEGORY", length = 50)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
*/

}
