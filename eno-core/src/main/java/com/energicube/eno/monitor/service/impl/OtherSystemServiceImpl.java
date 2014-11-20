package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.dto.PfeObjectDTO;
import com.energicube.eno.common.jdbc.*;
import com.energicube.eno.common.page.Page;
import com.energicube.eno.common.page.PaginationHelper;
import com.energicube.eno.common.procedure.SQLStoredProcedure;
import com.energicube.eno.monitor.model.DataIndexconfig;
import com.energicube.eno.monitor.repository.DataIndexconfigRepository;
import com.energicube.eno.monitor.service.OtherSystemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EnergyUser
 * Date: 13-11-25
 * Time: 下午6:25
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OtherSystemServiceImpl implements OtherSystemService {

    private static Log logger = LogFactory.getLog(OtherSystemServiceImpl.class);

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


    public List<String[]> getShiftAll(String areaId) {
        EpShiftRowMapper epShiftRowMapper = new EpShiftRowMapper();
        String sql = "select bc.id,bc.Name  from BC_class bc";
        if (areaId != null && !"".equals(areaId)) {
            sql = sql + " where AreaId=" + areaId;
        }
        List list = epTemplate.query(sql, epShiftRowMapper);
        return list;
    }


    public List<String[]> getRoadAll(String areaId) {
        EpShiftRowMapper epShiftRowMapper = new EpShiftRowMapper();
        String sql = "select bc.id,bc.Name  from BC_route bc";
        if (areaId != null && !"".equals(areaId)) {
            sql = sql + " where AreaId=" + areaId;
        }
        List list = epTemplate.query(sql, epShiftRowMapper);
        return list;
    }


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
        logger.debug("-==condition=:" + condition);
        Page page = paginationHelper.fetchPage(parkmTemplate, sqlCount + condition, sql + condition, null, pageNum, pageSize, parkmRowMapper);
        return page;
    }


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

                logger.debug("-==condition=:" + condition);

                Page page = paginationHelper.fetchPage(sasacTemplate, sqlCount, sql, null, pageNum, pageSize, sasacRowMapper);
                return page;
            }
        }

        String sql = "select  pce.id,pce.userId,pce.CardID,pce.EventTime,pei.Content from " + table + " ,PubEventID pei where 1=1 " + condition + " and   pce.MessageID=pei.MessageID ";
        String sqlCount = "select count(*) from " + table + " ,PubEventID pei where 1=1 " + condition + "  and pce.MessageID=pei.MessageID ";

        logger.debug("-==condition=:" + condition);

        Page page = paginationHelper.fetchPage(sasacTemplate, sqlCount, sql, null, pageNum, pageSize, sasacRowMapper);
        return page;
    }


    public List<PfeObjectDTO> findAllShopPassengerFlow(String floor, String date) {
        String sql = "";
        PfeRowMapper pfeRowMapper = new PfeRowMapper();
        String condition = "";
        if (floor != null && !"".equals(floor)) {
            condition = condition + " and sb.block_name like '" + floor + "%' ";
        }
        if (date != null && !"".equals(date)) {
            condition = condition + " and  scb.date='" + date + "' ";
        }
        sql = "SELECT scb.block_id,sb.block_name,scb.date,scb.in_count,scb.out_count FROM sys_count_block scb,sys_block sb where sb.block_id=scb.block_id  " + condition + "  order by scb.in_count desc";

        List list = pfeTemplate.query(sql, pfeRowMapper);
        return list;
    }


    public List<PfeObjectDTO> findAllShopOrder(String floor, String hundredText, String startDate, String endDate) {
        String sql = "";
        PfeRowMapper pfeRowMapper = new PfeRowMapper();
        String condition = "";
        if (floor != null && !"".equals(floor)) {
            condition = condition + " and (sb.block_name like '" + floor + "%' or sb.block_name = '" + hundredText + "') ";
        }
        if (startDate != null && !"".equals(startDate)) {
            condition = condition + " and  scb.date>='" + startDate + "' ";
        }
        if (endDate != null && !"".equals(endDate)) {
            condition = condition + " and  scb.date<'" + endDate + "' ";
        }
        sql = "SELECT scb.block_id,sb.block_name,scb.date,sum(scb.in_count),sum(scb.out_count) FROM sys_count_block scb,sys_block sb where sb.block_id=scb.block_id " + condition + " group by scb.block_id order by scb.in_count desc";
        List list = pfeTemplate.query(sql, pfeRowMapper);
        return list;
    }


    public List<PfeObjectDTO> findAllShopInsidePerson(String floor, String hundredText, String startDate, String endDate) {
        String sql = "";
        PfeInsidePersonMapper pfeMapper = new PfeInsidePersonMapper();
        String condition = "";
        if (floor != null && !"".equals(floor)) {
            condition = condition + " and (sb.block_name like '" + floor + "%' or sb.block_name = '" + hundredText + "') ";
        }
        if (startDate != null && !"".equals(startDate)) {
            condition = condition + " and  scb.date>='" + startDate + "' ";
        }
        if (endDate != null && !"".equals(endDate)) {
            condition = condition + " and  scb.date<'" + endDate + "' ";
        }
        sql = "SELECT scb.block_id,sb.block_name,scb.date,sum(scb.in_count),sum(scb.out_count),((sum(scb.in_count)) - (sum(scb.out_count))) FROM sys_count_block scb,sys_block sb where sb.block_id=scb.block_id " + condition + " group by scb.block_id order by scb.in_count desc";
        List list = pfeTemplate.query(sql, pfeMapper);
        return list;
    }


    public List<PfeObjectDTO> findShopPassengerFlow(String shopName, String date) {
        String sql = "";
        PfeRowMapper pfeRowMapper = new PfeRowMapper();
        String condition = "";
        if (shopName != null && !"".equals(shopName)) {
            condition = condition + " and sb.block_name like '%" + shopName + "%' ";
        }
        if (date != null && !"".equals(date)) {
            condition = condition + " and  scb.date>='" + date + "' ";
        }
        sql = "SELECT scb.block_id,sb.block_name,scb.date,scb.in_count,scb.out_count FROM sys_count_block scb,sys_block sb where sb.block_id=scb.block_id " + condition + " order by scb.date asc";
        List list = pfeTemplate.query(sql, pfeRowMapper);
        return list;
    }


    public String findTotalPassengerFlow(String startDate, String endDate, String totalId) {
        PfeTotalRowMapper pfeTotalRowMapper = new PfeTotalRowMapper();
        String condition1 = "";
        if (totalId != null && !"".equals(totalId)) {
            condition1 = condition1 + " where  id='" + totalId + "' ";
        }
        String condition = "";
        if (startDate != null && !"".equals(startDate)) {
            condition = condition + " and  scb.date>='" + startDate + "' ";
        }
        if (endDate != null && !"".equals(endDate)) {
            condition = condition + " and  scb.date<'" + endDate + "' ";
        }
        String sql = "select SUM(scb.in_count) from  sys_count_block scb  where 1=1   " + condition + "  and scb.block_id in (select block_id from sys_count_block where  FIND_IN_SET(block_id,(select blocks from sys_blocktype  " + condition1 + ")) GROUP BY block_id );";
        List list = pfeTemplate.query(sql, pfeTotalRowMapper);
        Object obj = list.get(0);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }


    public List<PfeObjectDTO> findTotalPassengerFlowDay(String year, String month, String day, String totalId) {
        String condition1 = "";
        PfeRowMapper pfeRowMapper = new PfeRowMapper();
        if (totalId != null && !"".equals(totalId)) {
            condition1 = condition1 + " where  id='" + totalId + "' ";
        }
        String condition = "";
        if (year != null && !"".equals(year)) {
            condition = condition + " and  scb.date_year='" + year + "' ";
        }
        if (month != null && !"".equals(month)) {
            condition = condition + " and  scb.date_month='" + month + "' ";
        }
        if (day != null && !"".equals(day)) {
            condition = condition + " and  scb.date_day='" + day + "' ";
        }
        String sql = "select scb.block_id,scb.block_id,scb.date,SUM(scb.in_count),SUM(scb.out_count)  from  sys_count_block scb where 1=1  " + condition + "  and  FIND_IN_SET(block_id,(select blocks from sys_blocktype  " + condition1 + ")) group by scb.date_time order by scb.date;";
        List list = pfeTemplate.query(sql, pfeRowMapper);
        return list;
    }


    public List<PfeObjectDTO> findShopTotalPassengerFlowByMonth(String shopName, String year) {
        String condition = "";
        PfeRowMapper pfeRowMapper = new PfeRowMapper();
        if (shopName != null && !"".equals(shopName)) {
            condition = condition + " and sb.block_name like '%" + shopName + "%' ";
        }

        String sql = "SELECT scb.block_id,sb.block_name,scb.date_month,SUM(scb.in_count),SUM(scb.out_count) FROM sys_count_block scb,sys_block sb where scb.date_year='" + year + "' and sb.block_id=scb.block_id " + condition + " group by scb.date_month ";
        List list = pfeTemplate.query(sql, pfeRowMapper);
        return list;
    }


    public List<PfeObjectDTO> findShopTotalPassengerFlowByDay(String shopName, String startDate, String endDate) {
        String condition = "";
        PfeRowMapper pfeRowMapper = new PfeRowMapper();
        if (shopName != null && !"".equals(shopName)) {
            condition = condition + " and sb.block_name like '%" + shopName + "%' ";
        }
        if (startDate != null && !"".equals(startDate)) {
            condition = condition + " and  scb.date>='" + startDate + "' ";
        }
        if (endDate != null && !"".equals(endDate)) {
            condition = condition + " and  scb.date<'" + endDate + "' ";
        }
        String sql = "SELECT scb.block_id,sb.block_name,scb.date,SUM(scb.in_count),SUM(scb.out_count) FROM sys_count_block scb,sys_block sb where  sb.block_id=scb.block_id " + condition + " group by scb.block_id";
        List list = pfeTemplate.query(sql, pfeRowMapper);
        return list;
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
            logger.debug("---title---" + value);
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
}
