package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.utilities.SessionUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for user logout.
 * Acts as a Controller by clearing the current session and returning the user to login.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles logout requests.
     *
     * @param request  The HTTP request containing the active session.
     * @param response The HTTP response used to redirect to login.
     * @throws IOException If redirecting fails.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Remove session data so the user is no longer authenticated.
        SessionUtil.invalidateSession(request);
        // Redirect to login page after logout completes.
        response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
    }
}
