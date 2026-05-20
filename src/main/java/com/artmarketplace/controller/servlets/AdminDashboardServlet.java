package com.artmarketplace.controller.servlets;

import java.io.IOException;
import java.util.List;

import com.artmarketplace.dao.AdminAnalyticsDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        AdminAnalyticsDAO analyticsDAO =
                new AdminAnalyticsDAO();

        // =========================================
        // GET SELECTED YEAR
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
        // FORWARD
        // =========================================

        request.getRequestDispatcher(
        	    "/WEB-INF/views/admin/dashboard-admin.jsp")
        	    .forward(request, response);
    }
}