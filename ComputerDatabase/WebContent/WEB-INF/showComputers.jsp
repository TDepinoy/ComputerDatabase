<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Computers database</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/main.css" />
</head>
<body>

	<header class="topbar">
		<h1 class="fill">
			<a href="/"> Computer Database application </a>
		</h1>
	</header>

	<section id="main">


		<h1>${totalComputers} computers found</h1>



		<div id="actions">
			<form action="/computers" method="GET">

				<input type="search" id="searchbox" name="f" value=""
					placeholder="Filter by computer name..."> <input
					type="submit" id="searchsubmit" value="Filter by name"
					class="btn primary">

			</form>

			<a class="btn success" id="add" href="/computers/new">Add a new	computer</a>
		</div>



		<table class="computers zebra-striped">
			<thead>
				<tr>

					<th class="col2 header headerSortUp"><a href="/computers?s=-2">Computer name</a></th>
					<th class="col3 header "><a href="/computers?s=3">Introduced</a></th>
					<th class="col4 header "><a href="/computers?s=4">Discontinued</a></th>
					<th class="col5 header "><a href="/computers?s=5">Company</a></th>

				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${computers}">
					<tr>
						<td><a href="UpdateComputerServlet/?idComputer=${c.id}">${c.name}</a></td>
						<td><em>${c.introduced}</em></td>
						<td><em>${c.discontinued}</em></td>
						<td><em>${c.company.name}</em></td>
					</tr>
				</c:forEach>
			</tbody>

		</table>

		<div id="pagination" class="pagination">
			<ul>
				<c:choose>
					<c:when test="${from == 1}">
						<li class="prev disabled"><a>&larr; Previous</a></li>
					</c:when>
					<c:when test="${from != 1}">
						<li class="prev"><a href="showComputers?currentIndex=${currentIndex - 1}">&larr; Previous</a></li>
					</c:when>
				</c:choose>
				
				<li class="current"><a>Displaying ${from} to ${to} of ${totalComputers}</a></li>
				
				<c:choose>
					<c:when test="${to != totalComputers}">
						<li class="next"><a href="showComputers?currentIndex=${currentIndex + 1}">Next &rarr;</a></li>
					</c:when>
					<c:when test="${to == totalComputers}">
						<li class="next disabled"><a>Next &rarr;</a></li>
					</c:when>
				</c:choose>
				
			</ul>
		</div>
	</section>

</body>
</html>


