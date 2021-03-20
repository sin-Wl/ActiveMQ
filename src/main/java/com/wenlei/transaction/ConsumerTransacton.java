package com.wenlei.transaction;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerTransacton {

    private static final String ACTIVEMQ_URL = "tcp://192.168.108.129:61616";

    private static final String QUEUE_NAME = "queue001";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂，按照给定的url地址，采用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过链接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建会话
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        while (true)
        {
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000);
            if(null != textMessage){
                System.out.println("********消费者接收消息："+ textMessage.getText());
            }else{
                break;
            }
        }
        messageConsumer.close();
        /** 如果消费者Session session =
         connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
         * 1.如果设置的是false 消费了消息的话，就不会重复消费
         * 2.如果是true 。必须session.commit()否则会出现重复消费的情况
         */
        session.close();
        connection.close();
    }



}
