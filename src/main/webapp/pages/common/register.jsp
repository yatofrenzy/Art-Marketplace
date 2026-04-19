<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>

<h2>Register</h2>

<p style="color:red;">
    <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
</p>

<form action="${pageContext.request.contextPath}/register" method="post">
    <input type="text" name="name" placeholder="Full Name" required><br><br>
    <input type="email" name="email" placeholder="Email" required><br><br>
    <input type="password" name="password" placeholder="Password" required><br><br>
    <input type="password" name="confirmPassword" placeholder="Confirm Password" required><br><br>
    <button type="submit">Register</button>
</form>

<p>
    Already have an account?
    <a href="${pageContext.request.contextPath}/pages/common/login.jsp">Login</a>
</p>

</body>
</html>