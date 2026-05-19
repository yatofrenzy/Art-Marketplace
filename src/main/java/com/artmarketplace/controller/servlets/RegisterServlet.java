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
 * Servlet responsible for handling new user registration.
 * Validates user input, checks for existing emails, and creates new customer accounts.
 * All new accounts require admin approval before login.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to display the registration page.
     * Forwards the request to the registration JSP page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for user registration.
     * Validates all input fields, checks for duplicate emails, and creates new user accounts.
     * Passwords are hashed before storage. New accounts start with "Pending" status.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract registration parameters from request
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        UserDAO dao = new UserDAO();

        // Validate that all required fields are provided
        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        // Validate name contains only letters and spaces
        if (!name.matches("^[A-Za-z ]+$")) {
            request.setAttribute("error", "Name must contain only letters.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        // Validate phone number is exactly 10 digits
        if (!phone.matches("^[0-9]{10}$")) {
            request.setAttribute("error", "Phone number must be 10 digits.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("error", "Please enter a valid email address.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        // Validate password minimum length
        if (password.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters long.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        // Validate password confirmation matches
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        // Check if email already exists in database
        if (dao.isEmailExists(email.trim())) {
            request.setAttribute("error", "Email already exists.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        // Hash password for secure storage
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Create new user object with registration data
        User user = new User();
        user.setName(name.trim());
        user.setEmail(email.trim());
        user.setPhone(phone.trim());
        user.setPassword(hashedPassword);
        user.setRole("customer");
        user.setAccountStatus("Pending");

        // Attempt to register user in database
        boolean result = dao.registerUser(user);

        // Redirect based on registration success
        if (result) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp?registered=true");
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
        }
    }
}