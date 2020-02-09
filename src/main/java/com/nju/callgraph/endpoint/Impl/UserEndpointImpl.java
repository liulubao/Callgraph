package com.nju.callgraph.endpoint.Impl;


import com.nju.callgraph.endpoint.UserEndpoint;
import com.nju.callgraph.pojo.User;
import com.nju.callgraph.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(value = "/user")
public class UserEndpointImpl implements UserEndpoint {
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public Map<String, Object> hello() {
        Map<String, Object> json = new HashMap<>();
        json.put("status",1);
        return json;
    }

    @Autowired
    UserService userService;

    @Override
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(String username, String password) {
        Map<String,Object> json = new HashMap<>();
            User user = userService.login(username,password);
            if(user == null){
                json.put("status",0);
                return new ResponseEntity<Map<String,Object>>(json, HttpStatus.OK);
            }else{
                json.put("result",user);
                json.put("status",1);
                return new ResponseEntity<Map<String,Object>>(json,HttpStatus.OK);
            }

    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map<String, Object> register(@RequestBody User user){
        Map<String, Object> json = new HashMap<String, Object>();
        try{
            userService.saveUser(user);
            json.put("status",1);
        }catch (Exception e){
            json.put("status",0);
            json.put("message","该账号已经存在");
        }
        return json;
    }

    @Override
    @RequestMapping(value = "/findbyloginname",method = RequestMethod.GET)
    public Map<String, Object> findByLoginname(String loginname) {
        List<User> list = userService.findByLoginname(loginname);
        Map<String,Object> map = new HashMap<>();
        map.put("result",list);
        return map;
    }

    private HttpHeaders generateAuthenticationHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, token);
        return headers;
    }

}
