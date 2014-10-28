/**   
 * @Title: MongoDBBasic.java
 * @Package servlet
 * @Description: TODO
 * @author 刘兴 
 * @date 2014年10月26日 下午4:15:23
 * @version V1.0   
 */
package com.dooioo.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.dooioo.db.MongoService;

/**
 * 类功能说明：
 * Title: MongoDBBasic.java
 * @author 刘兴
 * @date 2014年10月26日 下午4:15:23
 * @version V1.0
 */
public class DbServlet extends HttpServlet { 
	 
	private static final long serialVersionUID = 3553230795361397498L;
	
	private MongoService mongoService = new MongoService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String requestUrl = request.getRequestURI();
		
		if(requestUrl.endsWith("query")) {
			this.query(request, response);
		}
		
		if(requestUrl.endsWith("findOne")) {
			this.findOne(request, response);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		 
		String requestUrl = request.getRequestURI();
		
		if(requestUrl.endsWith("delete")) {
			this.delete(request, response);
		}
		
		if(requestUrl.endsWith("update")) {
			this.update(request, response);
		}
		
		if(requestUrl.endsWith("add")) {
			this.add(request, response);
		}
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
		String keyword = defaultStr(request, "keyword", "");
		String status = defaultStr(request, "status", "");
		String dateFrom = defaultStr(request, "dateFrom", "");
		String dateTo = defaultStr(request, "dateTo", "");
		String pageNo = defaultStr(request, "pageNo", "1");
		
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
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void findOne(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String userCode = defaultStr(request, "userCode", "");
		
		Map<String, Object> map = mongoService.findOne(userCode);
		String json = JSON.toJSONString(map);
		this.write(json, response);
	}
	
	/**
	 * 
	 * 功能说明：删除一个记录
	 * @author 刘兴 
	 * @Date 2014年10月26日 下午9:30:18
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String userCode = defaultStr(request, "userCode", "");
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
	private void update(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String userCode = defaultStr(request, "userCode", "");
		String status = defaultStr(request, "status", "");
		String userName = defaultStr(request, "userName", "");
		String orgName = defaultStr(request, "orgName", "");
		String position = defaultStr(request, "position", "");
		String createdAt = defaultStr(request, "createdAt", "");
		
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
	 * 功能说明：新增一个记录
	 * @author 刘兴 
	 * @Date 2014年10月26日 下午11:00:59
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void add(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		String status = defaultStr(request, "status", "");
		String userName = defaultStr(request, "userName", "");
		String orgName = defaultStr(request, "orgName", "");
		String position = defaultStr(request, "position", "");
		String createdAt = defaultStr(request, "createdAt", "");
		
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
	
	private String defaultStr(HttpServletRequest request, String name, String defaultStr) {
		String obj = request.getParameter(name);
		
		if (obj == null) {
			return defaultStr;
		}
		
		if ("".equals(obj)) {
			return defaultStr;
		}
		
		return obj.toString();
	}
 
}