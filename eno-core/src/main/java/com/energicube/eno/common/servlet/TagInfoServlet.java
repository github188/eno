package com.energicube.eno.common.servlet;

import com.energicube.eno.message.redis.TagInfo;
import com.energicube.eno.monitor.model.Dict;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagInfoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final static Log logger = LogFactory.getLog(TagInfoServlet.class);

    public TagInfoServlet() {
        super();
    }

    public void init() {
        try {
            logger.debug("--进来了这里init--");
            ServletContext application = getServletConfig().getServletContext();

//			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//			DictRepository dictRepository = (DictRepository) wac.getBean("dictRepository");
//			PagelayoutRepository pagelayoutRepository = (PagelayoutRepository) wac.getBean("pagelayoutRepository");
//			PagetagRepository pagetagRepository = (PagetagRepository) wac.getBean("pagetagRepository");
//			RedisOpsService redisOpsService = (RedisOpsService) wac.getBean("redisOpsService");

            List<TagInfo> result = new ArrayList<TagInfo>();

//			int arrLength = 60000;
//			String[] tagids = new String[arrLength];
//			int length = 5000;
//			// 获取tagid列表
//			for (int i = 0; i < arrLength; i++) {
//				String value = "0";
//				try {
//					value = redisOpsService.hGetAllValueByKey(String.format("tags:%s", i));
//					value = (value != null && "null".equalsIgnoreCase(value) ? "0" : value);
//				} catch(Exception e) {
//					value = "0";
//					logger.error(e.getMessage());
//				}
//				TagInfo tagInfo = new TagInfo();
//				tagInfo.setId(i);
//				tagInfo.setF(0);
//				tagInfo.setP(1);
//				tagInfo.setT(1.0);
//				tagInfo.setV("" + (i % 2 == 0 ? 0 : 1));
//				result.add(tagInfo);
//			}

//			try {
            // 从redis中获取tagid最后的测量值
//				List<TagInfo> lastValues = redisOpsService.mGetByKeys(tagids);
//				for (int i = 0; i < lastValues.size(); i++) {
//					TagInfo currInfo = lastValues.get(i);
//					// 设置值
//					if (currInfo != null && pagetagList.get(i).getTagid().equals(currInfo.getId().toString())) {
//						pagetagList.get(i).setTagval(lastValues.get(i).getV());
//					}
//				}
//			} catch (Exception ex) {
//				logger.error("Redis获取TAG值失败," + ex.getMessage());
//			}


            // 获取数据字典表dict中需要进行转换的数据，供设备列表和面板数据对应[2014-10-14, zzx]
//			List<Dict> dictList = dictRepository.findByNullValue();
            Map<String, Dict> map = new HashMap<String, Dict>();
//			for (Dict dict : dictList) {
//				String key = dict.getClassstructureid() + "_" + dict.getAssetattrid() + "_" + dict.getValue();
//				map.put(key, dict); // 存储显示值
//			}

            // 设置Application属性
            application.setAttribute("context", map); // 存储设备列表和面板转换关系值
            application.setAttribute("taglist", result); // 存储相关的tag值
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        super.destroy();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
