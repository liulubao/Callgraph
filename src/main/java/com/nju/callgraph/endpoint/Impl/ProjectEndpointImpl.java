package com.nju.callgraph.endpoint.Impl;

import com.nju.callgraph.endpoint.ProjectEndpoint;
import com.nju.callgraph.endpoint.UserEndpoint;

import com.nju.callgraph.pojo.Project;
import com.nju.callgraph.service.ProjectService;
import com.nju.callgraph.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/project")
public class ProjectEndpointImpl implements ProjectEndpoint {

    @Autowired
    ProjectService projectService;
    @Autowired
    UserService userService;
    @Override
    @RequestMapping(value = "/saveproject",method = RequestMethod.POST)
    public Map<String, Object> saveProject(@RequestBody Project project) {
        Map<String, Object> json = new HashMap<String, Object>();
        try {
            projectService.saveProject(project);
            json.put("status",1);
        }catch (Exception e){
            json.put("status",0);
        }
        return json;
    }

    @Override
    @RequestMapping(value = "/getdenpendenbyprojectid",method = RequestMethod.GET)
    public Map<String, Object> getDenpendenByProjectId(String id) {
        String result = projectService.getByProjectId(id);
        if(result.contains("$")){
            result = result.replaceAll("[$]", "0");
        }
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("status",1);
        JSONObject myJson1 = JSONObject.fromObject(result);
        json.put("data",myJson1);
        return json;
    }

    @Override
    @RequestMapping(value = "/getallproject",method = RequestMethod.GET)
    public Map<String, Object> getAllProject(String username,int limit,int offset) {
        Map<String, Object> json = new HashMap<String, Object>();
        try{
            List<Project> list = projectService.getByUserName(username,limit,offset);
            List<Project> numlist = projectService.getByUserName(username,Integer.MAX_VALUE,0);
            json.put("total",numlist.size());
            json.put("rows",list);
            json.put("status",1);
        }catch (Exception e){
            e.printStackTrace();
            json.put("status",0);
        }

        return json;
    }

    @Override
    @RequestMapping(value = "/getrecentproject",method = RequestMethod.GET)
    public Map<String, Object> getRecentProject(String username, int limit, int offset) {
        Map<String, Object> json = new HashMap<String, Object>();
        try{
            List<Project> list = projectService.getByUserName(username,limit,offset);
            List<Project> numlist = projectService.getByUserName(username,Integer.MAX_VALUE,0);
            json.put("total",numlist.size());
            json.put("rows",list);
            json.put("status",1);
        }catch (Exception e){
            e.printStackTrace();
            json.put("status",0);
        }

        return json;
    }

    @RequestMapping(value = "/share",method = RequestMethod.POST)
    public Map<String,Object> share(String username,String projectid){
        List<Object>  list = (List)userService.findByLoginname(username);
        Map<String,Object> json = new HashMap<>();
        if(list.size() == 0){
            json.put("status",0);
            json.put("message","没有这个用户");
        }else{
            try{
                projectService.shareProject(username,projectid);
                json.put("status",1);
            }catch (Exception e){
                e.printStackTrace();
                json.put("status",0);
                json.put("message","分享失败");
            }
        }
        return json;
    }
}
