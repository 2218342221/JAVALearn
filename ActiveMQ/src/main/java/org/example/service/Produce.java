package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@EnableScheduling
public class Produce {

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    @Qualifier("demo_queue")
    private Queue queue;

    @Autowired
    @Qualifier("demo_topic")
    private Topic topic;

    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue,"springboot_activemq_demo");
    }

    public void produceMsgToTopic(){
        jmsMessagingTemplate.convertAndSend(topic,"springboot_activemq_demo");
    }

//    @Scheduled(cron = "*/3 * * * * ?")
    public void produceMsgScheduled(){
        System.out.println("send _____________");
        jmsMessagingTemplate.convertAndSend(queue,"springboot_activemq_demo time: "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    @Scheduled(cron = "*/3 * * * * ?")
    public void produceMsgScheduledToTopic(){
        System.out.println("send _____________");
        jmsMessagingTemplate.convertAndSend(topic,"springboot_activemq_demo time: "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
