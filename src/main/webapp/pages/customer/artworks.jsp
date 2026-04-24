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

        <section class="hero gallery-header">
            <div class="gallery-top">
                <div>
                    <h1>Art Gallery</h1>
                    <p>Explore beautiful artworks across different creative styles.</p>
                </div>

                <div class="gallery-stats">
                    <div class="gallery-stat">
                        <strong>5</strong>
                        <span>Categories</span>
                    </div>
                    <div class="gallery-stat">
                        <strong>4</strong>
                        <span>Artworks</span>
                    </div>
                </div>
            </div>
        </section>

        <div style="margin: 30px 0 20px; display: flex; justify-content: flex-end;">
            <select id="categoryFilter" onchange="filterArtworks()" style="
                padding: 12px 18px;
                border-radius: 999px;
                border: 1px solid var(--border);
                background: var(--surface);
                color: var(--text);
                font-weight: 800;
                outline: none;
                box-shadow: var(--shadow);
            ">
                <option value="all">All Categories</option>
                <option value="painting">Painting</option>
                <option value="sketch">Sketch</option>
                <option value="digital">Digital Art</option>
                <option value="portrait">Portrait</option>
                <option value="nature">Nature Art</option>
            </select>
        </div>

        <div class="art-grid">

            <div class="art-card" data-category="painting">
                <img src="${pageContext.request.contextPath}/resources/images/cherryblossom.jpg" alt="Cherry Blossom">
                <div class="art-info">
                    <h3>Cherry Blossom</h3>
                    <p>Soft floral painting with peaceful colors.</p>
                    <div class="price">Rs. 5000</div>
                    <button class="btn btn-primary"
                        onclick="openArtModal('Cherry Blossom','Rs. 5000','${pageContext.request.contextPath}/resources/images/cherryblossom.jpg','A peaceful painting with floral beauty and calm composition.')">
                        View Details
                    </button>
                </div>
            </div>

            <div class="art-card" data-category="portrait">
                <img src="${pageContext.request.contextPath}/resources/images/picaso 1.jpg" alt="Picasso Lady">
                <div class="art-info">
                    <h3>Picasso Lady</h3>
                    <p>Abstract portrait artwork.</p>
                    <div class="price">Rs. 4200</div>
                    <button class="btn btn-primary"
                        onclick="openArtModal('Picasso Lady','Rs. 4200','${pageContext.request.contextPath}/resources/images/picaso 1.jpg','A modern abstract portrait with bold artistic expression.')">
                        View Details
                    </button>
                </div>
            </div>

            <div class="art-card" data-category="sketch">
                <img src="${pageContext.request.contextPath}/resources/images/picaso 2.jpg" alt="Picasso Devil">
                <div class="art-info">
                    <h3>Picasso Devil</h3>
                    <p>Sketch-style dramatic artwork.</p>
                    <div class="price">Rs. 6200</div>
                    <button class="btn btn-primary"
                        onclick="openArtModal('Picasso Devil','Rs. 6200','${pageContext.request.contextPath}/resources/images/picaso 2.jpg','A powerful sketch artwork with strong emotions and dark tone.')">
                        View Details
                    </button>
                </div>
            </div>

            <div class="art-card" data-category="digital">
                <img src="${pageContext.request.contextPath}/resources/images/wukong.jpg" alt="Wukong">
                <div class="art-info">
                    <h3>Wukong</h3>
                    <p>Digital artwork inspired by mythology.</p>
                    <div class="price">Rs. 7100</div>
                    <button class="btn btn-primary"
                        onclick="openArtModal('Wukong','Rs. 7100','${pageContext.request.contextPath}/resources/images/wukong.jpg','A digital artwork showing a mythological warrior with strong visual impact.')">
                        View Details
                    </button>
                </div>
            </div>

        </div>

    </main>
</div>

<div class="modal" id="artModal">
    <div class="modal-content">
        <button class="close-modal" onclick="closeArtModal()">×</button>
        <img id="modalImage" src="" alt="Artwork">
        <div class="modal-info">
            <h2 id="modalTitle"></h2>
            <div class="price" id="modalPrice"></div>
            <p id="modalDescription"></p>
            <a href="${pageContext.request.contextPath}/pages/customer/cart.jsp" class="btn btn-secondary">Add to Cart</a>
        </div>
    </div>
</div>

<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Dark</button>

<script src="${pageContext.request.contextPath}/js/ui.js"></script>

</body>
</html>