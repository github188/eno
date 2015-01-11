package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.Config;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.dto.PfeHuiNaCameraDTO;
import com.energicube.eno.common.dto.PfeHuiNaDTO;
import com.energicube.eno.common.dto.PfeObjectDTO;
import com.energicube.eno.common.dto.TransferUtil;
import com.energicube.eno.common.jdbc.*;
import com.energicube.eno.common.page.Page;
import com.energicube.eno.common.page.PaginationHelper;
import com.energicube.eno.common.procedure.SQLStoredProcedure;
import com.energicube.eno.monitor.model.DataIndexconfig;
import com.energicube.eno.monitor.model.UcPassengerFlowDay;
import com.energicube.eno.monitor.model.UcPassengerFlowDetail;
import com.energicube.eno.monitor.model.UcPassengerFlowMonth;
import com.energicube.eno.monitor.repository.DataIndexconfigRepository;
import com.energicube.eno.monitor.service.OtherSystemService;
import com.energicube.eno.monitor.service.PassengerFlowService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:  Copyright(C) 2013-2014
 * Company  ZCLF Energy Technologies Inc.
 *
 * @author 尚培宝
 * @version 1.0
 * @date 2014年11月26日
 * @Description 电子巡更相关方法、 客流相关方法
 */
@Service
public class OtherSystemServiceImpl implements OtherSystemService {

    private static Log logger = LogFactory.getLog(OtherSystemServiceImpl.class);

    private Config config = new Config();
    
    @Autowired
    private JdbcTemplate subPatrolTemplate;

    @Autowired
    private JdbcTemplate epTemplate;

    @Autowired
    private JdbcTemplate parkmTemplate;

    @Autowired
    private JdbcTemplate sasacTemplate;

    @Autowired
    private JdbcTemplate pfeTemplate;

    @Autowired
    private JdbcTemplate dataSourceTemplate;

    @Autowired
    private DataIndexconfigRepository dataIndexconfigRepository;
    
    @Autowired
    private PassengerFlowService passengerFlowService;

	@Override
    public Page getSubPatrols(int pageSize, int pageNum, String checkDate)
            throws DataAccessException {
        PaginationHelper paginationHelper = new PaginationHelper();
        SubPatrolMapper subPatrolMapper = new SubPatrolMapper();
        String sql = "select sub.id, sub.lineNum, sub.lineName, sub.userId, sub.userName, sub.placeName, sub.startTime, sub.endTime,"
                + "sub.checkTime, sub.checkResult, sub.shifts, sub.missedNum, sub.onTimeNum, sub.earlyNum, sub.lateNum from SUB_patrol sub ";
        String sqlCount = "select count(*) from SUB_patrol sub";
        String condition = " where 1=1 ";
        if (checkDate != null && !"".equals(checkDate)) {
            condition += " and sub.checkTime = convert(varchar(10),'" + checkDate + "',120)";
        }
        Page page = paginationHelper.fetchPage(subPatrolTemplate, sqlCount + condition, sql + condition, null, pageNum, pageSize, subPatrolMapper);
        return page;
    }

	@Override
    public Page getEpObjects(int pageSize, int pageNum, String areaId, String shift, String road, String checkDate) throws DataAccessException {
        PaginationHelper paginationHelper = new PaginationHelper();
        EpRowMapper epRowMapper = new EpRowMapper();
        String sql = "select  crd.id,a.Name,r.Name,c.Name,crd.beginTime,crd.EndTime,crd.Resultid,m.name,\n" +
                "crd.FirstDatetime,crd.LastDatetime,crd.Typeid,mem.memo,crd.RemarkId ,crd.MissedNum,crd.OnTimeNum,crd.EarlyNum,crd.LateNum " +
                "from BC_CheckResult crd \n" +
                "left join BC_area a on a.id=crd.areaid \n" +
                "left join BC_Class c on c.id=crd.classid\n" +
                "left join bc_route r on r.id=crd.routeid \n" +
                "left join bc_member m on m.id=crd.memberid \n" +
                "left join BC_memo mem on mem.id=crd.MemoId ";
        String sqlCount = "select  count(*) from BC_CheckResult crd \n" +
                "left join BC_area a on a.id=crd.areaid \n" +
                "left join BC_Class c on c.id=crd.classid\n" +
                "left join bc_route r on r.id=crd.routeid \n" +
                "left join bc_member m on m.id=crd.memberid \n" +
                "left join BC_memo mem on mem.id=crd.MemoId ";

        String condition = " where a.id=" + areaId;
        if (shift != null && !"".equals(shift)) {
            condition = condition + " and crd.classid=" + shift;
        }
        if (road != null && !"".equals(road)) {
            condition = condition + " and crd.routeid=" + road;
        }
        if (checkDate != null && !"".equals(checkDate)) {
            condition = condition + " and crd.CheckDate=convert(varchar(10),'" + checkDate + "',120)";
        }
        Page page = paginationHelper.fetchPage(epTemplate, sqlCount + condition, sql + condition, null, pageNum, pageSize, epRowMapper);
        return page;
    }

