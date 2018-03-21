<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ipg.servlet.Utility" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sign in</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="Login.css" type="text/css" rel="stylesheet" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="app-ajax.js"></script>
</head>
<body>
	<%
		Cookie[] cookies = request.getCookies();
		if(com.ipg.servlet.Utility.checkCookieMain(cookies)){ 
			response.sendRedirect("Main.jsp");
		}
	%>
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-md-4 col-md-offset-4">
				<div class="account-wall">
					<img class="profile-img" src="logo.jpg" alt="">
					<form class="form-signin" method="POST" action="ServletUsername">
						<div id="ajaxGetUserServletResponse">
							<%
								String login_msg = (String) request.getAttribute("errorUsername");
								if (login_msg != null) {
									out.println("<font color=red size=3px font family:times serif>" + login_msg + "</font>");
								}
							%>
						</div>
						<input type="text" class="form-control" placeholder="Username"
							name="Username" required autofocus>
						<button class="btn btn-lg btn-primary btn-block" type="submit">Next</button>
					</form>
				</div>
				<a href="Registration.jsp" class="text-center new-account">Create
					an account</a>
			</div>
		</div>
	</div>
</body>
</html>