<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart</title>
    <link rel="stylesheet" href="../../css/customer.css">
</head>
<body>

    <div class="navbar">
        <h2>Art Marketplace</h2>
        <div class="nav-links">
            <a href="home.jsp">Home</a>
            <a href="cart.jsp">Cart</a>
            <a href="order.jsp">Orders</a>
        </div>
    </div>

    <div class="container">
        <h1>My Cart</h1>

        <div class="card">
            <h3>Artwork Name</h3>
            <p>Price: Rs. 2000</p>
            <p>Quantity: 1</p>
            <button>Remove</button>
        </div>

        <div class="summary">
            <h3>Total: Rs. 2000</h3>
            <button>Proceed to Checkout</button>
        </div>
    </div>

</body>
</html>