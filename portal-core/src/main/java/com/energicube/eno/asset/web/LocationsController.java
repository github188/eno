package com.energicube.eno.asset.web;

import com.energicube.eno.asset.model.Asset;
import com.energicube.eno.asset.model.LocSystem;
import com.energicube.eno.asset.model.LocationMeter;
import com.energicube.eno.asset.model.Locations;
import com.energicube.eno.asset.service.AssetService;
import com.energicube.eno.asset.service.LocationsService;
import com.energicube.eno.asset.service.MeterService;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.monitor.web.BaseController;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationsController extends BaseController {

    private LocationsService locationsService;
    private AssetService assetService;
    private MeterService meterService;

    @Autowired
    public LocationsController(LocationsService locationsService,
                               AssetService assetService, MeterService meterService) {
        this.locationsService = locationsService;
        this.assetService = assetService;
        this.meterService = meterService;
    }

    /**
     * 显示位置列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showLocationsList(Model model) {
        List<Locations> locationsList = locationsService.findAllLocations(1)
                .getContent();
        model.addAttribute("locationsList", locationsList);
        return "locations/locationsList";
    }

    /**
     * 资台台账对话框
     */
    @RequestMapping(value = "/list/dialog", method = RequestMethod.GET)
    public String showAssetWindow(Model model) {
        return "dialog/dlgLocationList";
    }


    /**
     * 位置列表数据源
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<Locations> findLocationsWithDatatablesCriterias(HttpServletRequest request) {
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        DataSet<Locations> locations = locationsService.findLocationsWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(locations, criterias);
    }

    /**
     * 新增位置信息
     *
     * @param model
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationLocations(Model model) {
        model.addAttribute("locations", new Locations());
        return "locations/editLocations";
    }

    /**
     * 保存新增位置信息
     *
     * @param locations 位置对象
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreationLocations(@Valid Locations locations,
                                           BindingResult result, SessionStatus status,
                                           RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "locations/editLocations";
        } else {
            if (locationsService.existLocation(locations.getLocation())) {
                result.addError(new ObjectError("location", "位置已经存在"));
                return "locations/editLocations";
            } else {
                locations = locationsService.saveLocations(locations);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "新增成功");
                return "redirect:/locations/edit/" + locations.getLocationsid()
                        + ".html";
            }
        }
    }

    /**
     * edit the locations object of the specified id
     *
     * @param locationsid locations id
     */
    @RequestMapping(value = "/edit/{locationsid}", method = RequestMethod.GET)
    public String initUpdateLocations(
            @PathVariable("locationsid") Long locationsid, Model model) {
        Locations locations = null;
        if (locationsid > 0) {
            locations = locationsService.findOne(locationsid);
        } else {
            locations = new Locations();
        }
        model.addAttribute("locations", locations);
        model.addAttribute("locationsid", locationsid);
        return "locations/editLocations";
    }

    /**
     * save edit the locations of the specified id
     */
    @RequestMapping(value = "/edit/{locationsid}", method = RequestMethod.PUT)
    public String processUpdateLocations(@Valid Locations locations,
                                         BindingResult result, SessionStatus status,
                                         RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "locations/editLocations";
        } else {

            status.setComplete();
            redirectAttrs.addFlashAttribute("message", "更新成功");
            return "redirect:/locations/edit/" + locations.getLocationsid()
                    + ".html";
        }
    }

    /**
     * show assert list of the specified locations id
     */
    @RequestMapping(value = "/{locationsid}/assets", method = RequestMethod.GET)
    public String showAssetList(@PathVariable("locationsid") Long locationsid,
                                Model model) {

        Locations locations = null;
        if (locationsid > 0) {
            locations = locationsService.findOne(locationsid);
        } else {
            locations = new Locations();
        }
        model.addAttribute("locations", locations);

        List<Asset> assetList = assetService.findByLocation(locations
                .getLocation());

        model.addAttribute("assetList", assetList);
        model.addAttribute("locationsid", locationsid);
        return "locations/assetList";
    }

    /**
     * show move history list of the specified locations id
     */
    @RequestMapping(value = "/{locationsid}/histories", method = RequestMethod.GET)
    public String showHistoryList(
            @PathVariable("locationsid") Long locationsid, Model model) {

        Locations locations = null;
        if (locationsid > 0) {
            locations = locationsService.findOne(locationsid);
        } else {
            locations = new Locations();
        }
        model.addAttribute("locations", locations);

        model.addAttribute("locationsid", locationsid);
        return "locations/historyList";
    }

    /**
     * show meter list of the specified locations id
     */
    @RequestMapping(value = "/{locationsid}/meters", method = RequestMethod.GET)
    public String showMeterList(@PathVariable("locationsid") Long locationsid,
                                Model model) {
        Locations locations = null;
        if (locationsid > 0) {
            locations = locationsService.findOne(locationsid);
        } else {
            locations = new Locations();
        }
        model.addAttribute("locations", locations);

        List<LocationMeter> locationMeters = locationsService
                .findMeterByLocation(locations.getLocation());
        model.addAttribute("locationMeters", locationMeters);
        model.addAttribute("locationMeter", new LocationMeter());
        model.addAttribute("locationsid", locationsid);
        return "locations/meterList";
    }

    /**
     * 保存附加到位置的计量器
     */
    @RequestMapping(value = "/{locationsid}/locationmeter/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processSaveLocationMeter(
            @PathVariable("locationsid") Long locationsid,
            @Valid LocationMeter locationMeter, BindingResult result,
            SessionStatus status) {
        Message message = null;
        if (result.hasErrors()) {
            message = new Message(false, result);
            return message;
        } else {
            message = new Message();
            message.setSuccess(false);
            // 判断记录器是否存在
            boolean isExistMeter = meterService.existMetername(locationMeter
                    .getMetername());
            if (!isExistMeter) {
                message.setMsg("填写的仪表不存在");
                return message;
            }
            // 判断记录器是否存在于位置
            boolean isExistLocationMeter = locationsService
                    .existMeterInLocationMeter(locationMeter.getLocation(),
                            locationMeter.getMetername());
            if (isExistLocationMeter) {
                message.setMsg("该位置已经存在名称为" + locationMeter.getMetername()
                        + "的仪表");
                return message;
            }
            // 保存附件加到位置的计量器
            locationMeter = locationsService.saveLocationMeter(locationMeter);

            message.setSuccess(true);
            message.setMsg("");
        }

        return message;
    }

    @RequestMapping(value = "/locsystems", method = RequestMethod.GET)
    public String showLocsystemList(Model model) {
        Page<LocSystem> locSystemList = locationsService.findAllLocSystem(1);
        LocSystem locSystem = new LocSystem();
        model.addAttribute("locSystemList", locSystemList);
        model.addAttribute("locSystem", locSystem);
        return "dialog/dlgLocSystem";
    }

    /**
     * save {@link LocSystem} object
     *
     * @param locSystem {@link LocSystem} object
     * @param result    Validation Result
     */
    @RequestMapping(value = "/locsystems/save", method = RequestMethod.GET)
    public Message processEditLocsystemFrom(@Valid LocSystem locSystem,
                                            BindingResult result, SessionStatus status, Model model) {
        Message message = new Message();

        if (result.hasErrors()) {
            message.setSuccess(false);
            StringBuilder sb = new StringBuilder();
            for (ObjectError err : result.getAllErrors()) {
                if (sb.length() > 0)
                    sb.append(",");
                sb.append(err.getDefaultMessage());
            }
            message.setMsg(sb.toString());
        } else {
            if (locationsService.existLocSystem(locSystem.getSystemid())) {
                message.setMsg("ID已经存在");
                message.setSuccess(false);
            } else {
                locationsService.saveLocSystem(locSystem);
                status.setComplete();
                message.setSuccess(true);
            }
        }
        return message;
    }

}
