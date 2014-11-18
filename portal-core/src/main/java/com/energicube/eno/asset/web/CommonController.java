package com.energicube.eno.asset.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/common")
public class CommonController extends BaseAssetController {

/*	@Autowired
    private DictionaryService dictionaryService;*/

    @RequestMapping(value = "/dictionary/aln", method = RequestMethod.GET)
    public String showDMAlphaNumDialog(@RequestParam(value = "dictid", required = false, defaultValue = "") String dictid, Model model) {
        model.addAttribute("dictid", dictid);
        return "dialog/dlgDictionary";
    }

    /**
     * 资台台账数据源
     * */
	/*@RequestMapping(value = "/dictionary/aln", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTablesResponse<OkcDMAlphaNum> showAlnDictionaryData(
			@RequestParam(value="dictid",required=false,defaultValue="")String dictid,
			DataTablesRequestParams params) {
		return dictionaryService.findALNDictionaryByDictid(dictid, params);
	}*/
}
