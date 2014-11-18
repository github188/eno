package com.energicube.eno.monitor.service;

import com.energicube.eno.common.dto.PfeObjectDTO;
import com.energicube.eno.common.page.Page;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EnergyUser
 * Date: 13-11-25
 * Time: 下午6:24
 * To change this template use File | Settings | File Templates.
 */
public interface OtherSystemService {

    /**
     * 查询巡更情况
     *
     * @param pageSize  每页数量
     * @param pageNum   页号
     * @param shift     班次
     * @param road      路线
     * @param checkDate 巡检日期
     * @return
     * @throws DataAccessException
     */
    public Page getSubPatrols(int pageSize, int pageNum, String checkDate) throws DataAccessException;

    /**
     * 老版本（查询巡更情况）
     *
     * @param pageSize
     * @param pageNum
     * @param areaId
     * @param shift
     * @param road
     * @param checkDate
     * @return
     * @throws DataAccessException
     */
    public Page getEpObjects(int pageSize, int pageNum, String areaId, String shift, String road, String checkDate) throws DataAccessException;

    /**
     * 查询所有班次
     *
     * @param areaId 区域编码
     * @return
     */
    public List<String[]> getShiftAll(String areaId);

    /**
     * 查询所有路线
     *
     * @param areaId 区域编码
     * @return
     */
    public List<String[]> getRoadAll(String areaId);

    /**
     * 查询停车管理信息
     *
     * @param pageSize 每页数量
     * @param pageNum  页号
     * @param inDate   进入时间
     * @param outDate  出去时间
     * @param carNum   车号
     * @return
     * @throws DataAccessException
     */
    public Page getParkmObjects(int pageSize, int pageNum, String startInDate, String endInDate, String startOutDate, String endOutDate, String carNum, String goName, String comeName) throws DataAccessException;


    /**
     * 查询门禁信息
     *
     * @param pageSize              页数
     * @param pageNum               页号
     * @param controlMachineAddress 控制器地址
     * @param subPointId            子点
     * @param eventType             事件类型
     * @return
     * @throws DataAccessException
     */
    public Page getSasacObjects(int pageSize, int pageNum, String controlMachineAddress, String subPointId, String eventType) throws DataAccessException;


    /**
     * 某层所有店铺的某时间的客流数据
     *
     * @param floor 层
     * @param date  某一时间点
     * @return
     */
    public List<PfeObjectDTO> findAllShopPassengerFlow(String floor, String date);

    /**
     * 所有店铺的客流排名,这个排名是一个累积值
     *
     * @param floor       层，为空串表示查询全场排名
     * @param hundredText 对应楼层百货名称
     * @param startDate   时间 从此时间到当前时间的，此时间是一个开始时间
     * @param endDate     时间 从此时间到当前时间的，此时间是一个结束时间
     * @return
     */
    public List<PfeObjectDTO> findAllShopOrder(String floor, String hundredText, String startDate, String endDate);

    /**
     * 所有店铺的场内人数排名
     *
     * @param floor       层，为空串表示查询全场排名
     * @param hundredText 对应楼层百货名称
     * @param startDate   时间 从此时间到当前时间的，此时间是一个开始时间
     * @param endDate     时间 从此时间到当前时间的，此时间是一个结束时间
     * @return
     */
    public List<PfeObjectDTO> findAllShopInsidePerson(String floor, String hundredText, String startDate, String endDate);

    /**
     * 查询店铺的详细数据
     *
     * @param shopName 店铺名
     * @param date     时间
     * @return
     */
    public List<PfeObjectDTO> findShopPassengerFlow(String shopName, String date);

    /**
     * 某天的总客流
     *
     * @param startDate 时间
     * @param endDate   时间
     * @param totalId   总客流的ID 目前是67
     * @return
     */
    public String findTotalPassengerFlow(String startDate, String endDate, String totalId);

    /**
     * 某天的客流小时数据
     *
     * @param year  时间
     * @param month 时间
     * @param day   时间
     * @return
     */
    public List<PfeObjectDTO> findTotalPassengerFlowDay(String year, String month, String day, String totalId);

    /**
     * 店铺的每个月总客流
     *
     * @param shopName 店铺名
     * @param year     年
     * @return
     */
    public List<PfeObjectDTO> findShopTotalPassengerFlowByMonth(String shopName, String year);

    /**
     * 店铺的每天实时总客流
     *
     * @param shopName  店铺名
     * @param startDate 时间
     * @param endDate   时间
     * @return
     */
    public List<PfeObjectDTO> findShopTotalPassengerFlowByDay(String shopName, String startDate, String endDate);


    /**
     * 用于设备台帐的导入
     * <p/>
     * 将上传的Excel的Sheet存入数据
     *
     * @param sheetCollection Excel的Sheet
     * @throws Exception
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void saveAssetToDatabase(Collection<Sheet> sheetCollection) throws Exception;


    /**
     * 用于首页配置表的导入
     * <p/>
     * 将上传的Excel的Sheet存入数据
     *
     * @param sheetCollection Excel的Sheet
     * @throws Exception
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void saveIndexConfigToDatabase(Collection<Sheet> sheetCollection) throws Exception;
}
