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
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)

public class AddArtworkServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)

            throws ServletException, IOException {

        try {

            /* =========================================
               FORM DATA
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
               DAO
            ========================================= */

            ArtworkDAO artworkDAO =
                    new ArtworkDAO();


            /* =========================================
               CATEGORY NAME
            ========================================= */

            String category =
                    artworkDAO.getCategoryNameById(categoryId);

            category =
                    category.replaceAll("\\s+", "_");


            /* =========================================
               IMAGE FILE
            ========================================= */

            Part image =
                    request.getPart("imageFile");


            /* =========================================
               IMAGE NAME
            ========================================= */

            String originalImageName =
                    image.getSubmittedFileName();

            String imageName =
                    System.currentTimeMillis()
                    + "_"
                    + originalImageName;


            /* =========================================
               INTERNAL UPLOAD FOLDER
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
               CREATE DIRECTORY
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
               ARTWORK OBJECT
            ========================================= */

            Artwork artwork =
                    new Artwork();

            artwork.setTitle(title);

            artwork.setDescription(description);

            artwork.setPrice(price);

            artwork.setCategoryId(categoryId);

            artwork.setImagePath(fileAccessPath);


            /* =========================================
               DATABASE INSERT
            ========================================= */

            boolean result =
                    artworkDAO.addArtwork(artwork);


            /* =========================================
               REDIRECT
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

            e.printStackTrace();

            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/admin/artwork-admin.jsp?error=true");
        }
    }
}