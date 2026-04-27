<%@ page import="com.artmarketplace.model.Artwork" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    Artwork artwork = (Artwork) request.getAttribute("artwork");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Artwork</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>

<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>Edit Artwork</h1>
            <p>Update artwork information and approval status.</p>
        </section>

        <h2 class="section-title">Update Details</h2>

        <div class="form-card">
            <% if (artwork != null) { %>

            <form action="${pageContext.request.contextPath}/admin/artwork" method="post">

                <input type="hidden" name="action" value="update">
                <input type="hidden" name="artworkId" value="<%= artwork.getArtworkId() %>">

                <div class="form-group">
                    <label>Category</label>
                    <select name="categoryId" required>
                        <option value="1" <%= artwork.getCategoryId() == 1 ? "selected" : "" %>>Painting</option>
                        <option value="2" <%= artwork.getCategoryId() == 2 ? "selected" : "" %>>Sketch</option>
                        <option value="3" <%= artwork.getCategoryId() == 3 ? "selected" : "" %>>Digital Art</option>
                        <option value="4" <%= artwork.getCategoryId() == 4 ? "selected" : "" %>>Portrait</option>
                        <option value="5" <%= artwork.getCategoryId() == 5 ? "selected" : "" %>>Nature Art</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Artwork Title</label>
                    <input type="text" name="title" value="<%= artwork.getTitle() %>" required>
                </div>

                <div class="form-group">
                    <label>Description</label>
                    <textarea name="description"><%= artwork.getDescription() %></textarea>
                </div>

                <div class="form-group">
                    <label>Price</label>
                    <input type="number" name="price" value="<%= artwork.getPrice() %>" min="1" required>
                </div>

                <div class="form-group">
                    <label>Image Path</label>
                    <input type="text" name="imagePath" value="<%= artwork.getImagePath() %>" required>
                </div>

                <div class="form-group">
                    <label>Status</label>
                    <select name="status" required>
                        <option value="Approved" <%= "Approved".equals(artwork.getStatus()) ? "selected" : "" %>>Approved</option>
                        <option value="Pending" <%= "Pending".equals(artwork.getStatus()) ? "selected" : "" %>>Pending</option>
                        <option value="Rejected" <%= "Rejected".equals(artwork.getStatus()) ? "selected" : "" %>>Rejected</option>
                    </select>
                </div>

                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">Update Artwork</button>
                    <a href="${pageContext.request.contextPath}/admin/artwork" class="btn btn-dark">Back</a>
                </div>

            </form>

            <% } else { %>
                <p>Artwork not found.</p>
                <a href="${pageContext.request.contextPath}/admin/artwork" class="btn btn-dark">Back</a>
            <% } %>
        </div>
    </main>
</div>

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