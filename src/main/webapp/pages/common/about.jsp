<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Informational About page for visitors and customers. --%>
<!DOCTYPE html>
<html>
<head>
    <title>About | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>
<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>About Art Marketplace</h1>
            <p>A digital space where artists and customers connect through creativity.</p>
        </section>

        <h2 class="section-title">Our Purpose</h2>
        <div class="grid grid-3">
            <div class="card"><h3>Support Artists</h3><p>We help artists showcase their work to a wider audience.</p></div>
            <div class="card"><h3>Simple Buying</h3><p>Customers can browse and buy artwork in a clean online platform.</p></div>
            <div class="card"><h3>Creative Community</h3><p>Our system encourages art discovery, collection, and appreciation.</p></div>
        </div>
    </main>
</div>
<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Mode</button>
<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<%@ include file="/pages/common/footer.jsp" %>

</body>
</body>
</html>