	@Override
    public List<String[]> getShiftAll(String areaId) {
        EpShiftRowMapper epShiftRowMapper = new EpShiftRowMapper();
        String sql = "select bc.id,bc.Name  from BC_class bc";
        if (areaId != null && !"".equals(areaId)) {
            sql = sql + " where AreaId=" + areaId;
        }
        List list = epTemplate.query(sql, epShiftRowMapper);
        return list;
    }

	@Override
    public List<String[]> getRoadAll(String areaId) {
        EpShiftRowMapper epShiftRowMapper = new EpShiftRowMapper();
        String sql = "select bc.id,bc.Name  from BC_route bc";
        if (areaId != null && !"".equals(areaId)) {
            sql = sql + " where AreaId=" + areaId;
        }
        List list = epTemplate.query(sql, epShiftRowMapper);
        return list;
    }

	@Override
    public Page getParkmObjects(int pageSize, int pageNum, String startInDate, String endInDate, String startOutDate, String endOutDate, String carNum, String goName, String comeName) throws DataAccessException {
        PaginationHelper paginationHelper = new PaginationHelper();
        ParkmRowMapper parkmRowMapper = new ParkmRowMapper();
        String sql = "select Car_No,Come_Date,Go_Date,M_money,Card_Type,Come_Name,Go_Name,Card_No,Card_Name from gocar ";
        String sqlCount = "select count(*) from gocar ";
        String condition = " where 1=1 ";
        if (carNum != null && !"".equals(carNum)) {
            condition = condition + " and Car_No='" + carNum + "' ";
        }
        if (startInDate != null && !"".equals(startInDate)) {
            condition = condition + " and Come_Date>=convert(varchar(10),'" + startInDate + "',120) ";
        }
        if (endInDate != null && !"".equals(endInDate)) {
            condition = condition + " and Come_Date<=convert(varchar(10),'" + endInDate + "',120) ";
        }
        if (startOutDate != null && !"".equals(startOutDate)) {
            condition = condition + " and Go_Date>=convert(varchar(10),'" + startOutDate + "',120) ";
        }
        if (endOutDate != null && !"".equals(endOutDate)) {
            condition = condition + " and Go_Date<=convert(varchar(10),'" + endOutDate + "',120) ";
        }
        if (goName != null && !"".equals(goName)) {
            condition = condition + " and Go_Name like '%" + goName + "%' ";
        }
        if (comeName != null && !"".equals(comeName)) {
            condition = condition + " and Come_Name like '%" + comeName + "%' ";
        }
        logger.info("-==condition=:" + condition);
        Page page = paginationHelper.fetchPage(parkmTemplate, sqlCount + condition, sql + condition, null, pageNum, pageSize, parkmRowMapper);
        return page;
    }

	@Override
    public Page getSasacObjects(int pageSize, int pageNum, String controlMachineAddress, String subPointId, String eventType) throws DataAccessException {
        PaginationHelper paginationHelper = new PaginationHelper();
        SasacRowMapper sasacRowMapper = new SasacRowMapper();
        String condition = "";
        if (controlMachineAddress != null && !"".equals(controlMachineAddress)) {
            condition = condition + " and pce.ControlMachineAddress=" + controlMachineAddress;
        }
        if (subPointId != null && !"".equals(subPointId)) {
            condition = condition + " and pce.PointAddress in (select PointAddress from LwsNetPoint where  ControlMachineAddress=" + controlMachineAddress + " and  PointName  like '%" + controlMachineAddress + "-" + subPointId + "%' ) ";
        }
        String table = " PubGeneralEvent pce ";
        if (eventType != null && !"".equals(eventType)) {
            if (PatternConst.SASAC_EVENT_TYPE_GENERAL.equals(eventType)) {
                table = " PubGeneralEvent pce ";
            } else {

                if (PatternConst.SASAC_EVENT_TYPE_INVALID.equals(eventType)) {
                    table = " PubInvalidCardEvent pce ";
                }
                if (PatternConst.SASAC_EVENT_TYPE_VALID.equals(eventType)) {
                    table = " PubValidCardEvent pce ";
                }

                String sql = "select  pce.id,pui.userName,pce.CardID,pce.EventTime,pei.Content from " + table + " ,PubUserInfo pui,PubEventID pei where 1=1 " + condition + " and  pce.userId=pui.userId and pce.MessageID=pei.MessageID ";
                String sqlCount = "select count(*) from " + table + " ,PubUserInfo pui,PubEventID pei where 1=1 " + condition + " and  pce.userId=pui.userId and pce.MessageID=pei.MessageID ";

                logger.info("-==condition=:" + condition);

                Page page = paginationHelper.fetchPage(sasacTemplate, sqlCount, sql, null, pageNum, pageSize, sasacRowMapper);
                return page;
            }
        }

        String sql = "select  pce.id,pce.userId,pce.CardID,pce.EventTime,pei.Content from " + table + " ,PubEventID pei where 1=1 " + condition + " and   pce.MessageID=pei.MessageID ";
        String sqlCount = "select count(*) from " + table + " ,PubEventID pei where 1=1 " + condition + "  and pce.MessageID=pei.MessageID ";

        logger.info("-==condition=:" + condition);

        Page page = paginationHelper.fetchPage(sasacTemplate, sqlCount, sql, null, pageNum, pageSize, sasacRowMapper);
        return page;
    }

