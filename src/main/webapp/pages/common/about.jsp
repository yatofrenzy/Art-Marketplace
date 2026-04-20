<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>About Us</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
</head>
<body>

<div class="main-page">
    <%@ include file="/pages/common/navbar.jsp" %>

    <div class="page-content">
        <div class="page-card">
            <div class="page-header">
                <h1>About Art Marketplace</h1>
                <p>Learn more about our purpose, platform, and creative mission.</p>
            </div>

            <div class="page-body">
                <div class="info-grid">
                    <div class="info-box">
                        <h3>Our Purpose</h3>
                        <p>Art Marketplace is designed to connect artists and customers in one simple online platform.</p>
                    </div>

                    <div class="info-box">
                        <h3>Creative Community</h3>
                        <p>We support artists by giving them a space to showcase, manage, and sell their creations.</p>
                    </div>

                    <div class="info-box">
                        <h3>Easy Experience</h3>
                        <p>Customers can easily browse, view, and explore different categories of artwork.</p>
                    </div>

                    <div class="info-box">
                        <h3>Future Vision</h3>
                        <p>Our vision is to grow into a trusted digital marketplace for unique and original art.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>