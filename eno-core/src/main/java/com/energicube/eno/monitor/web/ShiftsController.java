package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.repository.*;
import com.energicube.eno.monitor.service.OpLogService;
import com.energicube.eno.monitor.service.ShiftsService;
import com.energicube.eno.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户登陆处理控制
 *
 * @author CHENPING
 */
@Controller
public class ShiftsController extends BaseController {

    private ShiftsService shiftsService;

    @Autowired
    private ShiftsRepository shiftsRepository;

    @Autowired
    private ShiftworkRepository shiftworkRepository;

    @Autowired
    private ShifttypesRepository shifttypesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PersonsRepository PersonsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private OpLogService opLogService;

    @Autowired
    public ShiftsController(ShiftsService shiftsService) {
        this.shiftsService = shiftsService;
    }

    @RequestMapping(value = "/shifts/shifttypesList", method = RequestMethod.GET)
    public String initShifttypesList(Model model) {
        List<Shifttypes> shifttypesListks = shifttypesRepository.findAll();
        List<Shifttypes> shifttypesList = new ArrayList<Shifttypes>();
        List listsd = new ArrayList();
        List listed = new ArrayList();
        for (int i = 0; i < shifttypesListks.size(); i++) {
            Shifttypes shifttypes = shifttypesListks.get(i);
            Date startdateTime = shifttypes.getStarttime();
            Date enddateTime = shifttypes.getEndtime();
            String sd = startdateTime.toString();
            sd = sd.substring(11, 16);
            listsd.add(sd);
            String ed = enddateTime.toString();
            ed = ed.substring(11, 16);
            listed.add(ed);
            shifttypesList.add(shifttypes);
        }
        model.addAttribute("listsd", listsd);
        model.addAttribute("listed", listed);
        model.addAttribute("shifttypesList", shifttypesList);
        return "shifts/shifttypesList";
    }

    @RequestMapping(value = "/shifts/shifttypesAdd", method = RequestMethod.GET)
    public String initShifttypesForm(Model model) {
        Shifttypes shifttypes = new Shifttypes();
        model.addAttribute("shifttypes", shifttypes);
        return "shifts/shifttypesAdd";
    }

