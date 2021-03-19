package com.wenlei.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicProducerPresistent {

    private static final String ACTIVEMQ_URL = "tcp://192.168.108.129:61616";
    private static final String TOPIC_NAME = "topic001";

    public static void main(String[] args) throws JMSException {

        // 创建连接工厂，按照给定的url地址采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 通过连接工厂，获取Connection
        Connection connection = activeMQConnectionFactory.createConnection();
        // 创建Session
        // 有两个参数，第一个是事务，第二个是签收，后面详细介绍
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（目的地有两个子接口，分别是Queue和Topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        // 创建消息生产者，生产的消息放到topic中
        MessageProducer messageProducer = session.createProducer(topic);
        // 设置持久化主题并启动
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
        // 使用messageProducer生产消息发送到主题中
        for (int i = 0; i < 3; i++) {
            // 创建一条消息，可以理解成字符串
            TextMessage textMessage = session.createTextMessage("message-" + i);
            // 通过messageProducer发送消息给mq
            messageProducer.send(textMessage);
        }
        // 按照资源打开的相反顺序关闭资源
        messageProducer.close();
        session.close();
        connection.close();
    }



}
