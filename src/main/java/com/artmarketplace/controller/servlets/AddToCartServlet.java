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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        String artworkIdParam = request.getParameter("artworkId");
        System.out.println("Received artworkId = " + artworkIdParam);

        if (artworkIdParam == null || artworkIdParam.trim().isEmpty() || "undefined".equals(artworkIdParam)) {
            response.sendRedirect(request.getContextPath() + "/pages/customer/artworks.jsp?error=invalidArtwork");
            return;
        }

        int artworkId = Integer.parseInt(artworkIdParam);

        CartDAO dao = new CartDAO();
        boolean added = dao.addToCart(user.getUserId(), artworkId);

        System.out.println("Add to cart result = " + added);

        response.sendRedirect(request.getContextPath() + "/pages/customer/cart.jsp");
    }
}