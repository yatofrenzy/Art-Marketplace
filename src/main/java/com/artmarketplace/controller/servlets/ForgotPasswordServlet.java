package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.UserDAO;
import com.artmarketplace.model.User;
import com.artmarketplace.utilities.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (email == null || email.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()
                || newPassword == null || newPassword.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
            return;
        }

        if (!phone.matches("^[0-9]{10}$")) {
            request.setAttribute("error", "Phone number must be 10 digits.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
            return;
        }

        if (newPassword.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters long.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmailAndPhone(email.trim(), phone.trim());

        if (user == null) {
            request.setAttribute("error", "Email and phone number do not match.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(newPassword);
        boolean updated = dao.updatePassword(user.getUserId(), hashedPassword);

        if (updated) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp?reset=success");
        } else {
            request.setAttribute("error", "Password reset failed. Please try again.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
        }
    }
}