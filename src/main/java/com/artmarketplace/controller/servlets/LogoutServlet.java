package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.utilities.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        SessionUtil.invalidateSession(request);
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
    }
}