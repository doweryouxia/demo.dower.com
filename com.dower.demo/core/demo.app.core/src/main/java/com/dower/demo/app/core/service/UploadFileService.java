package com.dower.demo.app.core.service;



import com.dower.demo.app.core.dto.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2017/6/14.
 */
public interface UploadFileService {
    public HashMap<String,Object> uploadFile(HashMap<String,Object> map);
    public List<HashMap<String,Object>> queryFileListServer(HashMap<String,Object> map);
}
