package com.nju.callgraph.endpoint;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileServiceEndpoint {
    Map<String, Object> uploadFile(MultipartFile file_data, String loginname);
    Map<String, Object> uploadUrl(String url, String username);
}
