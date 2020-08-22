package org.example.service;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class Consumer {

//    @JmsListener(destination = "${demo_queue}")
    public void receive(Message message) throws JMSException {
        if (message instanceof TextMessage) {
            System.out.println(((TextMessage) message).getText());
        } else {
            System.out.println();
        }
    }

    @JmsListener(destination = "${demo_topic}")
    public void receiveTopic(Message message) throws JMSException {
        if (message instanceof TextMessage) {
            System.out.println(((TextMessage) message).getText());
        } else {
            System.out.println();
        }
    }
}
