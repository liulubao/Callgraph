package com.nju.callgraph.service;

import com.nju.callgraph.pojo.Message;

import java.util.List;

public interface MessageService {
    void saveMessage(Message message);
    List<Message> getByBothLoginname(String userName, String friendName);
    List<Message> getMessageListByLoginname(String userName);
}
