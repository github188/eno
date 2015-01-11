package com.energicube.eno.monitor.repository.jpa;

import com.energicube.eno.asset.model.Asset;
import com.energicube.eno.asset.model.ClassSpec;
import com.energicube.eno.asset.model.ClassStructure;
import com.energicube.eno.asset.model.Locations;
import com.energicube.eno.monitor.model.ClassSpecTemp;
import com.energicube.eno.monitor.service.impl.SubSystemServiceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AssetMeasurementRepository {
	
	private final static Log logger = LogFactory.getLog(AssetMeasurementRepository.class);
	
    @PersistenceContext
    private EntityManager entityManager;

    public List<Object> findByTag(String tagid, String tagvalue)
            throws DataAccessException {

        String sqlString = "SELECT LOCATIONS.DESCRIPTION as locationname, ASSETATTRIBUTE.DESCRIPTION as assetname, ASSETATTRDIALECT.DESCRIPTION as description FROM MEASUREMENT INNER JOIN MEASUREPOINT ON MEASUREMENT.ASSETNUM = MEASUREPOINT.ASSETNUM AND MEASUREMENT.VALUETAG = '"
                + tagid
                + "'  INNER JOIN LOCATIONS ON MEASUREPOINT.LOCATION = LOCATIONS.LOCATION INNER JOIN MEASURESPEC ON MEASUREMENT.MEASURESPECID = MEASURESPEC.MEASURESPECID INNER JOIN ASSETATTRIBUTE ON MEASURESPEC.ASSETATTRID = ASSETATTRIBUTE.ASSETATTRID INNER JOIN ASSET ON MEASUREMENT.ASSETID = ASSET.ASSETID INNER JOIN ASSETATTRDIALECT ON ASSET.SPECCLASS = ASSETATTRDIALECT.SPECCLASS AND MEASURESPEC.ASSETATTRID=ASSETATTRDIALECT.ASSETATTRID AND ASSETATTRDIALECT.DESCRIPTION IS NOT NULL AND ASSETATTRDIALECT.ATTRVALUE= '"
                + tagvalue + "' ";

        Query query = entityManager.createNativeQuery(sqlString);

        @SuppressWarnings("unchecked")
        List<Object> result = query.getResultList();

        return result;
    }

    public List<Object> findByTagId(String tagid) throws DataAccessException {

        String sqlString = "select tagid,tagname from PAGETAG where TAGID='"
                + tagid + "'";

        Query query = entityManager.createNativeQuery(sqlString);

        @SuppressWarnings("unchecked")
        List<Object> result = query.getResultList();

        return result;
    }

    /**
     * 查询有多少类设备信息
     *
     * @return
     */
    public List<Asset> findSpecClass() {

        String sqlString = "select distinct specclass from Asset";

        Query query = entityManager.createNativeQuery(sqlString);

        @SuppressWarnings("unchecked")
        List<Asset> result = query.getResultList();

        return result;
    }

    /**
     * 查询有多少设备位置信息
     *
     * @return
     */
    public List<Locations> findLocations() {

        String sqlString = "select s.location,s.description from (select distinct location from Asset) a , Locations s where a.location = s.location order by location ";

        Query query = entityManager.createNativeQuery(sqlString);

        @SuppressWarnings("unchecked")
        List<Locations> result = query.getResultList();

        return result;
    }

    /**
     * 查询有多少分类对应的属性信息
     *
     * @return
     */
    public List<Object> findAttributeByClassId(String classId) {

        String sqlString = "select distinct measurespec.classstructureid,measurespec.assetattrid,assetattribute.description from measurement inner join measurespec on measurement.measurespecid=measurespec.measurespecid inner join assetattribute on measurespec.assetattrid=assetattribute.assetattrid where measurespec.classstructureid = '"
                + classId + "'";

        Query query = entityManager.createNativeQuery(sqlString);

        @SuppressWarnings("unchecked")
        List<Object> result = query.getResultList();

        return result;
    }

    /**
     * 查询指定分类下的非设定值属性
     *
     * @param assetnum 设备编号
     * @param classstructureid 分类id
     * @param isNot            空则模糊查询设定值，传not查询非设定值
     * @return
     */
    public List<Object> findNotSetAttribute(String assetnum, String classstructureid, String isNot) {

//    	String sqlString = "select c.assetattrid,c.description,c.measureunitid,c.classstructureid from ClassSpec c where c.classstructureid = '" + classstructureid + "' and c.assetattrid " + isNot + " like '%_sp'";
        String sqlString = "select c.assetattrid,c.description,c.measureunitid,c.classstructureid, t.tagid, t.valuetag from measurement m, classspec c, tags t where m.assetnum = '" + assetnum + "' and c.classstructureid='" + classstructureid + "' and t.valuetag = m.valuetag and t.tagid is not null and t.tagid <> '' and m.assetattrid = c.assetattrid and c.assetattrid " + isNot + " like '%_sp'";
        logger.info("----findNotSetAttribute----" + sqlString);
        Query query = entityManager.createNativeQuery(sqlString);
        @SuppressWarnings("unchecked")
        List<Object> result = query.getResultList();

        return result;
    }

    /**
     * 查询指定分类下的非设定值属性 OR  可设定属性
     *
     * @param classstructureid 分类id
     * @param isNot            空则模糊查询设定值，传not查询非设定值
     * @return
     */
    public List<ClassSpec> findSetAttribute(String classstructureid, String isNot) {
//        String sqlString = "select c.APPLYDOWNHIER, c.ASSETATTRIBUTEID, c.ASSETATTRID, c.ATTRDESCPREFIX, c.CLASSSPECID, c.CLASSSTRUCTUREID,"
//                + " c.DOMAINID, c.INHERITEDFROM, c.INHERITEDFROMID, c.LOOKUPNAME, c.MEASUREUNITID, c.ORGID, c.SECTION, c.SITEID,"
//                + " c.TABLEATTRIBUTE, c.DESCRIPTION from CLASSSPEC c where c.classstructureid = '" + classstructureid + "' and c.assetattrid " + isNot + " like '%_sp'";
    	String sqlString = "select c.assetattrid,c.description,c.measureunitid,c.classstructureid, t.tagid, t.valuetag from measurement m, classspec c, tags t where c.classstructureid='" + classstructureid + "' and t.valuetag = m.valuetag and t.tagid is not null and t.tagid <> '' and m.assetattrid = c.assetattrid and c.assetattrid " + isNot + " like '%_sp'";
    	Query query = entityManager.createNativeQuery(sqlString, ClassSpec.class);

        @SuppressWarnings("unchecked")
        List<ClassSpec> result = query.getResultList();

        return result;
    }

    /**
     * 关联查询相应属性信息
     *
     * @return
     * @author zouzhixiang 2014-09-26
     */
    public List<Object> findAssetattridByClassstructureid() {
        String sql = "select c.description inheritedfrom,cs.description,cs.assetattrid from classstructure c, classspec cs where c.classstructureid = cs.classstructureid";
        Query query = entityManager.createNativeQuery(sql);
        @SuppressWarnings("unchecked")
        List<Object> specs = query.getResultList();
        return specs;
    }

    /**
     * 查询有属性的分类
     *
     * @return
     * @author zouzhixiang 2014-10-08
     */
    public List<Object> findAttributes() {
        String sql = "select distinct c.classstructureid,cs.description from classspec c, classstructure cs where c.classstructureid = cs.classstructureid";
        Query query = entityManager.createNativeQuery(sql);
        @SuppressWarnings("unchecked")
        List<Object> specs = query.getResultList();
        return specs;
    }

    /**
     * 查询所有分类以及属性，并对相对应的属性进行绑定
     *
     * @return
     */
    public List<ClassSpecTemp> findAllClassspecs() {
        String structureSQL = "select c.CLASSIFICATIONID, c.CLASSSTRUCTUREID, c.CLASSSTRUCTUREUID, c.DESCRIPTION, c.GENASSETDESC,"
                + " c.HASCHILDREN, c.HASLD, c.LANGCODE, c.ORGID, c.PARENT, c.SITEID, c.TYPE, c.USECLASSINDESC from CLASSSTRUCTURE c";
        String specSQL = "select c.APPLYDOWNHIER, c.ASSETATTRIBUTEID, c.ASSETATTRID, c.ATTRDESCPREFIX, c.CLASSSPECID, c.CLASSSTRUCTUREID,"
                + " c.DOMAINID, c.INHERITEDFROM, c.INHERITEDFROMID, c.LOOKUPNAME, c.MEASUREUNITID, c.ORGID, c.SECTION, c.SITEID,"
                + " c.TABLEATTRIBUTE, c.DESCRIPTION from CLASSSPEC c";
        Query query = entityManager.createNativeQuery(structureSQL, ClassStructure.class);
        List<ClassStructure> structures = query.getResultList();
        query = entityManager.createNativeQuery(specSQL, ClassSpec.class);
        List<ClassSpec> specs = query.getResultList();
        List<ClassSpecTemp> classSpecTemps = new ArrayList<ClassSpecTemp>();
        for (ClassStructure classStructure : structures) {
            classSpecTemps.add(dealClassspecs(classStructure, specs));
        }
        return classSpecTemps;
    }

    private ClassSpecTemp dealClassspecs(ClassStructure structure, List<ClassSpec> specs) {
        ClassSpecTemp classSpecTemp = new ClassSpecTemp();
        classSpecTemp.setClassStructure(structure);
        List<ClassSpec> classSpecs = new ArrayList<ClassSpec>();
        for (ClassSpec spec : specs) {
            if (spec.getClassstructureid().equals(structure.getClassstructureid())) {
                classSpecs.add(spec);
            }
            classSpecTemp.setClassSpecs(classSpecs);
        }
        return classSpecTemp;
    }
}
