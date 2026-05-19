<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.dao.ArtworkDAO" %>
<%@ page import="com.artmarketplace.model.Artwork" %>

<%
    ArtworkDAO dao = new ArtworkDAO();
    List<Artwork> artworks = dao.getAllArtworks();
%>

<!DOCTYPE html>
<html>

<head>

    <title>Artwork</title>

    <!-- GOOGLE FONT -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

    <!-- FONT AWESOME -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- DASHBOARD CSS -->
    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/css/dashboard.css?v=15">

</head>

<body>

<div class="dashboard">

    <!-- SIDEBAR -->
    <jsp:include page="/pages/common/sidebar.jsp">
        <jsp:param name="active" value="artworks"/>
    </jsp:include>

    <!-- MAIN CONTENT -->
    <main class="main-content">

        <!-- TOPBAR -->
        <div class="topbar">

            <h1>Artworks</h1>

            <div class="topbar-actions">
            
            
                            <button class="add-artwork-btn"
                        onclick="openArtworkModal()">

                    <i class="fa-solid fa-plus"></i>
                    Add Artwork

                </button>

                <select class="artwork-filter">

                    <option value="all">All Categories</option>
                    <option value="3">Digital Art</option>
                    <option value="6">Nature Art</option>
                    <option value="1">Paintings</option>
                    <option value="7">Portrait</option>
                    <option value="2">Sketch</option>

                </select>



            </div>

        </div>

        <!-- SUCCESS MESSAGE -->
        <% if(request.getParameter("success") != null){ %>

            <div class="success-message">
                Artwork uploaded successfully.
            </div>

        <% } %>

        <!-- ERROR MESSAGE -->
        <% if(request.getParameter("error") != null){ %>

            <div class="error-message">
                Failed to upload artwork.
            </div>

        <% } %>

        <!-- SECTION TITLE -->
        <h3 class="section-title">Top Selling Products</h3>

        <!-- PRODUCT GRID -->
        <div class="product-grid">

            <%
                if (artworks != null && !artworks.isEmpty()) {

                    for (Artwork art : artworks) {

                        String imagePath = art.getImagePath();

                        if(imagePath == null || imagePath.trim().isEmpty()) {

                            imagePath = "resources/images/default.jpg";
                        }

                        String fullImagePath =
                                request.getContextPath() + "/" + imagePath;
            %>

            <div class="product-card" data-category="<%= art.getCategoryId() %>">

                <img src="<%= fullImagePath %>"
                     alt="<%= art.getTitle() %>">

                <div class="product-content">

                    <h4><%= art.getTitle() %></h4>

                    <div class="art-description">
                        <%= art.getDescription() %>
                    </div>

                    <p>Rs <%= art.getPrice() %></p>

                </div>

            </div>

            <%
                    }
                } else {
            %>

                <p>No artwork found.</p>

            <%
                }
            %>

        </div>

    </main>

</div>

<!-- ========================= -->
<!-- ADD ARTWORK MODAL -->
<!-- ========================= -->

<div id="artworkModal" class="artwork-modal">

    <div class="artwork-modal-content">

        <!-- MODAL HEADER -->
        <div class="modal-header">

            <h2>Add New Artwork</h2>

            <span class="close-modal"
                  onclick="closeArtworkModal()">

                &times;

            </span>

        </div>

        <!-- FORM -->
        <form action="${pageContext.request.contextPath}/addArtwork"
              method="post"
              enctype="multipart/form-data"
              class="artwork-form">

            <!-- TITLE -->
            <div class="form-group">

                <label>Artwork Title</label>

                <input type="text"
                       name="title"
                       placeholder="Enter artwork title"
                       required>

            </div>

            <!-- DESCRIPTION -->
            <div class="form-group">

                <label>Description</label>

                <textarea name="description"
                          placeholder="Artwork description"
                          required></textarea>

            </div>

            <!-- PRICE + CATEGORY -->
            <div class="form-row">

                <div class="form-group">

                    <label>Price</label>

                    <input type="number"
                           step="0.01"
                           name="price"
                           placeholder="Price"
                           required>

                </div>

                <div class="form-group">

                    <label>Category</label>

                    <select name="categoryId" required>

                        <option value="">Select Category</option>

                        <option value="3">Digital_Art</option>

                        <option value="6">Nature_Art</option>

                        <option value="1">Paintings</option>

                        <option value="7">Portrait</option>

                        <option value="2">Sketch</option>

                    </select>

                </div>

            </div>

            <!-- IMAGE -->
            <div class="form-group">

                <label>Artwork Image</label>

                <input type="file"
                       name="image"
                       accept="image/*"
                       required>

            </div>

            <!-- BUTTON -->
            <button type="submit" class="upload-btn">

                Upload Artwork

            </button>

        </form>

    </div>

</div>

<!-- JAVASCRIPT -->
<script>

function openArtworkModal() {

    document.getElementById("artworkModal")
            .style.display = "flex";
}

function closeArtworkModal() {

    document.getElementById("artworkModal")
            .style.display = "none";
}

window.onclick = function(event) {

    const modal = document.getElementById("artworkModal");

    if (event.target === modal) {

        modal.style.display = "none";
    }
}

</script>


<script>

    const filterDropdown = document.querySelector('.artwork-filter');
    const artworkCards = document.querySelectorAll('.product-card');

    filterDropdown.addEventListener('change', function () {

        const selectedCategory = this.value;

        artworkCards.forEach(card => {

            const cardCategory = card.getAttribute('data-category');

            if (selectedCategory === 'all' || selectedCategory === cardCategory) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }

        });

    });

</script>

</body>
</html>