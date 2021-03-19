package com.wenlei.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class TopicConsumerPresistent {

    private static final String ACTIVEMQ_URL = "tcp://192.168.108.129:61616";
    private static final String TOPIC_NAME = "topic001";

    public static void main(String[] args) throws JMSException, IOException {


        // 创建连接工厂，按照给定的url地址采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 通过连接工厂，获取Connection
        Connection connection = activeMQConnectionFactory.createConnection();
        // 设置ClientID
        connection.setClientID("client1");
        // 创建Session
        // 有两个参数，第一个是事务，第二个是签收，后面详细介绍
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（目的地有两个子接口，分别是Queue和Topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        // 创建一个主题订阅者，指明订阅的主题，指明订阅者的标识
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "topicSubscriber1");
        // 启动connection
        connection.start();
        // 接收消息
        Message message = topicSubscriber.receive();
        while (message != null) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("topicSubscriber接收到的消息：" + textMessage.getText());
            message = topicSubscriber.receive();
        }
        System.in.read();
        session.close();
        connection.close();
    }



}
