<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>
<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero admin-hero">
    <h1>Admin Command Center</h1>
    <p>Monitor marketplace activity, manage artworks, review orders, and keep the platform running smoothly.</p>

    <div class="admin-quick-grid">
        <div class="admin-quick">
            <strong>120</strong>
            <span>Total Users</span>
        </div>
        <div class="admin-quick">
            <strong>36</strong>
            <span>Pending Artworks</span>
        </div>
        <div class="admin-quick">
            <strong>18</strong>
            <span>Orders Today</span>
        </div>
        <div class="admin-quick">
            <strong>Rs. 82K</strong>
            <span>Sales</span>
        </div>
    </div>
</section>

        <h2 class="section-title">Admin Controls</h2>

        <div class="grid grid-3">
            <div class="card"><h3>Users</h3><p>View and manage registered customers and artists.</p></div>
            <div class="card"><h3>Artworks</h3><p>Approve, reject, edit, or remove uploaded artwork.</p></div>
            <div class="card"><h3>Orders</h3><p>Track payment status and order progress.</p></div>
            <div class="card"><h3>Categories</h3><p>Organize artworks into proper categories.</p></div>
            <div class="card"><h3>Reports</h3><p>View artwork sales and marketplace activity summary.</p></div>
            <div class="card"><h3>Messages</h3><p>Check customer contact and support messages.</p></div>
        </div>
    </main>
</div>
<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>
<a href="${pageContext.request.contextPath}/admin/artwork" class="btn btn-primary">
    Manage Artworks
</a>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Mode</button>
<script src="${pageContext.request.contextPath}/js/ui.js"></script>

</body>
</html>