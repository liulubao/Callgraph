package com.nju.callgraph.endpoint;

import com.nju.callgraph.pojo.Message;

import java.util.Map;

public interface MessageEndpoint {
    Map<String,Object> saveMessage(Message message);
    Map<String,Object> getByBothLoginname(String userName, String friendName);
    Map<String,Object> getMessageList(String userName);
}
