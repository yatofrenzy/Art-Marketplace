<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
</head>
<body>

<div class="page-wrapper">
    <div class="card">
        <div class="brand">
            <h1>Art Marketplace</h1>
            <p>Login to explore and manage beautiful artwork</p>
        </div>

        <h2 class="form-title">Login</h2>

        <% if(request.getAttribute("error") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>


<p>
    Don’t have an account?
    <a> href="${pageContext.request.contextPath}/pages/common/register.jsp">Register</a>
</p>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email" class="form-control" placeholder="Enter your email" required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" class="form-control" placeholder="Enter your password" required>
            </div>

            <button type="submit" class="btn">Login</button>
        </form>

        <p class="bottom-text text-center">
            Don’t have an account?
            <a href="${pageContext.request.contextPath}/pages/common/register.jsp">Register</a>
        </p>
    </div>
</div>


</body>
</html>