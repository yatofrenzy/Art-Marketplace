<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth_art.css">
</head>
<body>

<div class="auth-shell">
    <section class="art-side">
        <div class="brand-badge">Art Marketplace</div>

        <div class="hero-text">
            <h1>Recover your account safely.</h1>
            <p>Use your registered email and phone number to reset your password securely.</p>
        </div>

        <div class="paint-board">
            <div class="paint-tile"></div>
            <div class="paint-tile"></div>
            <div class="paint-tile"></div>
        </div>
    </section>

    <section class="form-side">
        <div class="auth-card">
            <div class="mini-title">Account Recovery</div>
            <h2>Forgot Password</h2>
            <p class="sub">Enter your email, phone number, and new password.</p>

            <% if(request.getAttribute("error") != null) { %>
                <div class="notice-error"><%= request.getAttribute("error") %></div>
            <% } %>

            <form action="${pageContext.request.contextPath}/forgot-password" method="post">
                <div class="input-box">
                    <label>Email Address</label>
                    <input type="email" name="email" placeholder="example@email.com" autocomplete="email" required>
                </div>

                <div class="input-box">
                    <label>Phone Number</label>
                    <input type="text" name="phone" placeholder="Registered phone number" autocomplete="tel" required>
                </div>

                <div class="input-box">
                    <label>New Password</label>
                    <input type="password" name="newPassword" placeholder="New password" autocomplete="new-password" required>
                </div>

                <div class="input-box">
                    <label>Confirm Password</label>
                    <input type="password" name="confirmPassword" placeholder="Confirm new password" autocomplete="new-password" required>
                </div>

                <button type="submit" class="art-btn">Reset Password</button>
            </form>

            <div class="switch-line">
                Remember your password?
                <a href="${pageContext.request.contextPath}/pages/common/login.jsp">Login here</a>
            </div>
        </div>
    </section>
</div>

</body>
</html>