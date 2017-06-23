package com.dower.demo.comm.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class FileUtil {
	private static Logger logger = Logger.getLogger(FileUtil.class);
	/**
	 * 复制文件
	 * @param sourceFile 原始文件路径
	 * @param targetFile 复制到文件路径
	 * @throws IOException
	 */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
	
    /**
     * 删除目录下的所有文件
     * @param filepath 文件目录
     * @throws IOException
     */
    public static void del(String filepath) throws IOException {
        File f = new File(filepath);// 定义文件路径
        if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
            if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
                f.delete();
            } else {// 若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();// 删除文件
                }
            }
        }
    }
    
    /**
     * 删除文件
     * @param filepath 文件路径
     * @throws IOException
     */
    public static void delFile(String filepath) throws IOException {
        File f = new File(filepath);// 定义文件路径
        if (f.exists()) {// 判断是文件
            f.delete();
        }
    }
    /**
     * 通过HTTP读取文件
     * @param paramUrl http地址
     * @param path 本地存放图片地址
     * @param fileName 文件名称
     * @throws Exception
     */
     public static void getImgByHttp(String paramUrl,String path,String fileName) throws Exception {
 		//new一个URL对象
 		URL url = new URL(paramUrl);
 		//打开链接
 		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
 		//设置请求方式为"GET"
 		conn.setRequestMethod("GET");
 		//超时响应时间为5秒
 		conn.setConnectTimeout(5 * 1000);
 		//通过输入流获取图片数据
 		InputStream inStream = conn.getInputStream();
 		//得到图片的二进制数据，以二进制封装得到数据，具有通用性
 		byte[] data = readInputStream(inStream);
 		//new一个文件对象用来保存图片，默认保存当前工程根目录
 		File f = new File(path);
 		
 		if(!f.exists()){
 			f.mkdirs();
 		}
 		
 		String filePath = path+File.separator+fileName;
 		
 		File imageFile = new File(filePath);
 		//创建输出流
 		FileOutputStream outStream = new FileOutputStream(imageFile);
 		//写入数据
 		outStream.write(data);
 		//关闭输出流
 		outStream.close();
 	}
 	public static byte[] readInputStream(InputStream inStream) throws Exception{
 		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
 		//创建一个Buffer字符串
 		byte[] buffer = new byte[1024];
 		//每次读取的字符串长度，如果为-1，代表全部读取完毕
 		int len = 0;
 		//使用一个输入流从buffer里把数据读取出来
 		while( (len=inStream.read(buffer)) != -1 ){
 			//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
 			outStream.write(buffer, 0, len);
 		}
 		//关闭输入流
 		inStream.close();
 		//把outStream里的数据写入内存
 		return outStream.toByteArray();
 	}

}
