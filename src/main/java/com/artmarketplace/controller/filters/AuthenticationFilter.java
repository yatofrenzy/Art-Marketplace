package com.artmarketplace.controller.filters;

import java.io.IOException;

import com.artmarketplace.model.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filter responsible for protecting secured pages in the application.
 * Acts as a controller-level guard in the MVC flow before requests reach JSP views or servlets.
 * Checks login sessions, allows public resources, and redirects users based on their role.
 */
@WebFilter("/*")
public class AuthenticationFilter extends HttpFilter {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Intercepts every request and decides whether it should continue or be redirected.
     *
     * @param request  The incoming HTTP request containing the URL and session data.
     * @param response The HTTP response used for redirects when access is not allowed.
     * @param chain    The filter chain used to continue processing valid requests.
     * @throws IOException If a redirect or filter operation fails.
     * @throws ServletException If servlet filtering fails.
     */
	@Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Read the current request path so public and protected areas can be separated.
        String path = request.getRequestURI();
        String context = request.getContextPath();

        // Allow static resources
        if (path.contains("/css/") || path.contains("/js/") || path.contains("/resources/")) {
            chain.doFilter(request, response);
            return;
        }

        // Allow authentication pages and their servlet endpoints without requiring an existing session.
        if (path.contains("login.jsp") || path.contains("register.jsp") || path.contains("/login") || path.contains("/register")) {
            chain.doFilter(request, response);
            return;
        }

        // Get logged-in user from the HTTP session.
        User user = (User) request.getSession().getAttribute("user");

        // If not logged in
        if (user == null) {
            response.sendRedirect(context + "/pages/common/login.jsp");
            return;
        }

        String role = user.getRole();

        // Admin trying to access customer pages
        if (path.contains("/pages/customer") && role.equalsIgnoreCase("admin")) {
            response.sendRedirect(context + "/pages/admin/dashboard.jsp");
            return;
        }

        // Customer trying to access admin pages
        if (path.contains("/pages/admin") && role.equalsIgnoreCase("customer")) {
            response.sendRedirect(context + "/pages/customer/home.jsp");
            return;
        }

        // Everything is valid, so pass control to the requested servlet or JSP page.
        chain.doFilter(request, response);
    }
}
