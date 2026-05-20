package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.OrderDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet responsible for creating customer orders from checkout submissions.
 * This Controller validates session and payment data before delegating order creation to OrderDAO.
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles order creation requests.
     *
     * @param request  The HTTP request containing payment method and customer session.
     * @param response The HTTP response used for success or error redirects.
     * @throws ServletException If servlet processing fails.
     * @throws IOException If redirecting fails.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get logged-in customer from session before creating an order.
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        // Read selected payment method from checkout form.
        String paymentMethod = request.getParameter("paymentMethod");

        // Payment method is mandatory for order creation.
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/pages/customer/checkout.jsp?error=true");
            return;
        }

        // Create the order in the database from the customer's cart.
        OrderDAO orderDAO = new OrderDAO();
        boolean created = orderDAO.createOrder(user.getUserId(), paymentMethod);

        // Redirect to the correct page based on DAO result.
        if (created) {
            response.sendRedirect(request.getContextPath() + "/pages/customer/orders.jsp?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/customer/checkout.jsp?error=true");
        }
    }
}
