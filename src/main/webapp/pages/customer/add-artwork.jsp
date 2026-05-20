<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Customer-facing artwork submission page for uploading new artwork details. --%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Artwork | Art Marketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>

<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>Submit Your Artwork</h1>
            <p>Upload your artwork to the marketplace. It will be reviewed before going live.</p>
        </section>

        <h2 class="section-title">Artwork Details</h2>

        <div class="form-card">
            <%-- enctype="multipart/form-data" is required so the servlet can read the uploaded image Part. --%>
            <form action="${pageContext.request.contextPath}/artist/add-artwork"
                  method="post"
                  enctype="multipart/form-data">

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
                              placeholder="Describe your artwork..."></textarea>
                </div>

                <div class="form-group">
                    <label>Price (Rs.)</label>
                    <input type="number" name="price" placeholder="Enter price" min="1" required>
                </div>

                <%-- FILE INPUT instead of text path --%>
                <div class="form-group">
                    <label>Upload Image</label>
                    <input type="file" name="imageFile" accept="image/*" required>
                </div>

                <%-- Status is auto-set to Pending, no need to show it to artist --%>

                <div class="btn-row">
                    <button type="submit" class="btn btn-primary">Submit Artwork</button>
                    <a href="${pageContext.request.contextPath}/pages/customer/home.jsp"
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

</body>
</html>
