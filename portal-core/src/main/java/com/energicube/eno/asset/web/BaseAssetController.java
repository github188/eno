package com.energicube.eno.asset.web;

import com.energicube.eno.asset.service.AssetService;
import com.energicube.eno.monitor.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

public class BaseAssetController extends BaseController {

    public static final String ISEDITABLE = "iseditable";

    @Autowired
    private AssetService assetService;

    @ModelAttribute
    public void setDefaultAssetId(WebRequest request, Model model) {
        String assetId = request.getParameter("assetId");
        if (!StringUtils.hasLength(assetId) || "0".equals(assetId)) {
            //assetId = assetService.getDefaultSelectedAssetId();
            model.addAttribute("assetId", assetId);
        }

    }

}
