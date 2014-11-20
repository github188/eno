package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Message;
import com.energicube.eno.monitor.repository.MessageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final static Log logger = LogFactory
            .getLog(MessageServiceImpl.class);

    @Autowired
    private MessageRepository messageRepository;

    public void updateSendbox(long messageId, Boolean isSend) {
        messageRepository.updateSendbox(messageId, isSend);
    }

    public void updateInbox(long messageId, Boolean Inbox) {
        messageRepository.updateInbox(messageId, Inbox);
    }

    public void updateInboxRead(long messageId, Boolean isRead) {
        messageRepository.updateInboxRead(messageId, isRead);
    }

    public void updateCgToSendbox(long messageId, String title, String content,
                                  String incept, Date dateTime, Boolean IsSend) {
        messageRepository.updateCgToSendbox(messageId, title, content, incept,
                dateTime, IsSend);
    }

    public void updateCgToCg(long messageId, String title, String content,
                             String incept, Date dateTime) {
        messageRepository.updateCgToCg(messageId, title, content, incept,
                dateTime);
    }

    public void updateRubishbox(long messageId) {
        messageRepository.updateRubishbox(messageId);
    }

    public List<Message> getAcceptList(Long InceptId, Boolean bool) {
        List<Message> acceptList = messageRepository.findByInceptId(InceptId,
                bool);
        return acceptList;
    }

    public List<Message> getSenderListA(Long senderId, Boolean bool,
                                        Boolean bools) {
        List<Message> senderListA = messageRepository.findBySenderId(senderId,
                bool, bools);
        return senderListA;
    }

    public List<Message> getCgListA(Long Id, Boolean bool, Boolean bools) {
        List<Message> cgListA = messageRepository.findByCg(Id, bool, bools);
        return cgListA;
    }

    public List<Message> findByRubish(Long Id, Boolean bool) {
        List<Message> rubishListA = messageRepository.findByRubish(Id, bool);
        return rubishListA;
    }

    public Message findMessage(Long Id) {
        Message message = messageRepository.findOne(Id);
        return message;
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }

    public void deleteMessageById(Long Id) {
        messageRepository.delete(Id);
    }
}
