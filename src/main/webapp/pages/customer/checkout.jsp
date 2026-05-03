<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.dao.CartDAO" %>
<%@ page import="com.artmarketplace.model.CartItem" %>
<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
        return;
    }

    CartDAO cartDAO = new CartDAO();
    List<CartItem> cartItems = cartDAO.getCartItems(user.getUserId());

    double grandTotal = 0;
    for (CartItem item : cartItems) {
        grandTotal += item.getTotal();
    }
%>

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
            <p>Confirm your order and choose payment method.</p>
        </section>

        <h2 class="section-title">Order Summary</h2>

        <% if(request.getParameter("error") != null) { %>
            <div class="error-message">Checkout failed. Please make sure your cart is not empty.</div>
        <% } %>

        <div class="table-card">
            <table>
                <tr>
                    <th>Artwork</th>
                    <th>Qty</th>
                    <th>Price</th>
                    <th>Total</th>
                </tr>

                <% if(cartItems != null && !cartItems.isEmpty()) {
                    for(CartItem item : cartItems) {
                %>
                <tr>
                    <td><%= item.getTitle() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td>Rs. <%= item.getPrice() %></td>
                    <td>Rs. <%= item.getTotal() %></td>
                </tr>
                <% } } else { %>
                <tr>
                    <td colspan="4">Your cart is empty.</td>
                </tr>
                <% } %>

                <tr>
                    <th colspan="3">Grand Total</th>
                    <th>Rs. <%= grandTotal %></th>
                </tr>
            </table>
        </div>

        <h2 class="section-title">Payment</h2>

        <div class="form-card">
            <form action="${pageContext.request.contextPath}/checkout" method="post">
                <div class="form-group">
                    <label>Payment Method</label>
                    <select name="paymentMethod" required>
                        <option value="">Choose payment method</option>
                        <option value="Cash on Delivery">Cash on Delivery</option>
                        <option value="eSewa">eSewa</option>
                        <option value="Khalti">Khalti</option>
                        <option value="Card">Card</option>
                    </select>
                </div>

                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">Place Order</button>
                    <a href="${pageContext.request.contextPath}/pages/customer/cart.jsp" class="btn btn-dark">Back to Cart</a>
                </div>
            </form>
        </div>
    </main>
</div>

<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Dark</button>
<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<%@ include file="/pages/common/footer.jsp" %>

</body>
</html>