<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

    <title>Orders</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
          
     <!-- CSS -->
    <link rel="stylesheet"
      type="text/css"
      href="${pageContext.request.contextPath}/css/dashboard.css?v=10">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/dashboard.css">

</head>

<body>

<div class="dashboard">

    <jsp:include page="/pages/common/sidebar.jsp">
        <jsp:param name="active" value="orders"/>
    </jsp:include>

    <main class="main-content">

        <div class="topbar">
            <h1>Orders</h1>
        </div>

        <div class="table-container">

            <table>

                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Customer</th>
                        <th>Status</th>
                        <th>Total</th>
                    </tr>
                </thead>

                <tbody>

                    <tr>
                        <td>#1001</td>
                        <td>John Doe</td>
                        <td>Completed</td>
                        <td>Rs 5000</td>
                    </tr>

                    <tr>
                        <td>#1002</td>
                        <td>Sam Smith</td>
                        <td>Pending</td>
                        <td>Rs 3500</td>
                    </tr>

                    <tr>
                        <td>#1003</td>
                        <td>Emily</td>
                        <td>Delivered</td>
                        <td>Rs 4500</td>
                    </tr>
                    
                    <tr>
                        <td>#1004</td>
                        <td>Lotus</td>
                        <td>Delivered</td>
                        <td>Rs 2700</td>
                    </tr>
                    
                    <tr>
                        <td>#1005</td>
                        <td>Ramos</td>
                        <td>Pending</td>
                        <td>Rs 3500</td>
                    </tr>
                    
                    <tr>
                        <td>#1006</td>
                        <td>Cena</td>
                        <td>Completed</td>
                        <td>Rs 5500</td>
                    </tr>

                </tbody>

            </table>

        </div>

    </main>

</div>

</body>
</html>