<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/pages/customer/home.jsp" class="logo">
        Art<span>Marketplace</span>
    </a>

    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/pages/customer/home.jsp">Home</a>
        <a href="${pageContext.request.contextPath}/pages/customer/artworks.jsp">Gallery</a>
        <a href="${pageContext.request.contextPath}/pages/customer/cart.jsp">Cart</a>
        <a href="${pageContext.request.contextPath}/pages/customer/orders.jsp">Orders</a>
        <a href="${pageContext.request.contextPath}/pages/customer/profile.jsp">Profile</a>
        <a href="${pageContext.request.contextPath}/logout" class="nav-logout">Logout</a>
    </div>
</nav>