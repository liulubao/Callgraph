package com.nju.callgraph.endpoint.Impl;

import com.nju.callgraph.endpoint.FileServiceEndpoint;
import com.nju.callgraph.pojo.Project;
import com.nju.callgraph.service.FileService;
import com.nju.callgraph.service.ProjectService;
import com.nju.callgraph.service.url.UrlDownload;
import com.nju.callgraph.service.url.JustTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/file")
public class FileServiceEndpointImpl implements FileServiceEndpoint {
    @Autowired
    private FileService fileService;
    @Autowired
    private ProjectService projectService;
    /**
     * 上传文件接口，用户上传一个文件，返回文件ID。
     */
    @PostMapping(path = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> uploadFile(MultipartFile file_data,String loginname) {
        Map<String,Object> map = fileService.uploadFile(file_data);
        if(loginname.length()!=0){
            Project project = new Project();
            project.setUsername(loginname);
            project.setProjectname(file_data.getOriginalFilename());
            project.setProjectid(map.get("fileid").toString());
            project.setData(map.get("data").toString().getBytes());
            projectService.saveProject(project);
        }else{ }
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("jar_id",map.get("fileid"));
        return json;
    }

    /**
     * 上传文件接口，用户上传一个文件，返回文件ID。
     */
    @PostMapping(path = "/uploadurl")
    public Map<String, Object> uploadUrl(String url,String username) {
        Map<String, Object> json = new HashMap<String, Object>();
        String result = null;
        if (!(url.contains("https") && url.contains("git"))){
            json.put("status",2);
            return json;
        }
        if(username.length()!=0){
            String targetName = UrlDownload.getJar(url);
            if (!searchFile(new File(targetName))){
                json.put("status",3);
                return json;
            }

            try {
                result = UrlDownload.transferToJar("testFile",targetName);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            String pathName = result + "\\target";

            File[] jars = new File(pathName).listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.isFile() && pathname.getName().endsWith(".jar");
                }
            });
            for(File file:jars){
                System.out.println("file is : " +file.getPath());
            }
            Map<String,Object> map = fileService.uploadFileList(jars);
            System.out.println("file upload success");
            json.put("status",1);
            json.put("jar_id",map.get("fileid").toString());
            Project project = new Project();
            project.setUsername(username);
            project.setProjectname(url);
            project.setProjectid(map.get("fileid").toString());
            json.put("jar_id",map.get("fileid").toString());
            project.setData(map.get("data").toString().getBytes());
            projectService.saveProject(project);
        }else{
            json.put("status",0);
            json.put("description","login is null");
        }
        deleteAll(new File(result));
        return json;
    }
    public static void deleteAll(File dir) {
        if (dir.isFile()) {
            return;
        } else {
            File[] files = dir.listFiles();
            for (File file : files) {
                deleteAll(file);
            }
        }
        dir.delete();
//        System.out.println(dir + " : " + dir.delete());
    }

    public static boolean searchFile(File dir) {
        String folder = "testFile";
        String realName= null;
        try {
            realName = JustTest.class.getClass().getResource("/").toURI().getPath().substring(1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        realName=realName.replace("%", " ");
        realName=realName.replace("target/classes/", "");
        realName=realName.replace("/", "\\\\");
        realName=realName+folder+"\\\\"+dir;
        File[] files = new File(realName).listFiles();
        boolean flag = false;
        for (File file : files) {
            if (file.getName().contains("pom.xml")){
                flag = true;
            }
        }
        return flag;
    }
}
