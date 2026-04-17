package com.artmarketplace.utilities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.artmarketplace.model.User;

public class SessionUtil {

    public static void setUserSession(HttpServletRequest request, User user) {
        request.setAttribute("user", user);
    }

    public static User getLoggedInUser(HttpServletRequest req) {
        return (User) req.getAttribute("user");
    }

    public static void invalidateSession(HttpServletRequest request) {
        ((HttpSession) request).invalidate();
    }

	public static void setUserSession1(HttpServletRequest request, User user) {
		// TODO Auto-generated method stub
		
	}
}