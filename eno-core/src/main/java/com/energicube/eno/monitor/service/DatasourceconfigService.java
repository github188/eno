package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Datasourceconfig;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;


public interface DatasourceconfigService {
    /**
     * 分页查询
     */
    Page<Datasourceconfig> findByPage(int page, int size);

    /**
     * 添加
     */
    void saveDatasourceconfig(Datasourceconfig datasourceconfig);

    /**
     * 删除
     */
    void deleteDatasourceconfig(int id);

    /**
     * 根据主键查找一个
     */
    Datasourceconfig findOne(int id);

    /**
     * 查找所有
     */
    List<Datasourceconfig> findAll();

    /**
     * 根据外键查找一个
     */
    Datasourceconfig findOneByFk(String fk);

    /**
     * 根据数据源id连接数据库
     */
    Connection connectDataBase(String id);

    /**
     * 用于读取一个excel文件
     *
     * @param file excel文件
     * @return 返回list 其中每一条代表excel中每一行数据，中间用，号隔开
     */
    List<List<String>> importDo(MultipartFile file);

    /**
     * 导出excel文件
     *
     * @param rs       数据库中数据
     * @param outs     外部导出列名
     * @param response http响应
     * @param name     配置名
     * @throws Exception 异常
     */
    public void createExcel(ResultSet rs, String[] outs,
                            HttpServletResponse response, String name) throws Exception;

    /**
     * 根据表名查找列名和数据类型
     */
    public Map<String, String> findColumnsType(String datatable, Connection conn);

    /**
     * 用于excel文件导入到数据库
     *
     * @param tablenames 表中列名 String数组
     * @param colstype   表中列数据类型 String数组(int,varchar,datetime)
     * @param excelnames excel标题数组
     * @param datatable  表名
     */
    public void importFromExcel(String[] tablenames, String[] colstype, String[] excelnames, String datatable, List<List<String>> list, Connection conn);

    /**
     * 测试临时数据源
     *
     * @param type
     * @param name
     * @param ip
     * @param port
     * @param user
     * @param pwd
     * @return
     */
    Connection connectDataBase(String type, String name, String ip, int port,
                               String user, String pwd) throws Exception;
}
