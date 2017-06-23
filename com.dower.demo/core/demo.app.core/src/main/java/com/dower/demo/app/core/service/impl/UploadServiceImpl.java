package com.dower.demo.app.core.service.impl;


import com.dower.demo.app.core.dao.UploadFileMapper;
import com.dower.demo.app.core.dao.UserMapper;
import com.dower.demo.app.core.dto.User;
import com.dower.demo.app.core.service.UploadFileService;
import com.dower.demo.app.core.service.UserService;
import com.dower.demo.comm.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2017/6/14.
 */
@Service
public class UploadServiceImpl implements UploadFileService {
    @Autowired
    private UploadFileMapper uploadFileMapper;
    @Override
    public HashMap<String,Object> uploadFile(HashMap<String,Object> map) {
        HashMap<String,Object> resultMap = new HashMap<String,Object>();
        int i = uploadFileMapper.insertUploadFile(map);
        resultMap.put("id",i);
        return resultMap;
    }

    @Override
    public List<HashMap<String, Object>> queryFileListServer(HashMap<String, Object> map) {
        return uploadFileMapper.queryFileList(map);
    }


}
