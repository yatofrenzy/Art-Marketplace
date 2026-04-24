<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("user");
    String userName = user != null ? user.getName() : "Artist";
%>
<!DOCTYPE html>
<html>
<head>
    <title>Home | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>
<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero home-hero">
    <div>
        <h1>Welcome, <%= userName %>!</h1>
        <p>Step into a curated art space where every piece tells a story. Explore, collect, and manage your creative journey.</p>

        <div class="btn-row">
            <a href="${pageContext.request.contextPath}/pages/customer/artworks.jsp" class="btn btn-primary">Explore Gallery</a>
            <a href="${pageContext.request.contextPath}/pages/customer/profile.jsp" class="btn btn-dark">My Profile</a>
        </div>
    </div>

    <div class="hero-art-preview">
    <img src="${pageContext.request.contextPath}/resources/images/cherryblossom.jpg" alt="Artwork">
</div>
</section>



  <h2 class="section-title" style="
    padding-left: 34px;
    margin-left: 0;
    font-size: 34px;
    font-weight: 900;
    letter-spacing: -1px;
    color: var(--secondary);
">
    What you can do
</h2>

   
        <div class="grid grid-3">
            <div class="card"><h3>Browse Art</h3><p>Explore paintings, sketches, digital art, photography, and more.</p></div>
            <div class="card"><h3>Manage Cart</h3><p>Add artworks you like and review them before checkout.</p></div>
            <div class="card"><h3>Track Orders</h3><p>View your order details and payment status easily.</p></div>
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