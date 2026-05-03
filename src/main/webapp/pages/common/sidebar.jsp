<%@ page contentType="text/html; charset=UTF-8" %>

<%
    String active = request.getParameter("active");
    if (active == null) active = "";
%>

<aside class="sidebar">

    <div class="logo">
        <h2>
            <span class="dark">Art</span>
            <span class="blue">Marketplace</span>
        </h2>
    </div>

    <ul class="menu">

        <li class="<%= active.equals("dashboard") ? "active" : "" %>">
            <a href="${pageContext.request.contextPath}/pages/admin/dashboard.jsp">
                <i class="fa-solid fa-house"></i>
                <span>Dashboard</span>
            </a>
        </li>

        <li class="<%= active.equals("analytics") ? "active" : "" %>">
            <a href="${pageContext.request.contextPath}/pages/admin/dashboard.jsp">
                <i class="fa-solid fa-chart-line"></i>
                <span>Analytics</span>
            </a>
        </li>

        <li class="<%= active.equals("products") ? "active" : "" %>">
            <a href="${pageContext.request.contextPath}/pages/admin/artwork-list.jsp">
                <i class="fa-solid fa-box"></i>
                <span>Artworks</span>
            </a>
        </li>

        <li class="<%= active.equals("orders") ? "active" : "" %>">
            <a href="${pageContext.request.contextPath}/pages/customer/orders.jsp">
                <i class="fa-solid fa-cart-shopping"></i>
                <span>Orders</span>
            </a>
        </li>

        <li class="<%= active.equals("customers") ? "active" : "" %>">
            <a href="${pageContext.request.contextPath}/pages/admin/customers.jsp">
                <i class="fa-solid fa-users"></i>
                <span>Customers</span>
            </a>
        </li>

        <li class="<%= active.equals("settings") ? "active" : "" %>">
            <a href="${pageContext.request.contextPath}/pages/admin/settings.jsp">
                <i class="fa-solid fa-gear"></i>
                <span>Settings</span>
            </a>
        </li>

    </ul>

</aside>