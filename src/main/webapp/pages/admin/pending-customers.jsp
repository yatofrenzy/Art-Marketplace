<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.dao.UserDAO" %>
<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Admin pending customer approval view. Allows admins to approve or reject new accounts. --%>
<%
    // Verify admin session before showing pending user approval actions.
    User loggedUser = (User) session.getAttribute("user");

    if (loggedUser == null || !"admin".equalsIgnoreCase(loggedUser.getRole())) {
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
        return;
    }

    // Load only customers whose accounts are waiting for approval.
    UserDAO userDAO = new UserDAO();
    List<User> pendingCustomers = userDAO.getPendingCustomers();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Approval Status</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/css/dashboard.css?v=10">
          

    <style>
    .approval-page {
        padding: 45px 45px 70px;
        background: #f4f7fb;
        min-height: 100vh;
    }

    .approval-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 28px;
    }

    .approval-header h1 {
        font-size: 46px;
        font-weight: 800;
        color: #111827;
        margin: 0;
    }

    .approval-header p {
        margin-top: 8px;
        color: #6b7280;
        font-size: 16px;
        font-weight: 500;
    }

    .approval-stat {
        background: #fff;
        padding: 22px 28px;
        border-radius: 24px;
        box-shadow: 0 18px 45px rgba(15, 23, 42, 0.08);
        min-width: 180px;
    }

    .approval-stat span {
        color: #7b8794;
        font-weight: 700;
        font-size: 14px;
    }

    .approval-stat h2 {
        margin: 8px 0 0;
        font-size: 34px;
        color: #27c3d5;
    }

    .pending-section {
        background: #ffffff;
        border-radius: 28px;
        padding: 28px;
        box-shadow: 0 22px 55px rgba(15, 23, 42, 0.08);
    }

    .pending-section-title {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 22px;
    }

    .pending-section-title h3 {
        font-size: 26px;
        margin: 0;
        color: #111827;
    }

    .pending-table {
        width: 100%;
        border-collapse: separate;
        border-spacing: 0 14px;
    }

    .pending-table th {
        color: #7b8794;
        font-size: 13px;
        text-transform: uppercase;
        letter-spacing: .5px;
        padding: 0 16px 8px;
        text-align: left;
    }

    .pending-table td {
        background: #f8fafc;
        padding: 18px 16px;
        font-size: 15px;
        font-weight: 600;
        color: #111827;
    }

    .pending-table tr td:first-child {
        border-radius: 18px 0 0 18px;
    }

    .pending-table tr td:last-child {
        border-radius: 0 18px 18px 0;
    }

    .status-pill {
        padding: 8px 14px;
        border-radius: 999px;
        background: #fff4d8;
        color: #b7791f;
        font-weight: 800;
        font-size: 12px;
    }

    .approval-actions {
        display: flex;
        gap: 10px;
    }

    .approve-btn,
    .reject-btn {
        border: none;
        padding: 11px 18px;
        border-radius: 14px;
        font-weight: 800;
        cursor: pointer;
        transition: 0.25s ease;
    }

    .approve-btn {
        background: #27c3d5;
        color: #061012;
    }

    .approve-btn:hover {
        transform: translateY(-2px);
        box-shadow: 0 12px 25px rgba(39, 195, 213, 0.35);
    }

    .reject-btn {
        background: #ff5f6d;
        color: #fff;
    }

    .reject-btn:hover {
        transform: translateY(-2px);
        box-shadow: 0 12px 25px rgba(255, 95, 109, 0.32);
    }

    .admin-message {
        margin-bottom: 18px;
        padding: 14px 18px;
        border-radius: 16px;
        font-weight: 800;
    }

    .admin-message.success {
        background: #e6fffa;
        color: #0f766e;
    }

    .admin-message.error {
        background: #fff1f2;
        color: #be123c;
    }
</style>
</head>

<body>

<div class="dashboard">

    <jsp:include page="/pages/common/sidebar.jsp">
        <jsp:param name="active" value="customer-approval"/>
    </jsp:include>

    <main class="main-content approval-page">

        <div class="approval-header">
    <div>
        <h1>Customer Approval</h1>
        <p>Review pending customer registrations and manage account access.</p>
    </div>

    <div class="approval-stat">
        <span>Pending Requests</span>
        <h2><%= pendingCustomers != null ? pendingCustomers.size() : 0 %></h2>
    </div>
</div>

        <div class="pending-section">

            <% if("success".equals(request.getParameter("approval"))) { %>
                <div class="admin-message success">Customer status updated successfully.</div>
            <% } %>

            <% if("error".equals(request.getParameter("approval"))) { %>
                <div class="admin-message error">Customer status update failed.</div>
            <% } %>
<div class="pending-section-title">
    <h3>Pending Customer List</h3>
</div>
            <table class="pending-table">
                <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>

                <% if (pendingCustomers != null && !pendingCustomers.isEmpty()) {
                    for (User customer : pendingCustomers) {
                %>
                <tr>
                    <td><%= customer.getUserId() %></td>
                    <td><%= customer.getName() %></td>
                    <td><%= customer.getEmail() %></td>
                    <td><%= customer.getPhone() != null ? customer.getPhone() : "Not Added" %></td>
                    <td><span class="status-pill"><%= customer.getAccountStatus() %></span></td>
                    <td>
                        <div class="approval-actions">
                            <form action="${pageContext.request.contextPath}/admin/user-approval" method="post">
                                <input type="hidden" name="userId" value="<%= customer.getUserId() %>">
                                <input type="hidden" name="action" value="approve">
                                <button type="submit" class="approve-btn">Approve</button>
                            </form>

                            <form action="${pageContext.request.contextPath}/admin/user-approval" method="post">
                                <input type="hidden" name="userId" value="<%= customer.getUserId() %>">
                                <input type="hidden" name="action" value="reject">
                                <button type="submit" class="reject-btn">Reject</button>
                            </form>
                        </div>
                    </td>
                </tr>
                <% } } else { %>
                <tr>
                    <td colspan="6">No pending customer registrations.</td>
                </tr>
                <% } %>
            </table>

        </div>

    </main>

</div>

</body>
</html>
