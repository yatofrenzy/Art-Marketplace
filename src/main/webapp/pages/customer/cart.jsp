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
%>

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
                    <th>Action</th>
                </tr>

                <%
                    for (CartItem item : cartItems) {
                        grandTotal += item.getTotal();
                %>
                <tr>
                    <td><%= item.getTitle() %></td>

                    <td>
                        <div class="qty-control">
                            <form action="${pageContext.request.contextPath}/update-cart" method="post">
                                <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                                <input type="hidden" name="quantity" value="<%= item.getQuantity() %>">
                                <input type="hidden" name="action" value="decrease">
                                <button type="submit">−</button>
                            </form>

                            <span><%= item.getQuantity() %></span>

                            <form action="${pageContext.request.contextPath}/update-cart" method="post">
                                <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                                <input type="hidden" name="quantity" value="<%= item.getQuantity() %>">
                                <input type="hidden" name="action" value="increase">
                                <button type="submit">+</button>
                            </form>
                        </div>
                    </td>

                    <td>Rs. <%= item.getPrice() %></td>
                    <td>Rs. <%= item.getTotal() %></td>

                    <td>
                        <form action="${pageContext.request.contextPath}/update-cart" method="post">
                            <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                            <input type="hidden" name="quantity" value="<%= item.getQuantity() %>">
                            <input type="hidden" name="action" value="remove">
                            <button type="submit" class="btn btn-dark">Remove</button>
                        </form>
                    </td>
                </tr>
                <% } %>

                <tr>
                    <th colspan="3">Grand Total</th>
                    <th colspan="2">Rs. <%= grandTotal %></th>
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

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Dark</button>
<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<%@ include file="/pages/common/footer.jsp" %>

</body>
</body>
</html>