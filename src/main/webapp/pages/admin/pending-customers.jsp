<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.dao.UserDAO" %>
<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    User loggedUser = (User) session.getAttribute("user");

    if (loggedUser == null || !"admin".equalsIgnoreCase(loggedUser.getRole())) {
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
        return;
    }

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
            padding: 30px;
        }

        .approval-header {
            background: #ffffff;
            border-radius: 24px;
            padding: 28px;
            margin-bottom: 25px;
            box-shadow: 0 12px 35px rgba(0,0,0,0.08);
        }

        .approval-header h1 {
            margin: 0;
            color: #111;
        }

        .approval-header p {
            margin-top: 8px;
            color: #777;
        }

        .pending-section {
            background: #ffffff;
            border-radius: 22px;
            padding: 24px;
            box-shadow: 0 12px 35px rgba(0,0,0,0.08);
        }

        .pending-table {
            width: 100%;
            border-collapse: collapse;
        }

        .pending-table th,
        .pending-table td {
            padding: 14px 12px;
            text-align: left;
            border-bottom: 1px solid #eee;
            font-size: 14px;
        }

        .pending-table th {
            color: #777;
            font-weight: 700;
        }

        .status-pill {
            padding: 6px 12px;
            border-radius: 999px;
            background: #fff4d8;
            color: #a46b00;
            font-weight: 700;
            font-size: 12px;
        }

        .approval-actions {
            display: flex;
            gap: 8px;
        }

        .approve-btn,
        .reject-btn {
            border: none;
            padding: 9px 14px;
            border-radius: 10px;
            font-weight: 700;
            cursor: pointer;
        }

        .approve-btn {
            background: #52d6c5;
            color: #06201d;
        }

        .reject-btn {
            background: #ff6b6b;
            color: white;
        }

        .admin-message {
            margin-bottom: 18px;
            padding: 12px 16px;
            border-radius: 12px;
            font-weight: 700;
        }

        .admin-message.success {
            background: #e7fff8;
            color: #0f7b63;
        }

        .admin-message.error {
            background: #ffeaea;
            color: #b32020;
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
            <h1>Customer Approval Status</h1>
            <p>Review newly registered customers and approve or reject their account access.</p>
        </div>

        <div class="pending-section">

            <% if("success".equals(request.getParameter("approval"))) { %>
                <div class="admin-message success">Customer status updated successfully.</div>
            <% } %>

            <% if("error".equals(request.getParameter("approval"))) { %>
                <div class="admin-message error">Customer status update failed.</div>
            <% } %>

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