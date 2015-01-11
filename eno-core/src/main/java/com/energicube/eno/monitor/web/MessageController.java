package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.Message;
import com.energicube.eno.monitor.model.Persons;
import com.energicube.eno.monitor.model.UserInfo;
import com.energicube.eno.monitor.model.Users;
import com.energicube.eno.monitor.service.MessageService;
import com.energicube.eno.monitor.service.UserService;
import org.apache.shiro.SecurityUtils;
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

/**
 * 消息
 *
 * @author ChenHonglie
 */
@Controller
public class MessageController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/message/messageList", method = RequestMethod.GET)
    public String initMessageList(Model model) {
        List<Message> acceptList = messageService.getAcceptList(
                Long.valueOf(getFinalUserId()), false);
        List<Message> senderListA = messageService.getSenderListA(
                Long.valueOf(getFinalUserId()), true, false);
        String id = "";
        String userName = "";
        List<Message> senderList = new ArrayList<Message>();
        for (int i = 0; i < senderListA.size(); i++) {
            Message messageSender = senderListA.get(i);
            String inceptIds = messageSender.getIncept();
            String inceptName = "";
            if (!inceptIds.equals("")) {
                String[] strings = inceptIds.split(",");
                for (int j = 0; j < strings.length; j++) {
                    id = strings[j];
                    Persons Persons = userService.findPersonsByUserid(id);
                    userName = Persons.getFirstname() + Persons.getLastname()
                            + ";";
                    inceptName += userName;
                }
                userName = "";
                inceptName = inceptName.substring(0, inceptName.length() - 1);
                messageSender.setIncept(inceptName);
                senderList.add(messageSender);
            }
        }
        List<Message> cgListA = messageService.getCgListA(Long.valueOf(getFinalUserId()),
                false, false);
        List<Message> cgList = new ArrayList<Message>();
        for (int i = 0; i < cgListA.size(); i++) {
            Message messageSender = cgListA.get(i);
            String inceptIds = messageSender.getIncept();
            String inceptName = "";
            if (!inceptIds.equals("")) {
                String[] strings = inceptIds.split(",");
                for (int j = 0; j < strings.length; j++) {
                    id = strings[j];
                    Persons Persons = userService.findPersonsByUserid(id);
                    userName = Persons.getFirstname() + Persons.getLastname()
                            + ";";
                    inceptName += userName;
                }
                userName = "";
                inceptName = inceptName.substring(0, inceptName.length() - 1);
                messageSender.setIncept(inceptName);
                cgList.add(messageSender);
            }
        }
        List<Message> rubishListA = messageService.findByRubish(
                Long.valueOf(getFinalUserId()), true);
        List<Message> rubishList = new ArrayList<Message>();
        for (int i = 0; i < rubishListA.size(); i++) {
            Message messageSender = rubishListA.get(i);
            if (messageSender.getIsSend() != null) {
                String inceptIds = messageSender.getIncept();
                String inceptName = "";
                if (!inceptIds.equals("")) {
                    String[] strings = inceptIds.split(",");
                    for (int j = 0; j < strings.length; j++) {
                        id = strings[j];
                        Persons Persons = userService.findPersonsByUserid(id);
                        userName = Persons.getFirstname() + Persons.getLastname()
                                + ";";
                        inceptName += userName;
                    }
                    userName = "";
                    inceptName = inceptName.substring(0,
                            inceptName.length() - 1);
                    messageSender.setIncept(inceptName);
                    rubishList.add(messageSender);
                }
            } else {
                rubishList.add(messageSender);
            }
        }
        model.addAttribute("acceptList", acceptList);
        model.addAttribute("senderList", senderList);
        model.addAttribute("cgList", cgList);
        model.addAttribute("rubishList", rubishList);
        model.addAttribute("acceptSize", acceptList.size());
        model.addAttribute("senderSize", senderList.size());
        model.addAttribute("cgSize", cgList.size());
        model.addAttribute("rubishSize", rubishList.size());
        return "message/messageList";
    }

    //消息查看
    @RequestMapping(value = "/message/messageView", method = RequestMethod.GET)
    public String initmessageView(HttpServletRequest request,
                                  HttpServletResponse response, Model model) {
        String messageId = request.getParameter("messageId");
        String ZT = request.getParameter("ZT");
        Message message = messageService.findMessage(Long.valueOf(messageId));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendTime = sdf.format(message.getSendTime());
        model.addAttribute("sendTime", sendTime);
        if (!ZT.equals("0")) {
            if (message.getIsSend() != null) {
                String userName = "";
                String id = "";
                String inceptIds = message.getIncept();
                String inceptName = "";
                if (!inceptIds.equals("")) {
                    String[] strings = inceptIds.split(",");
                    for (int j = 0; j < strings.length; j++) {
                        id = strings[j];
                        Persons Persons = userService.findPersonsByUserid(id);
                        userName = Persons.getFirstname() + Persons.getLastname()
                                + ";";
                        inceptName += userName;
                    }
                    userName = "";
                    inceptName = inceptName.substring(0,
                            inceptName.length() - 1);
                    message.setIncept(inceptName);
                }
            }
        }
        model.addAttribute("message", message);
        model.addAttribute("ZT", ZT);
        if (ZT.equals("0"))
            messageService.updateInboxRead(Long.valueOf(messageId), true);
        return "message/messageView";
    }

    //回复
    @RequestMapping(value = "/message/replyMessage", method = RequestMethod.GET)
    public String initreplyMessage(HttpServletRequest request,
                                   HttpServletResponse response, Model model) {
        String messageId = request.getParameter("messageId");
        Message message = messageService.findMessage(Long.valueOf(messageId));
        model.addAttribute("message", message);
        return "message/replyMessage";
    }

    /**
     * 新增短信信息
     */
    @RequestMapping(value = "/message/messageAdd", method = RequestMethod.GET)
    public String initMessageForm(Model model) {
        Message message = new Message();
        model.addAttribute("message", message);
        return "message/messageAdd";
    }

    @RequestMapping(value = "/message/doInsert", method = RequestMethod.POST)
    public String doInsert(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        try {
            Date dateTime = new Date();
            Message message = new Message();
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String ids = request.getParameter("Id");
            String id = "";
            if (!ids.equals("")) {
                String[] strings = ids.split(",");
                for (int i = 0; i < strings.length; i++) {
                    id = strings[i];
                    message.setTitle(title);
                    message.setContent(content);
                    Persons Persons = userService.findPersonsByUserid(id);
                    message.setInceptId(Long.valueOf(Persons.getUserid()));
                    message.setIncept(Persons.getFirstname()
                            + Persons.getLastname());
                    message.setSenderId(Long.valueOf(getFinalUserId()));
                    message.setSender(userService.findPersonsByUserid(getFinalUserId())
                            .getFirstname()
                            + userService.findPersonsByUserid(getFinalUserId()).getLastname());
                    message.setSendTime(dateTime);
                    message.setIsSend(null);
                    message.setIsDelSendbox(false);
                    message.setIsDelInbox(false);
                    message.setIsRead(false);
                    messageService.saveMessage(message);
                }
                message.setIncept(ids);
                message.setIsSend(true);
                messageService.saveMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:messageList";
    }

    //保存至草稿箱
    @RequestMapping(value = "/message/doInsertCg", method = RequestMethod.GET)
    public String doInsertCg(HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        try {
            Message message = new Message();
            String title = new String(request.getParameter("title").getBytes(
                    "ISO-8859-1"), "utf-8");
            String content = new String(request.getParameter("content")
                    .getBytes("ISO-8859-1"), "utf-8");
            String ids = request.getParameter("Id");
            message.setTitle(title);
            message.setContent(content);
            message.setIncept(ids);
            message.setSenderId(Long.valueOf(getFinalUserId()));
            message.setSender(userService.findPersonsByUserid(getFinalUserId())
                    .getFirstname()
                    + userService.findPersonsByUserid(getFinalUserId()).getLastname());
            Date dateTime = new Date();
            message.setSendTime(dateTime);
            message.setIsSend(false);
            message.setIsDelSendbox(false);
            message.setIsDelInbox(false);
            message.setIsRead(false);
            messageService.saveMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:messageList";
    }

    @RequestMapping(value = "/message/deleteSend", method = RequestMethod.GET)
    public String deleteSend(HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        try {
            String messageId = request.getParameter("messageId");
            String bz = request.getParameter("BZ");
            Message message = messageService.findMessage(Long
                    .valueOf(messageId));
            if (bz.equals("1") || bz.equals("2")) {
                message.setIsDelSendbox(true);
                messageService.updateSendbox(message.getMessageId(), true);
            }
            if (bz.equals("0")) {
                messageService.updateInbox(message.getMessageId(), true);
            }
            if (bz.equals("3")) {
                messageService.deleteMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:messageList";
    }

    //重发
    @RequestMapping(value = "/message/resendMessage", method = RequestMethod.GET)
    public String resendMessage(HttpServletRequest request,
                                HttpServletResponse response, Model model) {
        String messageId = request.getParameter("messageId");
        Message message = messageService.findMessage(Long.valueOf(messageId));
        messageService.deleteMessageById(Long.valueOf(messageId));
        Date dateTime = new Date();
        message.setSendTime(dateTime);
        message.setIsSend(true);
        message.setIsDelSendbox(false);
        message.setIsDelInbox(false);
        message.setIsRead(false);
        messageService.saveMessage(message);
        String Ids = message.getIncept();
        String id = "";
        if (!Ids.equals("")) {
            String[] strings = Ids.split(",");
            for (int i = 0; i < strings.length; i++) {
                id = strings[i];
                message.setIsSend(null);
                message.setInceptId(Long.valueOf(id));
                message.setIncept(userService.findPersonsByUserid(id)
                        .getFirstname()
                        + userService.findPersonsByUserid(id).getLastname());
                messageService.saveMessage(message);
            }
        }
        return "redirect:messageList";
    }

    @RequestMapping(value = "/message/resumeMessage", method = RequestMethod.GET)
    public String resumeMessage(HttpServletRequest request,
                                HttpServletResponse response, Model model) {
        String messageId = request.getParameter("messageId");
        messageService.updateRubishbox(Long.valueOf(messageId));
        return "redirect:messageList";
    }

    //修改消息
    @RequestMapping(value = "/message/updateMessage", method = RequestMethod.GET)
    public String updateMessage(HttpServletRequest request,
                                HttpServletResponse response, Model model) {
        String messageId = request.getParameter("messageId");
        Message message = messageService.findMessage(Long.valueOf(messageId));
        model.addAttribute("message", message);
        model.addAttribute("userIds", "[" + message.getIncept() + "]");
        return "message/messageUpdate";
    }

    //将草稿箱保存至发送箱
    @RequestMapping(value = "/message/updateCgToSendbox", method = RequestMethod.POST)
    public String updateCgToSendbox(HttpServletRequest request,
                                    HttpServletResponse response, Model model) {
        try {
            long messageId = Long.valueOf(request.getParameter("messageId"));
            String content = request.getParameter("content");
            String title = request.getParameter("title");
            String ids = request.getParameter("Id");
            String incept = ids;
            Date dateTime = new Date();
            Boolean IsSend = true;
            messageService.updateCgToSendbox(messageId, title, content, incept,
                    dateTime, IsSend);
            Message message = new Message();
            String id = "";
            if (!ids.equals("")) {
                String[] strings = ids.split(",");
                for (int i = 0; i < strings.length; i++) {
                    id = strings[i];
                    message.setTitle(title);
                    message.setContent(content);
                    Persons Persons = userService.findPersonsByUserid(id);
                    message.setInceptId(Long.valueOf(Persons.getUserid()));
                    message.setIncept(Persons.getFirstname()
                            + Persons.getLastname());
                    message.setSenderId(Long.valueOf(getFinalUserId()));
                    message.setSender(userService.findPersonsByUserid(getFinalUserId())
                            .getFirstname()
                            + userService.findPersonsByUserid(getFinalUserId()).getLastname());
                    message.setSendTime(dateTime);
                    message.setIsSend(null);
                    message.setIsDelSendbox(false);
                    message.setIsDelInbox(false);
                    message.setIsRead(false);
                    messageService.saveMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:messageList";
    }

    //更新草稿箱
    @RequestMapping(value = "/message/updateCgToCg", method = RequestMethod.GET)
    public String updateCgToCg(HttpServletRequest request,
                               HttpServletResponse response, Model model) {
        try {
            long messageId = Long.valueOf(request.getParameter("messageId"));
            String title = new String(request.getParameter("title").getBytes(
                    "ISO-8859-1"), "utf-8");
            String content = new String(request.getParameter("content")
                    .getBytes("ISO-8859-1"), "utf-8");
            String ids = request.getParameter("Id");
            String incept = ids;
            Date dateTime = new Date();
            messageService.updateCgToCg(messageId, title, content, incept,
                    dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:messageList";
    }

    @RequestMapping(value = "/message/getUsers")
    public
    @ResponseBody
    List<Persons> getPersonss(HttpServletRequest request,
                              HttpServletResponse response) {
        List<Users> users = userService.findAllUsers();
        List<Persons> Personss = new ArrayList<Persons>();
        String fistName = "";
        for (int i = 0; i < users.size(); i++) {
            Users user = users.get(i);
            if (user.getStatus().equals("0")) { //  && user.getType().equals("1")
                Persons Persons = userService.findPersonsByUserid(user.getUserid());
                fistName = Persons.getFirstname() + Persons.getLastname();
                Persons.setFirstname(fistName);
                Personss.add(Persons);
            }
        }
        return Personss;
    }

    public String getFinalUserId() {
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        Users users = userService.findUsersByLoginid(user.getLoginid());
        return users.getUserid();
    }
}
