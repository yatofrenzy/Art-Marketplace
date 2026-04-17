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

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

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
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        System.out.println("=== RegisterServlet called ===");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Confirm Password: " + confirmPassword);

        if (name == null || email == null || password == null || confirmPassword == null
                || name.trim().isEmpty() || email.trim().isEmpty()
                || password.trim().isEmpty() || confirmPassword.trim().isEmpty()) {

            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Password and confirm password do not match.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        boolean exists = userDAO.isEmailExists(email);
        System.out.println("Email exists? " + exists);

        if (exists) {
            request.setAttribute("error", "Email already exists.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
            return;
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole("customer");

        boolean result = userDAO.registerUser(user);
        System.out.println("Register result = " + result);

        if (result) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
        } else {
            request.setAttribute("error", "Registration failed.");
            request.getRequestDispatcher("/pages/common/register.jsp").forward(request, response);
        }
    }
}