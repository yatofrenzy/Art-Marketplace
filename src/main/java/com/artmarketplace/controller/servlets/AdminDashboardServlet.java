package com.artmarketplace.controller.servlets;

import java.io.IOException;

import com.artmarketplace.dao.AdminAnalyticsDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AdminAnalyticsDAO analyticsDAO = new AdminAnalyticsDAO();

        request.setAttribute("totalRevenue", analyticsDAO.getTotalRevenue());
        request.setAttribute("totalOrders", analyticsDAO.getTotalOrders());
        request.setAttribute("totalCustomers", analyticsDAO.getTotalCustomers());
        request.setAttribute("monthlySales", analyticsDAO.getMonthlySales());
        request.setAttribute("topProducts", analyticsDAO.getTopSellingProducts());

        request.getRequestDispatcher("/pages/admin/dashboard-admin.jsp")
               .forward(request, response);
    }
}

