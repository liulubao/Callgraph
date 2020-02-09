package com.nju.callgraph.service;

import com.nju.callgraph.pojo.User;

import java.util.List;

public interface UserService {
    User login(String name, String password);
    void saveUser(User user);
    List<User> findByLoginname(String loginname);
}
