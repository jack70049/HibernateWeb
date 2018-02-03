<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="../css/table.css" />
<title>Display</title>
</head>
<body>

<h3>Select Product Table Result : XXX row(s) selected</h3>

<c:if test="${not empty select}">
<table>
	<thead>
	<tr>
		<th>ID</th>
		<th>Name</th>
		<th>Price</th>
		<th>Make</th>
		<th>Expire</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach var="row" items="${select}">
		<c:url value="/pages/product.jsp" var="path">
			<c:param name="id" value="${row.id}" />
			<c:param name="name" value="${row.name}" />
			<c:param name="price" value="${row.price}" />
			<c:param name="make" value="${row.make}" />
			<c:param name="expire" value="${row.expire}" />
		</c:url>
	<tr>
		<td>${row.id}</td>
		<td><a href="${path}">${row.name}</a></td>
		<td>${row.price}</td>
		<td>${row.make}</td>
		<td>${row.expire}</td>
	</tr>
	</c:forEach>
	</tbody>
</table>
</c:if>

<h3><a href="<c:url value="/pages/product.jsp" />">Product Table</a></h3>

</body>
</html>