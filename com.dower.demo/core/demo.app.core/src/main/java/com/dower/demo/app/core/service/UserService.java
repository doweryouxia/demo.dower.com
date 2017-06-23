package com.dower.demo.app.core.service;



import com.dower.demo.app.core.dto.User;

import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2017/6/14.
 */
public interface UserService {
    public List<Map<String, String>> selectById(int id);
}
