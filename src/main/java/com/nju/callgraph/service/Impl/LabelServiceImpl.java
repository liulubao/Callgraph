package com.nju.callgraph.service.Impl;

import com.nju.callgraph.mapper.LabelMapper;
import com.nju.callgraph.pojo.Label;
import com.nju.callgraph.pojo.LabelExample;
import com.nju.callgraph.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    LabelMapper labelMapper;
    @Override
    public void saveLabel(Label label) {
        labelMapper.insert(label);
    }

    @Override
    public List<Label> getByProjectAndParent(String projectid, String parentelement) {
        List<Label> result = new ArrayList<>();
        try{
            LabelExample labelExample = new LabelExample();
            labelExample.or().andProjectidEqualTo(projectid).andParentelementEqualTo(parentelement);
//            labelExample.createCriteria().andParentelementEqualTo();
            result=labelMapper.selectByExample(labelExample);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return result;
    }

//    @Override
//    public List<Label> getByParent(String parentelement) {
//        List<Label> result = new ArrayList<>();
//        try{
//            LabelExample labelExample = new LabelExample();
//            labelExample.createCriteria().andParentelementEqualTo(parentelement);
//            result=labelMapper.selectByExample(labelExample);
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//        return result;
//    }

    @Override
    public List<Label> getByUserName(String username, int limit, int offset) {

        List<Label> result = new ArrayList<>();
        try{
            LabelExample labelExample = new LabelExample();
            labelExample.createCriteria().andOwnernameEqualTo(username);
            labelExample.setOrderByClause("createtime DESC");
            result = labelMapper.selectByExample(labelExample);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if (result.size()<limit){
            return result;
        } else{
            return result.subList(0,limit);
        }

    }

    @Override
    public List<Label> getByProject(String projectid) {
        List<Label> result = new ArrayList<>();
        try{
            LabelExample labelExample = new LabelExample();
            labelExample.createCriteria().andProjectidEqualTo(projectid);
            result = labelMapper.selectByExample(labelExample);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return result;
    }

}
