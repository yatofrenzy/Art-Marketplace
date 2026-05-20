<%@ page import="com.artmarketplace.model.TopProduct" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%

List<TopProduct> topProducts =
(List<TopProduct>) request.getAttribute("topProducts");

List<Double> monthlySales =
(List<Double>) request.getAttribute("monthlySales");

String selectedYear = request.getParameter("year");

if(selectedYear == null){

    selectedYear = "2026";
}

%>

<!DOCTYPE html>
<html>

<head>

    <title>Admin Dashboard</title>

    <!-- GOOGLE FONT -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

    <!-- FONT AWESOME -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- CSS -->
    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/css/dashboard.css?v=10">

    <!-- CHART JS -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

</head>

<body>

<div class="dashboard">

    <!-- SIDEBAR -->
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

            <!-- TOTAL REVENUE -->
            <div class="card">

                <div>

                    <h4>Total Revenue</h4>

                    <h2>Rs ${totalRevenue}</h2>

                    <span class="up">Live Revenue</span>

                </div>

                <div class="icon">

                    <i class="fa-solid fa-dollar-sign"></i>

                </div>

            </div>

            <!-- TOTAL ORDERS -->
            <div class="card">

                <div>

                    <h4>Total Orders</h4>

                    <h2>${totalOrders}</h2>

                    <span class="up">Orders Processed</span>

                </div>

                <div class="icon">

                    <i class="fa-solid fa-cart-shopping"></i>

                </div>

            </div>

            <!-- TOTAL CUSTOMERS -->
            <div class="card">

                <div>

                    <h4>Total Customers</h4>

                    <h2>${totalCustomers}</h2>

                    <span class="up">Registered Users</span>

                </div>

                <div class="icon">

                    <i class="fa-solid fa-users"></i>

                </div>

            </div>

        </div>

        <!-- SALES CHART -->
        <div class="chart-section">

            <div class="chart-header">

                <h3>Sales Analytics</h3>

                <form method="get" id="yearForm">

                    <select name="year"
                            onchange="document.getElementById('yearForm').submit()">

                        <option value="2026"
                            <%= "2026".equals(selectedYear) ? "selected" : "" %>>
                            2026
                        </option>

                        <option value="2025"
                            <%= "2025".equals(selectedYear) ? "selected" : "" %>>
                            2025
                        </option>

                        <option value="2024"
                            <%= "2024".equals(selectedYear) ? "selected" : "" %>>
                            2024
                        </option>

                    </select>

                </form>

            </div>

            <div class="chart-card">

                <canvas id="salesChart"></canvas>

            </div>

        </div>

        <!-- TOP PRODUCTS -->
        <div class="products-section">

            <h3>Top Selling Products</h3>

            <div class="product-grid">

            <%

            if(topProducts != null && !topProducts.isEmpty()){

                for(TopProduct product : topProducts){

            %>

                <div class="product-card">

                    <img src="${pageContext.request.contextPath}/<%= product.getImage() %>"
                         alt="Artwork">

                    <h4><%= product.getName() %></h4>

                    <p>Rs <%= product.getPrice() %></p>

                </div>

            <%

                }

            } else {

            %>

                <p>No top selling products found.</p>

            <%

            }

            %>

            </div>

        </div>

    </main>

</div>

<!-- CHART SCRIPT -->
<script>

const ctx = document.getElementById('salesChart').getContext('2d');

new Chart(ctx, {

    type: 'line',

    data: {

        labels: [
            'Jan',
            'Feb',
            'Mar',
            'Apr',
            'May',
            'Jun',
            'Jul',
            'Aug',
            'Sep',
            'Oct',
            'Nov',
            'Dec'
        ],

        datasets: [{

            label: 'Monthly Sales',

            data: <%= monthlySales %>,

            borderColor: '#52d6c5',

            backgroundColor: 'rgba(82,214,197,0.20)',

            borderWidth: 3,

            fill: true,

            tension: 0.45,

            pointRadius: 4,

            pointHoverRadius: 6

        }]
    },

    options: {

        responsive: true,

        maintainAspectRatio: false,

        plugins: {

            legend: {

                display: true
            }

        },

        scales: {

            x: {

                grid: {

                    display: false
                }

            },

            y: {

                beginAtZero: true,

                ticks: {

                    callback: function(value) {

                        return 'Rs ' + value;

                    }

                }

            }

        }

    }

});

</script>

<!-- JS -->
<script src="${pageContext.request.contextPath}/js/ui.js"></script>

</body>

</html>