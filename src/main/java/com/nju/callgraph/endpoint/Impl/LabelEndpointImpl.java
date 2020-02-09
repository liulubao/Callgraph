package com.nju.callgraph.endpoint.Impl;

import com.nju.callgraph.endpoint.LabelEndpoint;
import com.nju.callgraph.pojo.Label;
import com.nju.callgraph.pojo.Project;
import com.nju.callgraph.service.LabelService;
import com.nju.callgraph.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/label")
public class LabelEndpointImpl implements LabelEndpoint {
    @Autowired
    LabelService labelService;
    @Autowired
    ProjectService projectService;
    @Override
    @RequestMapping(value = "/savelabel",method = RequestMethod.POST)
    public Map<String, Object> saveLabel(@RequestBody Label label) {
        Map<String,Object> json = new HashMap<>();
        try{
            labelService.saveLabel(label);
            json.put("status",1);
        }catch (Exception e){
            e.printStackTrace();
            json.put("status",0);
            json.put("msg","保存失败");
        }

        return json;
    }

    @Override
    @RequestMapping(value = "/getbypidandparentelement",method = RequestMethod.GET)
    public Map<String,Object> getByPidAndParentElement(String projectid,String parentelement) {
        List<Label> list = new ArrayList<>();
        HashMap<String,Object> json = new HashMap<>();
        try{
            list = labelService.getByProjectAndParent(projectid,parentelement);
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            json.put("data",list);
            json.put("status",1);
        }catch (Exception e){
            e.printStackTrace();
            json.put("status",0);
        }
        return json;
    }

    @Override
    @RequestMapping(value = "/getlastestlabel",method = RequestMethod.GET)
    public List<Label> getLastestLabel(String username) {
        List<Project> project = new ArrayList<>();
        List<Label> label = new ArrayList<>();
        try{
            project = projectService.getByUserName(username,Integer.MAX_VALUE,0);
            for(Project p:project){
                List<Label> mid = labelService.getByProject(p.getProjectid());
                label.addAll(mid);
            }
            Collections.sort(label,new LabelComparetor());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return label;
    }

    @Override
    @RequestMapping(value = "/getbyusername",method = RequestMethod.GET)
    public Map<String,Object> getByUsername(String username, int limit, int offset) {
        Map<String,Object> json = new HashMap<>();
        try{
            List<Label> result = labelService.getByUserName(username,limit,offset);
            List<Label> numlist = labelService.getByUserName(username,Integer.MAX_VALUE,0);
            json.put("total",numlist.size());
            json.put("rows",result);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return json;
    }
}
