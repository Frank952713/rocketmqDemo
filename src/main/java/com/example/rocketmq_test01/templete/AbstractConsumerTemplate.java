package com.example.rocketmq_test01.templete;

import com.example.rocketmq_test01.handler.db.HandleEnum;
import com.example.rocketmq_test01.handler.db.RecordData;
import lombok.extern.slf4j.Slf4j;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Slf4j
public abstract class AbstractConsumerTemplate {

    protected String DATE_BASE_NAME;
    protected Set<String> TABLE_NAME_LIST = new HashSet<>();

    /**
     *
     * @param type 增/删/改
     * @param recordDataList
     */
    public void doHandle(String type, List<RecordData> recordDataList) {
        for (RecordData recordData : recordDataList) {
            if (this.needToDo(recordData)) {
                switch (type) {
                    case HandleEnum.TYPE_OF_INSERT:
                        //新增
                        this.insertHandle(recordData);
                        break;
                    case HandleEnum.TYPE_OF_UPDATE:
                        //修改
                        this.updateHandle(recordData);
                        break;
                    case HandleEnum.TYPE_OF_DELETE:
                        //删除
                        this.deleteHandle(recordData);
                        break;
                    default:
                        log.error("未匹配到处理控制器的变更类型:{}", type);
                }
            }
        }
    }

    /**
     * 查看是否需要处理
     */
    protected abstract boolean needToDo(RecordData recordData);

    /**
     * 新增
     */
    protected abstract void insertHandle(RecordData recordData);

    /**
     * 修改
     */
    protected abstract void updateHandle(RecordData recordData);

    /**
     * 删除
     */
    protected abstract void deleteHandle(RecordData recordData);
}
