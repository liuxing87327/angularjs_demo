/**   
 * @Title: DemoServlet.java
 * @Package servlet
 * @Description: TODO
 * @author 刘兴 
 * @date 2014年10月26日 下午1:58:52
 * @version V1.0   
 */
package com.dooioo.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类功能说明：
 * Title: DemoServlet.java
 * @author 刘兴
 * @date 2014年10月26日 下午1:58:52
 * @version V1.0
 */
public class DemoServlet extends HttpServlet {
	
	private static final long serialVersionUID = 6093570236560735330L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/biyesheji.jsp");
		dispatcher.forward(req, response);
	}

}
