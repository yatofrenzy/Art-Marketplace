<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login_register.css">
</head>
<body>

<div class="container">
    <h2>Register</h2>

    <p class="error">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
    </p>

    <form action="<%= request.getContextPath() %>/register" method="post">
        <input type="text" name="name" placeholder="Full Name" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
        <button type="submit">Register</button>
    </form>

    <p>
        Already have an account?
        <a href="<%= request.getContextPath() %>/pages/common/login.jsp">Login</a>
    </p>
</div>

</body>
</html>