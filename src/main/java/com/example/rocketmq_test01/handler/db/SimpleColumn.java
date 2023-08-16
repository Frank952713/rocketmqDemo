package com.example.rocketmq_test01.handler.db;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class SimpleColumn implements Serializable {

    private String name;

    private String beforeValue;

    private String afterValue;

    public SimpleColumn(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return StrUtil.format("{}:{}->{}", this.name, this.beforeValue, this.afterValue);
    }
}
