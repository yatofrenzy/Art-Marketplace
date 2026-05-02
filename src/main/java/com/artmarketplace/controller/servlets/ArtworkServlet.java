package com.artmarketplace.controller.servlets;

import java.io.IOException;
import com.artmarketplace.dao.ArtworkDAO;
import com.artmarketplace.model.Artwork;
import com.artmarketplace.model.User;

import java.io.File;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet({"/admin/artwork","/artworks"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize       = 1024 * 1024 * 10,
    maxRequestSize    = 1024 * 1024 * 15
)
public class ArtworkServlet extends HttpServlet {{
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

        boolean isAdd = !"update".equals(action);

        if (categoryIdText == null || categoryIdText.trim().isEmpty()
                || title == null || title.trim().isEmpty()
                || description == null || description.trim().isEmpty()
                || priceText == null || priceText.trim().isEmpty()
                || status == null || status.trim().isEmpty()
                || (!isAdd && (imagePath == null || imagePath.trim().isEmpty()))) {

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

     // Resolve imagePath — file upload for add, text field for update
        String resolvedImagePath;
        if (isAdd) {
            Part filePart = request.getPart("imageFile");
            String submittedName = filePart.getSubmittedFileName();
            String ext = submittedName.substring(submittedName.lastIndexOf(".") + 1).toLowerCase();
            if (!ext.equals("jpg") && !ext.equals("jpeg")
             && !ext.equals("png") && !ext.equals("gif")) {
                request.setAttribute("error", "Invalid image format. Only jpg, jpeg, png, gif allowed.");
                request.getRequestDispatcher("/pages/admin/add-artwork.jsp").forward(request, response);
                return;
            }
            String uploadFolder = getServletContext().getRealPath("")
                                + File.separator + "resources"
                                + File.separator + "images"
                                + File.separator + "artworks";
            new File(uploadFolder).mkdirs();
            String uniqueFileName = System.currentTimeMillis() + "_" + submittedName;
            filePart.write(uploadFolder + File.separator + uniqueFileName);
            resolvedImagePath = "resources/images/artworks/" + uniqueFileName;
        } else {
            resolvedImagePath = imagePath.trim();
        }

        Artwork artwork = new Artwork();
        artwork.setUserId(user.getUserId());
        artwork.setCategoryId(categoryId);
        artwork.setTitle(title.trim());
        artwork.setDescription(description.trim());
        artwork.setPrice(price);
        artwork.setImagePath(resolvedImagePath);
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