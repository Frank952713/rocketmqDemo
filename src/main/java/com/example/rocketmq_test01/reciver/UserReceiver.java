package com.example.rocketmq_test01.reciver;

import cn.hutool.json.JSONUtil;
import com.example.rocketmq_test01.handler.MsgHandlerContext;
import com.example.rocketmq_test01.handler.db.RecordData;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "sync_user_data_message_topic",//消费主题
        consumerGroup = "user_consumer",                        //消费者组
        selectorExpression = "sync_user_data_message_tag",      //消费过滤表达式
        consumeMode = ConsumeMode.ORDERLY,                      //消费模式
        maxReconsumeTimes = 3)
public class UserReceiver implements RocketMQListener<MessageExt> {

    private MsgHandlerContext msgHandlerContext;

    @Override
    public void onMessage(MessageExt message) {
        try {
            MDC.put("track_code", String.format("mq:%s", message.getMsgId()));
            log.info("message:{}", new String(message.getBody()));
            RecordData recordData = JSONUtil.toBean(new String(message.getBody()), RecordData.class);
            //检查数据库名称是否一致
            if (!"dbname".equals(recordData.getDbName())) {
                return;
            }
            //获取表名
            String tableName = recordData.getTable();
            //在处理器上下文中通过表名获取对应的处理器，然后做消息处理
            msgHandlerContext.getMsgHandler(tableName).doHandleMessage(recordData);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new BusinessException("寄修工单同步异常,", e);
        }
    }
}
