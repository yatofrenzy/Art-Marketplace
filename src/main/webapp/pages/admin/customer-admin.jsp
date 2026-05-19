<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.dao.UserDAO" %>
<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    User admin = (User) session.getAttribute("user");

    if (admin == null || !"admin".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
        return;
    }

    UserDAO userDAO = new UserDAO();
    List<User> customers = userDAO.getAllCustomersWithOrderCount();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customers</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/css/dashboard.css?v=15">

    <style>
        .customers-page {
            padding: 45px;
            background: #f4f7fb;
            min-height: 100vh;
        }

        .customers-title {
            font-size: 52px;
            font-weight: 800;
            color: #111827;
            margin-bottom: 35px;
        }

        .customers-card {
            background: #ffffff;
            border-radius: 28px;
            padding: 34px;
            box-shadow: 0 22px 55px rgba(15, 23, 42, 0.08);
        }

        .customers-table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0 14px;
        }

        .customers-table th {
            background: #eefcff;
            color: #111827;
            padding: 18px;
            text-align: left;
            font-weight: 800;
            font-size: 14px;
        }

        .customers-table th:first-child {
            border-radius: 16px 0 0 16px;
        }

        .customers-table th:last-child {
            border-radius: 0 16px 16px 0;
        }

        .customers-table td {
            background: #f8fafc;
            padding: 18px;
            font-weight: 600;
            color: #374151;
            font-size: 14px;
        }

        .customers-table tr td:first-child {
            border-radius: 16px 0 0 16px;
            color: #111827;
            font-weight: 800;
        }

        .customers-table tr td:last-child {
            border-radius: 0 16px 16px 0;
        }

        .status-pill {
            padding: 8px 13px;
            border-radius: 999px;
            font-size: 12px;
            font-weight: 900;
        }

        .status-approved {
            background: #e6fffa;
            color: #0f766e;
        }

        .status-pending {
            background: #fff4d8;
            color: #a46b00;
        }

        .status-rejected {
            background: #fff1f2;
            color: #be123c;
        }
    </style>
</head>

<body>

<div class="dashboard">

    <jsp:include page="/pages/common/sidebar.jsp">
        <jsp:param name="active" value="customers"/>
    </jsp:include>

    <main class="main-content customers-page">

        <h1 class="customers-title">Customers</h1>

        <div class="customers-card">
            <table class="customers-table">
                <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Status</th>
                    <th>Orders</th>
                </tr>

                <% if(customers != null && !customers.isEmpty()) {
                    for(User customer : customers) {
                        String status = customer.getAccountStatus() != null ? customer.getAccountStatus().toLowerCase() : "pending";
                %>

                <tr>
                    <td>#<%= customer.getUserId() %></td>
                    <td><%= customer.getName() %></td>
                    <td><%= customer.getEmail() %></td>
                    <td><%= customer.getPhone() != null ? customer.getPhone() : "Not Added" %></td>
                    <td>
                        <span class="status-pill status-<%= status %>">
                            <%= customer.getAccountStatus() %>
                        </span>
                    </td>
                    <td><%= customer.getOrderCount() %></td>
                </tr>

                <% } } else { %>

                <tr>
                    <td colspan="6">No customers found.</td>
                </tr>

                <% } %>
            </table>
        </div>

    </main>

</div>

</body>
</html>