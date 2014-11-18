package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Message;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 短消息数据接口类
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select a from  Message a where a.inceptId = ?1 and  a.isSend is  null and a.isDelInbox =?2")
    public List<Message> findByInceptId(Long inceptId, Boolean isDelInbox) throws DataAccessException;

    @Query("select a from  Message a where a.senderId = ?1 and a.isSend = ?2 and a.isDelSendbox =?3")
    public List<Message> findBySenderId(Long senderId, Boolean isSend, Boolean isDelSendbox) throws DataAccessException;

    @Query("select a from  Message a where a.senderId = ?1 and a.isSend = ?2 and a.isDelSendbox =?3")
    public List<Message> findByCg(Long senderId, Boolean isSend, Boolean isDelSendbox) throws DataAccessException;

    @Query("select a from  Message a where  a.messageId in  (select messageId from  Message b where b.inceptId = ?1 and  b.isDelInbox = ?2 ) or   a.messageId in   (select messageId from  Message c where c.senderId = ?1 and  c.isDelSendbox = ?2 ) ")
    public List<Message> findByRubish(Long senderId, Boolean zt) throws DataAccessException;

    @Transactional
    @Modifying
    @Query("update Message a set a.isDelSendbox = false ,  a.isDelInbox = false  where a.messageId = ?1")
    public void updateRubishbox(long messageId);

    @Transactional
    @Modifying
    @Query("update Message a set a.isDelSendbox = ?2   where a.messageId = ?1")
    public void updateSendbox(long messageId, Boolean isSend);

    @Transactional
    @Modifying
    @Query("update Message a set a.isDelInbox = ?2 where a.messageId = ?1")
    public void updateInbox(long messageId, Boolean Inbox);

    @Transactional
    @Modifying
    @Query("update Message a set a.isRead = ?2 where a.messageId = ?1")
    public void updateInboxRead(long messageId, Boolean isRead);

    @Transactional
    @Modifying
    @Query("update Message a set a.title = ?2 , a.content = ?3 ,  a.incept = ?4 , a.sendTime = ?5 , a.isSend = ?6 where a.messageId = ?1")
    public void updateCgToSendbox(long messageId, String title, String content, String incept, Date dateTime, Boolean IsSend);

    @Transactional
    @Modifying
    @Query("update Message a set a.title = ?2 , a.content = ?3 ,  a.incept = ?4 , a.sendTime = ?5 where a.messageId = ?1")
    public void updateCgToCg(long messageId, String title, String content, String incept, Date dateTime);

}
