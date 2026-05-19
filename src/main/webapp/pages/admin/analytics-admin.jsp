<%@ page import="java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <title>Analytics Admin</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/dashboard.css?v=1">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/analytics.css?v=1">

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>

<div class="dashboard">

    <!-- Sidebar -->
    <jsp:include page="/pages/common/sidebar.jsp">
        <jsp:param name="active" value="analytics"/>
    </jsp:include>

    <!-- Main Content -->
    <main class="main-content">

        <!-- Topbar -->
        <div class="topbar">

            <h1>Sales Analytics</h1>

            <div class="sort-box">

                <span>Sort by</span>

                <select>
                    <option>2026</option>
                    <option>2025</option>
                    <option>2024</option>
                </select>

            </div>

        </div>

        <!-- Summary Cards -->
        <div class="summary-cards">

            <!-- Income -->
            <div class="card">

                <p>Income</p>

                <h2>
                    Rs ${income}
                </h2>

                <span class="up">
                    Live Database Data
                </span>

            </div>

            <!-- Expenses -->
            <div class="card">

                <p>Total Orders</p>

                <h2>
                    ${totalOrders}
                </h2>

                <span class="down">
                    Orders Processed
                </span>

            </div>

        </div>

        <!-- Chart -->
        <div class="chart-card">

            <canvas id="salesChart"></canvas>

        </div>

    </main>

</div>

<!-- JSP DATA -->
<%
@SuppressWarnings("unchecked")
List<Double> salesData =
        (List<Double>) request.getAttribute("salesData");
%>

<!-- Chart Script -->
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

            data: [

                <%

                if(salesData != null){

                    for(int i = 0; i < salesData.size(); i++){

                        out.print(salesData.get(i));

                        if(i != salesData.size() - 1){
                            out.print(",");
                        }
                    }

                }

                %>

            ],

            borderColor: '#52d6c5',

            backgroundColor: 'rgba(82,214,197,0.20)',

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

                    callback: function(value){
                        return 'Rs ' + value;
                    }

                }

            }

        }

    }

});

</script>

</body>
</html>