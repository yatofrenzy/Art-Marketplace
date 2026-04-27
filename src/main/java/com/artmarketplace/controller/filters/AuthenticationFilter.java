package com.artmarketplace.controller.filters;

import java.io.IOException;

import com.artmarketplace.model.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthenticationFilter extends HttpFilter {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String path = request.getRequestURI();
        String context = request.getContextPath();

        // Allow static resources
        if (path.contains("/css/") || path.contains("/js/") || path.contains("/resources/")) {
            chain.doFilter(request, response);
            return;
        }

        // Allow login/register
        if (path.contains("login.jsp") || path.contains("register.jsp") || path.contains("/login") || path.contains("/register")) {
            chain.doFilter(request, response);
            return;
        }

        // Get logged user
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

        // Everything OK
        chain.doFilter(request, response);
    }
}