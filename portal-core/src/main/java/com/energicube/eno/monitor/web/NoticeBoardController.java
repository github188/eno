package com.energicube.eno.monitor.web;

import com.energicube.eno.common.PageProperties;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.monitor.model.NoticeBoard;
import com.energicube.eno.monitor.model.OpLogs;
import com.energicube.eno.monitor.model.Shiftwork;
import com.energicube.eno.monitor.model.Users;
import com.energicube.eno.monitor.repository.OpLogRepository;
import com.energicube.eno.monitor.repository.ShiftworkRepository;
import com.energicube.eno.monitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class NoticeBoardController extends BaseController {

    @Autowired
    private ShiftworkRepository shiftworkRepository;

    @Autowired
    private OpLogRepository opLogRepository;

    @Autowired
    private UserService userService;

    // 公告板消息汇总
    @RequestMapping(value = "/noticeBoard/noticeBoardView", method = RequestMethod.GET)
    public String initnoticeBoard(Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            Date date1 = sdf1.parse(sdf.format(date) + " 00:00:00");
            Date date2 = sdf1.parse(sdf1.format(date));
            List<Shiftwork> shiftworkList = shiftworkRepository
                    .findByNameOrderBy(date1, date2);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < shiftworkList.size(); i++) {
                Shiftwork shiftwork = shiftworkList.get(i);
                String shiftstartingby = shiftwork.getShiftstartingby();
                String shiftendingby = shiftwork.getShiftendingby();
                if (shiftstartingby != null) {
                    NoticeBoard noticeBoard = new NoticeBoard();
                    noticeBoard.setMsType("交接班");
                    if (shiftendingby != null) {
                        String strStrart[] = shiftstartingby.split("_");
                        String strEnd[] = shiftendingby.split("_");
                        noticeBoard.setMsContent(strEnd[0] + "交接给"
                                + strStrart[0]);
                        noticeBoard.setResponsiblePerson(strEnd[0]);
                    } else {
                        String strStrart[] = shiftstartingby.split("_");
                        noticeBoard.setMsContent(strStrart[0] + "接班");
                        noticeBoard.setResponsiblePerson(strStrart[0]);
                    }
                    Date shifDate = shiftwork.getShiftstartingtime();
                    noticeBoard.setMomentTime(shifDate);
                    noticeBoard.setMoment(sdf1.format(shifDate));
                    noticeBoardList.add(noticeBoard);
                }
            }
            List<OpLogs> ucWeatherList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_WEATER);
            for (int i = 0; i < ucWeatherList.size(); i++) {
                OpLogs ucWeather = ucWeatherList.get(i);
                String startDate = ucWeather.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("天气");
                noticeBoard.setResponsiblePerson(ucWeather.getUserid());
                noticeBoard.setMsContent(ucWeather.getMessage());
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoardList.add(noticeBoard);
            }
            List<OpLogs> ucHolidayList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_HOLIDAY);
            for (int i = 0; i < ucHolidayList.size(); i++) {
                OpLogs ucHoliday = ucHolidayList.get(i);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("节假日");
                noticeBoard.setResponsiblePerson(ucHoliday.getUserid());
                String startDate = ucHoliday.getDated().substring(0, 19);
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setMsContent(ucHoliday.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            List<OpLogs> eventList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_EVENT);
            for (int i = 0; i < eventList.size(); i++) {
                OpLogs event = eventList.get(i);
                String startDate = event.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("活动");
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setResponsiblePerson(event.getUserid());
                noticeBoard.setMsContent(event.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            List<OpLogs> ucPatternRecordList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_PATTERN);
            for (int i = 0; i < ucPatternRecordList.size(); i++) {
                OpLogs ucPatternRecord = ucPatternRecordList.get(i);
                String startDate = ucPatternRecord.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("运行模式");
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setResponsiblePerson(ucPatternRecord.getUserid());
                noticeBoard.setMsContent(ucPatternRecord.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            List<NoticeBoard> noticeBoardListByTimeList = noticeBoardListByTime(noticeBoardList);
            List<NoticeBoard> noticeBoardListPageList = PageProperties.getNbStartPageList(noticeBoardListByTimeList);
            int endIndex = PageProperties.getNbEndIndex(noticeBoardListByTimeList);
            for (int i = 0; i < noticeBoardListPageList.size(); i++) {
                NoticeBoard noticeBoard = noticeBoardListPageList.get(i);
                if (!noticeBoard.getMsType().equals("交接班")) {
                    Users users = userService.findUsersByLoginid(noticeBoard.getResponsiblePerson());
                    noticeBoard.setResponsiblePerson("");
                    if (users.getUserid() != null)
                        noticeBoard.setResponsiblePerson(userService.findPersonsByUserid(users.getUserid()).getFirstname() + userService.findPersonsByUserid(users.getUserid()).getLastname());
                    noticeBoardListPageList.set(i, noticeBoard);
                }
            }
            model.addAttribute("bzw", "1");
            model.addAttribute("endIndex", endIndex);
            model.addAttribute("noticeBoardList", noticeBoardListPageList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "noticeBoard/noticeBoardView";
    }

    // 公告板消息汇总
    @RequestMapping(value = "/noticeBoard/selectNoticeBoardPage", method = RequestMethod.GET)
    public
    @ResponseBody
    List<NoticeBoard> selectNoticeBoardPage(Model model,
                                            HttpServletRequest request, HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            Date date1 = sdf1.parse(sdf.format(date) + " 00:00:00");
            Date date2 = sdf1.parse(sdf1.format(date));
            List<Shiftwork> shiftworkList = shiftworkRepository
                    .findByNameOrderBy(date1, date2);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < shiftworkList.size(); i++) {
                Shiftwork shiftwork = shiftworkList.get(i);
                String shiftstartingby = shiftwork.getShiftstartingby();
                String shiftendingby = shiftwork.getShiftendingby();
                if (shiftstartingby != null) {
                    NoticeBoard noticeBoard = new NoticeBoard();
                    noticeBoard.setMsType("交接班");
                    if (shiftendingby != null) {
                        String strStrart[] = shiftstartingby.split("_");
                        String strEnd[] = shiftendingby.split("_");
                        noticeBoard.setMsContent(strEnd[0] + "交接给"
                                + strStrart[0]);
                        noticeBoard.setResponsiblePerson(strEnd[0]);
                    } else {
                        String strStrart[] = shiftstartingby.split("_");
                        noticeBoard.setMsContent(strStrart[0] + "接班");
                        noticeBoard.setResponsiblePerson(strStrart[0]);
                    }
                    Date shifDate = shiftwork.getShiftstartingtime();
                    noticeBoard.setMomentTime(shifDate);
                    noticeBoard.setMoment(sdf1.format(shifDate));
                    noticeBoardList.add(noticeBoard);
                }
            }
            List<OpLogs> ucWeatherList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_WEATER);
            for (int i = 0; i < ucWeatherList.size(); i++) {
                OpLogs ucWeather = ucWeatherList.get(i);
                String startDate = ucWeather.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("天气");
                noticeBoard.setResponsiblePerson(ucWeather.getUserid());
                noticeBoard.setMsContent(ucWeather.getMessage());
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoardList.add(noticeBoard);
            }
            List<OpLogs> ucHolidayList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_HOLIDAY);
            for (int i = 0; i < ucHolidayList.size(); i++) {
                OpLogs ucHoliday = ucHolidayList.get(i);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("节假日");
                noticeBoard.setResponsiblePerson(ucHoliday.getUserid());
                String startDate = ucHoliday.getDated().substring(0, 19);
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setMsContent(ucHoliday.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            List<OpLogs> eventList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_EVENT);
            for (int i = 0; i < eventList.size(); i++) {
                OpLogs event = eventList.get(i);
                String startDate = event.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("活动");
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setResponsiblePerson(event.getUserid());
                noticeBoard.setMsContent(event.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            List<OpLogs> ucPatternRecordList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_PATTERN);
            for (int i = 0; i < ucPatternRecordList.size(); i++) {
                OpLogs ucPatternRecord = ucPatternRecordList.get(i);
                String startDate = ucPatternRecord.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("运行模式");
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setResponsiblePerson(ucPatternRecord.getUserid());
                noticeBoard.setMsContent(ucPatternRecord.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            List<NoticeBoard> noticeBoardListByTimeList = noticeBoardListByTime(noticeBoardList);
            int pageNumber = Integer.valueOf(request.getParameter("pagef"));
            noticeBoardListPageList = PageProperties.getNbPagefList(noticeBoardListByTimeList, pageNumber);
            for (int i = 0; i < noticeBoardListPageList.size(); i++) {
                NoticeBoard noticeBoard = noticeBoardListPageList.get(i);
                if (!noticeBoard.getMsType().equals("交接班")) {
                    Users users = userService.findUsersByLoginid(noticeBoard.getResponsiblePerson());
                    noticeBoard.setResponsiblePerson("");
                    if (users.getUserid() != null)
                        noticeBoard.setResponsiblePerson(userService.findPersonsByUserid(users.getUserid()).getFirstname() + userService.findPersonsByUserid(users.getUserid()).getLastname());
                    noticeBoardListPageList.set(i, noticeBoard);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noticeBoardListPageList;
    }

    // 交接班消息
    @RequestMapping(value = "/noticeBoard/noticeShiftsView", method = RequestMethod.GET)
    public String initnoticeShiftsView(Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            Date date1 = sdf1.parse(sdf.format(date) + " 00:00:00");
            Date date2 = sdf1.parse(sdf1.format(date));
            List<Shiftwork> shiftworkList = shiftworkRepository
                    .findByNameOrderBy(date1, date2);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < shiftworkList.size(); i++) {
                Shiftwork shiftwork = shiftworkList.get(i);
                String shiftstartingby = shiftwork.getShiftstartingby();
                String shiftendingby = shiftwork.getShiftendingby();
                if (shiftstartingby != null) {
                    NoticeBoard noticeBoard = new NoticeBoard();
                    noticeBoard.setMsType("交接班");
                    if (shiftendingby != null) {
                        String strStrart[] = shiftstartingby.split("_");
                        String strEnd[] = shiftendingby.split("_");
                        noticeBoard.setMsContent(strEnd[0] + "交接给"
                                + strStrart[0]);
                        noticeBoard.setResponsiblePerson(strEnd[0]);
                    } else {
                        String strStrart[] = shiftstartingby.split("_");
                        noticeBoard.setMsContent(strStrart[0] + "接班");
                        noticeBoard.setResponsiblePerson(strStrart[0]);
                    }
                    Date shifDate = shiftwork.getShiftstartingtime();
                    noticeBoard.setMomentTime(shifDate);
                    noticeBoard.setMoment(sdf1.format(shifDate));
                    noticeBoardList.add(noticeBoard);
                }
            }
            noticeBoardListPageList = PageProperties.getNbStartPageList(noticeBoardList);
            int endIndex = PageProperties.getNbEndIndex(noticeBoardList);
            model.addAttribute("endIndex", endIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("noticeBoardList", noticeBoardListPageList);
        model.addAttribute("bzw", "6");
        return "noticeBoard/noticeBoardView";
    }

    // 交接班消息
    @RequestMapping(value = "/noticeBoard/selectNoticeBoardNoticeShiftsPage", method = RequestMethod.GET)
    public
    @ResponseBody
    List<NoticeBoard> selectNoticeBoardNoticeShiftsPage(Model model,
                                                        HttpServletRequest request, HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            Date date1 = sdf1.parse(sdf.format(date) + " 00:00:00");
            Date date2 = sdf1.parse(sdf1.format(date));
            List<Shiftwork> shiftworkList = shiftworkRepository
                    .findByNameOrderBy(date1, date2);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < shiftworkList.size(); i++) {
                Shiftwork shiftwork = shiftworkList.get(i);
                String shiftstartingby = shiftwork.getShiftstartingby();
                String shiftendingby = shiftwork.getShiftendingby();
                if (shiftstartingby != null) {
                    NoticeBoard noticeBoard = new NoticeBoard();
                    noticeBoard.setMsType("交接班");
                    if (shiftendingby != null) {
                        String strStrart[] = shiftstartingby.split("_");
                        String strEnd[] = shiftendingby.split("_");
                        noticeBoard.setMsContent(strEnd[0] + "交接给"
                                + strStrart[0]);
                        noticeBoard.setResponsiblePerson(strEnd[0]);
                    } else {
                        String strStrart[] = shiftstartingby.split("_");
                        noticeBoard.setMsContent(strStrart[0] + "接班");
                        noticeBoard.setResponsiblePerson(strStrart[0]);
                    }
                    Date shifDate = shiftwork.getShiftstartingtime();
                    noticeBoard.setMomentTime(shifDate);
                    noticeBoard.setMoment(sdf1.format(shifDate));
                    noticeBoardList.add(noticeBoard);
                }
            }
            int pageNumber = Integer.valueOf(request.getParameter("pagef"));
            noticeBoardListPageList = PageProperties.getNbPagefList(noticeBoardList, pageNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noticeBoardListPageList;
    }

    // 根据时间冒泡排序
    public List<NoticeBoard> noticeBoardListByTime(
            List<NoticeBoard> noticeBoardList) {
        for (int i = 0; i < noticeBoardList.size(); i++) {
            for (int j = 0; j < noticeBoardList.size() - i - 1; j++) {
                NoticeBoard noticeBoard1 = noticeBoardList.get(j);
                Date c1 = noticeBoard1.getMomentTime();
                NoticeBoard noticeBoard2 = noticeBoardList.get(j + 1);
                Date c2 = noticeBoard2.getMomentTime();
                if (c1.getTime() < c2.getTime()) {
                    Date temp = c2;
                    noticeBoard2.setMomentTime(temp);
                    noticeBoardList.set(j + 1, noticeBoard1);
                    noticeBoardList.set(j, noticeBoard2);
                }
            }
        }
        return noticeBoardList;
    }

    // 活动消息
    @RequestMapping(value = "/noticeBoard/activityView", method = RequestMethod.GET)
    public String initactivityView(Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            List<OpLogs> eventList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_EVENT);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < eventList.size(); i++) {
                OpLogs event = eventList.get(i);
                String startDate = event.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("活动");
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setResponsiblePerson(event.getUserid());
                noticeBoard.setMsContent(event.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            noticeBoardListPageList = PageProperties.getNbStartPageList(noticeBoardList);
            int endIndex = PageProperties.getNbEndIndex(noticeBoardList);
            model.addAttribute("endIndex", endIndex);
            for (int i = 0; i < noticeBoardListPageList.size(); i++) {
                NoticeBoard noticeBoard = noticeBoardListPageList.get(i);
                Users users = userService.findUsersByLoginid(noticeBoard.getResponsiblePerson());
                noticeBoard.setResponsiblePerson("");
                if (users.getUserid() != null)
                    noticeBoard.setResponsiblePerson(userService.findPersonsByUserid(users.getUserid()).getFirstname() + userService.findPersonsByUserid(users.getUserid()).getLastname());
                noticeBoardListPageList.set(i, noticeBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("noticeBoardList", noticeBoardListPageList);
        model.addAttribute("bzw", "4");
        return "noticeBoard/noticeBoardView";
    }

    // 活动消息
    @RequestMapping(value = "/noticeBoard/selectNoticeBoardActivityPage", method = RequestMethod.GET)
    public
    @ResponseBody
    List<NoticeBoard> selectNoticeBoardActivityPage(Model model,
                                                    HttpServletRequest request, HttpServletResponse response) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            List<OpLogs> eventList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_EVENT);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < eventList.size(); i++) {
                OpLogs event = eventList.get(i);
                String startDate = event.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("活动");
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setResponsiblePerson(event.getUserid());
                noticeBoard.setMsContent(event.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            int pageNumber = Integer.valueOf(request.getParameter("pagef"));
            noticeBoardListPageList = PageProperties.getNbPagefList(noticeBoardList, pageNumber);
            for (int i = 0; i < noticeBoardListPageList.size(); i++) {
                NoticeBoard noticeBoard = noticeBoardListPageList.get(i);
                Users users = userService.findUsersByLoginid(noticeBoard.getResponsiblePerson());
                noticeBoard.setResponsiblePerson("");
                if (users.getUserid() != null)
                    noticeBoard.setResponsiblePerson(userService.findPersonsByUserid(users.getUserid()).getFirstname() + userService.findPersonsByUserid(users.getUserid()).getLastname());
                noticeBoardListPageList.set(i, noticeBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noticeBoardListPageList;
    }

    // 运行消息
    @RequestMapping(value = "/noticeBoard/operatingModelView", method = RequestMethod.GET)
    public String initoperatingModelView(Model model) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            List<OpLogs> ucPatternRecordList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_PATTERN);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < ucPatternRecordList.size(); i++) {
                OpLogs ucPatternRecord = ucPatternRecordList.get(i);
                String startDate = ucPatternRecord.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("运行模式");
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setResponsiblePerson(ucPatternRecord.getUserid());
                noticeBoard.setMsContent(ucPatternRecord.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            noticeBoardListPageList = PageProperties.getNbStartPageList(noticeBoardList);
            int endIndex = PageProperties.getNbEndIndex(noticeBoardList);
            model.addAttribute("endIndex", endIndex);
            for (int i = 0; i < noticeBoardListPageList.size(); i++) {
                NoticeBoard noticeBoard = noticeBoardListPageList.get(i);
                Users users = userService.findUsersByLoginid(noticeBoard.getResponsiblePerson());
                noticeBoard.setResponsiblePerson("");
                if (users.getUserid() != null)
                    noticeBoard.setResponsiblePerson(userService.findPersonsByUserid(users.getUserid()).getFirstname() + userService.findPersonsByUserid(users.getUserid()).getLastname());
                noticeBoardListPageList.set(i, noticeBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("noticeBoardList", noticeBoardListPageList);
        model.addAttribute("bzw", "2");
        return "noticeBoard/noticeBoardView";
    }

    // 运行消息
    @RequestMapping(value = "/noticeBoard/selectNoticeBoardoperatingModelPage", method = RequestMethod.GET)
    public
    @ResponseBody
    List<NoticeBoard> selectNoticeBoardoperatingModelPage(Model model,
                                                          HttpServletRequest request, HttpServletResponse response) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            List<OpLogs> ucPatternRecordList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_PATTERN);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < ucPatternRecordList.size(); i++) {
                OpLogs ucPatternRecord = ucPatternRecordList.get(i);
                String startDate = ucPatternRecord.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("运行模式");
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setResponsiblePerson(ucPatternRecord.getUserid());
                noticeBoard.setMsContent(ucPatternRecord.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            int pageNumber = Integer.valueOf(request.getParameter("pagef"));
            noticeBoardListPageList = PageProperties.getNbPagefList(noticeBoardList, pageNumber);
            for (int i = 0; i < noticeBoardListPageList.size(); i++) {
                NoticeBoard noticeBoard = noticeBoardListPageList.get(i);
                Users users = userService.findUsersByLoginid(noticeBoard.getResponsiblePerson());
                noticeBoard.setResponsiblePerson("");
                if (users.getUserid() != null)
                    noticeBoard.setResponsiblePerson(userService.findPersonsByUserid(users.getUserid()).getFirstname() + userService.findPersonsByUserid(users.getUserid()).getLastname());
                noticeBoardListPageList.set(i, noticeBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noticeBoardListPageList;
    }

    // 天气消息
    @RequestMapping(value = "/noticeBoard/meteInformation", method = RequestMethod.GET)
    public String initmeteInformation(Model model) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            List<OpLogs> ucWeatherList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_WEATER);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < ucWeatherList.size(); i++) {
                OpLogs ucWeather = ucWeatherList.get(i);
                String startDate = ucWeather.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("天气");
                noticeBoard.setResponsiblePerson(ucWeather.getUserid());
                noticeBoard.setMsContent(ucWeather.getMessage());
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoardList.add(noticeBoard);
            }
            noticeBoardListPageList = PageProperties.getNbStartPageList(noticeBoardList);
            int endIndex = PageProperties.getNbEndIndex(noticeBoardList);
            model.addAttribute("endIndex", endIndex);
            for (int i = 0; i < noticeBoardListPageList.size(); i++) {
                NoticeBoard noticeBoard = noticeBoardListPageList.get(i);
                Users users = userService.findUsersByLoginid(noticeBoard.getResponsiblePerson());
                noticeBoard.setResponsiblePerson("");
                if (users.getUserid() != null)
                    noticeBoard.setResponsiblePerson(userService.findPersonsByUserid(users.getUserid()).getFirstname() + userService.findPersonsByUserid(users.getUserid()).getLastname());
                noticeBoardListPageList.set(i, noticeBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("noticeBoardList", noticeBoardListPageList);
        model.addAttribute("bzw", "3");
        return "noticeBoard/noticeBoardView";
    }

    @RequestMapping(value = "/noticeBoard/selectNoticeBoardmeteInfoPage", method = RequestMethod.GET)
    public
    @ResponseBody
    List<NoticeBoard> selectNoticeBoardmeteInfoPage(Model model,
                                                    HttpServletRequest request, HttpServletResponse response) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            List<OpLogs> ucWeatherList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_WEATER);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < ucWeatherList.size(); i++) {
                OpLogs ucWeather = ucWeatherList.get(i);
                String startDate = ucWeather.getDated().substring(0, 19);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("天气");
                noticeBoard.setResponsiblePerson(ucWeather.getUserid());
                noticeBoard.setMsContent(ucWeather.getMessage());
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoardList.add(noticeBoard);
            }
            int pageNumber = Integer.valueOf(request.getParameter("pagef"));
            noticeBoardListPageList = PageProperties.getNbPagefList(noticeBoardList, pageNumber);
            for (int i = 0; i < noticeBoardListPageList.size(); i++) {
                NoticeBoard noticeBoard = noticeBoardListPageList.get(i);
                Users users = userService.findUsersByLoginid(noticeBoard.getResponsiblePerson());
                noticeBoard.setResponsiblePerson("");
                if (users.getUserid() != null)
                    noticeBoard.setResponsiblePerson(userService.findPersonsByUserid(users.getUserid()).getFirstname() + userService.findPersonsByUserid(users.getUserid()).getLastname());
                noticeBoardListPageList.set(i, noticeBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noticeBoardListPageList;
    }

    // 节假日消息
    @RequestMapping(value = "/noticeBoard/holidayView", method = RequestMethod.GET)
    public String initholidayView(Model model) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            List<OpLogs> ucHolidayList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_HOLIDAY);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < ucHolidayList.size(); i++) {
                OpLogs ucHoliday = ucHolidayList.get(i);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("节假日");
                noticeBoard.setResponsiblePerson(ucHoliday.getUserid());
                String startDate = ucHoliday.getDated().substring(0, 19);
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setMsContent(ucHoliday.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            noticeBoardListPageList = PageProperties.getNbStartPageList(noticeBoardList);
            int endIndex = PageProperties.getNbEndIndex(noticeBoardList);
            model.addAttribute("endIndex", endIndex);
            for (int i = 0; i < noticeBoardListPageList.size(); i++) {
                NoticeBoard noticeBoard = noticeBoardListPageList.get(i);
                Users users = userService.findUsersByLoginid(noticeBoard.getResponsiblePerson());
                noticeBoard.setResponsiblePerson("");
                if (users.getUserid() != null)
                    noticeBoard.setResponsiblePerson(userService.findPersonsByUserid(users.getUserid()).getFirstname() + userService.findPersonsByUserid(users.getUserid()).getLastname());
                noticeBoardListPageList.set(i, noticeBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("noticeBoardList", noticeBoardListPageList);
        model.addAttribute("bzw", "5");
        return "noticeBoard/noticeBoardView";
    }

    @RequestMapping(value = "/noticeBoard/selectNoticeBoardHolidayPage", method = RequestMethod.GET)
    public
    @ResponseBody
    List<NoticeBoard> selectNoticeBoardHolidayPage(Model model,
                                                   HttpServletRequest request, HttpServletResponse response) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<NoticeBoard> noticeBoardListPageList = new ArrayList<NoticeBoard>();
        try {
            List<OpLogs> ucHolidayList = opLogRepository.findByCategoryOrderByDatedDesc(PatternConst.LOG_TYPE_HOLIDAY);
            List<NoticeBoard> noticeBoardList = new ArrayList<NoticeBoard>();
            for (int i = 0; i < ucHolidayList.size(); i++) {
                OpLogs ucHoliday = ucHolidayList.get(i);
                NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setMsType("节假日");
                noticeBoard.setResponsiblePerson(ucHoliday.getUserid());
                String startDate = ucHoliday.getDated().substring(0, 19);
                noticeBoard.setMomentTime(sdf1.parse(startDate));
                noticeBoard.setMoment(startDate);
                noticeBoard.setMsContent(ucHoliday.getMessage());
                noticeBoardList.add(noticeBoard);
            }
            int pageNumber = Integer.valueOf(request.getParameter("pagef"));
            noticeBoardListPageList = PageProperties.getNbPagefList(noticeBoardList, pageNumber);
            for (int i = 0; i < noticeBoardListPageList.size(); i++) {
                NoticeBoard noticeBoard = noticeBoardListPageList.get(i);
                Users users = userService.findUsersByLoginid(noticeBoard.getResponsiblePerson());
                noticeBoard.setResponsiblePerson("");
                if (users.getUserid() != null)
                    noticeBoard.setResponsiblePerson(userService.findPersonsByUserid(users.getUserid()).getFirstname() + userService.findPersonsByUserid(users.getUserid()).getLastname());
                noticeBoardListPageList.set(i, noticeBoard);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noticeBoardListPageList;
    }

    public int daysBetween(Date early, Date late) {
        java.util.Calendar calst = java.util.Calendar.getInstance();
        java.util.Calendar caled = java.util.Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calst.set(java.util.Calendar.MINUTE, 0);
        calst.set(java.util.Calendar.SECOND, 0);
        caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
        caled.set(java.util.Calendar.MINUTE, 0);
        caled.set(java.util.Calendar.SECOND, 0);
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
                .getTime().getTime() / 1000)) / 3600 / 24;

        return days;
    }

    public long[] getDistanceTimes(String str1, String str2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }
}
