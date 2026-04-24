package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.CartDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String action = request.getParameter("action");

        CartDAO dao = new CartDAO();

        if ("increase".equals(action)) {
            quantity++;
            dao.updateQuantity(cartItemId, quantity);
        } else if ("decrease".equals(action)) {
            if (quantity > 1) {
                quantity--;
                dao.updateQuantity(cartItemId, quantity);
            }
        } else if ("remove".equals(action)) {
            dao.removeItem(cartItemId);
        }

        response.sendRedirect(request.getContextPath() + "/pages/customer/cart.jsp");
    }
}