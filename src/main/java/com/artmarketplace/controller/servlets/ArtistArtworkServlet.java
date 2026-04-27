package com.artmarketplace.controller.servlets;

import java.io.File;
import java.io.IOException;

import com.artmarketplace.dao.ArtworkDAO;
import com.artmarketplace.model.Artwork;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/artist/add-artwork")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize       = 1024 * 1024 * 10,
    maxRequestSize    = 1024 * 1024 * 15
)
public class ArtistArtworkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ArtworkDAO artworkDAO;

    @Override
    public void init() {
        artworkDAO = new ArtworkDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        request.getRequestDispatcher("/pages/customer/add-artwork.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        int    categoryId  = Integer.parseInt(request.getParameter("categoryId"));
        String title       = request.getParameter("title");
        String description = request.getParameter("description");
        double price       = Double.parseDouble(request.getParameter("price"));

        Part   filePart       = request.getPart("imageFile");
        String fileName       = extractFileName(filePart);
        String uploadFolder   = getServletContext().getRealPath("")
                                + File.separator + "resources"
                                + File.separator + "images"
                                + File.separator + "artworks";

        File uploadDir = new File(uploadFolder);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        filePart.write(uploadFolder + File.separator + uniqueFileName);

        String imagePath = "resources/images/artworks/" + uniqueFileName;

        Artwork artwork = new Artwork();
        artwork.setUserId(user.getUserId());
        artwork.setCategoryId(categoryId);
        artwork.setTitle(title);
        artwork.setDescription(description);
        artwork.setPrice(price);
        artwork.setImagePath(imagePath);
        artwork.setStatus("Pending");

        boolean success = artworkDAO.addArtwork(artwork);

        if (success) {
            response.sendRedirect(request.getContextPath()
                + "/pages/customer/add-artwork.jsp?success=true");
        } else {
            response.sendRedirect(request.getContextPath()
                + "/pages/customer/add-artwork.jsp?error=true");
        }
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2,
                                       token.length() - 1);
            }
        }
        return "unknown";
    }
}