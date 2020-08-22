package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class StartDemo {

    private final static String ACTIVEMQ_URL = "tcp://49.235.202.56:61616";
    private final static String QUEUE_NAME = "demo_queue";
    private final static String TOPIC_NAME = "demo_topic";

    @Test
    //点对点-生产
    public void test01() throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //生产者
        MessageProducer producer = session.createProducer(queue);
        IntStream.range(0, 3).forEach(i -> {
            try {
                producer.send(session.createTextMessage("demo msg " + i));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    //点对点-消费
    public void test02() throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //消费者
        MessageConsumer consumer = session.createConsumer(queue);

        while (true) {
            Message receive = consumer.receive(1000L);
            if (receive == null) break;
            System.out.println(receive);
        }

        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    //点对点-消费 监听
    public void test03() throws JMSException, IOException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //消费者
        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(message -> {
            if (null != message) {
                System.out.println(message);
            }
        });

        System.in.read();

        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    //发布-订阅
    public void test04() throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer producer = session.createProducer(topic);
//        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        IntStream.range(1, 10).forEach(o -> {
            try {
                TextMessage textMessage = session.createTextMessage("msg" + o);
                producer.send(textMessage);
                TimeUnit.SECONDS.sleep(2);
            } catch (JMSException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.close();
        session.close();
        connection.close();

    }

    @Test
    //发布-订阅
    public void test05() throws JMSException, IOException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("demo_1");
        connection.start();
        //创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);

        //消费者
        MessageConsumer consumer = session.createDurableSubscriber(topic,"demo_topic_consumer_1");

        consumer.setMessageListener(message -> {
            if (null != message) {
                if (message instanceof TextMessage) {
                    try {
                        System.out.println(((TextMessage) message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(message);
                }
            }
        });

        System.in.read();

        consumer.close();


        session.close();
        connection.close();

    }

    @Test
    //发布-订阅
    public void test06() throws JMSException, IOException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("demo_2");
        connection.start();
        //创建会话session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);

        //消费者
        MessageConsumer consumer = session.createConsumer(topic);

        consumer.setMessageListener(message -> {
            if (null != message) {
                if (message instanceof TextMessage) {
                    try {
                        System.out.println(((TextMessage) message).getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(message);
                }
            }
        });

        System.in.read();

        consumer.close();


        session.close();
        connection.close();

    }
}
