package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.UserDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/admin/user-approval")
public class UserApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User admin = (User) request.getSession().getAttribute("user");

        if (admin == null || !"admin".equalsIgnoreCase(admin.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        String userIdText = request.getParameter("userId");
        String action = request.getParameter("action");

        if (userIdText == null || action == null) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard-admin.jsp?approval=error");
            return;
        }

        int userId = Integer.parseInt(userIdText);

        String status;

        if ("approve".equalsIgnoreCase(action)) {
            status = "Approved";
        } else if ("reject".equalsIgnoreCase(action)) {
            status = "Rejected";
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard-admin.jsp?approval=error");
            return;
        }

        UserDAO dao = new UserDAO();
        boolean updated = dao.updateAccountStatus(userId, status);

        if (updated) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard-admin.jsp?approval=success");
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard-admin.jsp?approval=error");
        }
    }
}