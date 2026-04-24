<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Cart | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>
<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>Your Cart</h1>
            <p>Review selected artworks and adjust quantities before checkout.</p>
        </section>

        <h2 class="section-title">Cart Items</h2>

        <div class="table-card">
            <table>
                <tr>
                    <th>Artwork</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Total</th>
                </tr>

                <tr>
                    <td>Cherry Blossom</td>
                    <td>
                        <div class="qty-control">
                            <button onclick="decreaseQty('qty1')">−</button>
                            <span id="qty1">1</span>
                            <button onclick="increaseQty('qty1')">+</button>
                        </div>
                    </td>
                    <td>Rs. 5000</td>
                    <td>Rs. 5000</td>
                </tr>

                <tr>
                    <td>Picasso Lady</td>
                    <td>
                        <div class="qty-control">
                            <button onclick="decreaseQty('qty2')">−</button>
                            <span id="qty2">1</span>
                            <button onclick="increaseQty('qty2')">+</button>
                        </div>
                    </td>
                    <td>Rs. 4200</td>
                    <td>Rs. 4200</td>
                </tr>
            </table>

            <div class="btn-row">
                <a href="${pageContext.request.contextPath}/pages/customer/artworks.jsp" class="btn btn-secondary">Continue Shopping</a>
                <a href="${pageContext.request.contextPath}/pages/customer/checkout.jsp" class="btn btn-primary">Proceed to Checkout</a>
            </div>
        </div>
    </main>
</div>

<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Mode</button>
<script src="${pageContext.request.contextPath}/js/ui.js"></script>

</body>
</html>