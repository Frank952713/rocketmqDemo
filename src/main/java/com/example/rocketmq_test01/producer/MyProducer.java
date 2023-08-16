package com.example.rocketmq_test01.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;


@Slf4j
public class MyProducer {
    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("my_producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        Message message = new Message("tp_1", "测试下的rocketmq生产者".getBytes(StandardCharsets.UTF_8));
        //延迟队列设置(1s、 5s、 10s、 30s、 1m、 2m、 3m、 4m、 5m、 6m、 7m、 8m、 9m、 10m、 20m、 30m、 1h、 2h；)
        // 3 表示设置的第三个等级：10s
        message.setDelayTimeLevel(3);

        SendResult send = producer.send(message);
        System.out.println(send);

        producer.shutdown();
    }
}
