package com.energicube.eno.monitor.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Entity
@Table(name = "OKCLOGS", schema = "zclfsys")
public class OkcLogs implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private static final Log classLogger = LogFactory.getLog(OkcLogs.class);

    // 增加常量  [ ChengKang 2014-08-02 ]
    public static final String LOG_ERROR = "ERROR";
    public static final String LOG_EXCEPTION = "EXCEPTION";
    public static final String LOG_TAGINFO = "TAGINFO";
    public static final String LOG_COMMAND = "COMMAND";
    public static final String LOG_PREINFO = "PREINFO";
    public static final String USER_UNKNOWN = "UnknownUser";

    private Long logid;
    private String userid;
    private String dated;
    private String logger;
    private String level;
    private String message;

    // 将日志信息写入到数据库表[OKCLOGS]
    public void WriteLogMsg(String Logger, String Level, String Message) {
        // 日志的当前时间
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowtime = sdf.format(cal.getTime());

        String username = "";
        try {
            Subject currentUser = SecurityUtils.getSubject();
            UserInfo userLoginDetails = (UserInfo) currentUser.getPrincipal();
            if (userLoginDetails != null) {
                username = userLoginDetails.getLoginid();
            } else {
                username = OkcLogs.USER_UNKNOWN;
            }
        } catch (Exception e) {
            classLogger.error(e);
        }

        // 给字段赋值  [ ChengKang 2014-08-02 ]
        this.setDated(nowtime);
        this.setUserid(username);
        this.setLogger(Logger);
        this.setLevel(Level);
        this.setMessage(Message);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOGID", unique = true, nullable = false)
    public Long getLogid() {
        return logid;
    }

    public void setLogid(Long logid) {
        this.logid = logid;
    }

    @Column(name = "USERID", length = 30)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "DATED", length = 30)
    public String getDated() {
        return dated;
    }

    public void setDated(String dated) {
        this.dated = dated;
    }

    @Column(name = "LOGGER", length = 200)
    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    @Column(name = "[LEVEL]", length = 10)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
