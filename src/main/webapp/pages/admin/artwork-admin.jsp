<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

    <title>Artworks</title>

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
        <jsp:param name="active" value="artworks"/>
    </jsp:include>

    <main class="main-content">

        <div class="topbar">
            <h1>Artworks</h1>
        </div>

        <div class="product-grid">

            <div class="product-card">
                <img src="https://via.placeholder.com/200" alt="">
                <h4>Mountain Painting</h4>
                <p>Rs 3000</p>
            </div>

            <div class="product-card">
                <img src="https://via.placeholder.com/200" alt="">
                <h4>Nature Art</h4>
                <p>Rs 2500</p>
            </div>
            
            <div class="product-card">
                <img src="https://via.placeholder.com/200" alt="">
                <h4>Picaso Art</h4>
                <p>Rs 25000</p>
            </div>
            
            <div class="product-card">
                <img src="https://via.placeholder.com/200" alt="">
                <h4>Modern Art</h4>
                <p>Rs 5000</p>
            </div>

        </div>

    </main>

</div>

</body>
</html>