<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
</head>
<body>

<div class="main-page">
    <%@ include file="/pages/common/navbar.jsp" %>

    <div class="page-content">
        <div class="page-card">
            <div class="page-header">
                <h1>Admin Dashboard</h1>
                <p>Manage users, artworks, categories, and monitor the marketplace system.</p>
            </div>

            <div class="page-body">
                <div class="success-message">
                    Welcome to the admin dashboard.
                </div>

                <div class="info-grid">
                    <div class="info-box">
                        <h3>Total Users</h3>
                        <p>Manage registered customers, artists, and admin accounts from one place.</p>
                    </div>

                    <div class="info-box">
                        <h3>Artwork Approval</h3>
                        <p>Review uploaded artwork and approve or reject them before public display.</p>
                    </div>

                    <div class="info-box">
                        <h3>Order Monitoring</h3>
                        <p>Track placed orders, payment status, and manage order processing activities.</p>
                    </div>

                    <div class="info-box">
                        <h3>Category Control</h3>
                        <p>Add, update, and organize categories for better artwork management.</p>
                    </div>
                </div>

                <div class="action-row">
                    <a href="#" class="action-btn primary-btn">Manage Users</a>
                    <a href="#" class="action-btn secondary-btn">Manage Artworks</a>
                    <a href="${pageContext.request.contextPath}/logout" class="action-btn logout-btn">Logout</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>