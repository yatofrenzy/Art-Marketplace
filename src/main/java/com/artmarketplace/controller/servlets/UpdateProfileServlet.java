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

@WebServlet("/update-profile")
@MultipartConfig
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User sessionUser = (User) request.getSession().getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        String email = request.getParameter("email");
        String contactNumber = request.getParameter("contactNumber");

        if (email == null || email.trim().isEmpty()
                || contactNumber == null || contactNumber.trim().isEmpty()) {

            response.sendRedirect(request.getContextPath() + "/pages/customer/profile.jsp?error=true");
            return;
        }

        String imagePath = sessionUser.getProfileImage();

        Part imagePart = request.getPart("profileImage");

        if (imagePart != null && imagePart.getSize() > 0) {
            String originalFileName = imagePart.getSubmittedFileName();
            String fileName = System.currentTimeMillis() + "_" + originalFileName;

            String uploadPath = getServletContext().getRealPath("/resources/profile");
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            imagePart.write(uploadPath + File.separator + fileName);
            imagePath = "resources/profile/" + fileName;
        }

        sessionUser.setEmail(email.trim());
        sessionUser.setContactNumber(contactNumber.trim());
        sessionUser.setProfileImage(imagePath);

        UserDAO dao = new UserDAO();
        boolean updated = dao.updateProfile(sessionUser);

        if (updated) {
            request.getSession().setAttribute("user", sessionUser);
            response.sendRedirect(request.getContextPath() + "/pages/customer/profile.jsp?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/customer/profile.jsp?error=true");
        }
    }
}