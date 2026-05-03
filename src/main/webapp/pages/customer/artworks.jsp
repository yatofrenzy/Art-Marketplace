<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.artmarketplace.dao.ArtworkDAO" %>
<%@ page import="com.artmarketplace.model.Artwork" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    ArtworkDAO dao = new ArtworkDAO();
    List<Artwork> artworks = dao.getAllArtworks();

    // 🔹 Only show Approved artworks
    List<Artwork> approvedList = new ArrayList<>();
    for (Artwork art : artworks) {
        if ("Approved".equalsIgnoreCase(art.getStatus())) {
            approvedList.add(art);
        }
    }
    artworks = approvedList;

    String search = request.getParameter("search");

    if (search != null && !search.trim().isEmpty()) {
        String keyword = search.trim().toLowerCase();
        List<Artwork> filteredArtworks = new ArrayList<>();

        for (Artwork art : artworks) {
            String title = art.getTitle() != null ? art.getTitle().toLowerCase() : "";
            String description = art.getDescription() != null ? art.getDescription().toLowerCase() : "";

            if (title.contains(keyword) || description.contains(keyword)) {
                filteredArtworks.add(art);
            }
        }
        artworks = filteredArtworks;
    }
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
                        <strong><%= artworks != null ? artworks.size() : 0 %></strong>
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
                <option value="6">Nature Art</option> <!-- ✅ FIXED -->
            </select>
        </div>

        <!-- ART GRID -->
        <div class="art-grid">

            <%
                if (artworks != null && !artworks.isEmpty()) {
                    for (Artwork art : artworks) {

                        String imagePath = art.getImagePath();

                        if (imagePath == null || imagePath.trim().isEmpty()) {
                            imagePath = "resources/images/Nature-Art/default.jpg";
                        }

                        String fullImagePath = request.getContextPath() + "/" + imagePath;

                        String title = art.getTitle() != null ? art.getTitle() : "";
                        String description = art.getDescription() != null ? art.getDescription() : "";

                        String safeTitle = title.replace("'", "\\'");
                        String safeDescription = description.replace("'", "\\'");
            %>

            <div class="art-card" data-category="<%= art.getCategoryId() %>">

                <img src="<%= fullImagePath %>" 
                     alt="<%= title %>" 
                     class="art-image">

                <div class="art-info">
                    <h3><%= title %></h3>
                    <p><%= description %></p>
                    <div class="price">Rs. <%= art.getPrice() %></div>

                    <button class="btn btn-primary"
                        onclick="openArtModal(
                            '<%= safeTitle %>',
                            'Rs. <%= art.getPrice() %>',
                            '<%= fullImagePath %>',
                            '<%= safeDescription %>',
                            '<%= art.getArtworkId() %>'
                        )">
                        View Details
                    </button>
                </div>

            </div>

            <%
                    }
                } else {
            %>

            <p>No artworks available.</p>

            <%
                }
            %>

        </div>

    </main>
</div>

<!-- MODAL -->
<div class="modal" id="artModal">
    <div class="modal-content">
        <button class="close-modal" onclick="closeArtModal()">×</button>

        <img id="modalImage" alt="Artwork image">

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

<%@ include file="/pages/common/footer.jsp" %>

</body>
</html>