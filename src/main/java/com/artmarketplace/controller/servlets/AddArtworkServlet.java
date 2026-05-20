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


            /* =====================================================
               DAO OBJECT
            ===================================================== */

            ArtworkDAO artworkDAO =
                    new ArtworkDAO();


            /* =====================================================
               GET CATEGORY NAME FROM DATABASE
            ===================================================== */

            String category =
                    artworkDAO.getCategoryNameById(categoryId);

            // Replace spaces with underscores
            category =
                    category.replaceAll("\\s+", "_");


            /* =====================================================
               RECEIVING IMAGE FILE
            ===================================================== */

            Part image =
                    request.getPart("imageFile");


            /* =====================================================
               ORIGINAL IMAGE NAME
            ===================================================== */

            String originalImageName =
                    image.getSubmittedFileName();


            /* =====================================================
               UNIQUE IMAGE NAME
            ===================================================== */

            String imageName =
                    System.currentTimeMillis()
                    + "_"
                    + originalImageName;


            /* =====================================================
               EXTERNAL ROOT FOLDER
            ===================================================== */

            String uploadRoot =
                    "D:/ArtMarketplaceUploads";


            /* =====================================================
               PHYSICAL CATEGORY FOLDER
            ===================================================== */

            String physicalUploadFolder =
                    uploadRoot
                    + File.separator
                    + category;


            /* =====================================================
               CREATE CATEGORY DIRECTORY
            ===================================================== */

            File fileSaveDir =
                    new File(physicalUploadFolder);

            if (!fileSaveDir.exists()) {

                fileSaveDir.mkdirs();
            }


            /* =====================================================
               DATABASE IMAGE PATH
            ===================================================== */

            String fileAccessPath =
                    "uploads/"
                    + category
                    + "/"
                    + imageName;


            /* =====================================================
               FULL PHYSICAL FILE PATH
            ===================================================== */

            String stringFilePath =
                    physicalUploadFolder
                    + File.separator
                    + imageName;


            /* =====================================================
               DEBUG LOGS
            ===================================================== */

            System.out.println("CATEGORY: " + category);

            System.out.println("PHYSICAL FOLDER: "
                    + physicalUploadFolder);

            System.out.println("DATABASE PATH: "
                    + fileAccessPath);

            System.out.println("FULL FILE PATH: "
                    + stringFilePath);


            /* =====================================================
               WRITE IMAGE FILE
            ===================================================== */

            image.write(stringFilePath);


            /* =====================================================
               CREATE ARTWORK OBJECT
            ===================================================== */

            Artwork artwork =
                    new Artwork();

            artwork.setTitle(title);

            artwork.setDescription(description);

            artwork.setPrice(price);

            artwork.setCategoryId(categoryId);

            artwork.setImagePath(fileAccessPath);


            /* =====================================================
               INSERT INTO DATABASE
            ===================================================== */

            boolean result =
                    artworkDAO.addArtwork(artwork);

            System.out.println(
                    "DATABASE INSERT RESULT: "
                    + result);


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