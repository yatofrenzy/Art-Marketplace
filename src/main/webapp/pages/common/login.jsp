<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth_art.css">
</head>
<body>

<div class="auth-shell">
    <section class="art-side">
        <div class="brand-badge">Art Marketplace</div>

        <div class="hero-text">
            <h1>Where art finds its people.</h1>
            <p>Login to explore creative artworks, manage your profile, and continue your journey inside a digital art space.</p>
        </div>

        <div class="paint-board">
            <div class="paint-tile"></div>
            <div class="paint-tile"></div>
            <div class="paint-tile"></div>
        </div>
    </section>

    <section class="form-side">
        <div class="auth-card">
            <div class="mini-title">Welcome Back</div>
            <h2>Login</h2>
            <p class="sub">Enter your account details to continue browsing the marketplace.</p>

            <% if(request.getAttribute("error") != null) { %>
                <div class="notice-error"><%= request.getAttribute("error") %></div>
            <% } %>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="input-box">
                    <label>Email Address</label>
                    <input type="email" name="email" placeholder="example@email.com" autocomplete="email" required>
                </div>

                <div class="input-box">
                    <label>Password</label>
                    <input type="password" name="password" placeholder="Enter password" autocomplete="current-password" required>
                </div>

                <button type="submit" class="art-btn">Enter Marketplace</button>
            </form>

            <div class="switch-line">
                New here?
                <a href="${pageContext.request.contextPath}/pages/common/register.jsp">Create an account</a>
            </div>
        </div>
    </section>
</div>
<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Mode</button>
<script src="${pageContext.request.contextPath}/js/ui.js"></script>

</body>
</html>