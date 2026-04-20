<%@ page import="java.util.*, com.artmarketplace.dao.ArtworkDAO, com.artmarketplace.model.Artwork" %>

<h2>Artwork</h2>

<%
ArtworkDAO dao = new ArtworkDAO();
List<Artwork> list = dao.getAllArtworks();
for (Artwork a : list) {
%>

<div>
    <img src="images/<%=a.getImagePath()%>" width="150"><br>
    <b><%=a.getTitle()%></b><br>
    $<%=a.getPrice()%><br>

    <a href="artwork-details.jsp?id=<%=a.getArtworkId()%>">View</a>
    <a href="CartServlet?action=add&id=<%=a.getArtworkId()%>">Add to Cart</a>
</div>

<hr>

<% } %>