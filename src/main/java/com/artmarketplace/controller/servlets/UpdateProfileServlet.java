package com.artmarketplace.controller.servlets;

import java.io.File;
import java.io.IOException;

import com.artmarketplace.dao.UserDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet responsible for updating customer profile details.
 * Acts as a Controller by reading profile form data, handling optional image upload,
 * updating the User model, and saving changes through UserDAO.
 */
@WebServlet("/update-profile")
@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles profile update form submission.
     *
     * @param request  The multipart HTTP request containing profile fields and optional image.
     * @param response The HTTP response used for success or error redirects.
     * @throws ServletException If multipart parsing fails.
     * @throws IOException If file writing or redirecting fails.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the logged-in user whose profile is being edited.
        User sessionUser = (User) request.getSession().getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        // Read updated profile values from the JSP form.
        String email = request.getParameter("email");
        String contactNumber = request.getParameter("contactNumber");

        // Email and contact number are required before updating the database.
        if (email == null || email.trim().isEmpty()
                || contactNumber == null || contactNumber.trim().isEmpty()) {

            response.sendRedirect(request.getContextPath() + "/pages/customer/profile.jsp?error=true");
            return;
        }

        // Keep the current profile image unless the user uploads a replacement.
        String imagePath = sessionUser.getProfileImage();

        Part imagePart = request.getPart("profileImage");

        if (imagePart != null && imagePart.getSize() > 0) {
            // Build a unique profile image filename to prevent overwriting existing uploads.
            String originalFileName = imagePart.getSubmittedFileName();
            String fileName = System.currentTimeMillis() + "_" + originalFileName;

            String uploadPath = getServletContext().getRealPath("/resources/profile");
            File uploadDir = new File(uploadPath);

            // Create the profile upload directory if it is missing in the deployed app.
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save the uploaded file and store a web-accessible relative path.
            imagePart.write(uploadPath + File.separator + fileName);
            imagePath = "resources/profile/" + fileName;
        }

        // Update the session model before passing it to the DAO.
        sessionUser.setEmail(email.trim());
        sessionUser.setContactNumber(contactNumber.trim());
        sessionUser.setProfileImage(imagePath);

        // Persist the profile changes in the users table.
        UserDAO dao = new UserDAO();
        boolean updated = dao.updateProfile(sessionUser);

        if (updated) {
            // Refresh session data so JSP pages show the updated profile immediately.
            request.getSession().setAttribute("user", sessionUser);
            response.sendRedirect(request.getContextPath() + "/pages/customer/profile.jsp?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/customer/profile.jsp?error=true");
        }
    }
}
