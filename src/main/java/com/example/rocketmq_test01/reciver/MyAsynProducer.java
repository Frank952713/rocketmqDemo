package com.example.rocketmq_test01.reciver;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

public class MyAsynProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("my_producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        for (int i=0;i<10;i++){
            Message message2 = new Message("tp_2", ("MQ异步的世界" + i).getBytes(StandardCharsets.UTF_8));
            producer.send(message2, new SendCallback() {
                //发送成功
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功"+sendResult);
                }
                //发送异常
                @Override
                public void onException(Throwable throwable) {
                    System.out.println("发送失败"+throwable.getMessage());

                }
            });
        }
        Thread.sleep(1000);
//        Message message = new Message("tp_1", "测试下RpcketMQ生产者".getBytes(StandardCharsets.UTF_8));
//        SendResult send = producer.send(message);
//        System.out.println(send);
        producer.shutdown();
    }
}
