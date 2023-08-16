package com.example.rocketmq_test01.handler;

public interface MsgHandlerContext {
    MsgHandler getMsgHandler(String tableName);
}
