package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.OrderDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for processing checkout requests.
 * Acts as a Controller by validating the logged-in customer and selected payment method,
 * then using OrderDAO to create an order from the customer's cart.
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles checkout form submission.
     *
     * @param request  The HTTP request containing the payment method and customer session.
     * @param response The HTTP response used to redirect to orders or checkout error page.
     * @throws ServletException If servlet processing fails.
     * @throws IOException If redirecting fails.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the currently logged-in customer from the session.
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            // Checkout requires authentication, so unauthenticated users go to login.
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        // Read payment option chosen on the checkout JSP page.
        String paymentMethod = request.getParameter("paymentMethod");

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            // Payment method is required before an order can be created.
            response.sendRedirect(request.getContextPath() + "/pages/customer/checkout.jsp?error=true");
            return;
        }

        // Create the order in the database using cart data for the logged-in user.
        OrderDAO dao = new OrderDAO();
        boolean result = dao.createOrder(user.getUserId(), paymentMethod);

        // Redirect according to whether the database transaction succeeded.
        if (result) {
            response.sendRedirect(request.getContextPath() + "/pages/customer/orders.jsp?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/customer/checkout.jsp?error=true");
        }
    }
}
