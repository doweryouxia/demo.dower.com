package com.dower.demo.app.core.service.impl;


import com.dower.demo.app.core.dao.UserMapper;
import com.dower.demo.app.core.dto.User;
import com.dower.demo.app.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2017/6/14.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<Map<String, String>> selectById(int id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1);
        List<Map<String, String>> list = userMapper.selectById(map);
        return list;
    }
}
