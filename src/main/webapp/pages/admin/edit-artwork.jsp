<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.dao.CategoryDAO" %>
<%@ page import="com.artmarketplace.model.Artwork" %>
<%@ page import="com.artmarketplace.model.Category" %>
<%@ page import="com.artmarketplace.model.User" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Admin edit artwork view. Pre-fills an artwork form using the model supplied by ArtworkServlet. --%>
<%
    // Verify admin session before allowing artwork edits.
    User admin = (User) session.getAttribute("user");

    if (admin == null || !"admin".equalsIgnoreCase(admin.getRole())) {
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
        return;
    }

    // ArtworkServlet places the selected artwork into request scope before forwarding here.
    Artwork artwork = (Artwork) request.getAttribute("artwork");

    if (artwork == null) {
        response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
        return;
    }

    // Categories are loaded to populate the edit form dropdown.
    CategoryDAO categoryDAO = new CategoryDAO();
    List<Category> categories = categoryDAO.getAllCategories();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Artwork</title>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/dashboard.css?v=15">

    <style>
        .edit-page {
            padding: 45px;
            background: #f4f7fb;
            min-height: 100vh;
        }

        .edit-card {
            background: #ffffff;
            border-radius: 28px;
            padding: 34px;
            box-shadow: 0 22px 55px rgba(15, 23, 42, 0.08);
            max-width: 750px;
        }

        h1 {
            font-size: 52px;
            font-weight: 800;
            color: #111827;
            margin-bottom: 30px;
        }

        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            font-weight: 800;
            margin-bottom: 8px;
        }

        .form-group input,
        .form-group textarea,
        .form-group select {
            width: 100%;
            padding: 14px 15px;
            border-radius: 14px;
            border: 1px solid #d8dee8;
            outline: none;
            font-weight: 600;
        }

        .form-group textarea {
            min-height: 110px;
            resize: vertical;
        }

        .preview-img {
            width: 220px;
            height: 220px;
            object-fit: cover;
            border-radius: 18px;
            margin-bottom: 15px;
        }

        .update-btn {
            border: none;
            padding: 14px 24px;
            border-radius: 16px;
            background: #2ec6d3;
            color: #061012;
            font-weight: 900;
            cursor: pointer;
        }

        .cancel-btn {
            display: inline-block;
            padding: 14px 24px;
            border-radius: 16px;
            background: #111827;
            color: white;
            font-weight: 900;
            text-decoration: none;
            margin-left: 10px;
        }
    </style>
</head>

<body>

<div class="dashboard">

    <jsp:include page="/pages/common/sidebar.jsp">
        <jsp:param name="active" value="artworks"/>
    </jsp:include>

    <main class="main-content edit-page">

        <h1>Edit Artwork</h1>

        <div class="edit-card">

            <img class="preview-img"
                 src="${pageContext.request.contextPath}/<%= artwork.getImagePath() %>"
                 alt="<%= artwork.getTitle() %>">

            <form action="${pageContext.request.contextPath}/admin/artwork"
                  method="post"
                  enctype="multipart/form-data">

                <input type="hidden" name="action" value="update">
                <input type="hidden" name="artworkId" value="<%= artwork.getArtworkId() %>">
                <input type="hidden" name="oldImagePath" value="<%= artwork.getImagePath() %>">

                <div class="form-group">
                    <label>Artwork Title</label>
                    <input type="text" name="title" value="<%= artwork.getTitle() %>" required>
                </div>

                <div class="form-group">
                    <label>Description</label>
                    <textarea name="description" required><%= artwork.getDescription() %></textarea>
                </div>

                <div class="form-group">
                    <label>Price</label>
                    <input type="number" step="0.01" name="price" value="<%= artwork.getPrice() %>" required>
                </div>

                <div class="form-group">
                    <label>Category</label>
                    <select name="categoryId" required>
                        <% for(Category category : categories) { %>
                            <option value="<%= category.getCategoryId() %>"
                                <%= category.getCategoryId() == artwork.getCategoryId() ? "selected" : "" %>>
                                <%= category.getCategoryName() %>
                            </option>
                        <% } %>
                    </select>
                </div>

                <div class="form-group">
                    <label>Change Image</label>
                    <input type="file" name="imageFile" accept="image/*">
                </div>

                <button type="submit" class="update-btn">Update Artwork</button>

                <a href="${pageContext.request.contextPath}/pages/admin/artwork-admin.jsp"
                   class="cancel-btn">
                   Cancel
                </a>
            </form>
        </div>

    </main>

</div>

</body>
</html>
