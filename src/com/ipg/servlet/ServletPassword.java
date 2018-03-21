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

/**
 * Servlet implementation class ServletPassword
 */
@WebServlet("/ServletPassword")
public class ServletPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String password = request.getParameter("Password");	
		String username = null;
		Cookie[] cookies = request.getCookies();
		if(cookies !=null){
	    		for(Cookie cookie : cookies){
	    			if(cookie.getName().equals("user")) username = cookie.getValue();
	    		}
		}
		
		//Connect to database and check if Username and Password are exist
		try {;
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/iDB", "root", "rs059822440"); 
			PreparedStatement ps = c.prepareStatement("SELECT Username,Password FROM Accounts WHERE Username=? AND Password=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Cookie loginCookie = new Cookie("main",password);
				loginCookie.setMaxAge(30*60);
				response.addCookie(loginCookie);
				response.sendRedirect("Main.jsp");
			} else {
				request.setAttribute("errorPassword","Invalid Password");
				RequestDispatcher rd=request.getRequestDispatcher("/LoginPassword.jsp");            
				rd.include(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
