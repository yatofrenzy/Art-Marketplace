package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.CartDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        int artworkId = Integer.parseInt(request.getParameter("artworkId"));

        CartDAO dao = new CartDAO();
        dao.addToCart(user.getUserId(), artworkId);

        response.sendRedirect(request.getContextPath() + "/pages/customer/artworks.jsp?added=true");
    }
}