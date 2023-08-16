package com.example.rocketmq_test01.producer;

public interface MsgSender {

	public void sendMessage(Object  data,String topic,String tag);
}
