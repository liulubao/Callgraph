package com.nju.callgraph.endpoint;

import com.nju.callgraph.pojo.User;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserEndpoint {
    public ResponseEntity<Map<String, Object>> login(String username, String password);
    public Map<String,Object> register(User user);
    public Map<String,Object> findByLoginname(String loginname);
}
