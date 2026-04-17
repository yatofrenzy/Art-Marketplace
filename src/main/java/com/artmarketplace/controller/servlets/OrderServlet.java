package com.artmarketplace.controller.servlets;

import com.artmarketplace.dao.OrderDAO;
import com.artmarketplace.model.Order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        double total = Double.parseDouble(request.getParameter("total"));

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmt(total);
        order.setOrderDate(LocalDate.now().toString());
        order.setStatus("Pending");
        order.setPaymentMethod("COD");
        order.setPaymentStatus("Unpaid");

        OrderDAO dao = new OrderDAO();
        dao.createOrder(order);

        response.sendRedirect("success.jsp");
    }
}