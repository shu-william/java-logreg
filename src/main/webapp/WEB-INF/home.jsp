<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/css/main.css"/>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<title>Home</title>
</head>
<body>
	<div class="text-center col-md-6 mx-auto my-3">
		<h1>Hello, <c:out value="${user.userName}"></c:out></h1>
		<a href="/logout" class="my-4 btn btn-info">Logout</a>	
	</div>
</body>
</html>