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
            <a href="${pageContext.request.contextPath}/pages/admin/dashboard-admin.jsp">
                <i class="fa-solid fa-house"></i>
                <span>Dashboard</span>
            </a>
        </li>

        <li class="<%= active.equals("analytics") ? "active" : "" %>">
            <a href="${pageContext.request.contextPath}/pages/admin/analytics-admin.jsp">
                <i class="fa-solid fa-chart-line"></i>
                <span>Analytics</span>
            </a>
        </li>

        <li class="<%= active.equals("artworks") ? "active" : "" %>">
            <a href="${pageContext.request.contextPath}/pages/admin/artwork-admin.jsp">
                <i class="fa-solid fa-palette"></i>
                <span>Artworks</span>
            </a>
        </li>

        <li class="<%= active.equals("orders") ? "active" : "" %>">
           <a href="${pageContext.request.contextPath}/pages/admin/orders-admin.jsp">
    		<i class="fa-solid fa-cart-shopping"></i>
   			 <span>Orders</span>
			</a>
        </li>

        <li class="<%= active.equals("customers") ? "active" : "" %>">
            <a href="${pageContext.request.contextPath}/pages/admin/customer-admin.jsp">
                <i class="fa-solid fa-users"></i>
                <span>Customers</span>
            </a>
        </li>
<li>
<a href="${pageContext.request.contextPath}/pages/admin/pending-customers.jsp">
    <i class="fa-solid fa-user-check"></i>
    <span>Customer Approval</span>
</a>
</li>
    </ul>

    <div class="sidebar-logout">
        <a href="${pageContext.request.contextPath}/logout">
            <i class="fa-solid fa-right-from-bracket"></i>
            <span>Logout</span>
        </a>
    </div>

</aside>