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

@WebServlet({"/admin/artwork","/artworks"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize       = 1024 * 1024 * 10,
    maxRequestSize    = 1024 * 1024 * 15
)
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
            request.setAttribute("artworks", artworkDAO.getAllArtworks());
            request.getRequestDispatcher("/pages/customer/artworks.jsp").forward(request, response);
            return;
        }

        User user = (User) request.getSession().getAttribute("user");

        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
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

            boolean deleted = artworkDAO.deleteArtwork(artworkId);

            if (deleted) {
                response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?deleted=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?deleteError=true");
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        User user = (User) request.getSession().getAttribute("user");

        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        String categoryIdText = request.getParameter("categoryId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceText = request.getParameter("price");
        String oldImagePath = request.getParameter("oldImagePath");

        boolean isUpdate = "update".equals(action);

        if (categoryIdText == null || categoryIdText.trim().isEmpty()
                || title == null || title.trim().isEmpty()
                || description == null || description.trim().isEmpty()
                || priceText == null || priceText.trim().isEmpty()) {

            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
            return;
        }

        double price;

        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
            return;
        }

        if (price <= 0) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
            return;
        }

        int categoryId = Integer.parseInt(categoryIdText);
        String resolvedImagePath = oldImagePath;

        Part filePart = request.getPart("imageFile");

        if (filePart != null && filePart.getSize() > 0) {
            String submittedName = filePart.getSubmittedFileName();
            String ext = submittedName.substring(submittedName.lastIndexOf(".") + 1).toLowerCase();

            if (!ext.equals("jpg") && !ext.equals("jpeg")
                    && !ext.equals("png") && !ext.equals("gif")) {
                response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
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
        }

        if (!isUpdate && (resolvedImagePath == null || resolvedImagePath.trim().isEmpty())) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
            return;
        }

        Artwork artwork = new Artwork();
        artwork.setCategoryId(categoryId);
        artwork.setTitle(title.trim());
        artwork.setDescription(description.trim());
        artwork.setPrice(price);
        artwork.setImagePath(resolvedImagePath);

        if (isUpdate) {
            int artworkId = Integer.parseInt(request.getParameter("artworkId"));
            artwork.setArtworkId(artworkId);
            artworkDAO.updateArtwork(artwork);
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?updated=true");
        } else {
            artworkDAO.addArtwork(artwork);
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?success=true");
        }
    }
}