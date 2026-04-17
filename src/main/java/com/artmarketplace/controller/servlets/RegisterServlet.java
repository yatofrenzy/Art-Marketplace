package com.artmarketplace.controller.servlets;

import com.artmarketplace.dao.UserDAO;
import com.artmarketplace.model.User;
import com.artmarketplace.utilities.PasswordUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = new User();
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));

        String rawPassword = request.getParameter("password");
        user.setPassword(PasswordUtil.hashPassword(rawPassword));

        user.setRole("user");

        UserDAO dao = new UserDAO();
        dao.registerUser(user);

        response.sendRedirect("login.jsp");
    }
}