    @Override
    public void saveAssetToDatabase(Collection<Sheet> sheetCollection) throws Exception {
        for (Sheet sheet : sheetCollection) {
            //sheet名不为空，且此名称是特殊名称，不能是随便的sheet1等。
            if (StringUtils.isNotEmpty(sheet.getSheetName()) && sheet.getSheetName().toLowerCase().indexOf("sheet") < 0) {
                //创建临时的表
                String col = createTable(sheet);
                //向临时表里添加数据
                saveData(sheet, col);
            }
        }
        //执行存储过程整理数据
        SQLStoredProcedure sp = new SQLStoredProcedure(dataSourceTemplate, "UC_SP_UpdateAsset");
        sp.execute();
    }

    /**
     * 根据表名称创建一张表
     * 只支持SQLServer
     *
     * @param sheet Excel的sheet
     * @return 列名
     */
    private String createTable(Sheet sheet) throws Exception {
        StringBuffer col = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        String deltable = "IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'" + sheet.getSheetName() + "$') AND type in (N'U')) DROP TABLE " + sheet.getSheetName() + "$";
        dataSourceTemplate.update(deltable);
        sb.append(" create table " + sheet.getSheetName() + "$ ( ");
        sb.append(" id  varchar(36)   not null, ");
        Iterator<Row> allRows = sheet.rowIterator();
        // 得到第一行，也就是标题行
        Row title = allRows.next();
        // 得到第一行的所有列
        Iterator<Cell> cellTitle = title.cellIterator();
        while (cellTitle.hasNext()) {
            Cell cell = cellTitle.next();
            String value = "";
            if (null != cell) {//当前单元格不为空
                if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                    DecimalFormat formatter = new DecimalFormat("#");
                    value = formatter.format(cell.getNumericCellValue());
                } else if (cell.getCellType() == cell.CELL_TYPE_STRING) {
                    value = String.valueOf(cell.getStringCellValue());
                } else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {
                    value = String.valueOf(cell.getDateCellValue());
                } else if (cell.getCellType() == cell.CELL_TYPE_BLANK) {
                    value = String.valueOf(cell.getStringCellValue());
                } else if (cell.getCellType() == cell.CELL_TYPE_ERROR) {
                    value = "";
                }
            }
            logger.info("---title---" + value);
            if (StringUtils.isNumeric(value)) {
                sb.append(" " + value + " float, ");
            } else {
                sb.append(" " + value + " nvarchar(255), ");
            }
            col.append(value + ",");
        }

