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
 * Servlet responsible for uploading artwork image
 * and storing artwork details into database.
 */
@WebServlet(urlPatterns = { "/addArtwork" })

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,   // 2MB
    maxFileSize = 1024 * 1024 * 10,        // 10MB
    maxRequestSize = 1024 * 1024 * 50      // 50MB
)

public class AddArtworkServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Handles artwork upload request.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)

            throws ServletException, IOException {

        try {

            /* =====================================================
               RECEIVING FORM DATA
            ===================================================== */

            String title = request.getParameter("title");

            String description =
                    request.getParameter("description");

            double price =
                    Double.parseDouble(
                            request.getParameter("price"));

            int categoryId =
                    Integer.parseInt(
                            request.getParameter("categoryId"));


            /* =====================================================
               RECEIVING IMAGE FILE
            ===================================================== */

            Part image = request.getPart("image");


            /* =====================================================
               EXTRACTING IMAGE FILE NAME
            ===================================================== */

            String imageName =
                    image.getSubmittedFileName();


            /* =====================================================
               UPLOAD FOLDER
            ===================================================== */

            String uploadFolder =
                    "resources/uploaded-artworks";


            /* =====================================================
               MAIN PROJECT PATH
            ===================================================== */

            String mainFolder =
                    request.getServletContext().getRealPath("");


            /* =====================================================
               FILE ACCESS PATH
            ===================================================== */

            String fileAccessPath =
                    uploadFolder + "/" + imageName;


            /* =====================================================
               FULL FILE PATH
            ===================================================== */

            String stringFilePath =
                    mainFolder + "/" + fileAccessPath;


            /* =====================================================
               CREATE FOLDER IF NOT EXISTS
            ===================================================== */

            File fileSaveDir =
                    new File(mainFolder + "/" + uploadFolder);

            if (!fileSaveDir.exists()) {

                fileSaveDir.mkdirs();
            }


            /* =====================================================
               WRITING IMAGE FILE
            ===================================================== */

            image.write(stringFilePath);


            /* =====================================================
               CREATE ARTWORK OBJECT
            ===================================================== */

            Artwork artwork = new Artwork();

            artwork.setTitle(title);

            artwork.setDescription(description);

            artwork.setPrice(price);

            artwork.setCategoryId(categoryId);

            artwork.setImagePath(fileAccessPath);


            /* =====================================================
               INSERT INTO DATABASE
            ===================================================== */

            ArtworkDAO artworkDAO = new ArtworkDAO();

            boolean result =
                    artworkDAO.addArtwork(artwork);


            /* =====================================================
               SUCCESS / FAILURE
            ===================================================== */

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

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/admin/artwork-admin.jsp?error=true");
        }
    }
}