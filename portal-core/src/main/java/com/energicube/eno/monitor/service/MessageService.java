package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Message;

import java.util.Date;
import java.util.List;

/**
 * 操作接口
 */
public interface MessageService {

    public void updateSendbox(long messageId, Boolean isSend);

    public void updateInbox(long messageId, Boolean Inbox);

    public void updateInboxRead(long messageId, Boolean isRead);

    public void updateCgToSendbox(long messageId, String title, String content, String incept, Date dateTime, Boolean IsSend);

    public void updateCgToCg(long messageId, String title, String content, String incept, Date dateTime);

    public void updateRubishbox(long messageId);

    public List<Message> getAcceptList(Long InceptId, Boolean bool);

    public List<Message> getSenderListA(Long senderId, Boolean bool, Boolean bools);

    public List<Message> getCgListA(Long Id, Boolean bool, Boolean bools);

    public List<Message> findByRubish(Long Id, Boolean bool);

    public Message findMessage(Long Id);

    public void saveMessage(Message message);

    public void deleteMessage(Message message);

    public void deleteMessageById(Long Id);
}
