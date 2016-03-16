<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h3>User list</h3>
<form method="post" action="users">
    Choose user: <select name="userID">
    <option value="1" selected>User</option>
    <option value="2">Admin</option>
</select>
    <button type="submit">Choose</button>
</form>
</body>
</html>
