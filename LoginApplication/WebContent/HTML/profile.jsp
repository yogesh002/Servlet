<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="ISO-8859-1">
<title>My personal webpage</title>
<link href='https://fonts.googleapis.com/css?family=Slabo+27px'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="./CSS/profile.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body class="body">
	<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Welcome Mr.${userName}</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="active"><a href="#">Home</a></li>
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#">About<span class="caret"></span></a>
				<ul class="dropdown-menu list-group" role="menu">
					<li class="list-group-item dropdown_list"><a href="#"
						class="nav_link_text">Education</a></li>
					<li class="list-group-item dropdown_list"><a href="#"
						class="nav_link_text" class="nav_link_text">Work Experience</a></li>
					<li class="list-group-item dropdown_list"><a href="#"
						class="nav_link_text">Hobbies</a></li>
				</ul></li>
			<li><a href="#">Videos</a></li>
			<li><a href="/LoginApplication/logout">Logout</a></li>
		</ul>
	</div>
	</nav>
	<section class="row section container"> <article
		class="col-md-4 profile_image"> <figure> <img
		alt="ABOUT_PIC" src="./Image/profile.jpg"
		class="img-responsive img-squared"> </figure>
	<div>
		<ul class="pager">
			<li class="previous"><a href="#">Previous</a></li>
			<li class="next"><a href="#">Next</a></li>
		</ul>
	</div>
	</article> <article class="about_font col-md-4"> <header
		class="article_header">
	<h3>About</h3>
	Lorem Ipsum has been the industry's standard dummy text ever since the
	1500s, when an unknown printer took a galley of type and scrambled it
	to make a type specimen book. It has survived not only five centuries,
	but also the leap into electronic typesetting, remaining essentially
	unchanged. It wasthe including versions of Lorem Ipsum.Lorem Ipsum has
	been the industry's standard dummy text ever since the 1500s, when an
	unknown printer took a galley of type and scrambled it to make a type
	specimen book. It has survived not only five centuries, but also the
	leap into electronic typesetting, remaining essentially unchanged. It
	wasthe including versions of Lorem Ipsum. <a href="#" class="continue"><i>Read
			more...</i></a></article> <article class="about_font col-md-4"> <header
		class="article_header">
	<h3>About</h3>
	</header> Lorem Ipsum has been the industry's standard dummy text ever since the
	1500s, when an unknown printer took a galley of type and scrambled it
	to make a type specimen book. It has survived not only five centuries,
	but also the leap into electronic typesetting, remaining essentially
	unchanged. It wasthe including versions of Lorem Ipsum.Lorem Ipsum has
	been the industry's standard dummy text ever since the 1500s, when an
	unknown printer took a galley of type and scrambled it to make a type
	specimen book. It has survived not only five centuries, but also the
	leap into electronic typesetting, remaining essentially unchanged. It
	wasthe including versions of Lorem Ipsum. <a href="#" class="continue"><i>Read
			more...</i></a> </article> </section>
	<footer class="container footer">
	<div class="row">
		<div class="col-md-4">
			<label class="social_media">Connect with me:</label>
			<figure> <a target="_blank" href="http://www.facebook.com"><img
				alt="Media:"
				src="//login.create.net/images/icons/user/facebook_30x30.png"
				border=0></a> <a target="_blank"
				href="http://yogesh002.github.io/Servlet/"><img
				alt="Github Logo" src="./Image/git.png" border=0 width="8%"></a> <a
				href="https://www.coursera.org/user/i/a97e74c76b106943f4e820d67ab1c974"
				target="_blank"> <img alt="Coursera Logo"
				src="https://upload.wikimedia.org/wikipedia/commons/e/e5/Coursera_logo.PNG"
				border=0 width="35%">
			</a> <a href="https://www.freecodecamp.com/yogesh002" target="_blank"><img
				alt="Free Code Camp Logo" src="./Image/freecodecamp.png" border=0
				width="15%"></a> </figure>
		</div>
		<address class="col-md-4">
			<dl>
				<dt class="contact_header">General Contact:</dt>
				<dd>1101 S Brookside Ave,</dd>
				<dd>Independence, MO 64053</dd>
				<dt class="telephone_header">Telephone:</dt>
				<dd>704-401-9724</dd>
				<dt class="email_header">Email:</dt>
				<dd>ygh002@gmail.com</dd>
				<dd>yugeshghimire@hotmail.com</dd>
			</dl>
		</address>
		<address class="col-md-4">
			<label class="mail_me">Mail me:</label>
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label class="control-label col-md-2" for="name">Name:</label>
					<div class="col-md-10">
						<input type="text" class="form-control" id="name"
							placeholder="Enter Name">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-2" for="email">Email:</label>
					<div class="col-md-10">
						<input type="email" class="form-control" id="email"
							placeholder="Enter email">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-2" for="message">Message:</label>
					<div class="col-md-10">
						<textarea rows="5" id="message" class="form-control"></textarea>
					</div>
				</div>
				<button type="submit" class="btn btn-primary">Submit</button>
			</form>
		</address>
	</div>
	</footer>
	<div class="container-fluid copyright_msg_container">
		<p class="copyright_msg">&copy; 2016. All rights reserved.</p>
	</div>
</body>
</html>