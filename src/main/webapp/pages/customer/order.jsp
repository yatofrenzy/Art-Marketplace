<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Orders</title>
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
        <h1>My Orders</h1>

        <div class="card">
            <h3>Order #101</h3>
            <p>Total Amount: Rs. 3500</p>
            <p>Status: Completed</p>
            <p>Payment Status: Paid</p>
        </div>

        <div class="card">
            <h3>Order #102</h3>
            <p>Total Amount: Rs. 1500</p>
            <p>Status: Pending</p>
            <p>Payment Status: Pending</p>
        </div>
    </div>

</body>
</html>