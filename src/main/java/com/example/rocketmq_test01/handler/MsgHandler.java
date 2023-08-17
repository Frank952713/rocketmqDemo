package com.example.rocketmq_test01.handler;


import com.example.rocketmq_test01.handler.db.HandleEnum;
import com.example.rocketmq_test01.handler.db.RecordData;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 表变化消息处理器
 */
@AllArgsConstructor
public abstract class MsgHandler {

    public void doHandleMessage(RecordData msg) {
        Long id = Long.valueOf(msg.getPkValue());
        switch (msg.getType()) {
            //增
            case HandleEnum.TYPE_OF_INSERT:
                this.add(id);
                break;
            //删
            case HandleEnum.TYPE_OF_DELETE:
                this.delete(id);
                break;
            //改
            case HandleEnum.TYPE_OF_UPDATE:
                //stream8 Collectors.toMap，value为空会报错
                Map<String,Object> changeColumns =
                        msg.getColumns().stream()
                                .collect(HashMap::new,(m,v)-> m.put(v.getName(),v.getAfterValue()),HashMap::putAll);
                //m: HashMap::new新建的HashMap,v:columns
                this.update(id,changeColumns);
                break;
        }
    }

    /**
     * 处理新增
     */
    protected abstract void add(Long id);

    /**
     * 处理修改
     */
    protected abstract void update(Long id, Map<String, Object> changeColumns);

    /**
     * 处理删除
     */
    protected abstract void delete(Long id);
}
