package com.wenlei.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class SpringMQ_Producer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 通过Spring容器获取SpringMQ_Producer对象的实例，在getBean时候，默认是类名首字母小写
        SpringMQ_Producer springMQ_producer = (SpringMQ_Producer) applicationContext.getBean("springMQ_Producer");
        springMQ_producer.jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("Spring和ActiveMQ整合");
            }
        });
        // Lambda表达式写法
        // springMQ_producer.jmsTemplate.send(session -> session.createTextMessage("Spring和ActiveMQ整合"));
        System.out.println("消息发送完毕！");
    }
}
