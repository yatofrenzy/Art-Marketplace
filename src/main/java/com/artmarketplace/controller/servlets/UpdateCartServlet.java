package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.CartDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for updating customer cart quantities and removing cart items.
 * Acts as a Controller between cart.jsp form buttons and CartDAO database updates.
 */
@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles cart update form submissions.
     *
     * @param request  The HTTP request containing cart item ID, quantity, and action.
     * @param response The HTTP response used to redirect back to the cart page.
     * @throws ServletException If servlet processing fails.
     * @throws IOException If redirecting fails.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Convert hidden form values into numeric identifiers for DAO operations.
        int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        // Action determines whether quantity is increased, decreased, or item is removed.
        String action = request.getParameter("action");

        CartDAO dao = new CartDAO();

        if ("increase".equals(action)) {
            // Increase quantity and persist the new value.
            quantity++;
            dao.updateQuantity(cartItemId, quantity);
        } else if ("decrease".equals(action)) {
            // Prevent quantity from going below one item.
            if (quantity > 1) {
                quantity--;
                dao.updateQuantity(cartItemId, quantity);
            }
        } else if ("remove".equals(action)) {
            // Remove the selected cart row from the database.
            dao.removeItem(cartItemId);
        }

        // Return to cart after the update operation.
        response.sendRedirect(request.getContextPath() + "/pages/customer/cart.jsp");
    }
}
