package com.nju.callgraph.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

/**
 * 提供文件服务，可以有多个实现，比如文件、对象存储服务器、分布式文件存储等。
 */
public interface FileService {

    Map<String,Object> uploadFile(MultipartFile file);

    Map<String,Object> uploadFileList(File[] file);

    boolean deleteFile(String id);

}
