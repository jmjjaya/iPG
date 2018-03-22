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
 * Servlet implementation class ServletRegistration
 */
@WebServlet("/ServletRegistration")
public class ServletRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		String email = request.getParameter("Email");

		// Connect to database and check if Username and Password are exist
		try {
			;
			Class.forName("com.mysql.jdbc.Driver");
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ipgdb", "root", "admin");

			PreparedStatement statement = c.prepareStatement("select Username from Accounts where Username=?");
			statement.setString(1, username);
			ResultSet check = statement.executeQuery();
			if (!check.next()) {
				PreparedStatement ps = c.prepareStatement("INSERT INTO Accounts(Username,Password,Email) values(?,?,?)");
				ps.setString(1, username);
				ps.setString(2, password);
				ps.setString(3, email);
				int rs = ps.executeUpdate();
				if (rs == 1) {
					Cookie loginCookie = new Cookie("user",username);
					loginCookie.setMaxAge(30*60);
					response.addCookie(loginCookie);
					response.sendRedirect("Main.jsp");
				}
			} else {
				request.setAttribute("existUsername", "Username is exist");
				RequestDispatcher rd = request.getRequestDispatcher("/Registration.jsp");
				rd.include(request, response);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
