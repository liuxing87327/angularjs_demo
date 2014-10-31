package com.dooioo.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Strings;

/**
 * 
 * 类功能说明：IP处理类
 * Title: IpUtils.java
 * @author 刘兴
 * @date 2014年10月31日 上午1:36:44
 * @version V1.0
 */
public class IpUtils {
	
	/**
     * 判断当前系统是否是windows系统
     * @return
     */
    public static boolean isWinOS() { 
        if (System.getProperty("os.name").toLowerCase().contains("windows")) { 
        	return true; 
        } 
        
        return false; 
    } 
   
    /**
     * 获取本机IP地址
     * 并自动区分Windows还是Linux操作系统 
     * @return
     * @throws UnknownHostException 
     * @throws SocketException 
     */
    public static String getLocalIP() { 
        String sIp = ""; 
        InetAddress ip = null;
        
        // 如果是Windows操作系统 
        if (isWinOS()) { 
        	try {
				ip = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
        	
            if (null != ip) { 
            	sIp = ip.getHostAddress();
            	ip = null;
            	return sIp;
            }
        } 
        
        // 如果是Linux操作系统 
        boolean bFindIP = false;
        NetworkInterface network = null;
        Enumeration<InetAddress> ips = null;
        Enumeration<NetworkInterface> netInterfaces = null;
        
		try {
			netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		} 
        
		while (netInterfaces.hasMoreElements()) { 
            if (bFindIP) { 
                break; 
            } 
            
            network = (NetworkInterface) netInterfaces.nextElement(); 
            
            // ----------特定情况，可以考虑用ni.getName判断 
            // 遍历所有ip 
            ips = network.getInetAddresses(); 
			while (ips.hasMoreElements()) {
				ip = (InetAddress) ips.nextElement();
				
				if(ip.isLoopbackAddress()){
					continue;
				}
				
				if(ip.getHostAddress().contains(":")){
					continue;
				}
				
				if (ip.isSiteLocalAddress()) {
					bFindIP = true;
					break;
				}
			}
   
        } 
   
        if (null != ip) { 
        	sIp = ip.getHostAddress(); 
        }
        
        ip = null;
        ips = null;
        network = null;
        netInterfaces = null;
        
        return sIp; 
    }
    
    /**
     * 获取客户端请求的IP地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("Cdn-Src-Ip");//增加CDN获取ip
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
}
