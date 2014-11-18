package com.energicube.eno.message.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.Serializable;

@Component
public class MessageSender {


    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * Send the objectMessage to the Broker and Queue defined in
     * application.properties.
     *
     * @param objectMessage Object Message
     */
    public void sendMessageObject(final Serializable objectMessage) {

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage message = session
                        .createObjectMessage(objectMessage);
                return message;

            }
        });


    }
}
