<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Checkout | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>
<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>Checkout</h1>
            <p>Complete your order by selecting your payment method and confirming your details.</p>
        </section>

        <h2 class="section-title">Payment Details</h2>

        <div class="form-card">
            <form action="#" method="post">
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" placeholder="Enter your name">
                </div>

                <div class="form-group">
                    <label>Delivery Address</label>
                    <textarea placeholder="Enter your delivery address"></textarea>
                </div>

                <div class="form-group">
                    <label>Payment Method</label>
                    <select>
                        <option>Cash on Delivery</option>
                        <option>eSewa</option>
                        <option>Khalti</option>
                        <option>Card</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Place Order</button>
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