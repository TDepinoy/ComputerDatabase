<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<title>Computers database</title>
	<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" media="screen" href="css/main.css">
</head>
<body>

	<header class="topbar">
		<h1 class="fill">
			<a href="/"> Computer Database application </a>
		</h1>
	</header>

	<section id="main">

		<h1>Add computer</h1>

		<form action="validationServlet" method="POST">

			<fieldset>
								
				<div class="${className}">

					<label for="name">Computer name</label>
					<div class="input">
						<input type="text" id="name" name="name" > 
						<span class="help-inline">Required</span>
					</div>
				</div>
				
				<div class="${classIntroduced}">
					<label for="introduced">Introduced date</label>
					<div class="input">
						<input type="text" id="introduced" name="introduced" >
						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>

				<div class="${classDiscontinued}">
					<label for="discontinued">Discontinued date</label>
					<div class="input">
						<input type="text" id="discontinued" name="discontinued" >
						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>

				<div class="${classCompany}">
					<label for="company">Company</label>
					<div class="input">
						<select id="company" name="company">

							<c:forEach var="c" items="${companies}">
								<option value="${c.id}" >${c.name}</option>		
							</c:forEach>

						</select> 
						<span class="help-inline" ></span>
					</div>
				</div>
			</fieldset>

			<div class="actions">
				<input type="submit" value="Save this computer" class="btn primary" />
				or <a href="showComputers" class="btn">Cancel</a>
			</div>
		</form>
	</section>
</body>
</html>