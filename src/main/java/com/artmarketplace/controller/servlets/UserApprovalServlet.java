package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.UserDAO;
import com.artmarketplace.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Servlet responsible for approving or rejecting customer accounts.
 * Acts as an admin Controller by validating admin access, interpreting the approval action,
 * and updating account status through UserDAO.
 */
@WebServlet("/admin/user-approval")
public class UserApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles customer approval actions submitted from the admin interface.
     *
     * @param request  The HTTP request containing user ID, action, and admin session.
     * @param response The HTTP response used for approval result redirects.
     * @throws ServletException If servlet processing fails.
     * @throws IOException If redirecting fails.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Only logged-in admins can approve or reject customer accounts.
        User admin = (User) request.getSession().getAttribute("user");

        if (admin == null || !"admin".equalsIgnoreCase(admin.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/common/login.jsp");
            return;
        }

        // Read selected customer ID and requested approval action.
        String userIdText = request.getParameter("userId");
        String action = request.getParameter("action");

        // Required fields must be present before status can be updated.
        if (userIdText == null || action == null) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard-admin.jsp?approval=error");
            return;
        }

        // Convert the selected customer ID into the integer primary key used by UserDAO.
        int userId = Integer.parseInt(userIdText);

        String status;

        // Map button action values to database account status values.
        if ("approve".equalsIgnoreCase(action)) {
            status = "Approved";
        } else if ("reject".equalsIgnoreCase(action)) {
            status = "Rejected";
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard-admin.jsp?approval=error");
            return;
        }

        // Persist the new account status in the database.
        UserDAO dao = new UserDAO();
        boolean updated = dao.updateAccountStatus(userId, status);

        // Redirect with approval result so the JSP can show user feedback.
        if (updated) {
            response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard-admin.jsp?approval=success");
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard-admin.jsp?approval=error");
        }
    }
}
