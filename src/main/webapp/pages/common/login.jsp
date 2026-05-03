<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth_art.css">

    <style>
        /* Minimal addition — blends with your theme */
        .role-toggle {
            display: flex;
            gap: 10px;
            margin-bottom: 15px;
        }

        .role-btn {
            flex: 1;
            padding: 8px;
            border-radius: 6px;
            border: none;
            cursor: pointer;
            background: #e0e0e0;
            font-weight: 600;
            transition: 0.3s;
        }

        .role-btn.active {
            background: #111;
            color: #fff;
        }
    </style>
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

            <% if(request.getParameter("registered") != null) { %>
                <div class="success-message">Registration successful. Please login.</div>
            <% } %>

            <% if(request.getParameter("reset") != null) { %>
                <div class="success-message">Password reset successful. Please login.</div>
            <% } %>

            <% if(request.getAttribute("error") != null) { %>
                <div class="notice-error"><%= request.getAttribute("error") %></div>
            <% } %>

            <div class="role-toggle">
                <button type="button" id="userBtn" class="role-btn active" onclick="setRole('user')">User</button>
                <button type="button" id="adminBtn" class="role-btn" onclick="setRole('admin')">Admin</button>
            </div>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <input type="hidden" name="role" id="role" value="user">

                <div class="input-box">
                    <label>Email Address</label>
                    <input type="email" name="email" placeholder="example@email.com" autocomplete="email" required>
                </div>

                <div class="input-box">
                    <label>Password</label>
                    <input type="password" name="password" placeholder="Enter password" autocomplete="current-password" required>
                </div>

                <div style="text-align:right; margin-top:-8px; margin-bottom:14px;">
                    <a href="${pageContext.request.contextPath}/pages/common/forgot-password.jsp"
                       style="color:var(--pink); font-weight:800; font-size:13px; text-decoration:none;">
                        Forgot Password?
                    </a>
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

<script>
function setRole(role) {
    document.getElementById("role").value = role;

    if (role === "admin") {
        document.getElementById("adminBtn").classList.add("active");
        document.getElementById("userBtn").classList.remove("active");
    } else {
        document.getElementById("userBtn").classList.add("active");
        document.getElementById("adminBtn").classList.remove("active");
    }
}
</script>

<%@ include file="/pages/common/footer.jsp" %>

</body>
</html>