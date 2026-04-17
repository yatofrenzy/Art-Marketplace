package com.artmarketplace.utilities;

import jakarta.servlet.http.HttpSession;
import com.artmarketplace.model.User;

public class SessionUtil {

    public static void setUserSession(HttpSession session, User user) {
        session.setAttribute("user", user);
    }

    public static User getLoggedInUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    public static void invalidateSession(HttpSession session) {
        session.invalidate();
    }
}