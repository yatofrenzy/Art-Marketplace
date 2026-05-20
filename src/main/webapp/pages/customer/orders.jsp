<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.dao.OrderDAO" %>
<%@ page import="com.artmarketplace.model.Order" %>
<%@ page import="com.artmarketplace.model.OrderItem" %>
<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Customer orders page that displays past orders and line items. --%>
<%
    // Orders are customer-specific, so the logged-in user is required.
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
        return;
    }

    // Load all orders and their items for the current customer.
    OrderDAO orderDAO = new OrderDAO();
    List<Order> orders = orderDAO.getOrdersByUser(user.getUserId());
%>

<!DOCTYPE html>
<html>
<head>
    <title>Orders | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>

<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>My Orders</h1>
            <p>View your order history and payment status.</p>
        </section>

        <%-- Show confirmation after successful checkout redirect. --%>
        <% if(request.getParameter("success") != null) { %>
            <div class="success-message">Order placed successfully.</div>
        <% } %>

        <h2 class="section-title">Order History</h2>

        <% if(orders != null && !orders.isEmpty()) { 
            for(Order order : orders) {
        %>

        <div class="table-card" style="margin-bottom: 25px;">
            <h3>Order #<%= order.getOrderId() %></h3>
            <p>Date: <%= order.getOrderDate() %></p>
            <p>Total: Rs. <%= order.getTotalAmount() %></p>
            <p>Payment: <%= order.getPaymentMethod() %> - <%= order.getPaymentStatus() %></p>
            <p>Status: <span class="badge"><%= order.getOrderStatus() %></span></p>

            <table style="margin-top: 20px;">
                <tr>
                    <th>Artwork</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>

                <% for(OrderItem item : order.getItems()) { %>
                <tr>
                    <td><%= item.getTitle() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td>Rs. <%= item.getPrice() %></td>
                </tr>
                <% } %>
            </table>
        </div>

        <% } } else { %>

        <div class="table-card">
            <p>No orders found.</p>
        </div>

        <% } %>
    </main>
</div>

<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Dark</button>
<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<%@ include file="/pages/common/footer.jsp" %>

</body>
</html>
