package com.nju.callgraph.service;

import com.nju.callgraph.pojo.Project;

import java.util.List;

public interface ProjectService {
    void saveProject(Project project);
    String getByProjectId(String id);
    List<Project> getByUserName(String username, int limit, int offset);
    void shareProject(String username, String projectid);
    List<Project> getRecentProject(String username, int limit, int offset);
}
