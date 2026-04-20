<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
</head>
<body>

<div class="page-wrapper">
    <div class="card">
        <div class="brand">
            <h1>Art Marketplace</h1>
            <p>Create your account and start your art journey</p>
        </div>

        <h2 class="form-title">Register</h2>

        <% if(request.getAttribute("error") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="name" class="form-control" placeholder="Enter your full name" required>
            </div>

            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email" class="form-control" placeholder="Enter your email" required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" class="form-control" placeholder="Create password" required>
            </div>

            <div class="form-group">
                <label>Confirm Password</label>
                <input type="password" name="confirmPassword" class="form-control" placeholder="Confirm password" required>
            </div>

            <button type="submit" class="btn">Create Account</button>
        </form>

        <p class="bottom-text text-center">
            Already have an account?
            <a href="${pageContext.request.contextPath}/pages/common/login.jsp">Login</a>
        </p>
    </div>
</div>

</body>
</html>