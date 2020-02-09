package com.nju.callgraph.service.Impl;

import com.nju.callgraph.mapper.ProjectMapper;
import com.nju.callgraph.pojo.Project;
import com.nju.callgraph.pojo.ProjectExample;
import com.nju.callgraph.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Override
    public void saveProject(Project project) {
        projectMapper.insert(project);
    }

    @Override
    public String getByProjectId(String id) {

        ProjectExample projectExample = new ProjectExample();
        projectExample.createCriteria().andProjectidEqualTo(id);
        List<Project> list = projectMapper.selectByExampleWithBLOBs(projectExample);

        Project project = list.get(0);
        String result = null;
        try {
            result = new String(project.getData(),"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        saveAsFileWriter(result);
        return result;
    }

    @Override
    public List<Project> getByUserName(String username,int limit,int offset) {
        List<Project> result = new LinkedList<>();
        try{
            ProjectExample projectExample = new ProjectExample();
            projectExample.createCriteria().andUsernameEqualTo(username);
            projectExample.setOrderByClause("createtime DESC");
            projectExample.setPageSize(limit);
            projectExample.setStartRow(offset);
            result=projectMapper.selectByExample(projectExample);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return result;
    }

    @Override
    public void shareProject(String username, String projectid) {
        ProjectExample projectExample = new ProjectExample();
        projectExample.createCriteria().andProjectidEqualTo(projectid);
        List<Project> list = projectMapper.selectByExampleWithBLOBs(projectExample);
        Project project = list.get(0);
        project.setProjectid(null);
        project.setUsername(username);
        projectMapper.insert(project);
    }

    @Override
    public List<Project> getRecentProject(String username, int limit, int offset) {
        List<Project> result = new LinkedList<>();
        try{
            ProjectExample projectExample = new ProjectExample();
            projectExample.createCriteria().andUsernameEqualTo(username);
            projectExample.setOrderByClause("createtime DESC");
            result=projectMapper.selectByExample(projectExample);
//            Collections.sort(result,new ProjectComparetor());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if (result.size() <= 5){
            return result;
        }else{
            return result.subList(0,5);
        }

    }

    private static String savefile = "D:\\test.txt";
    private static void saveAsFileWriter(String content) {

        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(savefile);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
