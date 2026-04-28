<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Artwork | Admin | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>

<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>Add New Artwork</h1>
            <p>Add a new artwork directly to the marketplace with full control over status.</p>
        </section>

        <h2 class="section-title">Artwork Details</h2>

        <div class="form-card">
            <form action="${pageContext.request.contextPath}/admin/artwork"
                  method="post">

                <input type="hidden" name="action" value="add">

                <div class="form-group">
                    <label>Category</label>
                    <select name="categoryId" required>
                        <option value="">-- Select Category --</option>
                        <option value="1">Painting</option>
                        <option value="2">Sketch</option>
                        <option value="3">Digital Art</option>
                        <option value="4">Portrait</option>
                        <option value="5">Nature Art</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Artwork Title</label>
                    <input type="text" name="title" placeholder="Enter artwork title" required>
                </div>

                <div class="form-group">
                    <label>Description</label>
                    <textarea name="description" rows="4"
                              placeholder="Describe the artwork..."></textarea>
                </div>

                <div class="form-group">
                    <label>Price (Rs.)</label>
                    <input type="number" name="price" placeholder="Enter price" min="1" required>
                </div>

                <div class="form-group">
                    <label>Image Path</label>
                    <input type="text" name="imagePath" placeholder="e.g. resources/images/artworks/example.jpg" required>
                </div>

                <div class="form-group">
                    <label>Status</label>
                    <select name="status" required>
                        <option value="Approved">Approved</option>
                        <option value="Pending">Pending</option>
                        <option value="Rejected">Rejected</option>
                    </select>
                </div>

                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">Add Artwork</button>
                    <a href="${pageContext.request.contextPath}/admin/artwork"
                       class="btn btn-dark">Cancel</a>
                </div>

            </form>
        </div>
    </main>
</div>

<div class="loader" id="loader">
    <div class="loader-circle"></div>
</div>

<button class="dark-toggle" onclick="toggleDarkMode()">🌙 Dark</button>
<script src="${pageContext.request.contextPath}/js/ui.js"></script>

<%@ include file="/pages/common/footer.jsp" %>

</body>
</html>