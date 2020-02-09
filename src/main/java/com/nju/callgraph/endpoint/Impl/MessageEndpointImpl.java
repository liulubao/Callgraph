package com.nju.callgraph.endpoint.Impl;

import com.nju.callgraph.endpoint.MessageEndpoint;
import com.nju.callgraph.pojo.Message;
import com.nju.callgraph.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/message")
public class MessageEndpointImpl implements MessageEndpoint {
    @Autowired
    MessageService messageService;
    @Override
    @RequestMapping(value = "/savemessage",method = RequestMethod.POST)
    public Map<String,Object> saveMessage(@RequestBody Message message) {
        Map<String,Object> result = new HashMap<>();
        message.setSendTime(new Date().toString());
        try {
            messageService.saveMessage(message);
            result.put("status",1);
        }catch (Exception e){
            e.printStackTrace();
            result.put("status",0);
        }
        return result;
    }

    @Override
    @RequestMapping(value = "/getbybothloginname",method = RequestMethod.GET)
    public Map<String, Object> getByBothLoginname(String userName, String friendName) {
        Map<String,Object> result = new HashMap<>();
        try{
            List<Message> list = messageService.getByBothLoginname(userName,friendName);
//            Collections.sort(list);
            result.put("status",1);
            result.put("data",list);
        }catch (Exception e){
            e.printStackTrace();
            result.put("status",0);
        }
        return result;
    }

    @Override
    @RequestMapping(value = "/getmessagelist",method = RequestMethod.GET)
    public Map<String, Object> getMessageList(String userName) {
        Map<String,Object> result = new HashMap<>();
        try{
            List<Message> list = messageService.getMessageListByLoginname(userName);
            result.put("status",1);
            result.put("data",list);
        }catch (Exception e){
            e.printStackTrace();
            result.put("status",0);
        }
        return result;
    }
}
