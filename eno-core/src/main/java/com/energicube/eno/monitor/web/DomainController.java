package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.OkcDMAlphaNum;
import com.energicube.eno.monitor.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统域数据
 */
@Controller
@RequestMapping("/domain")
public class DomainController extends BaseController {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 返回字母和数字型数据域
     *
     * @param dictid 域名称
     * @return 字母和数字型数据域列表
     */
    @RequestMapping(value = "/aln/{dictid}", method = RequestMethod.GET)
    @ResponseBody
    public List<OkcDMAlphaNum> showDomainAlphaNums(@PathVariable String dictid) {
        return dictionaryService.findALNDictionaryByDictid(dictid);
    }
}
