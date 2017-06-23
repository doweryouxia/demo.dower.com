package com.dower.demo.app.core.dao;


import com.dower.demo.app.core.dto.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2017/6/14.
 */

public interface UserMapper {
    public List<Map<String, String>> selectById(HashMap map);
}