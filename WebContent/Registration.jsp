<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sign in</title>
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js"></script>

</head>
<body style="background-color: #525252;">
	<%
		Cookie[] cookies = request.getCookies();
		if(com.ipg.servlet.Utility.checkCookieMain(cookies)){ 
			response.sendRedirect("Main.jsp");
		}
	%>
	<div class="container-fluid">
		<div class="row centered-form">
			<div class="col-md-6 col-sm-offset-2 col-md-offset-2" style="float: none; margin: 0 auto;">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Please sign up for using our service</h3>
					</div>
					<div class="panel-body">
						<form data-toggle="validator" role="form"
							action="ServletRegistration" method="POST">
							<div class="form-group">
								<label for="inputName" class="control-label">Username</label> <input
									type="text" class="form-control" id="Username" name="Username"
									required>
								<div id="ajaxGetUserServletResponse">
									<%
										String login_msg = (String) request.getAttribute("existUsername");
										if (login_msg != null) {
											out.println("<font color=red size=3px font family:times serif>" + login_msg + "</font>");
										}
									%>
								</div>
							</div>
							<div class="form-group">
								<label for="inputEmail" class="control-label">Email</label> <input
									type="email" class="form-control" id="Email"
									placeholder="Email" data-error="The email address is invalid"
									name="Email" required>
								<div class="help-block with-errors"></div>
							</div>
							<div class="form-group">
								<label for="inputPassword" class="control-label">Password</label>
								<div class="form-inline row">
									<div class="form-group col-sm-6">
										<input type="password" data-minlength="6" class="form-control"
											id="Password" placeholder="Password" name="Password" required>
										<div class="help-block">Minimum of 6 characters</div>
									</div>
									<div class="form-group col-sm-6">
										<input type="password" class="form-control"
											id="inputPasswordConfirm" data-match="#Password"
											data-match-error="These don't match" placeholder="Confirm"
											required>
										<div class="help-block with-errors"></div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-primary">Submit</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>