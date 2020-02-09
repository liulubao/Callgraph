package com.nju.callgraph.endpoint;

import com.nju.callgraph.pojo.Label;

import java.util.List;
import java.util.Map;

public interface LabelEndpoint {
    Map<String,Object> saveLabel(Label label);
    Map<String,Object> getByUsername(String username, int limit, int offset);
    Map<String,Object> getByPidAndParentElement(String projectid, String parentelement);
    List<Label> getLastestLabel(String username);
}