    @RequestMapping(value = "/shifts/doInsert", method = RequestMethod.POST)
    public String doInsert(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        try {
            String ksTime = "2013-07-31 ";
            Shifttypes shifttypes = new Shifttypes();
            String shifttypesid = "";
            shifttypesid = request.getParameter("shifttypesid");
            if (!shifttypesid.equals("")) {
                shifttypes = shifttypesRepository.findOne(Integer.valueOf(shifttypesid));
            }
            String shifttype = request.getParameter("shifttypeName");
            String season = request.getParameter("season");
            String starttime = request.getParameter("starttime");
            starttime = ksTime + starttime + ":00";
            String endtime = request.getParameter("endtime");
            endtime = ksTime + endtime + ":00";
            String description = request.getParameter("description");
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            Date startdateTime = format.parse(starttime);
            Date enddateTime = format.parse(endtime);
            shifttypes.setShifttype(shifttype);
            shifttypes.setSeason(season);
            shifttypes.setStarttime(startdateTime);
            shifttypes.setEndtime(enddateTime);
            shifttypes.setDescription(description);
            shifttypesRepository.save(shifttypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Shifttypes> shifttypesList = shifttypesRepository.findAll();
        model.addAttribute("shifttypesList", shifttypesList);
        return "redirect:shifttypesList";
    }

    @RequestMapping(value = "/shifts/shiftsView", method = RequestMethod.GET)
    public String initShiftView(Model model) {
        String week = "";
        String str = "";
        String seasonBz = "";
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        if (monthNow <= 3 && monthNow >= 1) {
            seasonBz = "春季";
        }
        if (monthNow <= 6 && monthNow >= 4) {
            seasonBz = "夏季";
        }
        if (monthNow <= 9 && monthNow >= 7) {
            seasonBz = "秋季";
        }
        if (monthNow <= 12 && monthNow >= 10) {
            seasonBz = "冬季";
        }
        List<Shifttypes> shifttypesCjList = shifttypesRepository
                .findBySeason("春季");
        List<Shifttypes> shifttypesXjList = shifttypesRepository
                .findBySeason("夏季");
        List<Shifttypes> shifttypesQjList = shifttypesRepository
                .findBySeason("秋季");
        List<Shifttypes> shifttypesDjList = shifttypesRepository
                .findBySeason("冬季");
        model.addAttribute("shifttypesCjList",
                shifttypesChangeList(shifttypesCjList));
        model.addAttribute("shifttypesXjList",
                shifttypesChangeList(shifttypesXjList));
        model.addAttribute("shifttypesQjList",
                shifttypesChangeList(shifttypesQjList));
        model.addAttribute("shifttypesDjList",
                shifttypesChangeList(shifttypesDjList));
        model.addAttribute("yearNow", yearNow);
        model.addAttribute("monthNow", monthNow);
        int maxDate = getMonthLastDay(yearNow, monthNow);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<DateToWeek> dateToWeeks = new ArrayList<DateToWeek>();
        List<Shifts> shiftsListView = new ArrayList<Shifts>();
        try {
            for (int i = 1; i <= maxDate; i++) {
                DateToWeek dateToWeek = new DateToWeek();
                str = String.valueOf(yearNow) + "-" + String.valueOf(monthNow)
                        + "-" + String.valueOf(i);
                Date date = sdf.parse(str);
                week = getWeekOfDate(date);
                dateToWeek.setDat(String.valueOf(i));
                dateToWeek.setWeek(week);
                dateToWeeks.add(dateToWeek);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        List<Persons> PersonsList = usersRepository.selectUserorderBy();
        List<Shifts> shiftsList = new ArrayList<Shifts>();
        int kb = 1;
        for (int i = 0; i < PersonsList.size(); i++) {
            Shifts shifts = new Shifts();
            Persons Persons = PersonsList.get(i);
            shifts.setDescription(Persons.getUserid() + "&");
            if (i > 0) {
                Persons PersonsBefore = PersonsList.get(i - 1);
                if (Persons.getJobcode().equals(PersonsBefore.getJobcode())) {
                    shifts.setDeptname("");
                    kb = kb + 1;
                    if (i == PersonsList.size() - 1) {
                        Shifts shiftskb = shiftsList.get(i - kb + 1);
                        String description = shiftskb.getDescription();
                        description = description + kb;
                        shiftskb.setDescription(description);
                        shiftsList.set(i - kb + 1, shiftskb);
                    }
                } else {
                    shifts.setDeptname(Persons.getJobcode());
                    Shifts shiftskb = shiftsList.get(i - kb);
                    String description = shiftskb.getDescription();
                    description = description + kb;
                    shiftskb.setDescription(description);
                    shiftsList.set(i - kb, shiftskb);
                    kb = 1;
                }
            } else {
                shifts.setDeptname(Persons.getJobcode());
                kb = 1;
            }
            shifts.setMobile(Persons.getWorkphone());
            shifts.setName(Persons.getFirstname() + Persons.getLastname());
            List<DateToWeek> dateToWeeklist = new ArrayList<DateToWeek>();
            for (int pk = 1; pk <= maxDate; pk++) {
                DateToWeek dateToWeek = new DateToWeek();
                str = String.valueOf(yearNow) + "-" + String.valueOf(monthNow)
                        + "-" + String.valueOf(pk);
                dateToWeek.setShifttypeName("/");
                dateToWeek.setWzDate(str);
                dateToWeeklist.add(dateToWeek);
            }
            shifts.setDateToWeeklist(dateToWeeklist);
            shiftsList.add(shifts);
        }
        int xb = 0;
        for (int j = 0; j < shiftsList.size(); j++) {
            Shifts shifts = shiftsList.get(j);
            List<DateToWeek> dateToWeeklist = shifts.getDateToWeeklist();
            List<Shifts> shiftsListbyBz = shiftsRepository
                    .findByNameOrderByShiftdate(
                            shifts.getDescription().split("&")[0], yearNow,
                            monthNow);
            if (shiftsListbyBz.size() > 0) {
                for (int pks = 0; pks < shiftsListbyBz.size(); pks++) {
                    DateToWeek dateToWeek = new DateToWeek();
                    String shifttype = shiftsListbyBz.get(pks).getShifttype();
                    String shifttypeName = shifttypesRepository.findOne(
                            Integer.valueOf(shifttype)).getShifttype();
                    String season = shifttypesRepository.findOne(
                            Integer.valueOf(shifttype)).getSeason();
                    String Shiftsidsl = String.valueOf(shiftsListbyBz.get(pks)
                            .getShiftsid());
                    // if (season.equals(seasonBz)) {
                    dateToWeek.setShifttype(shifttype);
                    dateToWeek.setShifttypeName(shifttypeName);
                    dateToWeek.setSeason(season);
                    dateToWeek.setShiftsid(Shiftsidsl);
                    String shiftdatea = shiftsListbyBz.get(pks).getShiftdate()
                            .toString();
                    dateToWeek.setWzDate(shiftdatea.split(" ")[0]);
                    shiftdatea = shiftdatea.substring(8, 10);
                    if (shiftdatea.substring(0, 1).equals("0")) {
                        xb = Integer.valueOf(shiftdatea.substring(1, 2)) - 1;
                    } else {
                        xb = Integer.valueOf(shiftdatea) - 1;
                    }
                    dateToWeeklist.set(xb, dateToWeek);
                }
                shifts.setDateToWeeklist(dateToWeeklist);
            }
            shiftsListView.add(shifts);
        }
        List<Shifts> shiftsListViewPageList = new ArrayList<Shifts>();
        int pageSize = 18;
        if (shiftsListView.size() <= pageSize) {
            shiftsListViewPageList = shiftsListView;
        } else {
            for (int k = 0; k < pageSize; k++) {
                shiftsListViewPageList.add(shiftsListView
                        .get(k));
            }
        }
        int endIndex = 0;
        if (shiftsListView.size() > 0) {
            endIndex = shiftsListView.size() / 18;
            if (shiftsListView.size() % 18 != 0) {
                endIndex = endIndex + 1;
            }
        }
        model.addAttribute("dateToWeeks", dateToWeeks);
        List<Shifttypes> shifttypesList = shifttypesRepository
                .findBySeason(seasonBz);
        model.addAttribute("shifttypesList", shifttypesList);
        model.addAttribute("monthchange", monthNow);
        model.addAttribute("shiftsListView", shiftsListViewPageList);
        model.addAttribute("endIndex", endIndex);
        return "shifts/shiftsView";
    }

    @RequestMapping(value = "/shifts/selectShiftsListViewPage", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Shifts> selectShiftsListViewPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        String week = "";
        String str = "";
        String seasonBz = "";
        Calendar cal = Calendar.getInstance();
        int yearNow = Integer.valueOf(request.getParameter("yearNowPage"));
        int monthNow = Integer.valueOf(request.getParameter("monthNowPage"));
        if (monthNow <= 3 && monthNow >= 1) {
            seasonBz = "春季";
        }
        if (monthNow <= 6 && monthNow >= 4) {
            seasonBz = "夏季";
        }
        if (monthNow <= 9 && monthNow >= 7) {
            seasonBz = "秋季";
        }
        if (monthNow <= 12 && monthNow >= 10) {
            seasonBz = "冬季";
        }
        List<Shifttypes> shifttypesCjList = shifttypesRepository
                .findBySeason("春季");
        List<Shifttypes> shifttypesXjList = shifttypesRepository
                .findBySeason("夏季");
        List<Shifttypes> shifttypesQjList = shifttypesRepository
                .findBySeason("秋季");
        List<Shifttypes> shifttypesDjList = shifttypesRepository
                .findBySeason("冬季");
        model.addAttribute("shifttypesCjList",
                shifttypesChangeList(shifttypesCjList));
        model.addAttribute("shifttypesXjList",
                shifttypesChangeList(shifttypesXjList));
        model.addAttribute("shifttypesQjList",
                shifttypesChangeList(shifttypesQjList));
        model.addAttribute("shifttypesDjList",
                shifttypesChangeList(shifttypesDjList));
        model.addAttribute("yearNow", yearNow);
        model.addAttribute("monthNow", monthNow);
        int maxDate = getMonthLastDay(yearNow, monthNow);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<DateToWeek> dateToWeeks = new ArrayList<DateToWeek>();
        List<Shifts> shiftsListView = new ArrayList<Shifts>();
        try {
            for (int i = 1; i <= maxDate; i++) {
                DateToWeek dateToWeek = new DateToWeek();
                str = String.valueOf(yearNow) + "-" + String.valueOf(monthNow)
                        + "-" + String.valueOf(i);
                Date date = sdf.parse(str);
                week = getWeekOfDate(date);
                dateToWeek.setDat(String.valueOf(i));
                dateToWeek.setWeek(week);
                dateToWeeks.add(dateToWeek);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        List<Persons> PersonsList = usersRepository.selectUserorderBy();
        List<Shifts> shiftsList = new ArrayList<Shifts>();
        int kb = 1;
        for (int i = 0; i < PersonsList.size(); i++) {
            Shifts shifts = new Shifts();
            Persons Persons = PersonsList.get(i);
            shifts.setDescription(Persons.getUserid() + "&");
            if (i > 0) {
                Persons PersonsBefore = PersonsList.get(i - 1);
                if (Persons.getJobcode().equals(PersonsBefore.getJobcode()) && (i % 18 != 0)) {
                    shifts.setDeptname("");
                    kb = kb + 1;
                    if (i == PersonsList.size() - 1) {
                        Shifts shiftskb = shiftsList.get(i - kb + 1);
                        String description = shiftskb.getDescription();
                        description = description + kb;
                        shiftskb.setDescription(description);
                        shiftsList.set(i - kb + 1, shiftskb);
                    }
                } else {
                    shifts.setDeptname(Persons.getJobcode());
                    Shifts shiftskb = shiftsList.get(i - kb);
                    String description = shiftskb.getDescription();
                    description = description + kb;
                    shiftskb.setDescription(description);
                    shiftsList.set(i - kb, shiftskb);
                    kb = 1;
                }
            } else {
                shifts.setDeptname(Persons.getJobcode());
                kb = 1;
            }
            shifts.setMobile(Persons.getWorkphone());
            shifts.setName(Persons.getFirstname() + Persons.getLastname());
            List<DateToWeek> dateToWeeklist = new ArrayList<DateToWeek>();
            for (int pk = 1; pk <= maxDate; pk++) {
                DateToWeek dateToWeek = new DateToWeek();
                str = String.valueOf(yearNow) + "-" + String.valueOf(monthNow)
                        + "-" + String.valueOf(pk);
                dateToWeek.setShifttypeName("/");
                dateToWeek.setWzDate(str);
                dateToWeeklist.add(dateToWeek);
            }
            shifts.setDateToWeeklist(dateToWeeklist);
            shiftsList.add(shifts);
        }
        int xb = 0;
        for (int j = 0; j < shiftsList.size(); j++) {
            Shifts shifts = shiftsList.get(j);
            List<DateToWeek> dateToWeeklist = shifts.getDateToWeeklist();
            List<Shifts> shiftsListbyBz = shiftsRepository
                    .findByNameOrderByShiftdate(
                            shifts.getDescription().split("&")[0], yearNow,
                            monthNow);
            if (shiftsListbyBz.size() > 0) {
                for (int pks = 0; pks < shiftsListbyBz.size(); pks++) {
                    DateToWeek dateToWeek = new DateToWeek();
                    String shifttype = shiftsListbyBz.get(pks).getShifttype();
                    String shifttypeName = shifttypesRepository.findOne(
                            Integer.valueOf(shifttype)).getShifttype();
                    String season = shifttypesRepository.findOne(
                            Integer.valueOf(shifttype)).getSeason();
                    String Shiftsidsl = String.valueOf(shiftsListbyBz.get(pks)
                            .getShiftsid());
                    // if (season.equals(seasonBz)) {
                    dateToWeek.setShifttype(shifttype);
                    dateToWeek.setShifttypeName(shifttypeName);
                    dateToWeek.setSeason(season);
                    dateToWeek.setShiftsid(Shiftsidsl);
                    String shiftdatea = shiftsListbyBz.get(pks).getShiftdate()
                            .toString();
                    dateToWeek.setWzDate(shiftdatea.split(" ")[0]);
                    shiftdatea = shiftdatea.substring(8, 10);
                    if (shiftdatea.substring(0, 1).equals("0")) {
                        xb = Integer.valueOf(shiftdatea.substring(1, 2)) - 1;
                    } else {
                        xb = Integer.valueOf(shiftdatea) - 1;
                    }
                    dateToWeeklist.set(xb, dateToWeek);
                }
                shifts.setDateToWeeklist(dateToWeeklist);
            }
            shiftsListView.add(shifts);
        }
        List<Shifts> shiftsListViewPageList = new ArrayList<Shifts>();
        int pageSize = 18;
        int pageNumber = Integer.valueOf(request.getParameter("pagef"));
        if (shiftsListView.size() <= pageSize) {
            shiftsListViewPageList = shiftsListView;
        } else {
            for (int k = 18 * (pageNumber - 1); k < 18 * (pageNumber - 1) + pageSize && k < shiftsListView.size(); k++) {
                shiftsListViewPageList.add(shiftsListView
                        .get(k));
            }
        }
        return shiftsListViewPageList;
    }

    public List<Shifttypes> shifttypesChangeList(
            List<Shifttypes> shifttypesListks) {
        List<Shifttypes> shifttypesList = new ArrayList<Shifttypes>();
        for (int i = 0; i < shifttypesListks.size(); i++) {
            Shifttypes shifttypes = shifttypesListks.get(i);
            Date startdateTime = shifttypes.getStarttime();
            Date enddateTime = shifttypes.getEndtime();
            String sd = startdateTime.toString();
            String ed = enddateTime.toString();
            sd = sd.substring(11, 16);
            ed = ed.substring(11, 16);
            String description = sd + "-" + ed;
            shifttypes.setDescription(description);
            shifttypesList.add(shifttypes);
        }
        return shifttypesList;
    }

    @RequestMapping(value = "/shifts/refreashMonth", method = RequestMethod.GET)
    public String refreashMonth(HttpServletRequest request,
                                HttpServletResponse response, Model model) {
        String seasonBz = "";
        String week = "";
        String str = "";
        String month = request.getParameter("month");
        int montht = Integer.valueOf(month);
        if (montht == 13) {
            Calendar cal = Calendar.getInstance();
            montht = cal.get(Calendar.MONTH) + 1;
        }
        String year = request.getParameter("year");
        if (montht <= 3 && montht >= 1) {
            seasonBz = "春季";
        }
        if (montht <= 6 && montht >= 4) {
            seasonBz = "夏季";
        }
        if (montht <= 9 && montht >= 7) {
            seasonBz = "秋季";
        }
        if (montht <= 12 && montht >= 10) {
            seasonBz = "冬季";
        }
        List<Shifttypes> shifttypesCjList = shifttypesRepository
                .findBySeason("春季");
        List<Shifttypes> shifttypesXjList = shifttypesRepository
                .findBySeason("夏季");
        List<Shifttypes> shifttypesQjList = shifttypesRepository
                .findBySeason("秋季");
        List<Shifttypes> shifttypesDjList = shifttypesRepository
                .findBySeason("冬季");
        model.addAttribute("shifttypesCjList",
                shifttypesChangeList(shifttypesCjList));
        model.addAttribute("shifttypesXjList",
                shifttypesChangeList(shifttypesXjList));
        model.addAttribute("shifttypesQjList",
                shifttypesChangeList(shifttypesQjList));
        model.addAttribute("shifttypesDjList",
                shifttypesChangeList(shifttypesDjList));
        model.addAttribute("yearNow", year);
        model.addAttribute("monthNow", month);
        int maxDate = getMonthLastDay(Integer.valueOf(year),
                Integer.valueOf(month));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<DateToWeek> dateToWeeks = new ArrayList<DateToWeek>();
        List<Shifts> shiftsListView = new ArrayList<Shifts>();
        try {
            for (int i = 1; i <= maxDate; i++) {
                DateToWeek dateToWeek = new DateToWeek();
                str = String.valueOf(year) + "-" + String.valueOf(month) + "-"
                        + String.valueOf(i);
                Date date = sdf.parse(str);
                week = getWeekOfDate(date);
                dateToWeek.setDat(String.valueOf(i));
                dateToWeek.setWeek(week);
                dateToWeeks.add(dateToWeek);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        List<Persons> PersonsList = usersRepository.selectUserorderBy();
        List<Shifts> shiftsList = new ArrayList<Shifts>();
        int kb = 1;
        for (int i = 0; i < PersonsList.size(); i++) {
            Shifts shifts = new Shifts();
            Persons Persons = PersonsList.get(i);
            shifts.setDescription(Persons.getUserid() + "&");
            if (i > 0) {
                Persons PersonsBefore = PersonsList.get(i - 1);
                if (Persons.getJobcode().equals(PersonsBefore.getJobcode())) {
                    shifts.setDeptname("");
                    kb = kb + 1;
                    if (i == PersonsList.size() - 1) {
                        Shifts shiftskb = shiftsList.get(i - kb + 1);
                        String description = shiftskb.getDescription();
                        description = description + kb;
                        shiftskb.setDescription(description);
                        shiftsList.set(i - kb + 1, shiftskb);
                    }
                } else {
                    shifts.setDeptname(Persons.getJobcode());
                    Shifts shiftskb = shiftsList.get(i - kb);
                    String description = shiftskb.getDescription();
                    description = description + kb;
                    shiftskb.setDescription(description);
                    shiftsList.set(i - kb, shiftskb);
                    kb = 1;
                }
            } else {
                shifts.setDeptname(Persons.getJobcode());
                kb = 1;
            }
            shifts.setMobile(Persons.getWorkphone());
            shifts.setName(Persons.getFirstname() + Persons.getLastname());
            List<DateToWeek> dateToWeeklist = new ArrayList<DateToWeek>();
            for (int pk = 1; pk <= maxDate; pk++) {
                DateToWeek dateToWeek = new DateToWeek();
                str = String.valueOf(year) + "-" + String.valueOf(month) + "-"
                        + String.valueOf(pk);
                dateToWeek.setShifttypeName("/");
                dateToWeek.setWzDate(str);
                dateToWeeklist.add(dateToWeek);
            }
            shifts.setDateToWeeklist(dateToWeeklist);
            shiftsList.add(shifts);
        }
        int xb = 0;
        for (int j = 0; j < shiftsList.size(); j++) {
            Shifts shifts = shiftsList.get(j);
            List<DateToWeek> dateToWeeklist = shifts.getDateToWeeklist();
            List<Shifts> shiftsListbyBz = shiftsRepository
                    .findByNameOrderByShiftdate(
                            shifts.getDescription().split("&")[0],
                            Integer.valueOf(year), montht);
            if (shiftsListbyBz.size() > 0) {
                for (int pks = 0; pks < shiftsListbyBz.size(); pks++) {
                    DateToWeek dateToWeek = new DateToWeek();
                    String shifttype = shiftsListbyBz.get(pks).getShifttype();
                    String shifttypeName = shifttypesRepository.findOne(
                            Integer.valueOf(shifttype)).getShifttype();
                    String season = shifttypesRepository.findOne(
                            Integer.valueOf(shifttype)).getSeason();
                    String Shiftsidsl = String.valueOf(shiftsListbyBz.get(pks)
                            .getShiftsid());
                    // if (season.equals(seasonBz)) {
                    dateToWeek.setShifttype(shifttype);
                    dateToWeek.setShifttypeName(shifttypeName);
                    dateToWeek.setSeason(season);
                    dateToWeek.setShiftsid(Shiftsidsl);
                    String shiftdatea = shiftsListbyBz.get(pks).getShiftdate()
                            .toString();
                    dateToWeek.setWzDate(shiftdatea.split(" ")[0]);
                    shiftdatea = shiftdatea.substring(8, 10);
                    if (shiftdatea.substring(0, 1).equals("0")) {
                        xb = Integer.valueOf(shiftdatea.substring(1, 2)) - 1;
                    } else {
                        xb = Integer.valueOf(shiftdatea) - 1;
                    }
                    dateToWeeklist.set(xb, dateToWeek);
                }
                shifts.setDateToWeeklist(dateToWeeklist);
            }
            shiftsListView.add(shifts);
        }
        List<Shifts> shiftsListViewPageList = new ArrayList<Shifts>();
        int pageSize = 18;
        if (shiftsListView.size() <= pageSize) {
            shiftsListViewPageList = shiftsListView;
        } else {
            for (int k = 0; k < pageSize; k++) {
                shiftsListViewPageList.add(shiftsListView
                        .get(k));
            }
        }
        int endIndex = 0;
        if (shiftsListView.size() > 0) {
            endIndex = shiftsListView.size() / 18;
            if (shiftsListView.size() % 18 != 0) {
                endIndex = endIndex + 1;
            }
        }
        model.addAttribute("dateToWeeks", dateToWeeks);
        model.addAttribute("monthchange", montht);
//		model.addAttribute("shiftsListView", shiftsListView);
        // List<Shifttypes> shifttypesList = shifttypesRepository.findAll();
        List<Shifttypes> shifttypesList = shifttypesRepository
                .findBySeason(seasonBz);
        model.addAttribute("shifttypesList", shifttypesList);
        model.addAttribute("shiftsListView", shiftsListViewPageList);
        model.addAttribute("endIndex", endIndex);
        return "shifts/shiftsView";
    }

    @RequestMapping(value = "/shifts/refreashYear", method = RequestMethod.GET)
    public String refreashYear(HttpServletRequest request,
                               HttpServletResponse response, Model model) {
        String seasonBz = "";
        String week = "";
        String str = "";
        String para = request.getParameter("para");
        String year = request.getParameter("year");
        int yearInt = Integer.valueOf(year);
        if (para.endsWith("before")) {
            yearInt = yearInt - 1;
        }
        if (para.endsWith("next")) {
            yearInt = yearInt + 1;
        }
        Calendar cal = Calendar.getInstance();
        int monthNow = cal.get(Calendar.MONTH) + 1;
        if (monthNow <= 3 && monthNow >= 1) {
            seasonBz = "春季";
        }
        if (monthNow <= 6 && monthNow >= 4) {
            seasonBz = "夏季";
        }
        if (monthNow <= 9 && monthNow >= 7) {
            seasonBz = "秋季";
        }
        if (monthNow <= 12 && monthNow >= 10) {
            seasonBz = "冬季";
        }
        List<Shifttypes> shifttypesCjList = shifttypesRepository
                .findBySeason("春季");
        List<Shifttypes> shifttypesXjList = shifttypesRepository
                .findBySeason("夏季");
        List<Shifttypes> shifttypesQjList = shifttypesRepository
                .findBySeason("秋季");
        List<Shifttypes> shifttypesDjList = shifttypesRepository
                .findBySeason("冬季");
        model.addAttribute("shifttypesCjList",
                shifttypesChangeList(shifttypesCjList));
        model.addAttribute("shifttypesXjList",
                shifttypesChangeList(shifttypesXjList));
        model.addAttribute("shifttypesQjList",
                shifttypesChangeList(shifttypesQjList));
        model.addAttribute("shifttypesDjList",
                shifttypesChangeList(shifttypesDjList));
        model.addAttribute("yearNow", yearInt);
        model.addAttribute("monthNow", monthNow);
        int maxDate = getMonthLastDay(yearInt, monthNow);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<DateToWeek> dateToWeeks = new ArrayList<DateToWeek>();
        List<Shifts> shiftsListView = new ArrayList<Shifts>();
        try {
            for (int i = 1; i <= maxDate; i++) {
                DateToWeek dateToWeek = new DateToWeek();
                str = String.valueOf(yearInt) + "-" + String.valueOf(monthNow)
                        + "-" + String.valueOf(i);
                Date date = sdf.parse(str);
                week = getWeekOfDate(date);
                dateToWeek.setDat(String.valueOf(i));
                dateToWeek.setWeek(week);
                dateToWeeks.add(dateToWeek);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        List<Persons> PersonsList = usersRepository.selectUserorderBy();
        List<Shifts> shiftsList = new ArrayList<Shifts>();
        int kb = 1;
        for (int i = 0; i < PersonsList.size(); i++) {
            Shifts shifts = new Shifts();
            Persons Persons = PersonsList.get(i);
            shifts.setDescription(Persons.getUserid() + "&");
            if (i > 0) {
                Persons PersonsBefore = PersonsList.get(i - 1);
                if (Persons.getJobcode().equals(PersonsBefore.getJobcode())) {
                    shifts.setDeptname("");
                    kb = kb + 1;
                    if (i == PersonsList.size() - 1) {
                        Shifts shiftskb = shiftsList.get(i - kb + 1);
                        String description = shiftskb.getDescription();
                        description = description + kb;
                        shiftskb.setDescription(description);
                        shiftsList.set(i - kb + 1, shiftskb);
                    }
                } else {
                    shifts.setDeptname(Persons.getJobcode());
                    Shifts shiftskb = shiftsList.get(i - kb);
                    String description = shiftskb.getDescription();
                    description = description + kb;
                    shiftskb.setDescription(description);
                    shiftsList.set(i - kb, shiftskb);
                    kb = 1;
                }
            } else {
                shifts.setDeptname(Persons.getJobcode());
                kb = 1;
            }
            shifts.setMobile(Persons.getWorkphone());
            shifts.setName(Persons.getFirstname() + Persons.getLastname());
            List<DateToWeek> dateToWeeklist = new ArrayList<DateToWeek>();
            for (int pk = 1; pk <= maxDate; pk++) {
                DateToWeek dateToWeek = new DateToWeek();
                str = String.valueOf(yearInt) + "-" + String.valueOf(monthNow)
                        + "-" + String.valueOf(pk);
                dateToWeek.setShifttypeName("/");
                dateToWeek.setWzDate(str);
                dateToWeeklist.add(dateToWeek);
            }
            shifts.setDateToWeeklist(dateToWeeklist);
            shiftsList.add(shifts);
        }
        int xb = 0;
        for (int j = 0; j < shiftsList.size(); j++) {
            Shifts shifts = shiftsList.get(j);
            List<DateToWeek> dateToWeeklist = shifts.getDateToWeeklist();
            List<Shifts> shiftsListbyBz = shiftsRepository
                    .findByNameOrderByShiftdate(
                            shifts.getDescription().split("&")[0],
                            Integer.valueOf(yearInt), monthNow);
            if (shiftsListbyBz.size() > 0) {
                for (int pks = 0; pks < shiftsListbyBz.size(); pks++) {
                    DateToWeek dateToWeek = new DateToWeek();
                    String shifttype = shiftsListbyBz.get(pks).getShifttype();
                    String shifttypeName = shifttypesRepository.findOne(
                            Integer.valueOf(shifttype)).getShifttype();
                    String season = shifttypesRepository.findOne(
                            Integer.valueOf(shifttype)).getSeason();
                    String Shiftsidsl = String.valueOf(shiftsListbyBz.get(pks)
                            .getShiftsid());
                    // if (season.equals(seasonBz)) {
                    dateToWeek.setShifttype(shifttype);
                    dateToWeek.setShifttypeName(shifttypeName);
                    dateToWeek.setSeason(season);
                    dateToWeek.setShiftsid(Shiftsidsl);
                    String shiftdatea = shiftsListbyBz.get(pks).getShiftdate()
                            .toString();
                    dateToWeek.setWzDate(shiftdatea.split(" ")[0]);
                    shiftdatea = shiftdatea.substring(8, 10);
                    if (shiftdatea.substring(0, 1).equals("0")) {
                        xb = Integer.valueOf(shiftdatea.substring(1, 2)) - 1;
                    } else {
                        xb = Integer.valueOf(shiftdatea) - 1;
                    }
                    dateToWeeklist.set(xb, dateToWeek);
                }
                shifts.setDateToWeeklist(dateToWeeklist);
            }
            shiftsListView.add(shifts);
        }
        List<Shifts> shiftsListViewPageList = new ArrayList<Shifts>();
        int pageSize = 18;
        if (shiftsListView.size() <= pageSize) {
            shiftsListViewPageList = shiftsListView;
        } else {
            for (int k = 0; k < pageSize; k++) {
                shiftsListViewPageList.add(shiftsListView
                        .get(k));
            }
        }
        int endIndex = 0;
        if (shiftsListView.size() > 0) {
            endIndex = shiftsListView.size() / 18;
            if (shiftsListView.size() % 18 != 0) {
                endIndex = endIndex + 1;
            }
        }
        model.addAttribute("dateToWeeks", dateToWeeks);
//		model.addAttribute("shiftsListView", shiftsListView);
        List<Shifttypes> shifttypesList = shifttypesRepository
                .findBySeason(seasonBz);
        model.addAttribute("shifttypesList", shifttypesList);
        model.addAttribute("monthchange", monthNow);
        model.addAttribute("shiftsListView", shiftsListViewPageList);
        model.addAttribute("endIndex", endIndex);
        return "shifts/shiftsView";
    }

    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    @RequestMapping(value = "/shifts/shifttypesUpdate")
    public
    @ResponseBody
    Map initShifttypesUpdate(HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        Map<String, String> map = new HashMap<String, String>();
        String shifttypesid = request.getParameter("shifttypesid");
        Shifttypes shifttypes = shifttypesRepository.findOne(Integer
                .valueOf(shifttypesid));
        String starttime = shifttypes.getStarttime().toString();
        String endtime = shifttypes.getEndtime().toString();
        starttime = starttime.substring(11, 16);
        endtime = endtime.substring(11, 16);
        map.put("shifttypesid", shifttypesid);
        map.put("shifttype", shifttypes.getShifttype());
        map.put("season", shifttypes.getSeason());
        map.put("starttime", starttime);
        map.put("endtime", endtime);
        map.put("description", shifttypes.getDescription());
        return map;
    }

    @RequestMapping(value = "/shifts/doSelect", method = RequestMethod.POST)
    public String doSelect(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        String shifttype = request.getParameter("shifttype");
        List<Shifttypes> shifttypesListks = shifttypesRepository
                .findByShifttypeContaining(shifttype);
        List<Shifttypes> shifttypesList = new ArrayList<Shifttypes>();
        List listsd = new ArrayList();
        List listed = new ArrayList();
        for (int i = 0; i < shifttypesListks.size(); i++) {
            Shifttypes shifttypes = shifttypesListks.get(i);
            Date startdateTime = shifttypes.getStarttime();
            Date enddateTime = shifttypes.getEndtime();
            String sd = startdateTime.toString();
            sd = sd.substring(11, 16);
            listsd.add(sd);
            String ed = enddateTime.toString();
            ed = ed.substring(11, 16);
            listed.add(ed);
            shifttypesList.add(shifttypes);
        }
        model.addAttribute("listsd", listsd);
        model.addAttribute("listed", listed);
        model.addAttribute("shifttypesList", shifttypesList);
        return "shifts/shifttypesList";
    }

    @RequestMapping(value = "/shifts/doUpdate", method = RequestMethod.POST)
    public String doUpdate(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        try {
            String ksTime = "2013-07-31 ";
            String shifttypesid = request.getParameter("shifttypesid");
            String shifttype = request.getParameter("shifttype");
            String season = request.getParameter("season");
            String starttime = request.getParameter("starttime");
            starttime = ksTime + starttime + ":00";
            String endtime = request.getParameter("endtime");
            endtime = ksTime + endtime + ":00";
            String description = request.getParameter("description");
            Shifttypes shifttypes = new Shifttypes();
            shifttypes.setShifttypesid(Integer.parseInt(shifttypesid));
            shifttypes.setShifttype(shifttype);
            shifttypes.setSeason(season);
            shifttypes.setDescription(description);
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            Date startdateTime = format.parse(starttime);
            Date enddateTime = format.parse(endtime);
            shifttypes.setStarttime(startdateTime);
            shifttypes.setEndtime(enddateTime);
            shifttypesRepository.save(shifttypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.initShifttypesList(model);
        return "redirect:shifttypesList";
    }

    @RequestMapping(value = "/shifts/insertOrUpdate")
    public
    @ResponseBody
    String insertOrUpdate(HttpServletRequest request,
                          HttpServletResponse response) {
        String shiftsid = request.getParameter("shiftsid");
        String userId = request.getParameter("userId");
        String wzDate = request.getParameter("wzDate");
        String shifttypesid = request.getParameter("shifttypesid");
        String shifttype = request.getParameter("shifttype");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Shifts shifts = new Shifts();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (shiftsid.equals("")) {
                if (shifttypesid.equals("sc")) {
                } else {
                    shifts.setName(userId);
                    shifts.setShifttype(shifttypesid);
                    shifts.setShiftdate(format.parse(wzDate));
                    shifts.setDeptname(PersonsRepository.findByUserid
                            (userId).getJobcode());
                    shifts.setMobile(PersonsRepository.findByUserid(userId).getWorkphone());
                    shiftsRepository.save(shifts);
                    Shiftwork shiftwork = new Shiftwork();
                    shiftwork.setShiftstartingbyid(Long.valueOf(userId));
                    shiftwork.setShiftstartingby(PersonsRepository.findByUserid(userId).getFirstname() + PersonsRepository.findByUserid(userId).getLastname()
                            + "_"
                            + PersonsRepository.findByUserid(userId).getWorkphone());
                    Shifttypes shifttypes = shifttypesRepository
                            .findOne(Integer.valueOf(shifttypesid));
                    String starttime = sdf.format(shifttypes.getStarttime());
                    String endtime = sdf.format(shifttypes.getEndtime());
                    starttime = wzDate + starttime.replace("2013-07-31", "");
                    if (endtime.replace("2013-07-31 ", "").equals("08:30:00")) {
                        wzDate = nextDate(wzDate);
                    }
                    endtime = wzDate + endtime.replace("2013-07-31", "");
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    shiftwork.setShiftstartingtime(df1.parse(starttime));
                    shiftwork.setShiftendingtime(df1.parse(endtime));
                    shiftwork.setShiftitems(PersonsRepository.findByUserid(userId).getJobcode());
                    List<Shiftwork> shiftworkList = shiftworkRepository
                            .selectShiftworkBy2(
                                    PersonsRepository.findByUserid(userId).getJobcode(), df1
                                            .parse(starttime));

                    Shiftwork shiftworkAfter = new Shiftwork();
                    shiftworkAfter.setShiftitems(PersonsRepository.findByUserid(userId).getJobcode());
                    shiftworkAfter.setShiftendingbyid(Long.valueOf(userId));
                    shiftworkAfter.setShiftendingby(PersonsRepository.findByUserid(userId).getFirstname() + PersonsRepository.findByUserid(userId).getLastname()
                            + "_"
                            + PersonsRepository.findByUserid(userId).getWorkphone());
                    String starttimeAfter = endtime;
                    shiftworkAfter.setShiftstartingtime(df1
                            .parse(starttimeAfter));
                    String endtimeAfter = "";
                    if (starttimeAfter.replace(wzDate + " ", "").equals(
                            "20:30:00")) {
                        wzDate = nextDate(wzDate);
                        endtimeAfter = wzDate + " 08:30:00";
                    }
                    if (starttimeAfter.replace(wzDate + " ", "").equals(
                            "08:30:00")) {
                        endtimeAfter = wzDate + " 20:30:00";
                    }
                    shiftworkAfter.setShiftendingtime(df1.parse(endtimeAfter));
                    List<Shiftwork> shiftworkAfterList = shiftworkRepository
                            .selectShiftworkBy1(
                                    PersonsRepository.findByUserid(userId).getJobcode(), df1
                                            .parse(endtime));
                    Shiftwork shiftworkend = shiftwork;
                    if (shiftworkList.size() > 0) {
                        shiftworkend = shiftworkList.get(0);
                        shiftworkend.setShiftstartingby(shiftwork
                                .getShiftstartingby());
                        shiftworkend.setShiftstartingbyid(shiftwork
                                .getShiftstartingbyid());
                    }
                    Shiftwork shiftworkendAfter = shiftworkAfter;
                    if (shiftworkAfterList.size() > 0) {
                        shiftworkendAfter = shiftworkAfterList.get(0);
                        shiftworkendAfter.setShiftendingbyid(shiftworkAfter
                                .getShiftendingbyid());
                        shiftworkendAfter.setShiftendingby(shiftworkAfter
                                .getShiftendingby());
                    }
                    if (shiftwork.getCreatedate() == null) {
                        shiftwork.setCreatedate(sdf
                                .parse("1999-01-01 00:00:00"));
                    }
                    if (shiftworkAfter.getCreatedate() == null) {
                        shiftworkAfter.setCreatedate(sdf
                                .parse("1999-01-01 00:00:00"));
                    }
                    shiftworkRepository.save(shiftworkend);
                    shiftworkRepository.save(shiftworkendAfter);
                }
            } else {
                if (shifttypesid.equals("sc")) {
                    shiftsRepository.delete(Long.valueOf(shiftsid));
                    Shifttypes shifttypes = shifttypesRepository
                            .findOne(Integer.valueOf(shifttype));
                    String starttime = sdf.format(shifttypes.getStarttime());
                    String endtime = sdf.format(shifttypes.getEndtime());
                    starttime = wzDate + starttime.replace("2013-07-31", "");
                    if (endtime.replace("2013-07-31 ", "").equals("08:30:00")) {
                        wzDate = nextDate(wzDate);
                    }
                    endtime = wzDate + endtime.replace("2013-07-31", "");
                    List<Shiftwork> shiftworkStartlist = shiftworkRepository
                            .findByStart(sdf.parse(starttime),
                                    Long.valueOf(userId));
                    for (int intStart = 0; intStart < shiftworkStartlist.size(); intStart++) {
                        Shiftwork shiftworkStart = shiftworkStartlist
                                .get(intStart);
                        if (shiftworkStart.getShiftendingbyid() == null) {
                            shiftworkRepository.delete(shiftworkStart
                                    .getShiftworkid());
                        } else {
                            shiftworkStart.setShiftstartingby(null);
                            shiftworkStart.setShiftstartingbyid(null);
                            shiftworkRepository.save(shiftworkStart);
                        }
                    }
                    List<Shiftwork> shiftworkEndlist = shiftworkRepository
                            .findByEnd(sdf.parse(endtime), Long.valueOf(userId));
                    for (int intEnd = 0; intEnd < shiftworkEndlist.size(); intEnd++) {
                        Shiftwork shiftworkEnd = shiftworkEndlist.get(intEnd);
                        if (shiftworkEnd.getShiftstartingbyid() == null) {
                            shiftworkRepository.delete(shiftworkEnd
                                    .getShiftworkid());
                        } else {
                            shiftworkEnd.setShiftendingbyid(null);
                            shiftworkEnd.setShiftendingby(null);
                            shiftworkRepository.save(shiftworkEnd);
                        }
                    }
                } else {
                    shifts = shiftsRepository.findOne(Long.valueOf(shiftsid));
                    shifts.setShiftsid(Long.valueOf(shiftsid));
                    shifts.setShifttype(shifttypesid);
                    shiftsRepository.save(shifts);
                    Shifttypes shifttypesBefore = shifttypesRepository
                            .findOne(Integer.valueOf(shifttype));
                    String starttimeBefore = sdf.format(shifttypesBefore
                            .getStarttime());
                    String endtimeBefore = sdf.format(shifttypesBefore
                            .getEndtime());
                    starttimeBefore = wzDate
                            + starttimeBefore.replace("2013-07-31", "");
                    if (endtimeBefore.replace("2013-07-31 ", "").equals(
                            "08:30:00")) {
                        wzDate = nextDate(wzDate);
                    }
                    endtimeBefore = wzDate
                            + endtimeBefore.replace("2013-07-31", "");
                    List<Shiftwork> shiftworkStartlist = shiftworkRepository
                            .findByStart(sdf.parse(starttimeBefore),
                                    Long.valueOf(userId));
                    for (int intStart = 0; intStart < shiftworkStartlist.size(); intStart++) {
                        Shiftwork shiftworkStart = shiftworkStartlist
                                .get(intStart);
                        if (shiftworkStart.getShiftendingbyid() == null) {
                            shiftworkRepository.delete(shiftworkStart
                                    .getShiftworkid());
                        } else {
                            shiftworkStart.setShiftstartingby(null);
                            shiftworkStart.setShiftstartingbyid(null);
                            shiftworkRepository.save(shiftworkStart);
                        }
                    }
                    List<Shiftwork> shiftworkEndlist = shiftworkRepository
                            .findByEnd(sdf.parse(endtimeBefore),
                                    Long.valueOf(userId));
                    for (int intEnd = 0; intEnd < shiftworkEndlist.size(); intEnd++) {
                        Shiftwork shiftworkEnd = shiftworkEndlist.get(intEnd);
                        if (shiftworkEnd.getShiftstartingbyid() == null) {
                            shiftworkRepository.delete(shiftworkEnd
                                    .getShiftworkid());
                        } else {
                            shiftworkEnd.setShiftendingbyid(null);
                            shiftworkEnd.setShiftendingby(null);
                            shiftworkRepository.save(shiftworkEnd);
                        }
                    }
                    Shiftwork shiftwork = new Shiftwork();
                    wzDate = request.getParameter("wzDate");
                    shiftwork.setShiftstartingbyid(Long.valueOf(userId));
                    shiftwork.setShiftstartingby(PersonsRepository.findByUserid(userId).getFirstname() + PersonsRepository.findByUserid(userId).getLastname()
                            + "_"
                            + PersonsRepository.findByUserid(userId).getWorkphone());
                    Shifttypes shifttypes = shifttypesRepository
                            .findOne(Integer.valueOf(shifttypesid));
                    String starttime = sdf.format(shifttypes.getStarttime());
                    String endtime = sdf.format(shifttypes.getEndtime());
                    starttime = wzDate + starttime.replace("2013-07-31", "");
                    if (endtime.replace("2013-07-31 ", "").equals("08:30:00")) {
                        wzDate = nextDate(wzDate);
                    }
                    endtime = wzDate + endtime.replace("2013-07-31", "");
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    shiftwork.setShiftstartingtime(df1.parse(starttime));
                    shiftwork.setShiftendingtime(df1.parse(endtime));
                    shiftwork.setShiftitems(PersonsRepository.findByUserid(userId).getJobcode());
                    List<Shiftwork> shiftworkList = shiftworkRepository
                            .selectShiftworkBy2(
                                    PersonsRepository.findByUserid(userId).getJobcode(), df1
                                            .parse(starttime));

                    Shiftwork shiftworkAfter = new Shiftwork();
                    shiftworkAfter.setShiftitems(PersonsRepository.findByUserid(userId).getJobcode());
                    shiftworkAfter.setShiftendingbyid(Long.valueOf(userId));
                    shiftworkAfter.setShiftendingby(PersonsRepository.findByUserid(userId).getFirstname() + PersonsRepository.findByUserid(userId).getLastname()
                            + "_"
                            + PersonsRepository.findByUserid(userId).getWorkphone());
                    String starttimeAfter = endtime;
                    shiftworkAfter.setShiftstartingtime(df1
                            .parse(starttimeAfter));
                    String endtimeAfter = "";
                    if (starttimeAfter.replace(wzDate + " ", "").equals(
                            "20:30:00")) {
                        wzDate = nextDate(wzDate);
                        endtimeAfter = wzDate + " 08:30:00";
                    }
                    if (starttimeAfter.replace(wzDate + " ", "").equals(
                            "08:30:00")) {
                        endtimeAfter = wzDate + " 20:30:00";
                    }
                    shiftworkAfter.setShiftendingtime(df1.parse(endtimeAfter));
                    List<Shiftwork> shiftworkAfterList = shiftworkRepository
                            .selectShiftworkBy1(
                                    PersonsRepository.findByUserid(userId).getJobcode(), df1
                                            .parse(endtime));
                    Shiftwork shiftworkend = shiftwork;
                    if (shiftworkList.size() > 0) {
                        shiftworkend = shiftworkList.get(0);
                        shiftworkend.setShiftstartingby(shiftwork
                                .getShiftstartingby());
                        shiftworkend.setShiftstartingbyid(shiftwork
                                .getShiftstartingbyid());
                    }
                    Shiftwork shiftworkendAfter = shiftworkAfter;
                    if (shiftworkAfterList.size() > 0) {
                        shiftworkendAfter = shiftworkAfterList.get(0);
                        shiftworkendAfter.setShiftendingbyid(shiftworkAfter
                                .getShiftendingbyid());
                        shiftworkendAfter.setShiftendingby(shiftworkAfter
                                .getShiftendingby());
                    }
                    if (shiftwork.getCreatedate() == null) {
                        shiftwork.setCreatedate(sdf
                                .parse("1999-01-01 00:00:00"));
                    }
                    if (shiftworkAfter.getCreatedate() == null) {
                        shiftworkAfter.setCreatedate(sdf
                                .parse("1999-01-01 00:00:00"));
                    }
                    shiftworkRepository.save(shiftworkend);
                    shiftworkRepository.save(shiftworkendAfter);
                }
            }
            return "success";
        } catch (Exception e) {
            return "success";
        }
    }

    // @RequestMapping(value = "/shifts/deleteqX")
    // public String deleteqX(HttpServletRequest request,
    // HttpServletResponse response) {
    // String shiftsid = request.getParameter("shiftsid");
    // try {
    // if (shiftsid.equals("")) {
    // } else {
    // shiftsRepository.delete(Long.valueOf(shiftsid));
    // }
    // return "success";
    // } catch (Exception e) {
    // return "success";
    // }
    // }

    @RequestMapping(value = "/shifts/deleteShifttypes")
    public String deleteShifttypes(HttpServletRequest request,
                                   HttpServletResponse response) {
        String shifttypesid = request.getParameter("shifttypesid");
        try {
            shifttypesRepository.delete(Integer.valueOf(shifttypesid));
            return "redirect:shifttypesList";
        } catch (Exception e) {
            return "";
        }
    }

    @RequestMapping(value = "/shifts/getShifttypes")
    public
    @ResponseBody
    List<Shifttypes> initGetShifttypes(Model model) {
        List<Shifttypes> shifttypesListks = shifttypesRepository.findAll();
        List<Shifttypes> shifttypesList = new ArrayList<Shifttypes>();
        for (int i = 0; i < shifttypesListks.size(); i++) {
            Shifttypes shifttypes = shifttypesListks.get(i);
            Date startdateTime = shifttypes.getStarttime();
            Date enddateTime = shifttypes.getEndtime();
            String sd = startdateTime.toString();
            String ed = enddateTime.toString();
            sd = sd.substring(11, 16);
            ed = ed.substring(11, 16);
            String description = sd + "-" + ed;
            shifttypes.setDescription(description);
            shifttypesList.add(shifttypes);
        }
        return shifttypesListks;
    }

    @RequestMapping(value = "/shifts/getUsers")
    public
    @ResponseBody
    List<Persons> getPersons(HttpServletRequest request, HttpServletResponse response) {
        List<Users> users = usersRepository.findAll();
        List<Persons> personses = new ArrayList<Persons>();
        for (int i = 0; i < users.size(); i++) {
            Users user = users.get(i);
            Persons Persons = PersonsRepository.findByUserid(user.getUserid());
            personses.add(Persons);
        }
        return personses;
    }

    @RequestMapping(value = "/shifts/shiftworkView", method = RequestMethod.GET)
    public String initShiftworkView(Model model) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nowdate = format.format(date);
        String fmd = nowdate.substring(5, 7);
        if (fmd.substring(0, 1).equals("0")) {
            fmd = fmd.substring(1, 2);
        }
        String fdd = nowdate.substring(8, 10);
        if (fdd.substring(0, 1).equals("0")) {
            fdd = fdd.substring(1, 2);
        }
        String nowdateJt = fmd + "/" + fdd;
        model.addAttribute("nowdateJt", nowdateJt);
        model.addAttribute("nowdate", nowdate);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dateNext = calendar.getTime();
        String nextdate = format.format(dateNext);
        String tmd = nextdate.substring(5, 7);
        if (tmd.substring(0, 1).equals("0")) {
            tmd = tmd.substring(1, 2);
        }
        String tdd = nextdate.substring(8, 10);
        if (tdd.substring(0, 1).equals("0")) {
            tdd = tdd.substring(1, 2);
        }
        String nextdateJt = tmd + "/" + tdd;
        model.addAttribute("nextdateJt", nextdateJt);
        model.addAttribute("nextdate", nextdate);
        model.addAttribute("yc", "-1");
        return "shifts/shiftworkView";
    }

    @RequestMapping(value = "/shifts/refreashDate", method = RequestMethod.GET)
    public String initRefreashDate(HttpServletRequest request,
                                   HttpServletResponse response, Model model) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String nowdate = request.getParameter("nowdate");
            String nextdate = request.getParameter("nextdate");
            String para = request.getParameter("para");
            Date nowDate = format.parse(nowdate);
            Date nextDate = format.parse(nextdate);
            if (para.equals("next")) {
                nowdate = format
                        .format((nowDate.getTime() + 1000 * 60 * 60 * 24));
                nextdate = format
                        .format((nextDate.getTime() + 1000 * 60 * 60 * 24));
            } else {
                nowdate = format
                        .format((nowDate.getTime() - 1000 * 60 * 60 * 24));
                nextdate = format
                        .format((nextDate.getTime() - 1000 * 60 * 60 * 24));
            }
            String fmd = nowdate.substring(5, 7);
            if (fmd.substring(0, 1).equals("0")) {
                fmd = fmd.substring(1, 2);
            }
            String fdd = nowdate.substring(8, 10);
            if (fdd.substring(0, 1).equals("0")) {
                fdd = fdd.substring(1, 2);
            }
            String nowdateJt = fmd + "/" + fdd;
            model.addAttribute("nowdateJt", nowdateJt);
            model.addAttribute("nowdate", nowdate);
            String tmd = nextdate.substring(5, 7);
            if (tmd.substring(0, 1).equals("0")) {
                tmd = tmd.substring(1, 2);
            }
            String tdd = nextdate.substring(8, 10);
            if (tdd.substring(0, 1).equals("0")) {
                tdd = tdd.substring(1, 2);
            }
            String nextdateJt = tmd + "/" + tdd;
            model.addAttribute("nextdateJt", nextdateJt);
            model.addAttribute("nextdate", nextdate);
            model.addAttribute("yc", "-1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "shifts/shiftworkView";
    }

    @RequestMapping(value = "/shifts/selectWorkByTime", method = RequestMethod.GET)
    public String selectWorkByTime(HttpServletRequest request,
                                   HttpServletResponse response, Model model) {
        try {
            String paraTime = request.getParameter("paraTime");
            String nowdate = request.getParameter("nowdate");
            String nextdate = request.getParameter("nextdate");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Shiftwork> shiftworkList = new ArrayList<Shiftwork>();
            String paraT = "";
            if (paraTime.equals("1")) {
                paraT = nowdate + " 08:30:00";
            }
            if (paraTime.equals("2")) {
                paraT = nowdate + " 20:30:00";
            }
            shiftworkList = shiftworkRepository.selectWorkByStartTime(df1
                    .parse(paraT));
            model.addAttribute("shiftworkList", shiftworkList);
            String fmd = nowdate.substring(5, 7);
            if (fmd.substring(0, 1).equals("0")) {
                fmd = fmd.substring(1, 2);
            }
            String fdd = nowdate.substring(8, 10);
            if (fdd.substring(0, 1).equals("0")) {
                fdd = fdd.substring(1, 2);
            }
            String nowdateJt = fmd + "/" + fdd;
            model.addAttribute("nowdateJt", nowdateJt);
            model.addAttribute("nowdate", nowdate);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String tmd = nextdate.substring(5, 7);
            if (tmd.substring(0, 1).equals("0")) {
                tmd = tmd.substring(1, 2);
            }
            String tdd = nextdate.substring(8, 10);
            if (tdd.substring(0, 1).equals("0")) {
                tdd = tdd.substring(1, 2);
            }
            String nextdateJt = tmd + "/" + tdd;
            model.addAttribute("nextdateJt", nextdateJt);
            model.addAttribute("nextdate", nextdate);
            model.addAttribute("yc", paraTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("bzTc", "0");
        return "shifts/shiftworkView";
    }

    private String lastDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date nowDate = format.parse(strDate);
            strDate = format.format((nowDate.getTime() - 1000 * 60 * 60 * 24));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return strDate;
    }

    private String nextDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date nowDate = format.parse(strDate);
            strDate = format.format((nowDate.getTime() + 1000 * 60 * 60 * 24));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return strDate;
    }

    @RequestMapping(value = "/shifts/shiftworkEdit", method = RequestMethod.GET)
    public String shiftworkEdit(HttpServletRequest request,
                                HttpServletResponse response, Model model) {
        try {
            String strDate = "";
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = new Date();
            String shiftworkid = request.getParameter("shiftworkid");
            String deptName = request.getParameter("deptName");
            Shiftwork shiftwork = shiftworkRepository.findOne(Long
                    .valueOf(shiftworkid));
            String paraTime = request.getParameter("paraTime");
            String nowdate = request.getParameter("nowdate");
            String nextdate = request.getParameter("nextdate");
            if (df1.format(shiftwork.getCreatedate()).subSequence(0, 4)
                    .equals("1999")) {
                strDate = df1.format(dateTime);
                strDate = strDate.substring(11, 19);
            } else {
                dateTime = shiftwork.getCreatedate();
                strDate = df1.format(dateTime);
                strDate = strDate.substring(11, 19);
            }
            model.addAttribute("shiftwork", shiftwork);
            model.addAttribute("deptName", deptName);
            model.addAttribute("paraTime", paraTime);
            model.addAttribute("nowdate", nowdate);
            model.addAttribute("nextdate", nextdate);
            model.addAttribute("strDate", strDate);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            List<Shiftwork> shiftworkList = new ArrayList<Shiftwork>();
            String paraT = "";
            if (paraTime.equals("1")) {
                paraT = nowdate + " 08:30:00";
            }
            if (paraTime.equals("2")) {
                paraT = nowdate + " 20:30:00";
            }
            shiftworkList = shiftworkRepository.selectWorkByStartTime(df1
                    .parse(paraT));
            model.addAttribute("shiftworkList", shiftworkList);
            String fmd = nowdate.substring(5, 7);
            if (fmd.substring(0, 1).equals("0")) {
                fmd = fmd.substring(1, 2);
            }
            String fdd = nowdate.substring(8, 10);
            if (fdd.substring(0, 1).equals("0")) {
                fdd = fdd.substring(1, 2);
            }
            String nowdateJt = fmd + "/" + fdd;
            model.addAttribute("nowdateJt", nowdateJt);
            model.addAttribute("nowdate", nowdate);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String tmd = nextdate.substring(5, 7);
            if (tmd.substring(0, 1).equals("0")) {
                tmd = tmd.substring(1, 2);
            }
            String tdd = nextdate.substring(8, 10);
            if (tdd.substring(0, 1).equals("0")) {
                tdd = tdd.substring(1, 2);
            }
            String nextdateJt = tmd + "/" + tdd;
            model.addAttribute("nextdateJt", nextdateJt);
            model.addAttribute("nextdate", nextdate);
            model.addAttribute("yc", paraTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("bzTc", "1");
        return "shifts/shiftworkView";
    }

    @RequestMapping(value = "/shifts/updateshiftwork", method = RequestMethod.POST)
    public String updateshiftwork(HttpServletRequest request,
                                  HttpServletResponse response, Model model) {
        try {
            String shiftworkid = request.getParameter("shiftworkid");
            String workcontent = request.getParameter("workcontent");
            String focuscontent = request.getParameter("focuscontent");
            String leadassign = request.getParameter("leadassign");
            String comments = request.getParameter("comments");
            Date dateTime = new Date();
            Shiftwork shiftwork = shiftworkRepository.findOne(Long
                    .valueOf(shiftworkid));
            shiftwork.setShiftworkid(Long.valueOf(shiftworkid));
            shiftwork.setCreatedate(dateTime);
            shiftwork.setComments(comments);
            shiftwork.setFocuscontent(focuscontent);
            shiftwork.setLeadassign(leadassign);
            shiftwork.setWorkcontent(workcontent);
            shiftworkRepository.save(shiftwork);
            String paraTime = request.getParameter("paraTime");
            String nowdate = request.getParameter("nowdate");
            String nextdate = request.getParameter("nextdate");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<Shiftwork> shiftworkList = new ArrayList<Shiftwork>();
            String paraT = "";
            if (paraTime.equals("1")) {
                paraT = nowdate + " 08:30:00";
            }
            if (paraTime.equals("2")) {
                paraT = nowdate + " 20:30:00";
            }
            shiftworkList = shiftworkRepository.selectWorkByStartTime(df1
                    .parse(paraT));
            model.addAttribute("shiftworkList", shiftworkList);
            String fmd = nowdate.substring(5, 7);
            if (fmd.substring(0, 1).equals("0")) {
                fmd = fmd.substring(1, 2);
            }
            String fdd = nowdate.substring(8, 10);
            if (fdd.substring(0, 1).equals("0")) {
                fdd = fdd.substring(1, 2);
            }
            String nowdateJt = fmd + "/" + fdd;
            model.addAttribute("nowdateJt", nowdateJt);
            model.addAttribute("nowdate", nowdate);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String tmd = nextdate.substring(5, 7);
            if (tmd.substring(0, 1).equals("0")) {
                tmd = tmd.substring(1, 2);
            }
            String tdd = nextdate.substring(8, 10);
            if (tdd.substring(0, 1).equals("0")) {
                tdd = tdd.substring(1, 2);
            }
            String nextdateJt = tmd + "/" + tdd;
            model.addAttribute("nextdateJt", nextdateJt);
            model.addAttribute("nextdate", nextdate);
            model.addAttribute("yc", paraTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("bzTc", "0");
        return "shifts/shiftworkView";
    }

    @RequestMapping(value = "/shifts/operatingListView", method = RequestMethod.GET)
    public String operatingListView(Model model) {
        return "shifts/operatingList";
    }

    @RequestMapping(value = "/shifts/operatingView", method = RequestMethod.GET)
    public String initoperatingView(Model model) {
        return "shifts/operatingView";
    }

    @RequestMapping(value = "/shifts/operatingBpdView", method = RequestMethod.GET)
    public String initoperatingBpdView(Model model) {
        return "shifts/operatingBpdView";
    }

    @RequestMapping(value = "/shifts/operatingDtView", method = RequestMethod.GET)
    public String initoperatingDtView(Model model) {
        return "shifts/operatingDtView";
    }

    @RequestMapping(value = "/shifts/operatingAhuView", method = RequestMethod.GET)
    public String initoperatingAhuView(HttpServletRequest request,
                                       HttpServletResponse response, Model model) {
        String querytime = request.getParameter("querytime");
        model.addAttribute("querytime", querytime);
        return "shifts/operatingAhuView";
    }

    @RequestMapping(value = "/shifts/operatingFahuView", method = RequestMethod.GET)
    public String initoperatingFahuView(HttpServletRequest request,
                                        HttpServletResponse response, Model model) {
        String querytime = request.getParameter("querytime");
        model.addAttribute("querytime", querytime);
        return "shifts/operatingFahuView";
    }

    @RequestMapping(value = "/shifts/operatingSlView", method = RequestMethod.GET)
    public String initoperatingSlView(Model model) {
        return "shifts/operatingSlView";
    }

    @RequestMapping(value = "/shifts/operatingSnView", method = RequestMethod.GET)
    public String initoperatingSnView(Model model) {
        return "shifts/operatingSnView";
    }

    @RequestMapping(value = "/shifts/operatingZslView", method = RequestMethod.GET)
    public String initoperatingZslView(Model model) {
        return "shifts/operatingZslView";
    }

    @RequestMapping(value = "/shifts/getAssetRunLog", method = RequestMethod.GET)
    @ResponseBody
    public Object getRunLog(Model model,
                            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                            @RequestParam(value = "category", required = true, defaultValue = "AUTO") String category) {
        Pageable pageable=new PageRequest(pageNumber,pageSize);
        Page<OpLogs> page=opLogService.findByCategory(category, pageable);

        return page;
    }
}
