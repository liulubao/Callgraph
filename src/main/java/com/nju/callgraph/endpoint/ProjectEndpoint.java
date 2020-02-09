package com.nju.callgraph.endpoint;


import com.nju.callgraph.pojo.Project;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface ProjectEndpoint {
    Map<String, Object> saveProject(Project project);
    Map<String, Object> getDenpendenByProjectId(String id);
    Map<String, Object> getAllProject(String username,int limit,int offset);
    Map<String, Object> getRecentProject(String username, int limit, int offset);
    Map<String,Object> share(String username, String projectid);
}
