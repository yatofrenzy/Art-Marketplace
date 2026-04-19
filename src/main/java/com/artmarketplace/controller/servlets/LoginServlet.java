package com.artmarketplace.controller.servlets;

import java.io.IOException;



import com.artmarketplace.dao.UserDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

        System.out.println("LoginServlet called");
        System.out.println("Email: " + email);

        if (email == null || password == null ||
            email.trim().isEmpty() || password.trim().isEmpty()) {

            request.setAttribute("error", "Email and password are required");
            request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmail(email);

        if (user != null && password.equals(user.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if ("admin".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/pages/user/home.jsp");
            }
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.getRequestDispatcher("/pages/common/login.jsp").forward(request, response);
        }
    }
}