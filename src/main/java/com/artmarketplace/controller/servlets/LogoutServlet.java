package com.artmarketplace.controller.servlets;



import java.io.IOException;

import com.artmarketplace.utilities.SessionUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("null")
	public static void invalidateSession(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    

        HttpServletRequest request = null;
		SessionUtil.invalidateSession(request);
        HttpServletResponse response = null;
		try {
			response.sendRedirect(request.getContextPath() + "/views/common/login.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}