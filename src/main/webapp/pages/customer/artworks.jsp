<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.dao.ArtworkDAO" %>
<%@ page import="com.artmarketplace.model.Artwork" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    ArtworkDAO dao = new ArtworkDAO();
    List<Artwork> artworks = dao.getApprovedArtworks();
%>

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

        <!-- HEADER -->
        <section class="hero gallery-header">
            <div class="gallery-top">
                <div>
                    <h1>Curated Gallery</h1>
                    <p>Explore artworks added by artists.</p>
                </div>

                <div class="gallery-stats">
                    <div class="gallery-stat">
                        <strong><%= artworks.size() %></strong>
                        <span>Artworks</span>
                    </div>
                </div>
            </div>
        </section>

        <!-- FILTER -->
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
                <option value="1">Painting</option>
                <option value="2">Sketch</option>
                <option value="3">Digital Art</option>
                <option value="4">Portrait</option>
                <option value="5">Nature Art</option>
            </select>
        </div>

        <!-- ART GRID -->
        <div class="art-grid">

            <% if (artworks != null && !artworks.isEmpty()) {
                for (Artwork art : artworks) {
            %>

            <div class="art-card" data-category="<%= art.getCategoryId() %>">

                <img src="${pageContext.request.contextPath}/<%= art.getImagePath() %>" alt="<%= art.getTitle() %>">

                <div class="art-info">
                    <h3><%= art.getTitle() %></h3>
                    <p><%= art.getDescription() %></p>
                    <div class="price">Rs. <%= art.getPrice() %></div>

                    <button class="btn btn-primary"
                        onclick="openArtModal(
                        '<%= art.getTitle() %>',
                        'Rs. <%= art.getPrice() %>',
                        '${pageContext.request.contextPath}/<%= art.getImagePath() %>',
                        '<%= art.getDescription() %>',
                        '<%= art.getArtworkId() %>'
                        )">
                        View Details
                    </button>
                </div>

            </div>

            <% } } else { %>

            <p>No artworks available.</p>

            <% } %>

        </div>

    </main>
</div>

<!-- MODAL -->
<div class="modal" id="artModal">
    <div class="modal-content">
        <button class="close-modal" onclick="closeArtModal()">×</button>

        <img id="modalImage">

        <div class="modal-info">
            <h2 id="modalTitle"></h2>
            <div class="price" id="modalPrice"></div>
            <p id="modalDescription"></p>

            <!-- ADD TO CART -->
            <form action="${pageContext.request.contextPath}/add-to-cart" method="post" onsubmit="showToast('Added to cart')">
                <input type="hidden" id="modalArtworkId" name="artworkId">
                <button type="submit" class="btn btn-secondary">Add to Cart</button>
            </form>

        </div>
    </div>
</div>

<!-- TOAST -->
<div id="toast" class="toast">Added to cart</div>

<!-- LOADER -->
<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Dark</button>

<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<%@ include file="/pages/common/footer.jsp" %>

</body>
</body>
</html>