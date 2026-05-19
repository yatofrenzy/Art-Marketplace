package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.OrderDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/admin/update-order-status")
public class UpdateOrderStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User admin = (User) request.getSession().getAttribute("user");

        if (admin == null || !"admin".equalsIgnoreCase(admin.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        String orderIdText = request.getParameter("orderId");
        String status = request.getParameter("status");

        if (orderIdText == null || status == null) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/orders-admin.jsp?error=true");
            return;
        }

        if (!status.equals("Pending") && !status.equals("Completed") && !status.equals("Cancelled")) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/orders-admin.jsp?error=true");
            return;
        }

        int orderId = Integer.parseInt(orderIdText);

        OrderDAO dao = new OrderDAO();
        boolean updated = dao.updateOrderStatus(orderId, status);

        if (updated) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/orders-admin.jsp?success=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/admin/orders-admin.jsp?error=true");
        }
    }
}