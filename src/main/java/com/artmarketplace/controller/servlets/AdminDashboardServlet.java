package com.artmarketplace.controller.servlets;

import java.io.IOException;
import java.util.List;

import com.artmarketplace.dao.AdminAnalyticsDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for preparing dashboard statistics for the admin panel.
 * Acts as a Controller by collecting analytics data from AdminAnalyticsDAO
 * and forwarding it to the dashboard JSP view.
 */
@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Handles admin dashboard requests.
     *
     * @param request  The HTTP request containing optional year filter.
     * @param response The HTTP response used to forward to the dashboard JSP.
     * @throws ServletException If forwarding fails.
     * @throws IOException If the JSP cannot be reached.
     */
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        AdminAnalyticsDAO analyticsDAO =
                new AdminAnalyticsDAO();

        // =========================================
        // GET SELECTED YEAR FOR MONTHLY SALES
        // =========================================

        String yearParam = request.getParameter("year");

        int selectedYear;

        if(yearParam == null || yearParam.isEmpty()){

            selectedYear = 2026;

        }else{

            selectedYear = Integer.parseInt(yearParam);
        }

        // =========================================
        // TOTAL REVENUE
        // =========================================

        request.setAttribute(
                "totalRevenue",
                analyticsDAO.getTotalRevenue());

        // =========================================
        // TOTAL ORDERS
        // =========================================

        request.setAttribute(
                "totalOrders",
                analyticsDAO.getTotalOrders());

        // =========================================
        // TOTAL CUSTOMERS
        // =========================================

        request.setAttribute(
                "totalCustomers",
                analyticsDAO.getTotalCustomers());

        // =========================================
        // MONTHLY SALES
        // =========================================

        List<Double> monthlySales =
                analyticsDAO.getMonthlySales(selectedYear);

        request.setAttribute(
                "monthlySales",
                monthlySales);

        // =========================================
        // TOP PRODUCTS
        // =========================================

        request.setAttribute(
                "topProducts",
                analyticsDAO.getTopSellingProducts());

        // =========================================
        // FORWARD DATA TO JSP VIEW
        // =========================================

        request.getRequestDispatcher(
                "/pages/admin/dashboard-admin.jsp")
                .forward(request, response);
    }
}
