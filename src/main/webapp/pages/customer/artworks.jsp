<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Artworks | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>
<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero">
            <h1>Artwork Gallery</h1>
            <p>Explore selected artworks from different styles and creative moods.</p>
        </section>

        <h2 class="section-title">Featured Artworks</h2>

        <div class="art-grid">
            <div class="art-card">
                <img src="${pageContext.request.contextPath}/resources/images/cherryblossom.jpg" alt="Cherry Blossom">
                <div class="art-info">
                    <h3>Cherry Blossom</h3>
                    <p>Soft floral artwork with peaceful colors.</p>
                    <div class="price">Rs. 5000</div>
                    <a href="${pageContext.request.contextPath}/pages/customer/cart.jsp" class="btn btn-primary">Add to Cart</a>
                </div>
            </div>

            <div class="art-card">
                <img src="${pageContext.request.contextPath}/resources/images/picaso 1.jpg" alt="Picasso Lady">
                <div class="art-info">
                    <h3>Picasso Lady</h3>
                    <p>Abstract inspired portrait with bold visual style.</p>
                    <div class="price">Rs. 4200</div>
                    <a href="${pageContext.request.contextPath}/pages/customer/cart.jsp" class="btn btn-primary">Add to Cart</a>
                </div>
            </div>

            <div class="art-card">
                <img src="${pageContext.request.contextPath}/resources/images/picaso 2.jpg" alt="Picasso Devil">
                <div class="art-info">
                    <h3>Picasso Devil</h3>
                    <p>Dramatic artwork inspired by mythical forms.</p>
                    <div class="price">Rs. 6200</div>
                    <a href="${pageContext.request.contextPath}/pages/customer/cart.jsp" class="btn btn-primary">Add to Cart</a>
                </div>
            </div>

            <div class="art-card">
                <img src="${pageContext.request.contextPath}/resources/images/wukong.jpg" alt="Wukong">
                <div class="art-info">
                    <h3>Wukong</h3>
                    <p>Ancient inspired scene with powerful storytelling.</p>
                    <div class="price">Rs. 7100</div>
                    <a href="${pageContext.request.contextPath}/pages/customer/cart.jsp" class="btn btn-primary">Add to Cart</a>
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>