<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Contact page view with a simple enquiry form and shared navigation/footer includes. --%>
<!DOCTYPE html>
<html>
<head>
    <title>Contact | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>
<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>Contact Us</h1>
            <p>Send your questions, support requests, or feedback to our team.</p>
        </section>

        <h2 class="section-title">Send Message</h2>

        <div class="form-card">
            <%-- Static contact form placeholder; no servlet action is currently connected. --%>
            <form action="#" method="post">
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" placeholder="Enter your name">
                </div>

                <div class="form-group">
                    <label>Email</label>
                    <input type="email" placeholder="Enter your email">
                </div>

                <div class="form-group">
                    <label>Message</label>
                    <textarea placeholder="Write your message"></textarea>
                </div>

                <button type="submit" class="btn btn-primary">Send Message</button>
            </form>
        </div>
    </main>
</div>
<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Mode</button>
<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<%@ include file="/pages/common/footer.jsp" %>

</body>
</body>
</html>
