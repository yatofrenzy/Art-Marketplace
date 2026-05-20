package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.CartDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for adding artwork items to a customer's cart.
 * Acts as the Controller between the customer artwork JSP and CartDAO database operations.
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles add-to-cart form submission.
     *
     * @param request  The HTTP request containing artwork ID and user session.
     * @param response The HTTP response used to redirect to login, artworks, or cart page.
     * @throws ServletException If servlet processing fails.
     * @throws IOException If redirecting fails.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // A cart item must belong to a logged-in customer.
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        // Read the artwork ID submitted from the artwork listing page.
        String artworkIdParam = request.getParameter("artworkId");
        System.out.println("Received artworkId = " + artworkIdParam);

        // Reject missing or invalid artwork IDs before calling the DAO.
        if (artworkIdParam == null || artworkIdParam.trim().isEmpty() || "undefined".equals(artworkIdParam)) {
            response.sendRedirect(request.getContextPath() + "/pages/customer/artworks.jsp?error=invalidArtwork");
            return;
        }

        // Convert the request parameter into an integer primary key for database use.
        int artworkId = Integer.parseInt(artworkIdParam);

        // Insert or update the cart entry for this customer and artwork.
        CartDAO dao = new CartDAO();
        boolean added = dao.addToCart(user.getUserId(), artworkId);

        System.out.println("Add to cart result = " + added);

        // Return the user to the cart page after the cart operation.
        response.sendRedirect(request.getContextPath() + "/pages/customer/cart.jsp");
    }
}
