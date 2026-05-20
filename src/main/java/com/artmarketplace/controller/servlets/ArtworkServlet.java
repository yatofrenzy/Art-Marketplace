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

/**
 * Servlet responsible for artwork browsing and admin artwork management.
 * Acts as a Controller in the MVC architecture by routing artwork requests,
 * validating admin actions, handling uploads, and calling ArtworkDAO for database work.
 */
@WebServlet({"/admin/artwork","/artworks"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize       = 1024 * 1024 * 10,
    maxRequestSize    = 1024 * 1024 * 15
)
public class ArtworkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ArtworkDAO artworkDAO;

    /**
     * Initializes the DAO dependency once when the servlet is created.
     * The DAO is reused for artwork database operations during request handling.
     */
    @Override
    public void init() {
        artworkDAO = new ArtworkDAO();
    }

    /**
     * Handles artwork listing, edit loading, and delete requests.
     *
     * @param request  The HTTP request containing servlet path and optional action/id parameters.
     * @param response The HTTP response used for forwards and redirects.
     * @throws ServletException If forwarding to a JSP fails.
     * @throws IOException If redirecting fails.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Use the servlet path to distinguish public artwork browsing from admin management.
        String path = request.getServletPath();

        if (path.equals("/artworks")) {
            // Load all artworks from the database for the customer artwork listing page.
            request.setAttribute("artworks", artworkDAO.getAllArtworks());
            request.getRequestDispatcher("/pages/customer/artworks.jsp").forward(request, response);
            return;
        }

        // Admin artwork actions require a logged-in admin session.
        User user = (User) request.getSession().getAttribute("user");

        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        // Default to list behavior when no explicit action is supplied.
        if (action == null) {
            action = "list";
        }

        if (action.equals("add")) {
            // Forward to the artwork add view.
            request.getRequestDispatcher("/pages/admin/add-artwork.jsp").forward(request, response);

        } else if (action.equals("edit")) {
            // Retrieve the selected artwork and pass it to the edit JSP.
            int artworkId = Integer.parseInt(request.getParameter("id"));
            Artwork artwork = artworkDAO.getArtworkById(artworkId);
            request.setAttribute("artwork", artwork);
            request.getRequestDispatcher("/pages/admin/edit-artwork.jsp").forward(request, response);

        } else if (action.equals("delete")) {
            // Delete the selected artwork through the DAO and redirect with a status flag.
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

    /**
     * Handles artwork create and update form submissions.
     * Validates required fields, processes optional image uploads, builds the Artwork model,
     * and calls DAO insert/update methods.
     *
     * @param request  The multipart HTTP request containing artwork form values.
     * @param response The HTTP response used for status redirects.
     * @throws ServletException If multipart parsing fails.
     * @throws IOException If file writing or redirecting fails.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Action tells the servlet whether this form is creating or updating an artwork.
        String action = request.getParameter("action");

        // Only admins are allowed to create or edit artwork records from this controller.
        User user = (User) request.getSession().getAttribute("user");

        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        // Read form data submitted from the admin artwork JSP.
        String categoryIdText = request.getParameter("categoryId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceText = request.getParameter("price");
        String oldImagePath = request.getParameter("oldImagePath");

        boolean isUpdate = "update".equals(action);

        // Validate required fields before converting values or saving to the database.
        if (categoryIdText == null || categoryIdText.trim().isEmpty()
                || title == null || title.trim().isEmpty()
                || description == null || description.trim().isEmpty()
                || priceText == null || priceText.trim().isEmpty()) {

            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
            return;
        }

        double price;

        try {
            // Convert price text to a numeric value for the model and database.
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
            return;
        }

        // Price must be positive for a valid artwork listing.
        if (price <= 0) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
            return;
        }

        // Keep the existing image path during updates unless a new image is uploaded.
        int categoryId = Integer.parseInt(categoryIdText);
        String resolvedImagePath = oldImagePath;

        Part filePart = request.getPart("imageFile");

        if (filePart != null && filePart.getSize() > 0) {
            // Validate file extension before writing the upload to the server folder.
            String submittedName = filePart.getSubmittedFileName();
            String ext = submittedName.substring(submittedName.lastIndexOf(".") + 1).toLowerCase();

            if (!ext.equals("jpg") && !ext.equals("jpeg")
                    && !ext.equals("png") && !ext.equals("gif")) {
                response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
                return;
            }

            // Create the upload directory inside the deployed web application if needed.
            String uploadFolder = getServletContext().getRealPath("")
                    + File.separator + "resources"
                    + File.separator + "images"
                    + File.separator + "artworks";

            new File(uploadFolder).mkdirs();

            // Store a timestamped filename so uploaded files do not overwrite each other.
            String uniqueFileName = System.currentTimeMillis() + "_" + submittedName;
            filePart.write(uploadFolder + File.separator + uniqueFileName);

            resolvedImagePath = "resources/images/artworks/" + uniqueFileName;
        }

        // New artwork records require an image path before database insertion.
        if (!isUpdate && (resolvedImagePath == null || resolvedImagePath.trim().isEmpty())) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?error=true");
            return;
        }

        // Build the model object that will be passed to the DAO layer.
        Artwork artwork = new Artwork();
        artwork.setCategoryId(categoryId);
        artwork.setTitle(title.trim());
        artwork.setDescription(description.trim());
        artwork.setPrice(price);
        artwork.setImagePath(resolvedImagePath);

        if (isUpdate) {
            // Update existing artwork record using its primary key.
            int artworkId = Integer.parseInt(request.getParameter("artworkId"));
            artwork.setArtworkId(artworkId);
            artworkDAO.updateArtwork(artwork);
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?updated=true");
        } else {
            // Insert a new artwork record into the database.
            artworkDAO.addArtwork(artwork);
            response.sendRedirect(request.getContextPath() + "/pages/admin/artwork-admin.jsp?success=true");
        }
    }
}
