package com.artmarketplace.controller.servlets;

import com.artmarketplace.dao.*;
import com.artmarketplace.model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = user.getUserId();

        CartDAO cartDAO = new CartDAO();
        List<CartItem> cartList = cartDAO.getCartByUserId(userId);

        if (cartList.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        double total = 0;
        for (CartItem item : cartList) {
            total += item.getPrice() * item.getQuantity();
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmt(total);
        order.setOrderDate(LocalDate.now().toString());
        order.setStatus("Pending");
        order.setPaymentMethod(request.getParameter("payment_method"));
        order.setPaymentStatus("Pending");
        order.setPaymentDate(null);

        OrderDAO orderDAO = new OrderDAO();
        int orderId = orderDAO.createOrder(order);

        OrderItemDAO orderItemDAO = new OrderItemDAO();

        for (CartItem item : cartList) {
            orderItemDAO.addOrderItem(
                orderId,
                item.getArtworkId(),
                item.getQuantity(),
                item.getPrice()
            );
        }

        cartDAO.clearCart(userId);

        response.sendRedirect("success.jsp");
    }
}