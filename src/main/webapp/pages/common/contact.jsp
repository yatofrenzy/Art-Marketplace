<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contact Us</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
</head>
<body>

<div class="main-page">
    <%@ include file="/pages/common/navbar.jsp" %>

    <div class="page-content">
        <div class="page-card">
            <div class="page-header">
                <h1>Contact Us</h1>
                <p>Send your message, suggestion, or support request to our team.</p>
            </div>

            <div class="page-body">
                <form class="contact-form">
                    <div class="two-column">
                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" class="form-control" placeholder="Enter your name">
                        </div>

                        <div class="form-group">
                            <label>Email Address</label>
                            <input type="email" class="form-control" placeholder="Enter your email">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Subject</label>
                        <input type="text" class="form-control" placeholder="Enter subject">
                    </div>

                    <div class="form-group">
                        <label>Message</label>
                        <textarea class="form-control" placeholder="Write your message here"></textarea>
                    </div>

                    <button type="submit" class="btn">Send Message</button>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>