package com.nju.callgraph.service.Impl;

import com.nju.callgraph.mapper.UserMapper;
import com.nju.callgraph.pojo.User;
import com.nju.callgraph.pojo.UserExample;
import com.nju.callgraph.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(String name, String password) {
        User user = userMapper.findByNameAndPassword(name,password);
        return user;
    }

    @Override
    public void saveUser(User user)  {
        userMapper.insert(user);
    }

    @Override
    public List<User> findByLoginname(String loginname) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginnameEqualTo(loginname);
        List<User> list = userMapper.selectByExample(userExample);
        return list;
    }

}
