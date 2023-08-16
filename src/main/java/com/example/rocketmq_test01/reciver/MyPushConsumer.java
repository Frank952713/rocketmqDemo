package com.example.rocketmq_test01.reciver;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;


/**
 * 消费者push模式
 */
public class MyPushConsumer {
    public static void main(String[] args) throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_0");
        consumer.setNamesrvAddr("127.0.0.1:9876");

        consumer.subscribe("tp_2", "*");


        consumer.setMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {

            MessageQueue messageQueue = consumeConcurrentlyContext.getMessageQueue();
            System.out.println(messageQueue);
            for (MessageExt ext : list) {
                System.out.println(new String(ext.getBody(), StandardCharsets.UTF_8));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
//        consumer.setMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//                MessageQueue messageQueue = consumeConcurrentlyContext.getMessageQueue();
//                System.out.println(messageQueue);
//                for (MessageExt ext : list) {
//                    System.out.println(new String(ext.getBody(), StandardCharsets.UTF_8));
//                }
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });

        consumer.start();
    }
}
