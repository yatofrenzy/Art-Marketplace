<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/pages/customer/home.jsp" class="logo">
        Art<span>Marketplace</span>
    </a>

    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/pages/customer/home.jsp">Home</a>
        <a href="${pageContext.request.contextPath}/pages/customer/artworks.jsp">Artworks</a>
        <a href="${pageContext.request.contextPath}/pages/customer/cart.jsp">Cart</a>
        <a href="${pageContext.request.contextPath}/pages/customer/orders.jsp">Orders</a>

        <% 
            com.artmarketplace.model.User navUser = 
                (com.artmarketplace.model.User) session.getAttribute("user");

            if (navUser != null && "admin".equalsIgnoreCase(navUser.getRole())) { 
        %>
            <a href="${pageContext.request.contextPath}/admin/artwork?action=add">Add Artwork</a>
        <% } %>

        <a href="${pageContext.request.contextPath}/pages/customer/profile.jsp">Profile</a>

        <form action="${pageContext.request.contextPath}/pages/customer/artworks.jsp"
              method="get"
              class="nav-search">
            <input type="text"
                   name="search"
                   placeholder="Search art..."
                   value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
            <button type="submit">⌕</button>
        </form>

        <a href="${pageContext.request.contextPath}/logout" class="nav-logout">Logout</a>
    </div>
</nav>