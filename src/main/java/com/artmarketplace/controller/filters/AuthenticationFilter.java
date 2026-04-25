package com.artmarketplace.controller.filters;

import java.io.IOException;

import com.artmarketplace.model.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/pages/*")
public class AuthenticationFilter extends HttpFilter implements Filter {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String path = request.getRequestURI();

        // allow login/register pages
        if (path.contains("login.jsp") || path.contains("register.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        // check session
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }
}