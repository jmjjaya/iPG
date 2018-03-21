package com.ipg.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletLogout
 */
@WebServlet("/ServletLogout")
public class ServletLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
	    	Cookie loginCookie = null;
	    	Cookie mainCookie = null;
	    	Cookie[] cookies = request.getCookies();
	    	if(cookies != null){
		    	for(Cookie cookie : cookies){
		    		if(cookie.getName().equals("user")){
		    			loginCookie = cookie;
		    		} else if (cookie.getName().equals("main")) {
		    			mainCookie = cookie;
		    		}
		    	}
	    	}
	    	loginCookie.setMaxAge(0);
	    	mainCookie.setMaxAge(0);
	    response.addCookie(loginCookie);
	    response.addCookie(mainCookie);
	    	response.sendRedirect("LoginUserName.jsp");
	}
}
