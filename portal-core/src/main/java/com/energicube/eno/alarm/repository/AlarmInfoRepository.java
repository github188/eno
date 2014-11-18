package com.energicube.eno.alarm.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AlarmInfoRepository {

	@PersistenceContext
	public EntityManager entityManager;

	/**
	 * 获取首页报警数据
	 * 
	 * @author zouzhixiang
	 * @date 2014-09-04 add
	 * @param starttime
	 * @param endtime
	 * @return
	 * @throws org.springframework.dao.DataAccessException
	 */
	public List<Object> findAlarmDataForIndex(String starttime, String endtime) throws DataAccessException {

		String sqlString = "select a.groupname,a.almpriority, COUNT(*) almlogid from UC_alarmactive a where a.almpriority < 800 and a.almt >= '" + starttime + " 00:00:00' and a.almt < '" + endtime + " 00:00:00' group by a.groupid,a.groupname,a.almpriority order by a.groupid";

		Query query = entityManager.createNativeQuery(sqlString);

		@SuppressWarnings("unchecked")
		List<Object> result = query.getResultList();

		return result;
	}

	/**
	 * 根据时间和报警级别找出对应的个数
	 *
	 * @param starttime
	 * @param endtime
	 * @param almpriority1
	 * @param almpriority2
	 * @return
	 * @throws org.springframework.dao.DataAccessException
	 */
	public List<Object> findCountByTimeAndAlmpriority(String groupname, String starttime, String endtime, int almpriority1, int almpriority2) throws DataAccessException {
		
		String sqlString = "select a.almpriority from Uc_Alarmlog a where 1=1 ";
		
		if (groupname != null && StringUtils.hasLength(groupname) && !"groupname".equals(groupname)) {
			sqlString += " and a.groupname = '" + groupname + "' ";
		}
		sqlString += "and a.almt >= '" + starttime + "' and a.almt < '" + endtime + "' and (a.almpriority = " + almpriority1 + " or a.almpriority = " + almpriority2 + ")";
		
		Query query = entityManager.createNativeQuery(sqlString);
		
		@SuppressWarnings("unchecked")
		List<Object> result = query.getResultList();
		
		return result;
	}


    /**
     * 根据时间和报警级别找出对应的个数
     *
     * @param year
     * @param month
     * @param day
     * @return
     * @throws org.springframework.dao.DataAccessException
     */
    public List<Object> findCountByTimeAndAlmpriorityGroup(String groupname, String year, String month,String day) throws DataAccessException {

        String sqlString = "select  a.almpriority as grade,DatePart(HOUR,a.almt) as alarmTime,COUNT(*) as alarmNum from Uc_Alarmlog as a where 1=1 ";

        if (groupname != null && StringUtils.hasLength(groupname) && !"groupname".equals(groupname)) {
            sqlString += " and a.groupname = '" + groupname + "' ";
        }
//        sqlString += "and a.almt >= '" + starttime + "' and a.almt < '" + endtime + "' and (a.almpriority = " + almpriority1 + " or a.almpriority = " + almpriority2 + ") group by  a.almpriority,DatePart(HOUR,a.almt)";
        sqlString += " and YEAR(a.almt) = "+year+" and MONTH(a.almt)="+month+" and DAY(a.almt)="+day+" group by  a.almpriority,DatePart(HOUR,a.almt)";

        Query query = entityManager.createNativeQuery(sqlString);

        @SuppressWarnings("unchecked")
        List<Object> result = query.getResultList();

        return result;
    }


}
