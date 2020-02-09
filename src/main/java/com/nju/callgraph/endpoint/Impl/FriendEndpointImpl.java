package com.nju.callgraph.endpoint.Impl;

import com.nju.callgraph.endpoint.FriendEndpoint;
import com.nju.callgraph.pojo.Friend;
import com.nju.callgraph.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/friend")
public class FriendEndpointImpl implements FriendEndpoint {
    @Autowired
    FriendService friendService;
    @Override
    @RequestMapping(value = "/savefriend",method = RequestMethod.POST)
    public Map<String, Object> saveFriend(@RequestBody Friend friend) {
        Map<String,Object> result = new HashMap<>();
        try{
            friendService.saveFrind(friend);
            result.put("status",1);
        }catch (Exception e){
            e.printStackTrace();
            result.put("status",0);
            result.put("message","关注失败");
        }
        return result;
    }

    @Override
    @RequestMapping(value = "/getbybothloginname",method = RequestMethod.GET)
    public Map<String, Object> getByBothLoginname(String followingloginname, String followedloginname) {
        Map<String,Object> result = new HashMap<>();
        List<Friend> list = new ArrayList<>();
        try{
            list = friendService.selectByBothLoginname(followingloginname,followedloginname);
            result.put("status",1);
            result.put("data",list);
        }catch (Exception e){
            e.printStackTrace();
            result.put("status",0);
        }
        return result;
    }

    @Override
    @RequestMapping(value = "/getbyfollowingloginname",method = RequestMethod.GET)
    public Map<String, Object> getByFollowingLoginname(String followingloginname) {
        Map<String,Object> result = new HashMap<>();
        List<Friend> list = new ArrayList<>();
        try{
            list = friendService.selectByFollowingLogingName(followingloginname);
            result.put("status",1);
            result.put("total",list.size());
            result.put("rows",list);
        }catch (Exception e){
            e.printStackTrace();
            result.put("status",0);
            return result;
        }
        return result;
    }
}
