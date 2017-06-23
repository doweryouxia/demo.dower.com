package com.dower.demo.comm.util.local;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LocalUtil {
	
	private static String MAC;
	private static String IP;
	
	public static Logger log = LoggerFactory.getLogger(LocalUtil.class);
	
	public static String getK(){
		if(StringUtils.isEmpty(MAC))
			return getMACAddress();
		else
			return MAC;
	}
	
	public static String getIP(){
		if(isNull(IP))
			return getWLIP();
		else
			return IP;
	}
	
	
    /**
     * 获取unix网卡的mac地址. 非windows的系统默认调用本方法获取. 如果有特殊系统请继续扩充新的取mac地址方法.
     *
     * @return mac地址
     */
    private static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // linux下的命令，一般取eth0作为本地主网卡
            process = Runtime.getRuntime().exec("ifconfig");
            // 显示信息中包含有mac地址信息
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(),System.getProperty("sun.jnu.encoding")));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[hwaddr]
                index = line.toLowerCase().indexOf("hwaddr");
                if (index >= 0) {// 找到了
                    // 取出mac地址并去除2边空格
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                }
            }
        }
        catch (IOException e) {
        	log.info("unix/linux方式未获取到网卡地址");
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }

    /**
     * 获取widnows网卡的mac地址.
     *
     * @return mac地址
     */
    private static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // windows下的命令，显示信息中包含有mac地址信息
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(),System.getProperty("sun.jnu.encoding")));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串[physical
                index = line.toLowerCase().indexOf("物理地址");
                if (index >= 0) {// 找到了
                    index = line.indexOf(":");// 寻找":"的位置
                    if (index >= 0) {
                        // 取出mac地址并去除2边空格
                        mac = line.substring(index + 1).trim();
                    }
                }
            }
        }
        catch (IOException e) {
        	log.info("widnows方式未获取到网卡地址");
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }

    /**
     * windows 7 专用 获取MAC地址
     *
     * @return
     * @throws Exception
     */
    private static String getWindows7MACAddress() {
        StringBuffer sb = new StringBuffer();
        try {
            // 获取本地IP对象
            InetAddress ia = InetAddress.getLocalHost();
            // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            for (int i = 0; i < mac.length; i++) {
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
        }
        catch (Exception ex) {
        	log.info("windows方式2未获取到网卡地址");
        }
        return sb.toString();
    }

    /**
     * 获取MAC地址
     *
     * @param argc
     *            运行参数.
     * @throws Exception
     */
    private static String getMACAddress() {
    	log.info("------------本地编码："+System.getProperty("sun.jnu.encoding"));
        // windows
        String mac = getWindowsMACAddress();
        // windows7
        if (isNull(mac)) {
            mac = getWindows7MACAddress();
        }
        // unix
        if (isNull(mac)) {
            mac = getUnixMACAddress();
        }

        if (!isNull(mac)) {
            mac = mac.replace("-", "");
        }
        else {
            mac = "ABCDEFGHIJ";
        }
        MAC =mac.toLowerCase();
        log.info("----------------本机mac地址为:"+MAC);
        return MAC;
    }
    
    private static String getWLIP(){
    	String ip = getLinuxIp();
    	if(isNull(ip))
    		ip = getWindowsIp();
    	if(isNull(ip))
    		ip = "127.0.0.1";
    	log.info("----------------本机ip地址为:"+ip);
    	return ip;
    }
    
    /**
     * linux获取ip方法
     * @作者sifan
     * @联系方式QQ：995998760
     * @时间：2015年12月22日上午11:48:38
     * @return String
     */
	private static String getLinuxIp(){
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			String[] command = { "/bin/sh", "-c", "ifconfig|grep Bcast|awk -F: '{print $2}'|awk -F ' ' '{print $1}'" };
			process = Runtime.getRuntime().exec(command);
			bufferedReader= new BufferedReader(new InputStreamReader(process.getInputStream(),System.getProperty("sun.jnu.encoding")));
			return bufferedReader.readLine();
		} catch (Exception e) {
			log.error("获取LINUX下本地IP报错，" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 得到windows系统的ip
	 * @作者sifan
	 * @联系方式QQ：995998760
	 * @时间：2015年12月22日下午1:09:12
	 * @return String
	 */
	private static String getWindowsIp(){
		try {
			 InetAddress ia = InetAddress.getLocalHost();
			 return ia.getHostAddress();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
    private static boolean isNull(String cs) {
        return StringUtils.isEmpty(cs);
    }
	
}
