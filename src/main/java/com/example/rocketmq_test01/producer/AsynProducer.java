package com.example.rocketmq_test01.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

public class AsynProducer {

    public static void main(String[] args) throws Exception {
        //实例化 消息生产者 Producer
        DefaultMQProducer producer = new DefaultMQProducer("my_producer_asyn");
        producer.setNamesrvAddr("127.0.0.1:9876");
        //设置消息同步发送失败的重试次数，默认为3
        producer.setRetryTimesWhenSendFailed(3);
        //设置消息发送超时时间，默认3000ms
        producer.setSendMsgTimeout(3000);
        producer.start();

        for (int i = 0; i < 10; i++) {
            int index = i;
            //创建消息，并指定 Topic，Tag和消息体
            Message message2 = new Message("Topic_Test_01",
                    "TagA",
                    ("MQ异步的世界" + i).getBytes(StandardCharsets.UTF_8));
            //SendCallBack接受异步返回结果的回调
            producer.send(message2, new SendCallback() {
                //发送成功
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }

                //发送异常
                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        producer.shutdown();
    }
}
