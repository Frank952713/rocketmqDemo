package com.example.rocketmq_test01.templete;

import cn.hutool.core.util.StrUtil;
import com.example.rocketmq_test01.handler.db.RecordData;
import com.example.rocketmq_test01.producer.impl.MsgSenderTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserTemplate extends AbstractConsumerTemplate {

    String SYNC_ORDER_DATA_MESSAGE_TOPIC = "sync_order_data_message_topic";
    String SYNC_ORDER_DATA_MESSAGE_TAG = "sync_order_data_message_tag";
    private final String topic = this.SYNC_ORDER_DATA_MESSAGE_TOPIC;
    private final String tags = this.SYNC_ORDER_DATA_MESSAGE_TAG;
    @Autowired
    private MsgSenderTemplate msgSenderTemplate;

    {
        DATE_BASE_NAME = "whale-ets-customer";
        TABLE_NAME_LIST.add("t_ets_order");
        TABLE_NAME_LIST.add("t_ets_order_template_value");
    }

    @Override
    public boolean needToDo(RecordData recordData) {
        String dateBaseName = recordData.getDbName();
        String tableName = recordData.getTable();
        if (!StrUtil.equalsIgnoreCase(dateBaseName,DATE_BASE_NAME)) {
            return false;
        }
        return TABLE_NAME_LIST.contains(tableName);
    }

    @Override
    public void insertHandle(RecordData recordData) {
        msgSenderTemplate.sendMessage(recordData, topic, tags);
    }

    @Override
    public void updateHandle(RecordData recordData) {
        msgSenderTemplate.sendMessage(recordData, topic, tags);
    }

    @Override
    public void deleteHandle(RecordData recordData) {
        msgSenderTemplate.sendMessage(recordData, topic, tags);
    }
}
