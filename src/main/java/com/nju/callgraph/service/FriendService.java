package com.nju.callgraph.service;

import com.nju.callgraph.pojo.Friend;

import java.util.List;

public interface FriendService {
    void saveFrind(Friend friend);
    List<Friend> selectByBothLoginname(String followingloginname, String followedloginname);
    List<Friend> selectByFollowingLogingName(String followingloginname);
}
