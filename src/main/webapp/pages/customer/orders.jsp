<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Orders | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>
<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero">
            <h1>My Orders</h1>
            <p>View your order history and payment status.</p>
        </section>

        <h2 class="section-title">Order History</h2>

        <div class="table-card">
            <table>
                <tr>
                    <th>Order ID</th>
                    <th>Artwork</th>
                    <th>Total</th>
                    <th>Payment</th>
                    <th>Status</th>
                </tr>
                <tr>
                    <td>#1001</td>
                    <td>Cherry Blossom</td>
                    <td>Rs. 5000</td>
                    <td>Paid</td>
                    <td><span class="badge">Completed</span></td>
                </tr>
                <tr>
                    <td>#1002</td>
                    <td>Picasso Lady</td>
                    <td>Rs. 4200</td>
                    <td>Pending</td>
                    <td><span class="badge">Processing</span></td>
                </tr>
            </table>
        </div>
    </main>
</div>
</body>
</html>