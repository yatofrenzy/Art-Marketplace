package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.UserDAO;
import com.artmarketplace.model.User;
import com.artmarketplace.utilities.PasswordUtil;
import com.artmarketplace.utilities.SessionUtil;
import com.artmarketplace.utilities.CookieUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet responsible for handling user login functionality.
 * Supports both admin and customer login with role-based authentication.
 * Validates credentials, checks account approval status, and manages sessions.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to display the login page.
     * Forwards the request to the login JSP page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for user login authentication.
     * Validates credentials, checks account status, and performs role-based login.
     * Supports both admin and customer login paths.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract login parameters from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String selectedRole = request.getParameter("role");

        // Default to customer role if not specified
        if (selectedRole == null || selectedRole.trim().isEmpty()) {
            selectedRole = "customer";
        }

        // Validate that email and password are provided
        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
            return;
        }

        // Retrieve user from database by email
        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmail(email.trim());

        // Authenticate user by checking password
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {

            // Verify account is approved by admin
            if (!"Approved".equalsIgnoreCase(user.getAccountStatus())) {
                request.setAttribute("error", "Your account is pending admin approval.");
                request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
                return;
            }

            String actualRole = user.getRole().toLowerCase();

            // Handle admin login
            if ("admin".equalsIgnoreCase(selectedRole)) {

                // Ensure user has admin role
                if (!actualRole.equals("admin")) {
                    request.setAttribute("error", "Access denied: Admins only.");
                    request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
                    return;
                }

                // Set session and redirect to admin dashboard
                SessionUtil.setUserSession(request, user);
                CookieUtil.addCookie(response, "rememberedEmail", user.getEmail(), 60 * 60 * 24 * 7);
                response.sendRedirect(request.getContextPath() + "/admin-dashboard");
                return;
            }

            // Handle customer login
            if ("customer".equalsIgnoreCase(selectedRole)) {

                // Ensure user has customer role
                if (!actualRole.equals("customer")) {
                    request.setAttribute("error", "Access denied: Customers only.");
                    request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
                    return;
                }

                // Set session and redirect to customer home
                SessionUtil.setUserSession(request, user);
                CookieUtil.addCookie(response, "rememberedEmail", user.getEmail(), 60 * 60 * 24 * 7);
                response.sendRedirect(request.getContextPath() + "/pages/customer/home.jsp");
                return;
            }

            // Handle invalid role selection
            request.setAttribute("error", "Invalid login option.");
            request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);

        } else {
            // Handle authentication failure
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
        }
    }
}