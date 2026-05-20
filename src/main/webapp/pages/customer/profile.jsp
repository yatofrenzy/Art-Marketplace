<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Customer profile page for viewing and updating account contact details. --%>
<%
    // Load logged-in user from the session before showing profile data.
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
        return;
    }

    // Prepare profile values for display and for the update form.
    String name = user.getName();
    String email = user.getEmail();
    String role = user.getRole();
    String contact = user.getContactNumber() != null ? user.getContactNumber() : "";
    String profileImage = user.getProfileImage();
    String letter = name.substring(0, 1).toUpperCase();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Profile | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>

<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>My Profile</h1>
            <p>View and update your account details.</p>
        </section>

        <h2 class="section-title">Account Information</h2>

        <%-- Show update result messages after profile servlet redirect. --%>
        <% if(request.getParameter("success") != null) { %>
            <div class="success-message">Profile updated successfully.</div>
        <% } %>

        <% if(request.getParameter("error") != null) { %>
            <div class="error-message">Profile update failed. Please check the details.</div>
        <% } %>

        <div class="card profile-box">

            <%-- Show uploaded profile image when available; otherwise show initials avatar. --%>
            <% if(profileImage != null && !profileImage.isEmpty()) { %>
                <img src="${pageContext.request.contextPath}/<%= profileImage %>"
                     alt="Profile Image"
                     style="width:190px;height:190px;border-radius:38px;object-fit:cover;">
            <% } else { %>
                <div class="avatar"><%= letter %></div>
            <% } %>

            <div>
                <h3><%= name %></h3>
                <p>Email: <%= email %></p>
                <p>Contact Number: <%= contact.isEmpty() ? "Not Added" : contact %></p>
                <p>Role: <%= role %></p>

                <div class="btn-row">
                    <button type="button"
                            onclick="document.getElementById('editForm').style.display='block'"
                            class="btn btn-primary">
                        Edit Profile
                    </button>

                    <a href="${pageContext.request.contextPath}/logout" class="btn btn-dark">
                        Logout
                    </a>
                </div>
            </div>
        </div>

        <div id="editForm" class="form-card" style="display:none; margin-top:30px;">
            <h2 style="margin-bottom:20px;">Edit Profile</h2>

            <%-- Multipart form sends updated contact details and optional image to UpdateProfileServlet. --%>
            <form action="${pageContext.request.contextPath}/update-profile"
                  method="post"
                  enctype="multipart/form-data">

                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" value="<%= email %>" required>
                </div>

                <div class="form-group">
                    <label>Contact Number</label>
                    <input type="text"
                           name="contactNumber"
                           value="<%= contact %>"
                           placeholder="Enter contact number"
                           required>
                </div>

                <div class="form-group">
                    <label>Profile Picture</label>
                    <input type="file" name="profileImage" accept="image/*">
                </div>

                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">
                        Save Changes
                    </button>

                    <button type="button"
                            onclick="document.getElementById('editForm').style.display='none'"
                            class="btn btn-dark">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    </main>
</div>

<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Mode</button>

<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<%@ include file="/pages/common/footer.jsp" %>

</body>
</html>
