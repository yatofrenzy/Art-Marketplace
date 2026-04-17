package com.artmarketplace.controller.servlets;

import java.io.IOException;



import com.artmarketplace.dao.UserDAO;
import com.artmarketplace.model.User;
import com.artmarketplace.utilities.PasswordUtil;
import com.artmarketplace.utilities.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("/views/common/login.jsp").forward(request, response);
            return;
        }

        User user = userDAO.loginUser(email);

        if (user != null && PasswordUtil.checkPassword1(password, user.getPassword())) {
            SessionUtil.setUserSession(request, user);

            if ("admin".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/views/admin/dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/user/home.jsp");
            }
        } else {
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("/views/common/login.jsp").forward(request, response);
        }
    }
}