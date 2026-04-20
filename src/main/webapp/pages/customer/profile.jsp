<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("user");
    String userName = user != null ? user.getName() : "Guest User";
    String userEmail = user != null ? user.getEmail() : "Not Available";
    String userRole = user != null ? user.getRole() : "Customer";
    String firstLetter = userName.substring(0, 1).toUpperCase();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
</head>
<body>

<div class="main-page">
    <%@ include file="/pages/common/navbar.jsp" %>

    <div class="page-content">
        <div class="page-card">
            <div class="page-header">
                <h1>My Profile</h1>
                <p>View your personal details and account information.</p>
            </div>

            <div class="page-body">
                <div class="profile-box">
                    <div class="profile-avatar"><%= firstLetter %></div>

                    <div class="profile-details">
                        <div class="detail-row">
                            <h4>Full Name</h4>
                            <p><%= userName %></p>
                        </div>

                        <div class="detail-row">
                            <h4>Email Address</h4>
                            <p><%= userEmail %></p>
                        </div>

                        <div class="detail-row">
                            <h4>Role</h4>
                            <p><%= userRole %></p>
                        </div>

                        <div class="detail-row">
                            <h4>Account Status</h4>
                            <p>Active and ready to explore artworks.</p>
                        </div>
                    </div>
                </div>

                <div class="action-row">
                    <a href="#" class="action-btn primary-btn">Edit Profile</a>
                    <a href="${pageContext.request.contextPath}/logout" class="action-btn logout-btn">Logout</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>