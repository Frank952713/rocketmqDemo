package com.example.rocketmq_test01.handler.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordData implements Serializable {

	/**
	 * id
	 */
	private String pkValue;
	
	/**
	 * 数据库
	 */
	private String dbName;
	
	/**
	 * 表名
	 */
	private String table;
	
	/**
	 * 类型：INSERT,UPDATE,DELETE
	 */
	private String type;
	
	/**
	 *变更的字段列表
	 */
	private List<SimpleColumn> columns;
}
