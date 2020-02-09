package com.nju.callgraph.endpoint;

import com.nju.callgraph.pojo.Friend;

import java.util.Map;

public interface FriendEndpoint {
    Map<String,Object> saveFriend(Friend friend);
    Map<String,Object> getByBothLoginname(String followingloginname, String followedloginname);
    Map<String,Object> getByFollowingLoginname(String followingloginname);
}
