package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.AdminAnalyticsDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for preparing analytics data for the admin analytics page.
 * Works as a Controller by reading the selected year, requesting reports from AdminAnalyticsDAO,
 * and forwarding the results to analytics-admin.jsp.
 */
@WebServlet("/admin-analytics")
public class AdminAnalyticsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Handles analytics page requests and supplies chart/statistic data to the JSP.
     *
     * @param request  The HTTP request containing optional year filter.
     * @param response The HTTP response used to forward to the analytics JSP.
     * @throws ServletException If forwarding fails.
     * @throws IOException If the JSP cannot be reached.
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        AdminAnalyticsDAO dao =
                new AdminAnalyticsDAO();

        // =========================================
        // GET SELECTED YEAR FOR CHART DATA
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
                "income",
                dao.getTotalRevenue());

        // =========================================
        // TOTAL ORDERS
        // =========================================

        request.setAttribute(
                "totalOrders",
                dao.getTotalOrders());

        // =========================================
        // SALES DATA
        // =========================================

        request.setAttribute(
                "salesData",
                dao.getMonthlySales(selectedYear));

        // =========================================
        // FORWARD DATA TO JSP VIEW
        // =========================================

        request.getRequestDispatcher(
                "/pages/admin/analytics-admin.jsp")
                .forward(request, response);
    }
}
