package com.wenlei.broker;

import org.apache.activemq.broker.BrokerService;

public class EmbedBroker {

    public static void main(String[] args) throws Exception {
        //内嵌的服务器
        BrokerService brokerService=new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
        //需要加上 System.in.read()，否则自动停止activemq
        System.in.read();
    }

}
