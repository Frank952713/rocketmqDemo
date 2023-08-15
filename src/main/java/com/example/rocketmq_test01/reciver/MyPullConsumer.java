package com.example.rocketmq_test01.reciver;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;


/**
 * 消费者pull模式
 */
public class MyPullConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("consumer_0");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.start();

        Set<MessageQueue> messageQueueSet = consumer.fetchSubscribeMessageQueues("tp_2");
        for (MessageQueue m : messageQueueSet) {

            PullResult result = consumer.pull(m, "*", 0, 10);
            System.out.println("消息消息：" + m);

            List<MessageExt> msgFoundList = result.getMsgFoundList();
            for (MessageExt ext : msgFoundList) {
                System.out.println(new String(ext.getBody(), StandardCharsets.UTF_8));
            }
        }
        consumer.shutdown();
    }
}
