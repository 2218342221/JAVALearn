package org.example.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class Config {

    @Value("${demo_queue}")
    String queueName;

    @Value("${demo_topic}")
    String topicName;

    @Bean(name = "demo_queue")
    public Queue queue(){
        return new ActiveMQQueue(queueName);
    }

    @Bean(name = "demo_topic")
    public Topic topic(){
        return new ActiveMQTopic(topicName);
    }
}
