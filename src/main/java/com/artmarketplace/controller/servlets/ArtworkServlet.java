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
 * Servlet controller for artwork management and browsing.
 *
 * <p>Handles two mapped routes:</p>
 * <ul>
 *   <li>{@code /artworks} — public customer view</li>
 *   <li>{@code /admin/artwork} — admin-only management panel</li>
 * </ul>
 *
 * <p>File uploads are supported with validation for type and size constraints.</p>
 *
 * @see ArtworkDAO
 * @see Artwork
 */
@WebServlet({"/admin/artwork", "/artworks"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize       = 1024 * 1024 * 10,
    maxRequestSize    = 1024 * 1024 * 15
)
public class ArtworkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /** Data Access Object used for all database operations on {@link Artwork} entities. */
    private ArtworkDAO artworkDAO;

    /**
     * Initializes the servlet and creates a shared {@link ArtworkDAO} instance.
     *
     * <p>Called once by the container when the servlet is first loaded.
     * The DAO is reused across all requests to avoid per-request instantiation.</p>
     */
    @Override
    public void init() {
        artworkDAO = new ArtworkDAO();
    }

    /**
     * Handles GET requests for artwork browsing and admin management actions.
     *
     * <p><b>Public route — {@code GET /artworks}:</b></p>
     * <ul>
     *   <li>Loads all artworks via {@link ArtworkDAO#getAllArtworks()}</li>
     *   <li>Forwards to {@code /pages/customer/artworks.jsp}</li>
     * </ul>
     *
     * <p><b>Admin route — {@code GET /admin/artwork} (requires role {@code "admin"}):</b></p>
     * <ul>
     *   <li>{@code action=add} — Forwards to {@code /pages/admin/add-artwork.jsp}</li>
     *   <li>{@code action=edit&id=X} — Loads artwork by ID and forwards to
     *       {@code /pages/admin/edit-artwork.jsp}</li>
     *   <li>{@code action=delete&id=X} — Deletes artwork by ID; redirects to
     *       {@code artwork-admin.jsp?deleted=true} on success or
     *       {@code artwork-admin.jsp?deleteError=true} on failure</li>
     *   <li>No {@code action} — Redirects to {@code /pages/admin/artwork-admin.jsp}</li>
     * </ul>
     *
     * <p>Unauthenticated or non-admin users are redirected to the login page.</p>
     *
     * @param request  the incoming {@link HttpServletRequest}; must carry a valid admin session
     *                 for the {@code /admin/artwork} route
     * @param response the {@link HttpServletResponse} used to forward or redirect
     * @throws ServletException if the JSP forward fails
     * @throws IOException      if the redirect or response write fails
     * @see ArtworkDAO#getAllArtworks()
     * @see ArtworkDAO#getArtworkById(int)
     * @see ArtworkDAO#deleteArtwork(int)
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
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

    /**
     * Handles POST requests for creating and updating artworks via the admin panel.
     *
     * <p>The operation is determined by the {@code action} form field:</p>
     * <ul>
     *   <li>{@code action=create} — Validates input, saves the uploaded image, and inserts
     *       a new {@link Artwork} via {@link ArtworkDAO#addArtwork(Artwork)};
     *       redirects to {@code artwork-admin.jsp?success=true}</li>
     *   <li>{@code action=update} — Validates input, optionally replaces the image, and
     *       updates the record via {@link ArtworkDAO#updateArtwork(Artwork)};
     *       redirects to {@code artwork-admin.jsp?updated=true}</li>
     * </ul>
     *
     * <p><b>Required form fields:</b></p>
     * <ul>
     *   <li>{@code categoryId} — Integer category identifier</li>
     *   <li>{@code title} — Artwork title (non-blank)</li>
     *   <li>{@code description} — Artwork description (non-blank)</li>
     *   <li>{@code price} — Positive decimal price value</li>
     *   <li>{@code imageFile} — Image upload; required for {@code create},
     *       optional for {@code update}</li>
     * </ul>
     *
     * <p><b>Update-only fields:</b></p>
     * <ul>
     *   <li>{@code artworkId} — ID of the record to update</li>
     *   <li>{@code oldImagePath} — Retained when no new image is uploaded</li>
     * </ul>
     *
     * <p><b>Image rules:</b> accepted extensions are {@code jpg}, {@code jpeg},
     * {@code png}, {@code gif}; max size 10 MB. Files are saved under
     * {@code resources/images/artworks/} with a timestamp prefix.</p>
     *
     * <p>All validation failures redirect to {@code artwork-admin.jsp?error=true}.
     * Unauthenticated or non-admin users are redirected to the login page.</p>
     *
     * @param request  the incoming {@link HttpServletRequest} carrying multipart form data
     *                 and the admin session
     * @param response the {@link HttpServletResponse} used to redirect after processing
     * @throws ServletException if multipart parsing fails
     * @throws IOException      if file write or redirect fails
     * @see ArtworkDAO#addArtwork(Artwork)
     * @see ArtworkDAO#updateArtwork(Artwork)
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        boolean isUpdate = "update".equals(action);

        User user = (User) request.getSession().getAttribute("user");
        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        String categoryIdText = request.getParameter("categoryId");
        String title          = request.getParameter("title");
        String description    = request.getParameter("description");
        String priceText      = request.getParameter("price");
        String oldImagePath   = request.getParameter("oldImagePath");

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