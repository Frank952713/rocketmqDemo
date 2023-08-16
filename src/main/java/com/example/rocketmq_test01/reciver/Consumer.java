package com.example.rocketmq_test01.reciver;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
/*
*消费者采用集群方式消费消息，一条消息同一个消费者组中只有一个消费者会消费到
*/
public class Consumer {
    public static void main(String[] args) throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_1");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        //订阅 Topic
        consumer.subscribe("Test","*");
        //********负载均衡模式消费*********
        consumer.setMessageModel(MessageModel.CLUSTERING);
        //注册回调函数，处理消息
        consumer.registerMessageListener((MessageListenerConcurrently) (list,context) -> {
            System.out.printf("%s Receive New Message: %s %n",Thread.currentThread().getName(),list);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //启动消费者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
