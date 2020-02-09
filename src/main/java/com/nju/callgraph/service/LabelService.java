package com.nju.callgraph.service;

import com.nju.callgraph.pojo.Label;

import java.util.List;

public interface LabelService {
    void saveLabel(Label label);
    List<Label> getByProjectAndParent(String projectid, String parentelement);
    List<Label> getByUserName(String username, int limit, int offset);
    List<Label> getByProject(String projectid);
}
