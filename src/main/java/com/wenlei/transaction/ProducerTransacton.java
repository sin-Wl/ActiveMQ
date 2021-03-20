package com.wenlei.transaction;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ProducerTransacton {

    private static final String ACTIVEMQ_URL = "tcp://192.168.108.129:61616";

    private static final String QUEUE_NAME = "queue001";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过 连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3.创建会话session
        //两个参数 ，第一个叫事务，第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4 .创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息生产者
        MessageProducer messageProducer = session.createProducer(queue);
        // 6.通过使用messageProduce生产3条消息发送到MQ队列里面
        for (int i = 0; i < 3; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("tx msg --- " + i);
            //8 .通过messageProducer发送给mq
            messageProducer.send(textMessage);
        }
        //9.关闭资源
        messageProducer.close();
        /**如果上面connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
         *  1.如果是true 这里需要session.commit()先提交事务，在关闭session ,否则队列中没有
         消息
         *  2.如果是false ，可以不用提交，会自动提交
         */
        session.close();
        connection.close();
        System.out.println("********tx消息发布到MQ完成");

        try {
            //如果是批量 消息 ok session.commit();
        }catch (Exception e){
            e.printStackTrace();
            session.rollback(); //事务回滚 要么都成功 要么都失败
        }finally {
            if(null!=session){
                session.close(); //关闭session
            }
        }
    }


}
