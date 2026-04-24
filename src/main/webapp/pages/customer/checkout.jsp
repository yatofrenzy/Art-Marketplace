<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout</title>
</head>

<body>

<h2>Checkout</h2>

<form action="order" method="post">

    <label>Payment Method:</label>
    <select name="payment_method" required>
        <option value="COD">Cash on Delivery</option>
        <option value="eSewa">eSewa</option>
        <option value="Khalti">Khalti</option>
    </select>

    <br><br>

    <button type="submit">Place Order</button>

</form>

</body>
</html>