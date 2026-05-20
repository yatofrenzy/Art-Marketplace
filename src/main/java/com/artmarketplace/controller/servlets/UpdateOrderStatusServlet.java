package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.OrderDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet responsible for admin order status updates.
 * Acts as a Controller by validating admin access, checking status input,
 * and calling OrderDAO to update the order record in the database.
 */
@WebServlet("/admin/update-order-status")
public class UpdateOrderStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles order status update form submissions from the admin orders page.
     *
     * @param request  The HTTP request containing order ID, status, and admin session.
     * @param response The HTTP response used for success or error redirects.
     * @throws ServletException If servlet processing fails.
     * @throws IOException If redirecting fails.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Confirm the current session belongs to an administrator.
        User admin = (User) request.getSession().getAttribute("user");

        if (admin == null || !"admin".equalsIgnoreCase(admin.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        // Read order status update values from the admin form.
        String orderIdText = request.getParameter("orderId");
        String status = request.getParameter("status");

        // Both order ID and status are required for the database update.
        if (orderIdText == null || status == null) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/orders-admin.jsp?error=true");
            return;
        }

        // Only allow known order statuses to protect database consistency.
        if (!status.equals("Pending") && !status.equals("Completed") && !status.equals("Cancelled")) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/orders-admin.jsp?error=true");
            return;
        }

        // Convert order ID to the primary key type used by the DAO.
        int orderId = Integer.parseInt(orderIdText);

        // Update the order status in the database.
        OrderDAO dao = new OrderDAO();
        boolean updated = dao.updateOrderStatus(orderId, status);

        // Redirect with a query flag so the JSP can show success or error feedback.
        if (updated) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/orders-admin.jsp?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/admin/orders-admin.jsp?error=true");
        }
    }
}
