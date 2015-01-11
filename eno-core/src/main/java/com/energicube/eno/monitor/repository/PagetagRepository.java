package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Pagetag;
import com.energicube.eno.monitor.model.Tags;
import com.energicube.eno.monitor.model.UcPassengerFlowDetail;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 页面设备点数据操作接口
 */
public interface PagetagRepository extends JpaRepository<Pagetag, Long> {

    /**
     * 查找指定页面的TAG数据
     *
     * @param layoutid 布局ID
     * @return Tag List Collection
     * @author CHENPING
     */
	@Query("select a from Pagetag a where a.layoutid = ?1 order by a.orderindex asc")
    public List<Pagetag> findByLayoutid(String layoutid) throws DataAccessException;

	/**
	 * 根据classstructureid查询指定页面的对应的数据
	 * @param layoutid
	 * @param classid
	 * @return
	 * @throws DataAccessException
	 */
	@Query("select a from Pagetag a where a.layoutid = ?1 and a.classstructureid = ?2 order by a.orderindex asc")
	public List<Pagetag> findByLayoutidAndClassId(String layoutid, String classid) throws DataAccessException;

    /**
     * 根据主键id查询对应的记录
     *
     * @param pagetagid
     * @return
     * @throws DataAccessException
     */
    @Query("select a from Pagetag a where a.pagetagid = ?1")
    public List<Pagetag> findByPagetagid(long pagetagid) throws DataAccessException;

    @Transactional
    @Modifying
    @Query("update Pagetag a set a.left = ?1 , a.top = ?2 where a.tagid = ?3")
    public void updatePagetag(String left, String top, String tagid);

    @Transactional
    @Modifying
    @Query("update Pagetag a set a.controlid = ?1 ,a.measureunitid = ?2,a.tagtype=?3 where a.layoutid = ?4")
    public void batchPagetagProps(String controlid, String measureunitid, int tagtype, String layoutid);

    @Query("select count(tag) from Pagetag tag where tag.controlid=?1 or tag.controlid2=?1 or tag.controlid3=?1")
    public long countByControlids(String controlid) throws DataAccessException;

    public List<Pagetag> findByExpressionsNotNull();

    public List<Pagetag> findByLayoutidAndExpressionsNotNull(String layoutid);

    @Transactional
    @Modifying
    @Query("update Pagetag a set a.showrange = ?1, a.controlid = ?2, a.controlid2 = ?3, a.controlid3 = ?4, a.tagtype = ?5, a.pagetagtype = ?6, a.tagval = ?7 where a.parentid = ?8")
    public void updateValue(String showrange, String controlid, String controlid2, String controlid3, int tagtype, int pagetagtype, String tagval, String parentid) throws DataAccessException;

    @Transactional
    @Modifying
    @Query("update Pagetag a set a.classstructureid = ?1 where a.tagid = ?2")
    public void updateParentClassId(String classstructureid, String parentid) throws DataAccessException;

    @Transactional
    @Query("select a from UcPassengerFlowDetail a ")
    public List<UcPassengerFlowDetail> findUcPassengerFlowDetailAll();
    
    @Query("select a from Pagetag a where a.layoutid = ?1 and a.parentid = '' order by orderindex asc")
    public List<Pagetag> findParentTagByLayoutid(String layoutid) throws DataAccessException;

    @Query("select a from Tags a ")
	public List<Tags> findTagsAll();
    
    /**
     * 根据tagname求对应的tagid
     * 
     * @param tagname
     * @return
     */
    @Query("select a from Tags a where valuetag = ?1")
    public List<Tags> findTagsByTagName(String tagname);
}
