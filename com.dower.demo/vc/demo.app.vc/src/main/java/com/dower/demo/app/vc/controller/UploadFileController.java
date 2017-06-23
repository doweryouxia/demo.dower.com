package com.dower.demo.app.vc.controller;

import com.dower.demo.app.core.service.UploadFileService;
import com.dower.demo.app.vc.util.SystemInfo;
import com.dower.demo.comm.util.SpringMVCUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wang on 2017/6/15.
 */
@Controller
@RequestMapping("/uploadfile")
public class UploadFileController {
    private static Log logger = LogFactory.getLog(UserController.class);
    @Autowired
    private UploadFileService uploadFileService;
    @RequestMapping("/uploadImge")
    public ModelAndView uploadImge(String remarks, HttpServletRequest request, HttpServletResponse response){
        Map<String , Object> result = new HashMap<>();
        try {
            long  startTime=System.currentTimeMillis();
            //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
            CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            //检查form中是否有enctype="multipart/form-data"
            if(multipartResolver.isMultipart(request))
            {
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator iter=multiRequest.getFileNames();

                while(iter.hasNext())
                {
                    //一次遍历所有文件
                    MultipartFile file=multiRequest.getFile(iter.next().toString());
                    if(file!=null&&file.getSize()!=0)
                    {
                        System.out.println("fileName："+file.getOriginalFilename());
                        String path=SystemInfo.UPLOADFILE_PATH+System.currentTimeMillis()+"_"+file.getOriginalFilename();




                        //上传
                        file.transferTo(new File(path));
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("file_name",file.getOriginalFilename());
                        map.put("file_path",path);
                        map.put("remarks",remarks);
                        int file_type = 1;//文件 1   图片  2；
                        if(isValid(file.getOriginalFilename())){
                            file_type = 2;
                        }
                        map.put("file_type",file_type);
                        uploadFileService.uploadFile(map);
                    }

                }

            }
            result.put("success",true);
            long  endTime=System.currentTimeMillis();
            logger.info("运行时间："+String.valueOf(endTime-startTime)+"ms");
        } catch (Exception e) {
            result.put("success", false);
            logger.info("测试失败！");
            e.printStackTrace();
        }
        //SpringMVCUtils.renderJson(response, result);
        ModelAndView mav=new ModelAndView("upload");
        return mav;
        //resp.sendRedirect("index.jsp");
    }
    @RequestMapping("/queryFileList")
    public void queryFileList(HttpServletRequest request, HttpServletResponse response){
        Map<String , Object> result = new HashMap<>();
        try {
            HashMap<String,Object> param = new HashMap<String,Object>();
            param.put("file_type",1);
            List<HashMap<String, Object>> fileList = uploadFileService.queryFileListServer(param);
            result.put("list",fileList);


            result.put("success",true);
            logger.info("查询文件列表成功！");
        }catch (Exception e) {
            result.put("success", false);
            logger.info("查询文件列表失败！");
            e.printStackTrace();
        }
        SpringMVCUtils.renderJson(response, result);
    }

    @RequestMapping("/queryImgeList")
    public void queryImgeList(HttpServletRequest request, HttpServletResponse response){
        Map<String , Object> result = new HashMap<>();
        try {

            HashMap<String,Object> param = new HashMap<String,Object>();
            param.put("file_type",2);
            List<HashMap<String, Object>> imgeList = uploadFileService.queryFileListServer(param);
            result.put("list",imgeList);
            result.put("success",true);
            logger.info("查询文件列表成功！");
        }catch (Exception e) {
            result.put("success", false);
            logger.info("查询文件列表失败！");
            e.printStackTrace();
        }
        SpringMVCUtils.renderJson(response, result);
    }

    /**
     * 文件下载
     */
    @RequestMapping("/download1")
    public void downloadFile(String fileName,
                               HttpServletRequest request, HttpServletResponse response) {

        //设置响应头和客户端保存文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
        //用于记录以完成的下载的数据量，单位是byte
        long downloadedLength = 0l;
        try {
            //打开本地文件流
            InputStream inputStream = new FileInputStream(fileName);
            //激活下载操作
            OutputStream os = response.getOutputStream();

            //循环写入输出流
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
                downloadedLength += b.length;
            }

            // 这里主要关闭。
            os.close();
            inputStream.close();
        } catch (Exception e) {
        }
        //存储记录
    }



        @RequestMapping("download")
        public ResponseEntity<byte[]> download(String fileName,String name,
                                               HttpServletRequest request, HttpServletResponse response) throws IOException {
            File file=new File(fileName);
            HttpHeaders headers = new HttpHeaders();
            name=new String(name.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
            headers.setContentDispositionFormData("attachment", name);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.CREATED);
        }

    public static boolean isValid(String contentType) {
        String[] allowTypes = new String[] {"bmp", "dib", "gif","jfif", "jpe", "jpeg","jpg", "png" ,"tif","tiff", "ico"};
        if (null == contentType || "".equals(contentType)) {
            return false;
        }
        for (String type : allowTypes) {
            if (contentType.indexOf(type) > -1) {
                return true;
            }
        }
        return false;
    }


   /* public static boolean isImage(File imageFile) {
        String imgeArray [] =
                {"bmp", "dib", "gif",
                "jfif", "jpe", "jpeg"},
                "jpg", "png" ,"tif",
                "tiff", "ico"}
        };
        reutrn null;
    }*/
}
