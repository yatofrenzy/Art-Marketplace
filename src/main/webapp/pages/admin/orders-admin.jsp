<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.dao.OrderDAO" %>
<%@ page import="com.artmarketplace.model.Order" %>
<%@ page import="com.artmarketplace.model.OrderItem" %>
<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    User admin = (User) session.getAttribute("user");

    if (admin == null || !"admin".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
        return;
    }

    OrderDAO orderDAO = new OrderDAO();
    List<Order> orders = orderDAO.getAllOrders();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Orders</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/css/dashboard.css?v=15">

    <style>
        .orders-page {
            padding: 45px;
            background: #f4f7fb;
            min-height: 100vh;
        }

        .orders-title {
            font-size: 52px;
            font-weight: 800;
            color: #111827;
            margin-bottom: 35px;
        }

        .orders-card {
            background: #ffffff;
            border-radius: 28px;
            padding: 34px;
            box-shadow: 0 22px 55px rgba(15, 23, 42, 0.08);
        }

        .orders-table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0 14px;
        }

        .orders-table th {
            background: #eefcff;
            color: #111827;
            padding: 18px;
            text-align: left;
            font-weight: 800;
            font-size: 14px;
        }

        .orders-table td {
            background: #f8fafc;
            padding: 18px;
            font-weight: 600;
            color: #374151;
            font-size: 14px;
            vertical-align: top;
        }

        .orders-table tr td:first-child {
            border-radius: 16px 0 0 16px;
            font-weight: 800;
            color: #111827;
        }

        .orders-table tr td:last-child {
            border-radius: 0 16px 16px 0;
        }

        .artwork-list {
            display: flex;
            flex-direction: column;
            gap: 8px;
            min-width: 210px;
        }

        .artwork-item {
            background: #ffffff;
            border: 1px solid #e5e7eb;
            padding: 10px 12px;
            border-radius: 14px;
        }

        .artwork-item strong {
            display: block;
            color: #111827;
            font-size: 13px;
            margin-bottom: 4px;
        }

        .artwork-item span {
            font-size: 12px;
            color: #6b7280;
            font-weight: 700;
        }

        .order-action-form {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .status-select {
            padding: 11px 14px;
            border-radius: 13px;
            border: 1px solid #d8dee8;
            background: white;
            font-weight: 800;
            outline: none;
        }

        .update-btn {
            border: none;
            padding: 12px 18px;
            border-radius: 13px;
            background: #2ec6d3;
            color: #061012;
            font-weight: 900;
            cursor: pointer;
        }

        .status-text {
            padding: 8px 13px;
            border-radius: 999px;
            font-size: 12px;
            font-weight: 900;
        }

        .status-pending {
            background: #fff4d8;
            color: #a46b00;
        }

        .status-completed {
            background: #e6fffa;
            color: #0f766e;
        }

        .status-cancelled {
            background: #fff1f2;
            color: #be123c;
        }

        .message-success {
            background: #e6fffa;
            color: #0f766e;
            padding: 14px 18px;
            border-radius: 16px;
            margin-bottom: 18px;
            font-weight: 800;
        }

        .message-error {
            background: #fff1f2;
            color: #be123c;
            padding: 14px 18px;
            border-radius: 16px;
            margin-bottom: 18px;
            font-weight: 800;
        }
    </style>
</head>

<body>

<div class="dashboard">

    <jsp:include page="/pages/common/sidebar.jsp">
        <jsp:param name="active" value="orders"/>
    </jsp:include>

    <main class="main-content orders-page">

        <h1 class="orders-title">Orders</h1>

        <% if(request.getParameter("success") != null) { %>
            <div class="message-success">Order status updated successfully.</div>
        <% } %>

        <% if(request.getParameter("error") != null) { %>
            <div class="message-error">Order status update failed.</div>
        <% } %>

        <div class="orders-card">
            <table class="orders-table">
                <tr>
                    <th>Order ID</th>
                    <th>Customer</th>
                    <th>Artworks</th>
                    <th>Date</th>
                    <th>Payment</th>
                    <th>Status</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>

                <% if (orders != null && !orders.isEmpty()) {
                    for (Order order : orders) {
                        String statusClass = order.getOrderStatus() != null ? order.getOrderStatus().toLowerCase() : "pending";
                %>

                <tr>
                    <td>#<%= order.getOrderId() %></td>
                    <td><%= order.getCustomerName() %></td>

                    <td>
                        <div class="artwork-list">
                            <% if(order.getItems() != null && !order.getItems().isEmpty()) {
                                for(OrderItem item : order.getItems()) {
                            %>
                                <div class="artwork-item">
                                    <strong><%= item.getTitle() %></strong>
                                    <span><%= item.getCategoryName() %> × <%= item.getQuantity() %></span>
                                </div>
                            <% } } else { %>
                                <span>No items</span>
                            <% } %>
                        </div>
                    </td>

                    <td><%= order.getOrderDate() %></td>
                    <td><%= order.getPaymentMethod() %> / <%= order.getPaymentStatus() %></td>

                    <td>
                        <span class="status-text status-<%= statusClass %>">
                            <%= order.getOrderStatus() %>
                        </span>
                    </td>

                    <td>Rs <%= order.getTotalAmount() %></td>

                    <td>
                        <form action="${pageContext.request.contextPath}/admin/update-order-status"
                              method="post"
                              class="order-action-form">

                            <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">

                            <select name="status" class="status-select">
                                <option value="Pending" <%= "Pending".equals(order.getOrderStatus()) ? "selected" : "" %>>Pending</option>
                                <option value="Completed" <%= "Completed".equals(order.getOrderStatus()) ? "selected" : "" %>>Completed</option>
                                <option value="Cancelled" <%= "Cancelled".equals(order.getOrderStatus()) ? "selected" : "" %>>Cancelled</option>
                            </select>

                            <button type="submit" class="update-btn">Update</button>
                        </form>
                    </td>
                </tr>

                <% } } else { %>

                <tr>
                    <td colspan="8">No orders found.</td>
                </tr>

                <% } %>
            </table>
        </div>

    </main>

</div>

</body>
</html>