        sb.append("constraint PK_UC_Temp" + sheet.getSheetName() + " primary key (id)");
        sb.append(")");
        dataSourceTemplate.update(sb.toString());
        return col.toString().substring(0, col.toString().length() - 1);
    }

    /**
     * 保存数据
     * 用于设备台帐的导入
     *
     * @param sheet 数据
     */
    private void saveData(Sheet sheet, String col) throws Exception {

        Iterator<Row> allRows = sheet.rowIterator();
        // 得到第一行，也就是标题行
        Row title = allRows.next();
        int i = 1;
        //读取每一行
        while (allRows.hasNext()) {
            // 标题下的第一行
            Row nextRow = allRows.next();
            // 得到第一行的所有列
            Iterator<Cell> cellTitle = nextRow.cellIterator();

            String[] cols = col.split(",");

            StringBuilder colValue = new StringBuilder();
            //读出一行的数据
            int k = 0;
            String value = "";
            while (k < cols.length) {
                value = "";
                Cell cell = nextRow.getCell(k);
                if (null != cell) {//当前单元格不为空
                    if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                        DecimalFormat formatter = new DecimalFormat("#");
                        value = formatter.format(cell.getNumericCellValue());
                    } else if (cell.getCellType() == cell.CELL_TYPE_STRING) {
                        value = String.valueOf(cell.getStringCellValue());
                    } else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {
                        value = String.valueOf(cell.getDateCellValue());
                    } else if (cell.getCellType() == cell.CELL_TYPE_BLANK) {
                        value = String.valueOf(cell.getStringCellValue());
                    } else if (cell.getCellType() == cell.CELL_TYPE_ERROR) {
                        value = "";
                    }
                }
                logger.debug("---value---" + value);
                colValue.append("'" + value + "',");
                k++;
            }
            //保存数据
            String sql = "insert into " + sheet.getSheetName() + "$ (id," + col + ") values('" + i + "'," + colValue.toString().substring(0, colValue.length() - 1) + ")";

            logger.info("---asset insert ---" + sql);
            dataSourceTemplate.update(sql);
            i++;
        }
    }
    
	@Override
    public void saveIndexConfigToDatabase(Collection<Sheet> sheetCollection) throws Exception {
        dataIndexconfigRepository.deleteAll();
        for (Sheet sheet : sheetCollection) {
            //sheet名不为空，且此名称是特殊名称，不能是随便的sheet1等。
            if (StringUtils.isNotEmpty(sheet.getSheetName())) {
                //保存数据
                Iterator<Row> allRows = sheet.rowIterator();
                // 得到第一行，也就是标题行
                Row title = allRows.next();
                int length = title.getLastCellNum();
                int i = 1;
                String value = "";
                //读取每一行
                while (allRows.hasNext()) {
                    //初始化参数
                    i = 1;
                    DataIndexconfig dataIndexconfig = new DataIndexconfig();
                    // 标题下的第一行
                    Row nextRow = allRows.next();
                    // 得到一行的所有列
                    Iterator<Cell> cellTitle = nextRow.cellIterator();

                    while (i <= length) {
                        value = "";
                        if (cellTitle.hasNext()) {
                            Cell cell = cellTitle.next();
                            if (null != cell) {//当前单元格不为空
                                if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
                                    DecimalFormat formatter = new DecimalFormat("#");
                                    value = formatter.format(cell.getNumericCellValue());
                                } else if (cell.getCellType() == cell.CELL_TYPE_STRING) {
                                    value = String.valueOf(cell.getStringCellValue());
                                } else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {
                                    value = String.valueOf(cell.getCellFormula());
                                } else if (cell.getCellType() == cell.CELL_TYPE_BLANK) {
                                    value = String.valueOf(cell.getStringCellValue());
                                } else if (cell.getCellType() == cell.CELL_TYPE_ERROR) {
                                    value = "";
                                }
                            }
                        }
                        logger.info(i + "---value---[" + value + "]-----" + length);
                        if (StringUtils.isNotEmpty(value)) {
                            switch (i) {
                                case 1:
                                    dataIndexconfig.setDescription(value);
                                    break;
                                case 2:
                                    dataIndexconfig.setName(value);
                                    break;
                                case 3:
                                    dataIndexconfig.setId(value);
                                    break;
                                case 4:
                                    dataIndexconfig.setFrequency(Integer.parseInt(value));
                                    break;
                                case 5:
                                    dataIndexconfig.setCategoryId(value);
                                    break;
                                case 6:
                                    dataIndexconfig.setRegionId(value);
                                    break;
                                case 7:
                                    dataIndexconfig.setValue(value);
                                    break;
                                case 8:
                                    dataIndexconfig.setTagname(value);
                                    break;
                                case 9:
                                    dataIndexconfig.setTagid(Integer.parseInt(value));
                                    break;
                                case 10:
                                    dataIndexconfig.setIscreate(Integer.parseInt(value));
                                    break;
                                case 11:
                                    dataIndexconfig.setCalGrade(Integer.parseInt(value));
                                    break;
                                case 12:
                                    dataIndexconfig.setIspd(Integer.parseInt(value));
                                    break;
                                case 13:
                                    dataIndexconfig.setIsmax(Integer.parseInt(value));
                                    break;
                                case 14:
                                    dataIndexconfig.setIsmin(Integer.parseInt(value));
                                    break;
                                case 15:
                                    dataIndexconfig.setIssum(Integer.parseInt(value));
                                    break;
                                case 16:
                                    dataIndexconfig.setIsavg(Integer.parseInt(value));
                                    break;
                                case 17:
                                    dataIndexconfig.setIspercent(Integer.parseInt(value));
                                    break;
                                case 18:
                                    dataIndexconfig.setIschange(Integer.parseInt(value));
                                    break;
                                case 19:
                                    dataIndexconfig.setBuildId(value);
                                    break;
                            }
                        }
                        i++;
                    }
                    //保存数据
                    if (dataIndexconfig.getDescription() != null && dataIndexconfig.getName() != null) {
                        dataIndexconfigRepository.save(dataIndexconfig);
                    }
                }
            }
        }
    }

	@Override
    public List<PfeObjectDTO> findAllShopPassengerFlow(String floor, String date) {
        String allShopType = config.getProps().getProperty("pfe.allShopType500");
        String type1 = config.getProps().getProperty("pfe.allShopType600");
        DateTime dt = DateTime.parse(date, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        List<UcPassengerFlowDay> ucPassengerFlowDetailList = passengerFlowService.findAllShopPassenger(allShopType, type1, dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
        List<PfeObjectDTO> pfeObjectDTOList = new ArrayList<PfeObjectDTO>();
        for (UcPassengerFlowDay ucPassengerFlowDay : ucPassengerFlowDetailList) {
            PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
            pfeObjectDTO.setInCount(ucPassengerFlowDay.getInCount());
            pfeObjectDTO.setBlockName(ucPassengerFlowDay.getShopName());
            pfeObjectDTO.setOutCount(ucPassengerFlowDay.getOutCount());
            pfeObjectDTO.setInsidePerson(ucPassengerFlowDay.getInCount() - ucPassengerFlowDay.getOutCount());
            pfeObjectDTO.setId(ucPassengerFlowDay.getShopCode());
            pfeObjectDTOList.add(pfeObjectDTO);
        }
        return pfeObjectDTOList;
    }

	@Override
	public List<PfeObjectDTO> findAllShopOrder(String floor, String hundredText, String startDate, String endDate) {
        String allShopType = config.getProps().getProperty("pfe.allShopType500");
        String type1 = config.getProps().getProperty("pfe.allShopType600");
        if (StringUtils.isEmpty(endDate)) {
            endDate = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
        }
        Collection<UcPassengerFlowDetail> ucPassengerFlowDetailList = passengerFlowService.findShopTypePassengerByDate(allShopType, type1, startDate, endDate);
        List<PfeObjectDTO> pfeObjectDTOList = new ArrayList<PfeObjectDTO>();
        for (UcPassengerFlowDetail ucPassengerFlowDay : ucPassengerFlowDetailList) {
            PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
            pfeObjectDTO.setInCount(ucPassengerFlowDay.getInCount());
            pfeObjectDTO.setBlockName(ucPassengerFlowDay.getShopName());
            pfeObjectDTO.setOutCount(ucPassengerFlowDay.getOutCount());
            pfeObjectDTO.setInsidePerson(ucPassengerFlowDay.getInCount() - ucPassengerFlowDay.getOutCount());
            pfeObjectDTO.setId(ucPassengerFlowDay.getShopCode());
            pfeObjectDTOList.add(pfeObjectDTO);
        }
        return pfeObjectDTOList;
    }

	@Override
	public PfeObjectDTO findTotalPassengerFlow(String startDate, String totalId) {

        String type1 = config.getProps().getProperty("pfe.allShopType300");
        DateTime dt = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        List<UcPassengerFlowDay> ucPassengerFlowDetailList = passengerFlowService.findAllShopPassenger(type1, type1, dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth());
        List<PfeObjectDTO> pfeObjectDTOList = new ArrayList<PfeObjectDTO>();
        if (ucPassengerFlowDetailList != null) {
            for (UcPassengerFlowDay ucPassengerFlowDay : ucPassengerFlowDetailList) {
                PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
                pfeObjectDTO.setInCount(ucPassengerFlowDay.getInCount());
                pfeObjectDTO.setBlockName(ucPassengerFlowDay.getShopName());
                pfeObjectDTO.setOutCount(ucPassengerFlowDay.getOutCount());
                pfeObjectDTO.setInsidePerson(ucPassengerFlowDay.getInCount() - ucPassengerFlowDay.getOutCount());
                pfeObjectDTO.setId(ucPassengerFlowDay.getShopCode());
                pfeObjectDTOList.add(pfeObjectDTO);
            }
        }
        return pfeObjectDTOList.get(0);
    }

	@Override
	public PfeObjectDTO findTotalPassengerFlow(String startDate, String endDate, String totalId) {
        if (StringUtils.isEmpty(endDate)) {
            endDate = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
        }
        String allShopType = config.getProps().getProperty("pfe.allShopType300");

        Collection<UcPassengerFlowDetail> ucPassengerFlowDetailList = passengerFlowService.findShopTypePassengerByDate(allShopType, allShopType, startDate, endDate);

        List<PfeObjectDTO> pfeObjectDTOList = new ArrayList<PfeObjectDTO>();
        if (ucPassengerFlowDetailList != null) {
            for (UcPassengerFlowDetail ucPassengerFlowDetail : ucPassengerFlowDetailList) {
                PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
                pfeObjectDTO.setInCount(ucPassengerFlowDetail.getInCount());
                pfeObjectDTO.setBlockName(ucPassengerFlowDetail.getShopName());
                pfeObjectDTO.setOutCount(ucPassengerFlowDetail.getOutCount());
                pfeObjectDTO.setInsidePerson(ucPassengerFlowDetail.getInCount() - ucPassengerFlowDetail.getOutCount());
                pfeObjectDTO.setCountTime(ucPassengerFlowDetail.getDateHour() + ":" + ucPassengerFlowDetail.getDateMinute());
                pfeObjectDTO.setId(ucPassengerFlowDetail.getShopCode());
                pfeObjectDTOList.add(pfeObjectDTO);
            }
        }
        return pfeObjectDTOList.get(0);
    }

	@Override
	public List<PfeObjectDTO> findTotalPassengerFlowDay(String shopType1, String shopType2, DateTime
            startDate, DateTime endDate) {
        List<UcPassengerFlowDetail> ucPassengerFlowDetailList = passengerFlowService.findAllShopPassengerDetail(shopType1, shopType2, startDate, endDate);
        List<PfeObjectDTO> pfeObjectDTOList = new ArrayList<PfeObjectDTO>();
        for (UcPassengerFlowDetail ucPassengerFlowDetail : ucPassengerFlowDetailList) {
            PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
            pfeObjectDTO.setInCount(ucPassengerFlowDetail.getInCount());
            pfeObjectDTO.setBlockName(ucPassengerFlowDetail.getShopName());
            pfeObjectDTO.setOutCount(ucPassengerFlowDetail.getOutCount());
            pfeObjectDTO.setInsidePerson(ucPassengerFlowDetail.getInCount() - ucPassengerFlowDetail.getOutCount());
            pfeObjectDTO.setId(ucPassengerFlowDetail.getShopCode());
            DateTime create = new DateTime(ucPassengerFlowDetail.getCreateDate());
            pfeObjectDTO.setCountTime(create.toString("yyyy-MM-dd HH:mm:ss"));
            pfeObjectDTOList.add(pfeObjectDTO);
        }
        return pfeObjectDTOList;
    }

	@Override
	public List<PfeObjectDTO> findShopTotalPassengerFlowByMonth(String shopName, String year) {

        String endDate = year + "-01-01 23:59:59";
        DateTime dt = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        List<UcPassengerFlowMonth> ucPassengerFlowDetailList = passengerFlowService.findShopPassengerShopName(shopName, dt.getYear());
        List<PfeObjectDTO> pfeObjectDTOList = new ArrayList<PfeObjectDTO>();
        for (UcPassengerFlowMonth ucPassengerFlowDetail : ucPassengerFlowDetailList) {
            PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
            pfeObjectDTO.setInCount(ucPassengerFlowDetail.getInCount());
            pfeObjectDTO.setBlockName(ucPassengerFlowDetail.getShopName());
            pfeObjectDTO.setOutCount(ucPassengerFlowDetail.getOutCount());
            pfeObjectDTO.setInsidePerson(ucPassengerFlowDetail.getInCount() - ucPassengerFlowDetail.getOutCount());
            pfeObjectDTO.setId(ucPassengerFlowDetail.getShopCode());
            pfeObjectDTOList.add(pfeObjectDTO);
        }
        return pfeObjectDTOList;
    }

	@Override
	public List<PfeObjectDTO> findShopTotalPassengerFlowByDay(String shopName, String startDate, String endDate) {
        if (StringUtils.isEmpty(endDate)) {
            endDate = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
        }

        List<UcPassengerFlowDay> ucPassengerFlowDayList = passengerFlowService.findShopPassengerByDate(shopName, startDate, endDate);
        List<PfeObjectDTO> pfeObjectDTOList = new ArrayList<PfeObjectDTO>();
        for (UcPassengerFlowDay ucPassengerFlowDay : ucPassengerFlowDayList) {
            PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
            pfeObjectDTO.setInCount(ucPassengerFlowDay.getInCount());
            pfeObjectDTO.setBlockName(ucPassengerFlowDay.getShopName());
            pfeObjectDTO.setOutCount(ucPassengerFlowDay.getOutCount());
            pfeObjectDTO.setInsidePerson(ucPassengerFlowDay.getInCount() - ucPassengerFlowDay.getOutCount());
            pfeObjectDTO.setId(ucPassengerFlowDay.getShopCode());
            pfeObjectDTOList.add(pfeObjectDTO);
        }
        return pfeObjectDTOList;
    }

	@Override
	public List<UcPassengerFlowDetail> findPassengerFlowAllShop(
			String blockIds, String startDate, String endDate) {
		String sql = "";
        PfeRowToPFlowMapper pfeRowToPFlowMapper = new PfeRowToPFlowMapper();
        String condition = "";
        if (startDate != null && !"".equals(startDate)) {
            condition = condition + " and  scb.date>='" + startDate + "' ";
        }
        if (endDate != null && !"".equals(endDate)) {
            condition = condition + " and  scb.date<'" + endDate + "' ";
        }
        sql = "SELECT scb.block_id,sb.block_name,scb.date,scb.date_year,scb.date_month,scb.date_day,scb.date_time,scb.date_minute,scb.in_count,scb.out_count FROM sys_count_block scb,sys_block sb  where sb.block_id=scb.block_id and  scb.block_id in ( " + blockIds + " ) " + condition + "  order by scb.in_count desc";
        List list = pfeTemplate.query(sql, pfeRowToPFlowMapper);
        return list;
	}

	@Override
	public List<UcPassengerFlowDetail> findPassengerFlowWanDa(String totalId,
			String startDate, String endDate) {
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(totalId)) {
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(totalId)) {
                try {
                    String totalName = new String(config.getProps().getProperty("pfe.totalName").getBytes("ISO-8859-1"), "utf-8");
                    PfeTotalMapper pfeTotalMapper = new PfeTotalMapper();
                    String totalSql = "select blocks as id from sys_blocktype where  id = " + totalId;
                    List<PfeObjectDTO> list = pfeTemplate.query(totalSql, pfeTotalMapper);
                    PfeObjectDTO obj = list.get(0);
                    String condition1 = " and scb.block_id in (" + obj.getId() + ") ";

                    PfeRowToPFlowMapper pfeRowToPFlowMapper = new PfeRowToPFlowMapper();
                    String condition = "";
                    if (startDate != null && !"".equals(startDate)) {
                        condition = condition + " and  scb.date>='" + startDate + "' ";
                    }
                    if (endDate != null && !"".equals(endDate)) {
                        condition = condition + " and  scb.date<'" + endDate + "' ";
                    }

                    String sql = "select scb.id,scb.block_id,scb.date,scb.date_year,scb.date_month,scb.date_day,scb.date_time,date_minute,SUM(scb.in_count),SUM(scb.out_count) from  sys_count_block scb  where 1=1   " + condition + condition1;
                    List<UcPassengerFlowDetail> listPfe = pfeTemplate.query(sql, pfeRowToPFlowMapper);

                    for (UcPassengerFlowDetail ucPassengerFlowDetail : listPfe) {
                        ucPassengerFlowDetail.setShopCode(config.getProps().getProperty("pfe.totalShopCode"));
                        ucPassengerFlowDetail.setShopName(totalName);
                    }
                    return listPfe;
                } catch (UnsupportedEncodingException e) {
                    logger.error("--NEC总客流查询异常--", e);
                }
            }
        }
        return null;
	}

	@Override
	public List<UcPassengerFlowDetail> findPassengerFlowAllShopHn(
			String allShopType, String startDate, String endDate) {
		List<UcPassengerFlowDetail> ucPassengerFlowDetailList = new ArrayList<UcPassengerFlowDetail>();
        SQLStoredProcedure sp = new SQLStoredProcedure(pfeTemplate, "usp_PI_GetCurrentPassenger");
        PfeHuiNaDTO pfeHuiNaDTO = new PfeHuiNaDTO();
        sp.setReturnParam("result-set-1", pfeHuiNaDTO);
        sp.addParameter("BeginTime", startDate);
        sp.addParameter("EndTime", endDate);
        Map<String, Object> result = sp.execute();
        List<PfeHuiNaDTO> results = (List<PfeHuiNaDTO>) result.get("result-set-1");
        for (PfeHuiNaDTO pfe : results) {
            logger.info("----proc list------" + pfe.toString());
            if (allShopType.contains(pfe.getSiteType())) {
                ucPassengerFlowDetailList.add(TransferUtil.PfeHuiNaToUcPassengerFlow(pfe));
            }
        }
        return ucPassengerFlowDetailList;
	}

	@Override
	public List<PfeHuiNaCameraDTO> findCameraState() {
		SQLStoredProcedure sp = new SQLStoredProcedure(pfeTemplate, "usp_PI_GetCameraState");
        PfeHuiNaCameraDTO pfeHuiNaCameraDTO = new PfeHuiNaCameraDTO();
        sp.setReturnParam("result-set-1", pfeHuiNaCameraDTO);
        Map<String, Object> result = sp.execute();
        return (List<PfeHuiNaCameraDTO>) result.get("result-set-1");
	}

	@Override
	public Map<String, Object> findWorkDayAndHoliDayAveragePassengerOfDay() {
		Map<String, Object> result = new HashMap<String, Object>();

		String startDate = DateTime.now().toString("yyyy-MM") + "-01"; // 每月1号
		String today = DateTime.now().toString("yyyy-MM-dd"); // 当前天

		// 这里要判断第二个参数日期要比第一个参数日期大先继续运行
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat holidaysdf = new SimpleDateFormat("MM-dd");

		// 查询总客流每月的数据
		String allShopType = config.getProps()
				.getProperty("pfe.allShopType300");
		List<UcPassengerFlowDay> list = passengerFlowService
				.findAllShopPassengerFlowByMonth(allShopType, gc.get(gc.YEAR),
						gc.get(gc.MONTH) + 1);

		int workDay = 0, holiday = 0; // 定义工作日和节假日
		int workDayCount = 0, holidayCount = 0; // 定义工作日和节假日进店人数
		try {
			Date d1 = sdf.parse(startDate);
			Date d2 = sdf.parse(today);
			gc.setTime(d1);
			long time = d2.getTime() - d1.getTime();
			long day = time / 3600000 / 24 + 1;
			for (int i = 0; i < day; i++) {
				UcPassengerFlowDay pass = list.get(i);
				if (gc.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SATURDAY
						&& gc.get(GregorianCalendar.DAY_OF_WEEK) != GregorianCalendar.SUNDAY) { // 周六、周日不进这里
					if (!holidayList(holidaysdf.format(gc.getTime()))
							&& !holidayOfCN(sdf.format(gc.getTime()))) { // 判断是否是法定节假日
						workDay++;
						workDayCount += pass.getInCount();
					} else {
						holiday++;
						holidayCount += pass.getInCount();
					}
				} else {
					holiday++;
					holidayCount += pass.getInCount();
				}

				// 天数加1
				gc.add(gc.DATE, 1);
			}

			result.put("workDay", workDay);
			result.put("holiday", holiday);
			result.put("workDayCount", workDayCount);
			result.put("holidayCount", holidayCount);
			result.put("avgWorkDayCount", workDayCount / workDay);
			result.put("avgHolidayCount", holidayCount / holiday);
		} catch (ParseException e) {
			logger.error(e);
		}

		return result;
	}

	// 春节放假三天，定义到2020年
	private boolean holidayOfCN(String year) {
		List<String> ls = new ArrayList<String>();
		ls.add("2015-02-19");
		ls.add("2015-02-20");
		ls.add("2015-02-21");
		ls.add("2006-02-08");
		ls.add("2006-02-09");
		ls.add("2006-02-10");
		ls.add("2017-01-28");
		ls.add("2017-01-29");
		ls.add("2017-01-30");
		ls.add("2018-02-16");
		ls.add("2018-02-17");
		ls.add("2018-02-18");
		ls.add("2019-02-05");
		ls.add("2019-02-06");
		ls.add("2019-02-07");
		ls.add("2020-01-25");
		ls.add("2020-01-26");
		ls.add("2020-01-27");
		if (ls.contains(year)) {
			return true;
		}
		return false;
	}

	// 法定假日，元旦、五一和国庆
	private boolean holidayList(String findDate) {
		List<String> ls = new ArrayList<String>();
		ls.add("01-01");
		ls.add("01-02");
		ls.add("01-03");
		ls.add("05-01");
		ls.add("05-02");
		ls.add("05-03");
		ls.add("10-01");
		ls.add("10-02");
		ls.add("10-03");
		ls.add("10-04");
		ls.add("10-05");
		ls.add("10-06");
		ls.add("10-07");
		if (ls.contains(findDate)) {
			return true;
		}
		return false;
	}

    @Override
    public List<PfeObjectDTO> findPassengerFlowAllShopHnRealTime(String allShopType) {
        List<PfeObjectDTO> ucPassengerFlowDetailList = new ArrayList<PfeObjectDTO>();
        SQLStoredProcedure sp = new SQLStoredProcedure(pfeTemplate, "usp_PI_GetCurrentPassenger");
        PfeHuiNaDTO pfeHuiNaDTO = new PfeHuiNaDTO();
        String time=null;
        sp.setReturnParam("result-set-1", pfeHuiNaDTO);
        sp.addParameter("BeginTime", time);
        sp.addParameter("EndTime", time);
        Map<String, Object> result = sp.execute();
        List<PfeHuiNaDTO> results = (List<PfeHuiNaDTO>) result.get("result-set-1");
        for (PfeHuiNaDTO pfe : results) {
            logger.info("----proc list------" + pfe.toString());
            if (allShopType.contains(pfe.getSiteType())) {
                ucPassengerFlowDetailList.add(TransferUtil.PfeHuiNaToPfeObject(pfe));
            }
        }
        return ucPassengerFlowDetailList;
    }
}
