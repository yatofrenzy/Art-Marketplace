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


            <h3>Top Selling Products</h3>

            <div class="product-grid">

                <div class="product-card">
   	 			<img src="${pageContext.request.contextPath}/resources/images/Digital_Art/La Robotte.png" alt="La Robotte">
    			<h4>La Robotte</h4>
    			<p>Rs 7100</p>
			</div>

			<div class="product-card">
   				<img src="${pageContext.request.contextPath}/resources/images/Nature-Art/Echoes in the Blue Forest.jpg" alt="Echoes in the Blue Forest">
    			<h4>Echoes in the Blue Forest</h4>
    			<p>Rs 6200</p>
			</div>

			<div class="product-card">
    			<img src="${pageContext.request.contextPath}/resources/images/Paintings/fox watercolor.png" alt="fox watercolor">
    			<h4>fox watercolor</h4>
    			<p>Rs 4200</p>
			</div>

			

        </div>

    </main>

</div>

</body>
</html>