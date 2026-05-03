<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth_art.css">
</head>
<body>

<div class="auth-shell">
    <section class="art-side">
        <div class="brand-badge">Art Marketplace</div>

        <div class="hero-text">
            <h1>Start your art journey today.</h1>
            <p>Create an account to discover artworks, save your favorites, and become part of a creative digital community.</p>
        </div>

        <div class="paint-board">
            <div class="paint-tile"></div>
            <div class="paint-tile"></div>
            <div class="paint-tile"></div>
        </div>
    </section>

    <section class="form-side">
        <div class="auth-card">
            <div class="mini-title">Join Us</div>
            <h2>Create Account</h2>
            <p class="sub">Register your details and begin exploring the marketplace.</p>

            <% if(request.getAttribute("error") != null) { %>
                <div class="notice-error"><%= request.getAttribute("error") %></div>
            <% } %>

            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="input-box">
                    <label>Full Name</label>
                    <input type="text" name="name" placeholder="Your full name" autocomplete="name" required>
                </div>

                <div class="input-box">
                    <label>Email Address</label>
                    <input type="email" name="email" placeholder="example@email.com" autocomplete="email" required>
                </div>

                <div class="input-box">
                    <label>Phone Number</label>
                    <input type="text" name="phone" placeholder="10 digit phone number" autocomplete="tel" required>
                </div>

                <div class="input-box">
                    <label>Password</label>
                    <input type="password" name="password" placeholder="Create password" autocomplete="new-password" required>
                </div>

                <div class="input-box">
                    <label>Confirm Password</label>
                    <input type="password" name="confirmPassword" placeholder="Confirm password" autocomplete="new-password" required>
                </div>

                <button type="submit" class="art-btn">Create My Account</button>
            </form>

            <div class="switch-line">
                Already registered?
                <a href="${pageContext.request.contextPath}/pages/common/login.jsp">Login here</a>
            </div>
        </div>
    </section>
</div>

<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Mode</button>

<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<%@ include file="/pages/common/footer.jsp" %>

</body>
</html>