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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        UserDAO dao = new UserDAO();

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        if (!name.matches("^[A-Za-z ]+$")) {
            request.setAttribute("error", "Name must contain only letters.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        if (!phone.matches("^[0-9]{10}$")) {
            request.setAttribute("error", "Phone number must be 10 digits.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("error", "Please enter a valid email address.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        if (password.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters long.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        if (dao.isEmailExists(email.trim())) {
            request.setAttribute("error", "Email already exists.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User();
        user.setName(name.trim());
        user.setEmail(email.trim());
        user.setPhone(phone.trim());
        user.setPassword(hashedPassword);
        user.setRole("customer");

        boolean result = dao.registerUser(user);

        if (result) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp?registered=true");
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
        }
    }
}