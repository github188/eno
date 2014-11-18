package com.energicube.eno.asset.web;

import com.energicube.eno.asset.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 资产附件控制
 *
 * @author CHENPING
 */
@Controller
@RequestMapping("/asset")
public class AssetAddfileController extends BaseAssetController {

    @Autowired
    private AssetService assetService;

    @RequestMapping(value = "/{assetId}/addfile", method = RequestMethod.GET)
    public String showAddfileList(@PathVariable("assetId") String assetId, Model model) {

        return "asset/addfile";
    }
}
