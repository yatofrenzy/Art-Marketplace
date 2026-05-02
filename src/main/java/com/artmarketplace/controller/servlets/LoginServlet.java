package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.UserDAO;
import com.artmarketplace.model.User;
import com.artmarketplace.utilities.PasswordUtil;
import com.artmarketplace.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String selectedRole = request.getParameter("role"); // "admin" or "user"

        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmail(email.trim());

        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {

            String actualRole = user.getRole().toLowerCase();

            // 🔹 ADMIN LOGIN (admin + artist allowed)
            if ("admin".equalsIgnoreCase(selectedRole)) {

                if (!(actualRole.equals("admin") || actualRole.equals("artist"))) {
                    request.setAttribute("error", "Access denied: Admins only.");
                    request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
                    return;
                }

                SessionUtil.setUserSession(request, user);

                // Optional redirect split
                if (actualRole.equals("artist")) {
                    response.sendRedirect(request.getContextPath() + "/pages/artist/dashboard.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard.jsp");
                }

                return;
            }

            // 🔹 USER LOGIN (customer only)
            if ("user".equalsIgnoreCase(selectedRole)) {

                if (!actualRole.equals("customer")) {
                    request.setAttribute("error", "Access denied: Users only.");
                    request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
                    return;
                }

                SessionUtil.setUserSession(request, user);
                response.sendRedirect(request.getContextPath() + "/pages/customer/home.jsp");
                return;
            }

        } else {
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
        }
    }
    }