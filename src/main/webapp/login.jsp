<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<form action="login" method="post">
    Email: <input type="email" name="email" required><br>
    Password: <input type="password" name="password" required><br>

    <button type="submit">Login</button>
</form>

<% if(request.getParameter("error") != null) { %>
    <p style="color:red;">Invalid credentials</p>
<% } %>
</body>
</html>