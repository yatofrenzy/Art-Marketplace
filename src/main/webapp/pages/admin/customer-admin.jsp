<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

    <title>Customers</title>

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
        <jsp:param name="active" value="customers"/>
    </jsp:include>

    <main class="main-content">

        <div class="topbar">
            <h1>Customers</h1>
        </div>

        <div class="table-container">

            <table>

                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Orders</th>
                    </tr>
                </thead>

                <tbody>

                    <tr>
                        <td>John Doe</td>
                        <td>john@gmail.com</td>
                        <td>5</td>
                    </tr>

                    <tr>
                        <td>Emily</td>
                        <td>emily@gmail.com</td>
                        <td>3</td>
                    </tr>

                    <tr>
                        <td>Sam Smith</td>
                        <td>sam@gmail.com</td>
                        <td>8</td>
                    </tr>
                    
                     <tr>
                        <td>Cena jon</td>
                        <td>cenan@gmail.com</td>
                        <td>5</td>
                    </tr>
                    
                     <tr>
                        <td>Ramos big</td>
                        <td>ramos@gmail.com</td>
                        <td>5</td>
                    </tr>

                </tbody>

            </table>

        </div>

    </main>

</div>

</body>
</html>