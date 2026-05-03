<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Analytics Admin</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

<link rel="stylesheet"
href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/dashboard.css?v=1">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/analytics.css?v=1">

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>

<div class="dashboard">

    <jsp:include page="/pages/common/sidebar.jsp">
        <jsp:param name="active" value="analytics"/>
    </jsp:include>

    <main class="main-content">

        <div class="topbar">
            <h1>Sales Analytic</h1>

            <div class="sort-box">
                <span>Sort by</span>
                <select>
                    <option>Jul 2023</option>
                    <option>Aug 2023</option>
                    <option>Sep 2023</option>
                </select>
            </div>
        </div>

        <div class="summary-cards">

            <div class="card">
                <p>Income</p>
                <h2>23,262.00</h2>
                <span class="up">+0.65%</span>
            </div>

            <div class="card">
                <p>Expenses</p>
                <h2>11,135.00</h2>
                <span class="down">-0.65%</span>
            </div>

        </div>

        <div class="chart-card">
            <canvas id="salesChart"></canvas>
        </div>

    </main>

</div>

<script>
const ctx = document.getElementById('salesChart').getContext('2d');

new Chart(ctx, {
    type: 'line',
    data: {
        labels: ['22 Jul', '23 Jul', '24 Jul', '25 Jul', '26 Jul', '27 Jul', '28 Jul', '29 Jul'],
        datasets: [{
            data: [5000, 28000, 12000, 18000, 52000, 30000, 12000, 48000],
            borderColor: '#52d6c5',
            backgroundColor: 'rgba(82,214,197,0.20)',
            fill: true,
            tension: 0.45,
            pointRadius: 0
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                display: false
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
                        return value / 1000 + 'k';
                    }
                }
            }
        }
    }
});
</script>

</body>
</html>
