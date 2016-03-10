<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: usr
  Date: 09.03.2016
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<html>
<head>
    <title>Title</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<h3>Meal List</h3>

<table border="3" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
</table>
<c:forEach var="mealList" items="${mealList}">
    <jsp:useBean id="mealList" scope="page" type="ru.javawebinar.topjava.model.UserMealWithExceed"/>
    <tr class="${mealList.exceed ? 'exceeded':'normal'}">
        <td>
                ${mealList.dateTime.toLocalDate()} ${mealList.dateTime.toLocalTime()}
        </td>
    </tr>
    <td>
            ${mealList.description}
    </td>
    <td>
            ${mealList.calories}
    </td>
</c:forEach>
</body>
</html>
