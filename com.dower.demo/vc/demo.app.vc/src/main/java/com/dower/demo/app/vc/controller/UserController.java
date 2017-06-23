package com.dower.demo.app.vc.controller;

import com.dower.demo.app.core.dto.User;
import com.dower.demo.app.core.service.UserService;
import com.dower.demo.comm.util.SpringMVCUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * Created by wang on 2017/6/14.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Log logger = LogFactory.getLog(UserController.class);

    @Resource(name = "userServiceImpl")
    private UserService userService;
    @RequestMapping("/login")
    public void login ( String username,String password ,HttpServletRequest request, HttpServletResponse response)throws IOException {
        Map<String , Object> result = new HashMap<>();
        try {
            if ("admin".equals(username)&&"admin".equals(password)){
                result.put("success", true);
                result.put("body", userService.selectById(1));
            }else {
                result.put("success", false);
            }

            logger.info("测试成功！");
        } catch (Exception e) {
            result.put("success", false);
            logger.info("测试失败！");
            e.printStackTrace();
        }
        SpringMVCUtils.renderJson(response, result);
    }
    @RequestMapping("/test")
    public void test (HttpServletRequest request, HttpServletResponse response)throws IOException {
        Map<String , Object> result = new HashMap<>();
        try {
            String body = "[ { \"did\":1, \"name\":\"【酸甜开胃虾】\", \"price\":\"36\", \"meterial\":\"原料是；；；；；\", \"detail\":\"详情是*******\", \"img_sm\":\"0.jpg\", \"img_lg\":\"1.jpg\" }, { \"did\":2, \"name\":\"【酸甜开胃虾】\", \"price\":\"36\", \"meterial\":\"原料是；；；；；\", \"detail\":\"详情是*******\", \"img_sm\":\"0.jpg\", \"img_lg\":\"1.jpg\" }, { \"did\":3, \"name\":\"【桂香紫薯山药卷】\", \"price\":\"36\", \"meterial\":\"原料是；；；；；\", \"detail\":\"详情是*******\", \"img_sm\":\"0.jpg\", \"img_lg\":\"1.jpg\" }, { \"did\":4, \"name\":\"【小米椒爆炒小蜜】\", \"price\":\"13.6\", \"meterial\":\"原料是；；；；；\", \"detail\":\"详情是*******\", \"img_sm\":\"0.jpg\", \"img_lg\":\"1.jpg\" }, { \"did\":5, \"name\":\"【麻辣土豆】\", \"price\":\"12.36\", \"meterial\":\"原料是；；；；；\", \"detail\":\"详情是*******\", \"img_sm\":\"0.jpg\", \"img_lg\":\"1.jpg\" } ]";
            result.put("success", true);
            result.put("body",body);
            logger.info("测试成功！");
        } catch (Exception e) {
            result.put("success", false);
            logger.info("测试失败！");
            e.printStackTrace();
        }
        SpringMVCUtils.renderJson(response, result);
    }
}
