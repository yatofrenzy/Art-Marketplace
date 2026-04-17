<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>

    <h2>Register Page</h2>

    <p style="color:red;">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
    </p>

    <form action="<%= request.getContextPath() %>/register" method="post">
        <label>Full Name:</label><br>
        <input type="text" name="name" required><br><br>

        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <label>Confirm Password:</label><br>
        <input type="password" name="confirmPassword" required><br><br>

        <button type="submit">Register</button>
    </form>

    <p>
        Already have an account?
        <a href="<%= request.getContextPath() %>/pages/common/login.jsp">Login</a>
    </p>

</body>
</html>