<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.dao.ArtworkDAO" %>
<%@ page import="com.artmarketplace.dao.CategoryDAO" %>
<%@ page import="com.artmarketplace.model.Artwork" %>
<%@ page import="com.artmarketplace.model.Category" %>
<%@ page import="com.artmarketplace.model.User" %>

<%
    User admin = (User) session.getAttribute("user");

    if (admin == null || !"admin".equalsIgnoreCase(admin.getRole())) {

        response.sendRedirect(
                request.getContextPath()
                + "/pages/common/login.jsp");

        return;
    }

    ArtworkDAO dao = new ArtworkDAO();

    CategoryDAO categoryDAO =
            new CategoryDAO();

    List<Artwork> artworks =
            dao.getAllArtworks();

    List<Category> categories =
            categoryDAO.getAllCategories();
%>

<!DOCTYPE html>
<html>

<head>

    <title>Artwork</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/css/dashboard.css?v=15">

    <style>

        .card-actions {

            display: flex;
            justify-content: center;
            gap: 14px;
            margin-top: 14px;
        }

        .edit-btn,
        .delete-btn {

            width: 42px;
            height: 42px;
            border-radius: 14px;
            display: flex;
            align-items: center;
            justify-content: center;
            text-decoration: none;
            font-size: 17px;
        }

        .edit-btn {

            background: #e6fffa;
            color: #0f766e;
        }

        .delete-btn {

            background: #fff1f2;
            color: #be123c;
        }

    </style>

</head>

<body>

<div class="dashboard">

    <jsp:include page="/pages/common/sidebar.jsp">
        <jsp:param name="active" value="artworks"/>
    </jsp:include>

    <main class="main-content">

        <div class="topbar">

            <h1>Artworks</h1>

            <div class="topbar-actions">

                <button class="add-artwork-btn"
                        onclick="openArtworkModal()">

                    <i class="fa-solid fa-plus"></i>
                    Add Artwork

                </button>

                <select class="artwork-filter">

                    <option value="all">
                        All Categories
                    </option>

                    <% for(Category category : categories) { %>

                        <option value="<%= category.getCategoryId() %>">

                            <%= category.getCategoryName() %>

                        </option>

                    <% } %>

                </select>

            </div>

        </div>

        <% if(request.getParameter("success") != null){ %>

            <div class="success-message">
                Artwork uploaded successfully.
            </div>

        <% } %>

        <% if(request.getParameter("updated") != null){ %>

            <div class="success-message">
                Artwork updated successfully.
            </div>

        <% } %>

        <% if(request.getParameter("deleted") != null){ %>

            <div class="success-message">
                Artwork deleted successfully.
            </div>

        <% } %>

        <%
            String error =
                    request.getParameter("error");

            if ("deletefailed".equals(error)) {
        %>

            <div class="error-message">
                Failed to delete artwork.
            </div>

        <%
            }
        %>

        <% if(request.getParameter("error") != null){ %>

            <div class="error-message">
                Artwork operation failed.
            </div>

        <% } %>

        <h3 class="section-title">
            Top Selling Products
        </h3>

        <!-- ===================================================== -->
        <!-- PRODUCT GRID -->
        <!-- ===================================================== -->

        <div class="product-grid">

            <%
                if (artworks != null && !artworks.isEmpty()) {

                    for (Artwork art : artworks) {

                        String imagePath =
                                art.getImagePath();

                        if (imagePath == null
                                || imagePath.trim().isEmpty()) {

                            imagePath =
                                    "resources/images/default.jpg";
                        }

                        String fullImagePath =
                                request.getContextPath()
                                + "/"
                                + imagePath;
            %>

            <div class="product-card"
                 data-category="<%= art.getCategoryId() %>">

                <img src="<%= fullImagePath %>"
                     alt="<%= art.getTitle() %>">

                <div class="product-content">

                    <h4>
                        <%= art.getTitle() %>
                    </h4>

                    <div class="art-description">

                        <%= art.getDescription() %>

                    </div>

                    <p>
                        Rs <%= art.getPrice() %>
                    </p>

                    <div class="card-actions">

                        <a class="edit-btn"
                           href="${pageContext.request.contextPath}/admin/artwork?action=edit&id=<%= art.getArtworkId() %>">

                            <i class="fa-solid fa-pen-to-square"></i>

                        </a>

                        <a class="delete-btn"
                           href="${pageContext.request.contextPath}/admin/artwork?action=delete&id=<%= art.getArtworkId() %>"
                           onclick="return confirm('Are you sure you want to delete this artwork?');">

                            <i class="fa-solid fa-trash"></i>

                        </a>

                    </div>

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

<!-- ===================================================== -->
<!-- MODAL -->
<!-- ===================================================== -->

<div id="artworkModal"
     class="artwork-modal">

    <div class="artwork-modal-content">

        <div class="modal-header">

            <h2>Add New Artwork</h2>

            <span class="close-modal"
                  onclick="closeArtworkModal()">

                &times;

            </span>

        </div>

        <form action="${pageContext.request.contextPath}/addArtwork"
              method="post"
              enctype="multipart/form-data"
              class="artwork-form">

            <input type="hidden"
                   name="action"
                   value="add">

            <div class="form-group">

                <label>Artwork Title</label>

                <input type="text"
                       name="title"
                       placeholder="Enter artwork title"
                       required>

            </div>

            <div class="form-group">

                <label>Description</label>

                <textarea name="description"
                          placeholder="Artwork description"
                          required></textarea>

            </div>

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

                    <select name="categoryId"
                            required>

                        <option value="">
                            Select Category
                        </option>

                        <% for(Category category : categories) { %>

                            <option value="<%= category.getCategoryId() %>">

                                <%= category.getCategoryName() %>

                            </option>

                        <% } %>

                    </select>

                </div>

            </div>

            <div class="form-group">

                <label>Artwork Image</label>

                <input type="file"
                       name="imageFile"
                       accept="image/*"
                       required>

            </div>

            <button type="submit"
                    class="upload-btn">

                Upload Artwork

            </button>

        </form>

    </div>

</div>

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

    const modal =
            document.getElementById("artworkModal");

    if (event.target === modal) {

        modal.style.display = "none";
    }
}

</script>

<script>

const filterDropdown =
        document.querySelector('.artwork-filter');

const artworkCards =
        document.querySelectorAll('.product-card');

filterDropdown.addEventListener('change', function () {

    const selectedCategory = this.value;

    artworkCards.forEach(card => {

        const cardCategory =
                card.getAttribute('data-category');

        if (selectedCategory === 'all'
                || selectedCategory === cardCategory) {

            card.style.display = 'block';

        } else {

            card.style.display = 'none';
        }

    });

});

</script>

</body>
</html>