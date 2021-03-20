package com.wenlei.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringMQ_Consumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 通过Spring容器获取SpringMQ_Producer对象的实例，在getBean时候，默认是类名首字母小写
        SpringMQ_Consumer springMQ_consumer = (SpringMQ_Consumer) applicationContext.getBean("springMQ_Consumer");
        String message = (String) springMQ_consumer.jmsTemplate.receiveAndConvert();
        System.out.println(message);
    }
}