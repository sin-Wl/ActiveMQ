package com.wenlei.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 生产者-->队列
 */
public class QueueProducer {

    //记得在linux上开放61616端口访问
    private static final String ACTIVEMQ_URL = "tcp://192.168.108.129:61616";

    private static final String QUEUE_NAME = "queue001";

    public static void main(String[] args) throws JMSException{

        //1、创建连接工厂。这里传入ActiveMQ消息服务连接地址，并使用默认用户名和密码。
        // 也可使用ActiveMQConnectionFactory()构造器或者ActiveMQConnectionFactory(String userName, String password, String brokerURL)构造器，连接接信息全部使用默认值或者全部指定
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2、通过工厂对象创建连接
        Connection connection = connectionFactory.createConnection();
        //3、通过连接对象创建会话。第一个参数是否开启事务，第二参数指定签收类型
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4、通过会话对象创建目的地（队列或者主题）。这里创建了一个名为 "queue001" 的队列
        Queue queue = session.createQueue(QUEUE_NAME);
        //5、通过会话对象创建生产者，并指定目的地
        MessageProducer producer = session.createProducer(queue);
        //6、连续创建3条消息，并有生产者发送到消息队列
        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("队列消息：message" + i);
            producer.send(textMessage);
            System.out.println(textMessage.getJMSDestination());
            System.out.println("发送消息" + textMessage.getText() + "成功");
        }

        //7、关闭资源
        producer.close();
        session.close();
        connection.close();
    }

}
