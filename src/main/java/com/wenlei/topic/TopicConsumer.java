package com.wenlei.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class TopicConsumer {

    private static final String ACTIVEMQ_URL = "tcp://192.168.108.129:61616";
    private static final String TOPIC_NAME = "topic001";


    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("我是1消费者");
        //1 创建连接工厂，按照给定的url地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2 通过链接工厂，获得连接connection并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3 创建session
        //两个参数，一个叫事务，一个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4 创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5 创建消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);
        messageConsumer.setMessageListener(message -> {
            if (null != message && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("***消费者接收消息" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }

}
