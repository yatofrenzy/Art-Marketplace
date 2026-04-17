package com.artmarketplace.controller.servlets;

import com.artmarketplace.utilities.SessionUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SessionUtil.invalidateSession(request.getSession());
        response.sendRedirect("login.jsp");
    }
}