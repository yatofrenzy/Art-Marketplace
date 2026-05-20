package com.artmarketplace.controller.servlets;

import java.io.File;
import java.io.IOException;

import com.artmarketplace.dao.ArtworkDAO;
import com.artmarketplace.model.Artwork;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet responsible for uploading artwork images and storing artwork details.
 * Works as a Controller in the MVC architecture by reading form data from the view,
 * preparing an Artwork model, and calling the ArtworkDAO to insert the record.
 */
@WebServlet(urlPatterns = { "/addArtwork" })

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)

public class AddArtworkServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Handles artwork creation from the admin artwork form.
     * Reads multipart form fields, saves the uploaded image, stores the relative image path,
     * and redirects to the admin artwork page based on the database insert result.
     *
     * @param request  The multipart HTTP request containing artwork fields and image file.
     * @param response The HTTP response used to redirect after processing.
     * @throws ServletException If file upload parsing fails.
     * @throws IOException If image writing or redirecting fails.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)

            throws ServletException, IOException {

        try {

            /* =========================================
               FORM DATA FROM JSP VIEW
            ========================================= */

            String title =
                    request.getParameter("title");

            String description =
                    request.getParameter("description");

            double price =
                    Double.parseDouble(
                            request.getParameter("price"));

            int categoryId =
                    Integer.parseInt(
                            request.getParameter("categoryId"));


            /* =========================================
               DAO USED FOR DATABASE INSERT
            ========================================= */

            ArtworkDAO artworkDAO =
                    new ArtworkDAO();


            /* =========================================
               CATEGORY NAME USED FOR IMAGE FOLDER
            ========================================= */

            String category =
                    artworkDAO.getCategoryNameById(categoryId);

            category =
                    category.replaceAll("\\s+", "_");


            /* =========================================
               IMAGE FILE FROM MULTIPART REQUEST
            ========================================= */

            Part image =
                    request.getPart("imageFile");


            /* =========================================
               UNIQUE IMAGE NAME TO AVOID OVERWRITING FILES
            ========================================= */

            String originalImageName =
                    image.getSubmittedFileName();

            String imageName =
                    System.currentTimeMillis()
                    + "_"
                    + originalImageName;


            /* =========================================
               INTERNAL UPLOAD FOLDER STORED UNDER WEB RESOURCES
            ========================================= */

            String uploadFolder =
                    "resources/images/" + category;


            /* =========================================
               TOMCAT PROJECT PATH
            ========================================= */

            String mainFolder =
                    request.getServletContext()
                           .getRealPath("");


            /* =========================================
               CREATE DIRECTORY IF IT DOES NOT ALREADY EXIST
            ========================================= */

            File fileSaveDir =
                    new File(mainFolder
                    + File.separator
                    + uploadFolder);

            if (!fileSaveDir.exists()) {

                fileSaveDir.mkdirs();
            }


            /* =========================================
               DATABASE IMAGE PATH
            ========================================= */

            String fileAccessPath =
                    uploadFolder
                    + "/"
                    + imageName;


            /* =========================================
               FULL FILE PATH
            ========================================= */

            String stringFilePath =
                    mainFolder
                    + File.separator
                    + fileAccessPath;


            /* =========================================
               WRITE FILE
            ========================================= */

            image.write(stringFilePath);


            /* =========================================
               ARTWORK MODEL OBJECT SENT TO DAO LAYER
            ========================================= */

            Artwork artwork =
                    new Artwork();

            artwork.setTitle(title);

            artwork.setDescription(description);

            artwork.setPrice(price);

            artwork.setCategoryId(categoryId);

            artwork.setImagePath(fileAccessPath);


            /* =========================================
               DATABASE INSERT THROUGH DAO
            ========================================= */

            boolean result =
                    artworkDAO.addArtwork(artwork);


            /* =========================================
               REDIRECT BASED ON INSERT RESULT
            ========================================= */

            if (result) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/admin/artwork-admin.jsp?success=true");

            } else {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/admin/artwork-admin.jsp?error=true");
            }

        } catch (Exception e) {

            // Print exception details for debugging and return the admin to an error state.
            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/admin/artwork-admin.jsp?error=true");
        }
    }
}
