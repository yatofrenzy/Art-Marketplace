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
        <section class="hero">
            <h1>Admin Dashboard</h1>
            <p>Manage users, artworks, categories, orders, and marketplace activities.</p>
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
</body>
</html>