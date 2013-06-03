<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
	<title>Error</title>
		<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="css/main.css">
	</head>
<body>
<header class="topbar">
		<h1 class="fill">
			<a href="showComputers.html"> Computer Database application </a>
		</h1>
	</header>

	<section id="main">

		<h1>An error has occurred</h1>
		
		<c:if test="${not empty exception}">
			<div class="alert-message warning">
		        <strong>Error: </strong>${exception.message}
	        </div>
		</c:if>
	</section>
</body>
</html>