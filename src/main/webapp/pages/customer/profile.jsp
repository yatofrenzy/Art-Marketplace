<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("user");
    String name = user != null ? user.getName() : "User";
    String email = user != null ? user.getEmail() : "Not Available";
    String role = user != null ? user.getRole() : "customer";
    String letter = name.substring(0,1).toUpperCase();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Profile | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>
<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>My Profile</h1>
            <p>View your personal account details.</p>
        </section>

        <h2 class="section-title">Account Information</h2>

        <div class="card profile-box">
            <div class="avatar"><%= letter %></div>
            <div>
                <h3><%= name %></h3>
                <p>Email: <%= email %></p>
                <p>Role: <%= role %></p>
                <div class="btn-row">
                    <a href="#" class="btn btn-primary">Edit Profile</a>
                    <a href="${pageContext.request.contextPath}/logout" class="btn btn-dark">Logout</a>
                </div>
            </div>
        </div>
    </main>
</div>
<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Mode</button>

<script src="${pageContext.request.contextPath}/js/ui.js"></script>
</body>
</html>