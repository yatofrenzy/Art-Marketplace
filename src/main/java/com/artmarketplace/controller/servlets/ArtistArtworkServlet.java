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
    fileSizeThreshold = 1024 * 1024,      // 1 MB — keep in memory below this
    maxFileSize       = 1024 * 1024 * 10, // 10 MB max per file
    maxRequestSize    = 1024 * 1024 * 15  // 15 MB max total request
)
public class ArtistArtworkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ArtworkDAO artworkDAO;

    @Override
    public void init() {
        artworkDAO = new ArtworkDAO();
    }

    // GET → show the add-artwork form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/customer/add-artwork.jsp")
               .forward(request, response);
    }

    // POST → handle form submission with file upload
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Check login
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }
        request.getRequestDispatcher("/pages/customer/add-artwork.jsp")
        .forward(request, response);
}

        // 2. Read text fields
        int    categoryId  = Integer.parseInt(request.getParameter("categoryId"));
        String title       = request.getParameter("title");
        String description = request.getParameter("description");
        double price       = Double.parseDouble(request.getParameter("price"));

        // 3. Handle the uploaded image file
        Part   filePart   = request.getPart("imageFile");
        String fileName   = extractFileName(filePart);  // get original filename

        // 4. Save image to webapp/resources/images/artworks/
        //    getServletContext().getRealPath() gives the absolute path on disk
        String uploadFolder = getServletContext().getRealPath("")
                              + File.separator + "resources"
                              + File.separator + "images"
                              + File.separator + "artworks";

        File uploadDir = new File(uploadFolder);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // create folder if it doesn't exist
        }

        // Make filename unique to avoid overwriting: timestamp + original name
        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        filePart.write(uploadFolder + File.separator + uniqueFileName);

        // 5. Store relative path (used in <img src="..."> in JSP)
        String imagePath = "resources/images/artworks/" + uniqueFileName;

        // 6. Build Artwork object — status defaults to "Pending" for artist submissions
        Artwork artwork = new Artwork();
        artwork.setUserId(user.getUserId());
        artwork.setCategoryId(categoryId);
        artwork.setTitle(title);
        artwork.setDescription(description);
        artwork.setPrice(price);
        artwork.setImagePath(imagePath);
        artwork.setStatus("Pending"); // always Pending for customer/artist submissions

        // 7. Save to database
        boolean success = artworkDAO.addArtwork(artwork);

        // 8. Redirect with result message
        if (success) {
            response.sendRedirect(request.getContextPath()
                + "/pages/customer/add-artwork.jsp?success=true");
        } else {
            response.sendRedirect(request.getContextPath()
                + "/pages/customer/add-artwork.jsp?error=true");
        }
    }

    // Helper: extract filename from the Part header
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                // filename="photo.jpg"  →  photo.jpg
                return token.substring(token.indexOf("=") + 2,
                                       token.length() - 1);
            }
        }
        return "unknown";
    }
}