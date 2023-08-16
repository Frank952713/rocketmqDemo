package com.example.rocketmq_test01.handler.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.rocketmq_test01.handler.MsgHandler;
import com.example.rocketmq_test01.handler.MsgHandlerContext;
import com.sun.istack.internal.NotNull;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class MsgHandlerContextImpl implements MsgHandlerContext {

    private final Map<String, MsgHandler> handlerMap = new HashMap();

    /**
     * 通过表名取出对应的处理器
     */
    @Override
    public MsgHandler getMsgHandler(String tableName) {
        return handlerMap.get(tableName);
    }

    /**
     *将全部表变化的处理器取出 放入msgHandlerMap中 表名为 key，处理器为 value
     * @param applicationContext Spring上下文对象
     */
    public void setApplicationContext(@NotNull ApplicationContext applicationContext){
        //从Spring上下文域中取出全部消息处理的实现类
        Map<String, MsgHandler> msgHandlerMap = applicationContext.getBeansOfType(MsgHandler.class);
        msgHandlerMap.values().forEach(msgHandler -> {
            //反射取出当前处理器的表名
            String tableName = msgHandler.getClass().getAnnotation(TableName.class).value();
            handlerMap.put(tableName,msgHandler);
        });
    }
}
