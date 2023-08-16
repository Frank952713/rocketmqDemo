package com.example.rocketmq_test01.handler.data.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.rocketmq_test01.handler.MsgHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
@TableName(value = "t_user")
public class UserHandler extends MsgHandler {

    @Override
    protected void add(Long id) {

    }

    @Override
    protected void update(Long id, Map<String, Object> changeColumns) {

    }

    @Override
    protected void delete(Long id) {

    }
}
