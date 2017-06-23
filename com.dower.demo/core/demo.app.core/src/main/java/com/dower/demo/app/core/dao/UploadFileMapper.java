package com.dower.demo.app.core.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2017/6/14.
 */

public interface UploadFileMapper {

    /**
     * 上传图片
     * @param map
     * @return
     */
    public int insertUploadFile(HashMap map);

    /**
     * 查询列表
     * @param map
     * @return
     */
    public List<HashMap<String, Object>> queryFileList(HashMap map);
}