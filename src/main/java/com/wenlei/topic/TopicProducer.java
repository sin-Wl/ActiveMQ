package com.wenlei.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicProducer {

    private static final String ACTIVEMQ_URL = "tcp://192.168.108.129:61616";
    private static final String TOPIC_NAME = "topic001";

    public static void main(String[] args) throws JMSException {

        //1.创建连接工厂，按照给定的url地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过链接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3 创建会话
        //两个参数 第一个叫事务，第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4 创建目的地（具体是队列还是主题）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5 创建消息的生产着
        MessageProducer messageProducer = session.createProducer(topic);
        //6 通过使用messageProduce生产3条消息发送到MQ的主题里面
        for (int i = 0; i < 3; i++) {
            //7 创建消息，
            TextMessage textMessage = session.createTextMessage("TOPIC_NAME" + i);
            //8 通过messageProduce发送mq
            messageProducer.send(textMessage);
        }
        //9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("***消息发布到mq完成");
    }


}
