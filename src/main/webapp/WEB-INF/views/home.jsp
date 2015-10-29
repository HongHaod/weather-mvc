<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<html>
<head>
<link href="<c:url value="/resources/style.css" />" rel="stylesheet" type="text/css" />
<title>Home</title>
</head>
<body>
	<h1>Current Weather</h1>

	<c:if test="${not empty errorMessage}">
		<div class="errorMessages">${errorMessage}</div>
	</c:if>

	<div>
		Select City:
		<form:form method="post" modelAttribute="searchForm">
			<form:select id="locationCode" path="locationCode"
				onchange="submit()">
				<form:options items="${searchForm.cityLocationCodetoNameTable}" />
			</form:select>
		</form:form>
	</div>

	<table border="1">
		<tr>
			<td>City</td>
			<td>${weather.city}</td>
		</tr>
		<tr>
			<td>Updated time</td>
			<td>${serverTime}</td>
		</tr>
		<tr>
			<td>Weather</td>
			<td>${weather.condition}</td>
		</tr>
		<tr>
			<td>Temperature</td>
			<td>${weather.temperature}&deg;C</td>
		</tr>
		<tr>
			<td>Wind</td>
			<td>${weather.windSpeed}km/h</td>
		</tr>
	</table>
</body>
</html>
