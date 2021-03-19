package com.wenlei.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 消费者-->队列
 */
public class QueueConsumer {

    private static final String ACTIVEMQ_URL = "tcp://192.168.108.129:61616";
    private static final String QUEUE_NAME = "queue001";

    public static void main(String[] args) throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer consumer = session.createConsumer(queue);

/*      receive()同步阻塞方式
        //在调用receive()方法之前必须要调用start()方法启动连接，否者receive()接收不到消息会被一直阻塞
        connection.start();
        while (true) {
            //生产的是TextMessage，接收也要是TextMessage
            TextMessage message = (TextMessage)consumer.receive();
            if(null!=message){
                System.out.println("接收queue消息：" + message.getText());
            }else{
                break;
            }
        }
        session.close();
        consumer.close();
        connection.close();*/

        connection.start();
        //通过监听的方式消费消息
        //异步非阻塞方式(监听器onMessage())
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(null!=message && message instanceof TextMessage){
                    try {
                        TextMessage textMessage= ((TextMessage) message);
                        System.out.println("接收queue消息：" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //System.in.read();为了使程序不退出
        System.in.read();
        session.close();
        consumer.close();
        connection.close();

    }
}
