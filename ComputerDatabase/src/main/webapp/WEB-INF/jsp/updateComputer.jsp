<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Computers database</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="css/main.css">
</head>
<body>

	<header class="topbar">
		<h1 class="fill">
			<a href="/"> Computer Database application </a>
		</h1>
	</header>

	<section id="main">

		<h1>Edit computer</h1>

		<form action="validationServlet.html" method="POST">

			<fieldset>

				<input type="hidden" value="${computer.id}" name="idComputer" />

				<div class="${className}">

					<label for="name">Computer name</label>
					<div class="input">
						<input type="text" id="name" name="name" value="${computer.name}">
						<span class="help-inline">Required</span>
					</div>
				</div>

				<div class="${classIntroduced}">
					<label for="introduced">Introduced date</label>
					<div class="input">
						<input type="text" id="introduced" name="introduced"
							value="<fmt:formatDate value="${computer.introduced}" pattern="yyyy-MM-dd" />">
						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>

				<div class="${classDiscontinued}">
					<label for="discontinued">Discontinued date</label>
					<div class="input">
						<input type="text" id="discontinued" name="discontinued"
							value="<fmt:formatDate value="${computer.discontinued}" pattern="yyyy-MM-dd" />">
						<span class="help-inline">Date (&#x27;yyyy-MM-dd&#x27;)</span>
					</div>
				</div>

				<div class="${classCompany}">
					<label for="company">Company</label>
					<div class="input">
						<select id="company" name="company">

							<option class="blank" value="">-- Choose a company --</option>

							<c:forEach var="c" items="${companies}">
								<c:choose>
									<c:when test="${computer.company.id ne c.id}">
										<option value="${c.id}">${c.name}</option>
									</c:when>
									<c:when test="${computer.company.id eq c.id}">
										<option selected="selected" value="${c.id}">${c.name}</option>
									</c:when>
								</c:choose>
							</c:forEach>

						</select> <span class="help-inline"></span>
					</div>
				</div>
			</fieldset>

			<div class="actions">
				<input type="submit" value="Save this computer" class="btn primary" />
				or <a href="showComputers.html" class="btn">Cancel</a>
			</div>
		</form>

		<form action="deleteComputer.html" method="POST" class="topRight">

			<input type="hidden" value="${computer.id}" name="idComputer" /> 
			<input type="submit" value="Delete this computer" class="btn danger" />

		</form>
	</section>
</body>
</html>