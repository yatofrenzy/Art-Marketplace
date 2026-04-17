package com.artmarketplace.controller.servlets;

import com.artmarketplace.dao.CartDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        int artworkId = Integer.parseInt(request.getParameter("artworkId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        CartDAO dao = new CartDAO();
        dao.addToCart(userId, artworkId, quantity);

        response.sendRedirect("cart.jsp");
    }
}