/**   
 * @Title: MongoDBBasic.java
 * @Package servlet
 * @Description: TODO
 * @author 刘兴 
 * @date 2014年10月26日 下午4:15:23
 * @version V1.0   
 */
package com.dooioo.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.dooioo.db.MongoService;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;

/**
 * 类功能说明：数据CURD相关操作的接口
 * Title: MongoDBBasic.java
 * @author 刘兴
 * @date 2014年10月26日 下午4:15:23
 * @version V1.0
 */
public class ApiServlet extends HttpServlet { 
	 
	private static final long serialVersionUID = 3553230795361397498L;

    private static final Logger logger = Logger.getLogger(ApiServlet.class);
	
	private MongoService mongoService = new MongoService();

    /**
     * 查询入口
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String requestUrl = request.getRequestURI();
		
		if(requestUrl.endsWith("employee")) {
			this.query(request, response);
		}else{
	        String userCode = requestUrl.substring(requestUrl.lastIndexOf("/") + 1);
			this.findOne(userCode, response);
		}
	}

    /**
     * 提交数据入口
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

        String requestUrl = request.getRequestURI();

		if(requestUrl.endsWith("employee")) {
			this.add(request, response);
		} else {
			this.update(request, response);
		}
		
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _method = defaultWebStr(request, "_method", "");

        if(Strings.equals(_method, "initDate")){
            this.initDate(request, response);
        }else{
            this.update(request, response);
        }
	}

    /**
     * 删除数据的入口
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String requestUrl = request.getRequestURI();
        String userCode = requestUrl.substring(requestUrl.lastIndexOf("/") + 1);
        this.delete(userCode, response);
    }

    /**
	 * 
	 * 功能说明：查询列表
	 * @author 刘兴 
	 * @Date 2014年10月26日 下午9:29:58
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void query(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String keyword = defaultWebStr(request, "keyword", "");
		String status = defaultWebStr(request, "status", "");
		String dateFrom = defaultWebStr(request, "dateFrom", "");
		String dateTo = defaultWebStr(request, "dateTo", "");
		String pageNo = defaultWebStr(request, "pageNo", "1");
		
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> paginate = mongoService.query(keyword, dateFrom, dateTo, status, Integer.parseInt(pageNo));
		
		map.put("paginate", paginate);
		String json = JSON.toJSONString(map);
		
		this.write(json, response);
	}
	
	/**
	 * 
	 * 功能说明：查询单个明细
	 * @author 刘兴 
	 * @Date 2014年10月26日 下午9:30:08
	 * @param userCode
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void findOne(String userCode, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map<String, Object> map = mongoService.findOne(userCode);
		String json = JSON.toJSONString(map);
		this.write(json, response);
	}
	
	/**
	 * 
	 * 功能说明：删除一个记录
	 * @author 刘兴 
	 * @Date 2014年10月26日 下午9:30:18
	 * @param userCode
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	private void delete(String userCode, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map<String, Object> map = new HashMap<>();
		
		if(mongoService.remove(userCode)){
			map.put("status", "ok");
		} else {
			map.put("status", "fail");
		}
		
		String json = JSON.toJSONString(map);
		this.write(json, response);
	}
	
	/**
	 * 
	 * 功能说明：更新一个记录
	 * @author 刘兴 
	 * @Date 2014年10月26日 下午10:02:23
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> param = this.getBody(request.getReader());

        String userCode = defaultStr(param.get("userCode"), "");
		String status = defaultStr(param.get("status"), "");
		String userName = defaultStr(param.get("userName"), "");
		String orgName = defaultStr(param.get("orgName"), "");
		String position = defaultStr(param.get("position"), "");
		String createdAt = defaultStr(param.get("createdAt"), "");

		Map<String, Object> map = new HashMap<>();
		
		if(mongoService.update(userCode, userName, orgName, position, createdAt, status)){
			map.put("status", "ok");
		} else {
			map.put("status", "fail");
		}
		
		String json = JSON.toJSONString(map);
		this.write(json, response);
	}

    /**
	 * 
	 * 功能说明：初始化列表数据
	 * @author 刘兴 
	 * @Date 2014年10月26日 下午11:00:59
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void initDate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		Map<String, Object> map = new HashMap<>();
		
		if(mongoService.initDate()){
			map.put("status", "ok");
		} else {
			map.put("status", "fail");
		}
		
		String json = JSON.toJSONString(map);
		this.write(json, response);
	}
	
	/**
	 * 
	 * 功能说明：新增一个记录
	 * @author 刘兴 
	 * @Date 2014年10月26日 下午11:00:59
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String status = defaultWebStr(request, "status", "");
		String userName = defaultWebStr(request, "userName", "");
		String orgName = defaultWebStr(request, "orgName", "");
		String position = defaultWebStr(request, "position", "");
		String createdAt = defaultWebStr(request, "createdAt", "");
		
		Map<String, Object> map = new HashMap<>();
		
		if(mongoService.insert(userName, orgName, position, createdAt, status)){
			map.put("status", "ok");
		} else {
			map.put("status", "fail");
		}
		
		String json = JSON.toJSONString(map);
		this.write(json, response);
	}
	
	private void write(String json, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		response.getOutputStream().write(json.toString().getBytes("UTF-8"));  
		response.setContentType("text/json; charset=UTF-8");  
	}

    /**
     * 从put请求的数据中提取参数
     * @param br
     * @return
     */
    private Map<String, Object> getBody(BufferedReader br) {
        Map<String, Object> paramsMap = new HashMap<>();

        String inputLine;
        String params = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                params += inputLine;
            }
            br.close();
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
        }

        if(Strings.isEmpty(params)){
            return paramsMap;
        }

        try {
            params = URLDecoder.decode(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage(), e);
        }

        String[] paramsArr = params.split("&");
        for (String param : paramsArr) {
            if (Strings.isEmpty(param)) {
                continue;
            }

            String[] paramArr = param.split("=");
            if (paramArr.length == 1){
                paramsMap.put(paramArr[0], null);
                continue;
            }

            paramsMap.put(paramArr[0], paramArr[1]);
        }

        return paramsMap;
    }
	
	private String defaultWebStr(HttpServletRequest request, String name, String defaultStr) {
		String obj = request.getParameter(name);
		
		if (obj == null) {
			return defaultStr;
		}
		
		if ("".equals(obj)) {
			return defaultStr;
		}
		
		return obj.toString();
	}

    private String defaultStr(Object value, String defaultStr) {
        if (value == null) {
            return defaultStr;
        }

        if ("".equals(value)) {
            return defaultStr;
        }

        return value.toString();
    }
 
}