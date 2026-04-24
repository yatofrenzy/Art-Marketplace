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
        <section class="hero">
            <h1>Welcome, <%= userName %>!</h1>
            <p>Discover original artwork, collect meaningful pieces, and enjoy a creative marketplace made for art lovers.</p>
            <div class="btn-row">
                <a href="${pageContext.request.contextPath}/pages/customer/artworks.jsp" class="btn btn-primary">Explore Artworks</a>
                <a href="${pageContext.request.contextPath}/pages/customer/profile.jsp" class="btn btn-dark">View Profile</a>
            </div>
        </section>

        <h2 class="section-title">What you can do</h2>
        <div class="grid grid-3">
            <div class="card"><h3>Browse Art</h3><p>Explore paintings, sketches, digital art, photography, and more.</p></div>
            <div class="card"><h3>Manage Cart</h3><p>Add artworks you like and review them before checkout.</p></div>
            <div class="card"><h3>Track Orders</h3><p>View your order details and payment status easily.</p></div>
        </div>
    </main>
</div>
</body>
</html>