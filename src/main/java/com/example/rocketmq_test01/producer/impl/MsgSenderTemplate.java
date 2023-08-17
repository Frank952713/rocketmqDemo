package com.example.rocketmq_test01.producer.impl;

import com.example.rocketmq_test01.producer.MsgSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MsgSenderTemplate implements MsgSender {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void sendMessage(Object data, String topic, String tags) {
        String dest = String.format("%s:%s", topic, tags);
        Message<Object> message = MessageBuilder.withPayload(data).build();
        //发送同步顺序消息
        //hashKey：用于确定消息要发送到的队列的关键字。
        // 具有相同 hashKey 的消息将被发送到同一个队列，确保这个数据库操作按照**时间顺序**发送到同一个队列。
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(dest, message, data.hashCode() + "", 5000L);
        log.warn("消息投递成功- msgId:{}, msg:{}", sendResult.getMsgId(), message);
    }
}
