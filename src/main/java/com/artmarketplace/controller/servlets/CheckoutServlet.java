package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.OrderDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        String paymentMethod = request.getParameter("paymentMethod");

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/pages/customer/checkout.jsp?error=true");
            return;
        }

        OrderDAO dao = new OrderDAO();
        boolean result = dao.createOrder(user.getUserId(), paymentMethod);

        if (result) {
            response.sendRedirect(request.getContextPath() + "/pages/customer/orders.jsp?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/customer/checkout.jsp?error=true");
        }
    }
}