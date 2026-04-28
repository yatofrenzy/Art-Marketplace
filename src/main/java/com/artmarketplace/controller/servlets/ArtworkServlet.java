package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.ArtworkDAO;
import com.artmarketplace.model.Artwork;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/admin/artwork","/artworks"})
public class ArtworkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ArtworkDAO artworkDAO;

    @Override
    public void init() {
        artworkDAO = new ArtworkDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String path = request.getServletPath();
    	
    	if (path.equals("/artworks")) {
    		request.setAttribute("artworks",artworkDAO.getApprovedArtworks());
    		request.getRequestDispatcher("/pages/customer/artworks.jsp").forward(request,response);
    		return;
    	}

        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        if (action.equals("add")) {
            request.getRequestDispatcher("/pages/admin/add-artwork.jsp").forward(request, response);

        } else if (action.equals("edit")) {
            int artworkId = Integer.parseInt(request.getParameter("id"));
            Artwork artwork = artworkDAO.getArtworkById(artworkId);
            request.setAttribute("artwork", artwork);
            request.getRequestDispatcher("/pages/admin/edit-artwork.jsp").forward(request, response);

        } else if (action.equals("delete")) {
            int artworkId = Integer.parseInt(request.getParameter("id"));
            artworkDAO.deleteArtwork(artworkId);
            response.sendRedirect(request.getContextPath() + "/admin/artwork");

        } else {
            request.setAttribute("artworks", artworkDAO.getAllArtworks());
            request.getRequestDispatcher("/pages/admin/artwork-list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        String categoryIdText = request.getParameter("categoryId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceText = request.getParameter("price");
        String imagePath = request.getParameter("imagePath");
        String status = request.getParameter("status");

        String targetPage = "update".equals(action)
                ? "/pages/admin/edit-artwork.jsp"
                : "/pages/admin/add-artwork.jsp";

        if (categoryIdText == null || categoryIdText.trim().isEmpty()
                || title == null || title.trim().isEmpty()
                || description == null || description.trim().isEmpty()
                || priceText == null || priceText.trim().isEmpty()
                || imagePath == null || imagePath.trim().isEmpty()
                || status == null || status.trim().isEmpty()) {

            request.setAttribute("error", "All artwork fields are required.");
            request.getRequestDispatcher(targetPage).forward(request, response);
            return;
        }

        double price;

        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Price must be a valid number.");
            request.getRequestDispatcher(targetPage).forward(request, response);
            return;
        }

        if (price <= 0) {
            request.setAttribute("error", "Price must be greater than 0.");
            request.getRequestDispatcher(targetPage).forward(request, response);
            return;
        }

        int categoryId = Integer.parseInt(categoryIdText);

        Artwork artwork = new Artwork();
        artwork.setUserId(user.getUserId());
        artwork.setCategoryId(categoryId);
        artwork.setTitle(title.trim());
        artwork.setDescription(description.trim());
        artwork.setPrice(price);
        artwork.setImagePath(imagePath.trim());
        artwork.setStatus(status);

        if ("update".equals(action)) {
            int artworkId = Integer.parseInt(request.getParameter("artworkId"));
            artwork.setArtworkId(artworkId);
            artworkDAO.updateArtwork(artwork);
        } else {
            artworkDAO.addArtwork(artwork);
        }

        response.sendRedirect(request.getContextPath() + "/admin/artwork");
    }
}