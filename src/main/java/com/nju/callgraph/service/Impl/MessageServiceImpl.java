package com.nju.callgraph.service.Impl;

import com.nju.callgraph.mapper.MessageMapper;
import com.nju.callgraph.pojo.Message;
import com.nju.callgraph.pojo.MessageExample;
import com.nju.callgraph.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Override
    public void saveMessage(Message message) {
        messageMapper.insert(message);
        String mid = message.getUserName();
        message.setUserName(message.getFriendName());
        message.setFriendName(mid);
        messageMapper.insert(message);
    }

    @Override
    public List<Message> getByBothLoginname(String userName, String friendName) {
        List<Message> result = new ArrayList<>();
        try{
            MessageExample messageExample = new MessageExample();
            messageExample.or().andUserNameEqualTo(userName).andFriendNameEqualTo(friendName);
            result = messageMapper.selectByExample(messageExample);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return result;
    }

    @Override
    public List<Message> getMessageListByLoginname(String userName) {
        List<Message> result = new ArrayList<>();
        try{
            MessageExample messageExample = new MessageExample();
//            messageExample.or();
            messageExample.createCriteria().andUserNameEqualTo(userName);
            result = messageMapper.selectListByLoginname(userName);
//            result = messageMapper.selectByExample(messageExample);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
