<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Font Awesome Icons -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- CSS -->
    <link rel="stylesheet"
      type="text/css"
      href="${pageContext.request.contextPath}/css/dashboard.css?v=10">

    <!-- Chart JS -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>

<div class="dashboard">

	<jsp:include page="/pages/common/sidebar.jsp">
    <jsp:param name="active" value="dashboard"/>
	</jsp:include>

    <!-- MAIN CONTENT -->
    <main class="main-content">

        <!-- TOPBAR -->
        <div class="topbar">

            <div>
                <h1>Overview</h1>
                <p>Welcome back Admin 👋</p>
            </div>

            <div class="search-box">
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" placeholder="Search...">
            </div>

        </div>

        <!-- STAT CARDS -->
        <div class="cards">

            <div class="card">
                <div>
                    <h4>Total Revenue</h4>
                    <h2>Rs 82,650</h2>
                    <span>+11% this month </span>
                </div>

                <div class="icon green">
                    <i class="fa-solid fa-dollar-sign"></i>
                </div>
            </div>

            <div class="card">
                <div>
                    <h4>Total Orders</h4>
                    <h2>1645</h2>
                    <span>+8% this month</span>
                </div>

                <div class="icon blue">
                    <i class="fa-solid fa-cart-shopping"></i>
                </div>
            </div>

            <div class="card">
                <div>
                    <h4>Total Customers</h4>
                    <h2>1462</h2>
                    <span>+5% this month</span>
                </div>

                <div class="icon orange">
                    <i class="fa-solid fa-users"></i>
                </div>
            </div>

        </div>

        <!-- CHART -->
        <div class="chart-section">

            <div class="chart-header">
                <h3>Sales Analytics</h3>

                <select>
                    <option>July 2023</option>
                    <option>August 2023</option>
                </select>
            </div>

            <canvas id="salesChart"></canvas>

        </div>

        <!-- PRODUCTS -->
        <div class="products-section">

            <h3>Top Selling Products</h3>

            <div class="product-grid">

                <div class="product-card">
   	 			<img src="${pageContext.request.contextPath}/resources/images/wukong.jpg" alt="Wukong">
    			<h4>Wukong</h4>
    			<p>Rs 7100</p>
			</div>

			<div class="product-card">
   				<img src="${pageContext.request.contextPath}/resources/images/picaso 2.jpg" alt="Picaso Devil">
    			<h4>Picaso Devil</h4>
    			<p>Rs 6200</p>
			</div>

			<div class="product-card">
    			<img src="${pageContext.request.contextPath}/resources/images/picaso 1.jpg" alt="Picaso Lady">
    			<h4>Picaso Lady</h4>
    			<p>Rs 4200</p>
			</div>

			<div class="product-card">
   		 		<img src="${pageContext.request.contextPath}/resources/images/cherryblossom.jpg" alt="cherryblossom">
    			<h4>Cherryblossom</h4>
    			<p>Rs 5000</p>
			</div>

            </div>

        </div>

    </main>

</div>

<!-- CHART SCRIPT -->
<script>

    const ctx = document.getElementById('salesChart');

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['22 Jul', '23 Jul', '24 Jul', '25 Jul', '26 Jul', '27 Jul', '28 Jul'],
            datasets: [{
                label: 'Sales',
                data: [12000, 42000, 18000, 38000, 52000, 22000, 48000],
                borderColor: '#52d6c5',
                backgroundColor: 'rgba(82,214,197,0.2)',
                fill: true,
                tension: 0.4
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    display: false
                }
            }
        }
    });

</script>

<script src="${pageContext.request.contextPath}/js/ui.js"></script>


</body>
</body>
</html>