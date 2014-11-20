package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.FileUtil;
import com.energicube.eno.monitor.model.NetvideoSystemConfig;
import com.energicube.eno.monitor.model.NetvideoVersion;
import com.energicube.eno.monitor.service.NetvideoSysConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class NetvideoSysConfigImpl implements NetvideoSysConfigService {

    private static final Log logger = LogFactory.getLog(NetvideoSysConfigImpl.class);

    @Override
    public String readVersions(HttpServletRequest request, String string) {
        return FileUtil.readConfig(string, request);
    }

    @Override
    public String saveDriver(HttpServletRequest request, String filename, String drivername) throws Exception {
        //设置上传目录
        String dirName = "resources/structures/Netvideo/Driver";
        String str = FileUtil.uploadFile(request, filename, dirName);
        filename = str.substring(str.lastIndexOf('/') + 1);
        //更改信息存到dat文件
        NetvideoVersion netvideoVersion = null;
        //从文件中取出原始记录
        String json = FileUtil.readConfig("netversion", request);
        if (json.equals("")) {
            netvideoVersion = new NetvideoVersion();
            netvideoVersion.setConfigName(drivername);
            netvideoVersion.setDriverName(filename);
            netvideoVersion.setVersionNo((long) 1.0);
            //存入文件
            FileUtil.saveconfigToFile(netvideoVersion, "netversion", request);
        } else {
            int flag = json.indexOf(drivername);
            if (flag < 0) {
                netvideoVersion = new NetvideoVersion();
                netvideoVersion.setConfigName(drivername);
                netvideoVersion.setDriverName(filename);
                netvideoVersion.setVersionNo((long) 1.0);
                ObjectMapper objectMapper = new ObjectMapper();
                List list = objectMapper.readValue(json, List.class);
                List<NetvideoVersion> lists = new ArrayList<NetvideoVersion>();
                for (Object obj : list) {
                    NetvideoVersion netvideoVersion1 = objectMapper.readValue(obj.toString(), NetvideoVersion.class);
                    lists.add(netvideoVersion1);
                }
                lists.add(netvideoVersion);
                Object obj1 = lists.toArray();
                //存入文件
                FileUtil.saveconfigToFile(obj1, "netversion", request);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                List list = objectMapper.readValue(json, List.class);
                NetvideoVersion[] obj = new NetvideoVersion[list.size()];
                int i = 0;
                for (Object objVer : list) {
                    NetvideoVersion version = objectMapper.readValue(objVer.toString(), NetvideoVersion.class);
                    if (version.getConfigName().equals(drivername)) {
                        FileUtil.deleteFile(request.getSession().getServletContext().getRealPath("/")
                                + "/" + dirName + "/" + version.getDriverName());
                        version.setDriverName(filename);
                        version.setVersionNo(version.getVersionNo() + 1);
                        obj[i] = version;
                        i++;
                    }
                }
                //nets = (NetvideoVersion[])obj.toArray();
                FileUtil.saveconfigToFile(obj, "netversion", request);
            }
        }
        //存入文件
        return str;
    }

    @Override
    public void saveSysconfig(NetvideoSystemConfig config, HttpServletRequest request) {
        FileUtil.saveconfigToFile(config, "systemconfig", request);
    }

    @Override
    public String readSysconfig(HttpServletRequest request, String filename) {
        String str = FileUtil.readConfig(filename, request);
        return str;
    }

    @Override
    public void downDriver(HttpServletRequest request, HttpServletResponse response, String drivername) {
        try {
            String vers = FileUtil.readConfig("netversion", request);
            ObjectMapper objectMapper = new ObjectMapper();
            List netvideoVersions = objectMapper.readValue(vers, List.class);

            String filename = "";
            for (Object netvideoVersion : netvideoVersions) {
                NetvideoVersion version = objectMapper.readValue(netvideoVersion.toString(), NetvideoVersion.class);
                if (version.getConfigName().equals(drivername)) {
                    filename = version.getDriverName();
                }
            }
            FileUtil.downLoadDriver(request, response, filename);
        } catch (Exception e) {
            logger.error("downDriver", e);
        }
    }


}
