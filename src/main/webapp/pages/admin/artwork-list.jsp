<%@ page import="java.util.List" %>
<%@ page import="com.artmarketplace.model.Artwork" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    List<Artwork> artworks = (List<Artwork>) request.getAttribute("artworks");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Artworks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/art_marketplace.css">
</head>
<body>

<div class="main-layout">
    <%@ include file="/pages/common/navbar.jsp" %>

    <main class="container">
        <section class="hero compact-hero">
            <h1>Manage Artworks</h1>
            <p>Add, edit, delete, and approve artworks in the marketplace.</p>

            <div class="btn-row">
                <a href="${pageContext.request.contextPath}/admin/artwork?action=add" class="btn btn-primary">
                    Add New Artwork
                </a>
            </div>
        </section>

        <h2 class="section-title">Artwork List</h2>

        <div class="table-card">
            <table>
                <tr>
                    <th>ID</th>
                    <th>Image</th>
                    <th>Title</th>
                    <th>Category ID</th>
                    <th>Price</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>

                <%
                    if (artworks != null && !artworks.isEmpty()) {
                        for (Artwork art : artworks) {
                %>
                <tr>
                    <td><%= art.getArtworkId() %></td>
                    <td>
                        <img src="${pageContext.request.contextPath}/<%= art.getImagePath() %>"
                             style="width:80px;height:60px;object-fit:cover;border-radius:10px;">
                    </td>
                    <td><%= art.getTitle() %></td>
                    <td><%= art.getCategoryId() %></td>
                    <td>Rs. <%= art.getPrice() %></td>
                    <td><span class="badge"><%= art.getStatus() %></span></td>
                    <td>
                        <a class="btn btn-secondary"
                           href="${pageContext.request.contextPath}/admin/artwork?action=edit&id=<%= art.getArtworkId() %>">
                            Edit
                        </a>

                        <a class="btn btn-dark"
                           href="${pageContext.request.contextPath}/admin/artwork?action=delete&id=<%= art.getArtworkId() %>"
                           onclick="return confirm('Are you sure you want to delete this artwork?');">
                            Delete
                        </a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7">No artworks found.</td>
                </tr>
                <% } %>
            </table>
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