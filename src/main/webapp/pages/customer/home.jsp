<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("user");
    String userName = "User";
    if (user != null) {
        userName = user.getName();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
</head>
<body>

<div class="main-page">
    <%@ include file="/pages/common/navbar.jsp" %>

    <div class="page-content">
        <div class="page-card">
            <div class="page-header">
                <h1>Welcome, <%= userName %>!</h1>
                <p>Discover, explore, and enjoy amazing artworks in our marketplace.</p>
            </div>

            <div class="page-body">
                <div class="success-message">
                    Login successful. Welcome to your home page.
                </div>

                <div class="info-grid">
                    <div class="info-box">
                        <h3>Browse Artwork</h3>
                        <p>View different categories and explore creative artwork from talented artists.</p>
                    </div>

                    <div class="info-box">
                        <h3>Manage Profile</h3>
                        <p>Check your account details and keep your profile updated.</p>
                    </div>

                    <div class="info-box">
                        <h3>Cart & Orders</h3>
                        <p>Add artwork to your cart and manage your future purchases with ease.</p>
                    </div>

                    <div class="info-box">
                        <h3>Stay Inspired</h3>
                        <p>Enjoy a clean and creative platform built for discovering visual art.</p>
                    </div>
                </div>

                <div class="action-row">
                    <a href="${pageContext.request.contextPath}/pages/customer/artcards.jsp" class="action-btn primary-btn">Explore Art</a>
                    <a href="${pageContext.request.contextPath}/pages/customer/profile.jsp" class="action-btn secondary-btn">View Profile</a>
                    <a href="${pageContext.request.contextPath}/logout" class="action-btn logout-btn">Logout</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>