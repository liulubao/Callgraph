package com.nju.callgraph.service.Impl;

import com.nju.callgraph.mapper.FriendMapper;
import com.nju.callgraph.pojo.Friend;
import com.nju.callgraph.pojo.FriendExample;
import com.nju.callgraph.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    FriendMapper friendMapper;
    @Override
    public void saveFrind(Friend friend) {
        friendMapper.insert(friend);
    }

    @Override
    public List<Friend> selectByBothLoginname(String followingloginname, String followedloginname) {

        List<Friend> result = new ArrayList<>();
        try{
            FriendExample friendExample = new FriendExample();
            friendExample.or().andFollowedloginnameEqualTo(followedloginname).andFollowingloginnameEqualTo(followingloginname);
            result = friendMapper.selectByExample(friendExample);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public List<Friend> selectByFollowingLogingName(String followingloginname) {
        List<Friend> result = new ArrayList<>();
        try{
            FriendExample friendExample = new FriendExample();
            friendExample.createCriteria().andFollowingloginnameEqualTo(followingloginname);
            result = friendMapper.selectByExample(friendExample);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
