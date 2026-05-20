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

/**
 * Servlet responsible for password reset using email and phone verification.
 * Works as a Controller by validating form input, locating the user through UserDAO,
 * hashing the new password, and updating the database.
 */
@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Displays the forgot password JSP page.
     *
     * @param request  The incoming HTTP request.
     * @param response The HTTP response used for forwarding.
     * @throws ServletException If forwarding fails.
     * @throws IOException If the JSP cannot be reached.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
    }

    /**
     * Handles password reset form submission.
     * Validates user details, verifies the account by email and phone, and stores a hashed password.
     *
     * @param request  The HTTP request containing reset form fields.
     * @param response The HTTP response used for success or error navigation.
     * @throws ServletException If forwarding fails.
     * @throws IOException If redirecting fails.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract password reset fields submitted by the JSP form.
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Ensure all required fields are present before validation and database lookup.
        if (email == null || email.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()
                || newPassword == null || newPassword.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
            return;
        }

        // Phone number must match the expected 10-digit format.
        if (!phone.matches("^[0-9]{10}$")) {
            request.setAttribute("error", "Phone number must be 10 digits.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
            return;
        }

        // Enforce the minimum password length used by the application.
        if (newPassword.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters long.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
            return;
        }

        // Confirm both password fields match before hashing the new password.
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
            return;
        }

        // Verify that the submitted email and phone belong to the same user record.
        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmailAndPhone(email.trim(), phone.trim());

        if (user == null) {
            request.setAttribute("error", "Email and phone number do not match.");
            request.getRequestDispatcher("/pages/common/forgot-password.jsp").forward(request, response);
            return;
        }

        // Hash the new password before updating it in the database.
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
