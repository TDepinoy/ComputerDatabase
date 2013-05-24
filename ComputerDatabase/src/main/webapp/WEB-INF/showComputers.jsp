<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Computers database</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/main.css" />
</head>
<body>

	<header class="topbar">
		<h1 class="fill">
			<a href="showComputers"> Computer Database application </a>
		</h1>
	</header>

	<section id="main">

		<h1>${page.totalResults eq 0 ? 'No' : page.totalResults} computers found</h1>

		<div id="actions">
			<form action="showComputers" method="GET">

				<input type="search" id="searchbox" name="filter"
					value="${empty filter ? '' : filter}"
					placeholder="Filter by computer name..."> 
				<input type="submit" id="searchsubmit" value="Filter by name" class="btn primary">

			</form>

			<a class="btn success" id="add" href="addComputer">Add a new computer</a>
		</div>

		<c:choose>
		
			<c:when test="${page.totalResults eq 0}">
				<div class="well">
					<em>Nothing to display</em>
				</div>
			</c:when>

			<c:otherwise>
				<table class="computers zebra-striped">
					<thead>
						<tr>
							<th
								class="col2 header ${s eq '1' ? 'headerSortUp' :  s eq '-1' ? 'headerSortDown' : empty s ? 'headerSortUp' : ''}">
								<a href="showComputers?filter=${filter}&s=${s eq '1' ? '-1' : '1'}">Computer name</a></th>
							<th
								class="col3 header ${s eq '2' ? 'headerSortUp' :  s eq '-2' ? 'headerSortDown' : ''}">
								<a href="showComputers?filter=${filter}&s=${s eq '2' ? '-2' : '2'}">Introduced</a></th>
							<th
								class="col4 header ${s eq '3' ? 'headerSortUp' :  s eq '-3' ? 'headerSortDown' : ''}">
								<a href="showComputers?filter=${filter}&s=${s eq '3' ? '-3' : '3'}">Discontinued</a></th>
							<th
								class="col5 header ${s eq '4' ? 'headerSortUp' :  s eq '-4' ? 'headerSortDown' : ''} ">
								<a href="showComputers?filter=${filter}&s=${s eq '4' ? '-4' : '4'}">Company</a></th>

						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${page.computers}">
							<tr>
								<td><a href="updateComputer?idComputer=${c.id}">${c.name}</a></td>
								<td>${c.introduced}</td>
								<td>${c.discontinued}</td>
								<td>${c.company.name}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

				<div id="pagination" class="pagination">
					<ul>
						<c:choose>
							<c:when test="${not page.firstPage}">
								<li class="prev"><a
									href="showComputers?p=${page.numPage - 1}&filter=${filter}&s=${s}">&larr; Previous</a></li>
							</c:when>
							<c:otherwise>
								<li class="prev disabled"><a>&larr; Previous</a></li>
							</c:otherwise>
						</c:choose>

						<li class="current"><a>Displaying ${page.displayFrom} to
								${page.displayTo} of ${page.totalResults}</a></li>

						<c:choose>
							<c:when test="${not page.lastPage}">
								<li class="next"><a
									href="showComputers?p=${page.numPage + 1}&filter=${filter}&s=${s}">Next
										&rarr;</a></li>
							</c:when>
							<c:otherwise>
								<li class="next disabled"><a>Next &rarr;</a></li>
							</c:otherwise>
						</c:choose>

					</ul>
				</div>
			</c:otherwise>
			
		</c:choose>
		
	</section>
</body>
</html>


