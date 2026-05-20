package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.AdminAnalyticsDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin-analytics")
public class AdminAnalyticsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        AdminAnalyticsDAO dao =
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
        // FORWARD
        // =========================================

        request.getRequestDispatcher(
                "/pages/admin/analytics-admin.jsp")
                .forward(request, response);
    }
}