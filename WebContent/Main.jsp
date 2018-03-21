<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="mainJquery.js"></script>
	<style>
		.imageupload.imageupload-disabled {
			cursor: not-allowed;
			opacity: 0.60;
		}
		
		.imageupload.imageupload-disabled>* {
			pointer-events: none;
		}
		
		.imageupload .panel-title {
			margin-right: 15px;
			padding-top: 8px;
		}
		
		.imageupload .alert {
			margin-bottom: 10px;
		}
		
		.imageupload .btn-file {
			overflow: hidden;
			position: relative;
		}
		
		.imageupload .btn-file input[type="file"] {
			cursor: inherit;
			display: block;
			font-size: 100px;
			min-height: 100%;
			min-width: 100%;
			opacity: 0;
			position: absolute;
			right: 0;
			text-align: right;
			top: 0;
		}
		
		.imageupload .file-tab button {
			display: none;
			margin-left: 20px;
		}
		
		.imageupload .file-tab .thumbnail {
			margin-bottom: 10px;
		}
		
		.uploader {
			position: relative;
			overflow: hidden;
			width: 100%;
			height: 100%;
			display: none;
			margin-bottom: 25px;
		}
		.row .user-row{
			margin-top: 15px;
		}
	</style>
</head>
<body>
	<%	
		Cookie[] cookies = request.getCookies();
		String userName = null;
		if(!com.ipg.servlet.Utility.checkCookieMain(cookies)){ 
			response.sendRedirect("LoginUserName.jsp");
		} else {
			for(Cookie cookie : cookies){
				if(cookie.getName().equals("user")) userName = cookie.getValue();
			}
		}
	%>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">iPG</a>
			</div>
			<ul class="nav navbar-nav pull-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" id="dropdownMenu">Welcome
					<%
						if (userName != null) {
							out.println("<font size=3px font family:times serif>" + userName + "</font>");
						}
					%>
					<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/user/preferences"><i class="glyphicon glyphicon-user"></i>User Information</a></li>
						<li><a href="/help/support"><i class="glyphicon glyphicon-envelope"></i> Contact Support</a></li>
						<li class="divider"></li>
						<li><form method="GET" action="ServletLogout" style="margin-left:17px;"><a href=""><button type="submit"><i class="glyphicon glyphicon-off"></i>Logout</button></a></form></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-6">
				<div class="imageupload panel panel-default">
					<div class="panel-heading clearfix">
						<h3 class="panel-title pull-left">Upload Image</h3>
					</div>
					<div class="file-tab panel-body">
						<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
							<div class="uploader">
								<img src="" class="img-responsive"/>
							</div>
							<label class="btn btn-primary btn-file" onclick="$('#filePhoto').click()"><input type="file" name="userprofile_picture" id="filePhoto" /><span>Browse</span></label>
							<button type="button" class="btn btn-success" id="submitImage">Submit</button>
							<button type="button" class="btn btn-danger" id="removeImage">Remove</button>
						</form>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="imageupload panel panel-default">
					<div class="panel-heading clearfix">
						<h3 class="panel-title pull-left">Result</h3>
					</div>
					<div class="file-tab panel-body">
						<div class="container">
 							<div class="well col-xs-8 col-sm-8 col-md-8 col-lg-8">
        							<div class="row user-row">
            							<div class="col-xs-3 col-sm-2 col-md-1 col-lg-1">
                							<img class="img-circle" src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=50" alt="User Pic">
            							</div>
						            <div class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
						                <strong>Celebrity</strong><br>
						                <span>Recognize a famous person</span>
						            </div>
						            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 dropdown-user" data-for=".Celebrity">
						                <i class="glyphicon glyphicon-chevron-down text-muted"></i>
						            </div>
        							</div>
						        <div class="row user-infos Celebrity">
						            <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10 col-xs-offset-0 col-sm-offset-0 col-md-offset-1 col-lg-offset-1">
						                <div class="panel panel-primary">
						                    <div class="panel-heading">
						                        <h3 class="panel-title">Celebrity</h3>
						                    </div>
						                    <div class="panel-body">
						                        <div class="row">
						                            <div class=" col-md-9 col-lg-9 hidden-xs hidden-sm">
						                                <strong>Result from JSON</strong><br>
						                                <table class="table table-user-information">
						                                    <tbody>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    </tbody>
						                                </table>
						                            </div>
						                        </div>
						                    </div>
						                </div>
						            </div>
						        </div>
						        <div class="row user-row">
            							<div class="col-xs-3 col-sm-2 col-md-1 col-lg-1">
                							<img class="img-circle" src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=50" alt="User Pic">
            							</div>
						            <div class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
						                <strong>Face</strong><br>
						                <span>Recognize a famous person</span>
						            </div>
						            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 dropdown-user" data-for=".Face">
						                <i class="glyphicon glyphicon-chevron-down text-muted"></i>
						            </div>
        							</div>
						        <div class="row user-infos Face">
						            <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10 col-xs-offset-0 col-sm-offset-0 col-md-offset-1 col-lg-offset-1">
						                <div class="panel panel-primary">
						                    <div class="panel-heading">
						                        <h3 class="panel-title">Celebrity</h3>
						                    </div>
						                    <div class="panel-body">
						                        <div class="row">
						                            <div class=" col-md-9 col-lg-9 hidden-xs hidden-sm">
						                                <strong>Result from JSON</strong><br>
						                                <table class="table table-user-information">
						                                    <tbody>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    </tbody>
						                                </table>
						                            </div>
						                        </div>
						                    </div>
						                </div>
						            </div>
						        </div>
						        <div class="row user-row">
            							<div class="col-xs-3 col-sm-2 col-md-1 col-lg-1">
                							<img class="img-circle" src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=50" alt="User Pic">
            							</div>
						            <div class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
						                <strong>Vehicle</strong><br>
						                <span>Recognize a famous person</span>
						            </div>
						            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 dropdown-user" data-for=".Vehicle">
						                <i class="glyphicon glyphicon-chevron-down text-muted"></i>
						            </div>
        							</div>
						        <div class="row user-infos Vehicle">
						            <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10 col-xs-offset-0 col-sm-offset-0 col-md-offset-1 col-lg-offset-1">
						                <div class="panel panel-primary">
						                    <div class="panel-heading">
						                        <h3 class="panel-title">Celebrity</h3>
						                    </div>
						                    <div class="panel-body">
						                        <div class="row">
						                            <div class=" col-md-9 col-lg-9 hidden-xs hidden-sm">
						                                <strong>Result from JSON</strong><br>
						                                <table class="table table-user-information">
						                                    <tbody>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    </tbody>
						                                </table>
						                            </div>
						                        </div>
						                    </div>
						                </div>
						            </div>
						        </div>
						        <div class="row user-row">
            							<div class="col-xs-3 col-sm-2 col-md-1 col-lg-1">
                							<img class="img-circle" src="https://lh5.googleusercontent.com/-b0-k99FZlyE/AAAAAAAAAAI/AAAAAAAAAAA/eu7opA4byxI/photo.jpg?sz=50" alt="User Pic">
            							</div>
						            <div class="col-xs-8 col-sm-9 col-md-10 col-lg-10">
						                <strong>Text</strong><br>
						                <span>Recognize a famous person</span>
						            </div>
						            <div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 dropdown-user" data-for=".Text">
						                <i class="glyphicon glyphicon-chevron-down text-muted"></i>
						            </div>
        							</div>
						        <div class="row user-infos Text">
						            <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10 col-xs-offset-0 col-sm-offset-0 col-md-offset-1 col-lg-offset-1">
						                <div class="panel panel-primary">
						                    <div class="panel-heading">
						                        <h3 class="panel-title">Celebrity</h3>
						                    </div>
						                    <div class="panel-body">
						                        <div class="row">
						                            <div class=" col-md-9 col-lg-9 hidden-xs hidden-sm">
						                                <strong>Result from JSON</strong><br>
						                                <table class="table table-user-information">
						                                    <tbody>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    <tr>
						                                        <td>AAAAAA:</td>
						                                        <td>BBBBBB</td>
						                                    </tr>
						                                    </tbody>
						                                </table>
						                            </div>
						                        </div>
						                    </div>
						                </div>
						            </div>
						        </div>
    							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		//upload image
		var imageLoader = document.getElementById('filePhoto');
		imageLoader.addEventListener('change', handleImage, false);
		function handleImage(e) {
			var reader = new FileReader();
			reader.onload = function(event) {
				$('.uploader img').attr('src', event.target.result);
			}
			reader.readAsDataURL(e.target.files[0]);
			$('.uploader').show();
			$('.imageupload .file-tab button').show();
		}
	</script>
</body>
</html>