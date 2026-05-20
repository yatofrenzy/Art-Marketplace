package com.artmarketplace.controller.servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Basic user route servlet.
 * Acts as a small Controller entry point that redirects generic user requests
 * to the login page used by the authentication flow.
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Redirects general user GET requests to the login view.
     *
     * @param request  The incoming HTTP request.
     * @param response The HTTP response used for redirecting.
     * @throws ServletException If servlet processing fails.
     * @throws IOException If redirecting fails.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
    }
}
