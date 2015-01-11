package com.energicube.eno.monitor.service;

import com.energicube.eno.common.dto.PfeHuiNaCameraDTO;
import com.energicube.eno.common.dto.PfeObjectDTO;
import com.energicube.eno.common.page.Page;
import com.energicube.eno.monitor.model.UcPassengerFlowDetail;

import org.apache.poi.ss.usermodel.Sheet;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    /**
     * 某层所有店铺的某天的客流数据，包括实时
     *
     * @param floor 层
     * @param date  某天
     * @return List<PfeObjectDTO>
     */
    public List<PfeObjectDTO> findAllShopPassengerFlow(String floor, String date);

    /**
     * 所有店铺的客流排名,这个排名是一个累积值
     *
     * @param floor       层，为空串表示查询全场排名
     * @param hundredText 对应楼层百货名称
     * @param startDate   时间 从此时间到当前时间的，此时间是一个开始时间
     * @param endDate     时间 从此时间到当前时间的，此时间是一个结束时间
     * @return List<PfeObjectDTO>
     */
    public List<PfeObjectDTO> findAllShopOrder(String floor, String hundredText, String startDate, String endDate);

    /**
     * 某天的实时总客流
     *
     * @param startDate 时间
     * @param totalId   总客流的ID
     * @return PfeObjectDTO
     */
    public PfeObjectDTO findTotalPassengerFlow(String startDate, String totalId);

    /**
     * 某时间段的实时总客流
     *
     * @param startDate 时间
     * @param endDate   结束时间
     * @param totalId   总客流的ID 目前是67
     * @return PfeObjectDTO
     */
    public PfeObjectDTO findTotalPassengerFlow(String startDate, String endDate, String totalId);

    /**
     * 按照inCount倒序排序查询某店铺某天每小时的客流
     *
     * @param shopType1 店铺类型
     * @param shopType2 店铺类型
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<PfeObjectDTO>
     */
    public List<PfeObjectDTO> findTotalPassengerFlowDay(String shopType1, String shopType2, DateTime
            startDate, DateTime endDate);

    /**
     * 店铺的每个月总客流
     *
     * @param shopName 店铺名
     * @param year     年
     * @return List<PfeObjectDTO>
     */
    public List<PfeObjectDTO> findShopTotalPassengerFlowByMonth(String shopName, String year);

    /**
     * 店铺的每天实时总客流
     *
     * @param shopName  店铺名
     * @param startDate 时间
     * @param endDate   时间
     * @return List<PfeObjectDTO>
     */
    public List<PfeObjectDTO> findShopTotalPassengerFlowByDay(String shopName, String startDate, String endDate);

    /**
     * 查询所有店铺的客流信息
     *
     * @param blockIds  店铺集合ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<UcPassengerFlowDetail>客流信息
     */
    public List<UcPassengerFlowDetail> findPassengerFlowAllShop(String blockIds, String startDate, String endDate);

    /**
     * 查询广场的客流信息
     *
     * @param totalId   广场ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return List<UcPassengerFlowDetail>客流信息
     */
    public List<UcPassengerFlowDetail> findPassengerFlowWanDa(String totalId, String startDate, String endDate);

    /**
     * 查询所有店铺的客流信息 汇纳
     *
     * @param allShopType 店铺类型
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @return List<UcPassengerFlowDetail>客流信息
     */
    public List<UcPassengerFlowDetail> findPassengerFlowAllShopHn(String allShopType, String startDate, String endDate);

    /**
     * 查询客流探头状态信息
     * @return List<PfeHuiNaCameraDTO>
     */
    public List<PfeHuiNaCameraDTO> findCameraState();

    /**
     * 新版客流查询工作日和节假日日均客流
     * @return Map<String, Object>
     */
    public Map<String, Object> findWorkDayAndHoliDayAveragePassengerOfDay();

    /**
     * 查询所有店铺的客流实时信息 汇纳
     *
     * @param allShopType 店铺类型
     * @return 客流信息
     */
    public List<PfeObjectDTO> findPassengerFlowAllShopHnRealTime(String allShopType);
}
