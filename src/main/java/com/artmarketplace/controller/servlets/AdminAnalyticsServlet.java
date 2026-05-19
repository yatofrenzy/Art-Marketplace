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

        request.setAttribute(
                "income",
                dao.getTotalRevenue());

        request.setAttribute(
                "totalOrders",
                dao.getTotalOrders());

        request.setAttribute(
                "salesData",
                dao.getMonthlySales());

        request.getRequestDispatcher(
                "/pages/admin/analytics-admin.jsp")
                .forward(request, response);
    }
}