package com.nju.callgraph.service;

import gr.gousiosg.javacg.stat.JCallGraph;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 提供本地文件的上传和下载服务。这个作为测试使用。 因为本地文件不支持分布式同步，导致文件服务无法进行多实例部署。
 * 由于测试使用，这里没有对文件大小、类型等做检查。使用者需要注意做好检查，包含系统安全。
 */
@Component
public class LocalFileService implements FileService {
    // maxmum BUFFER_SIZE * BUFFER_NUM
    private static final int BUFFER_SIZE = 10240;

    private static final File BASE_FILE = new File("H:\\fileservice");

    @Override
    public Map<String,Object> uploadFile(MultipartFile file) {
        Map<String,Object> json = new HashMap<>();
        byte[] buffer = new byte[BUFFER_SIZE];
        String fileId = UUID.randomUUID().toString();
        json.put("fileid",fileId);
        String target = BASE_FILE.getPath()+"\\"+file.getOriginalFilename();
        String[] arguments = new String[1];
        arguments[0]=target;
        json.put("data",JCallGraph.analysis(arguments));
        return json;
    }

    @Override
    public Map<String, Object> uploadFileList(File[] files) {
        Map<String,Object> json = new HashMap<>();
        byte[] buffer = new byte[BUFFER_SIZE];
        String fileId = UUID.randomUUID().toString();
        json.put("fileid",fileId);
        String[] arguments = new String[1];
        json.put("data",JCallGraph.analysisTest(files));
        return json;
    }

    @Override
    public boolean deleteFile(String id) {
        File outFile = new File(BASE_FILE, id);
        return outFile.delete();
    }

    public static void changeToJson(){

    }
}
