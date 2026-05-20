package com.artmarketplace.utilities;

import com.artmarketplace.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for handling session state.
 * Part of the Utility layer in the MVC architecture.
 * Manages user logins, session retrieval, and session invalidation for authentication checks.
 */
public class SessionUtil {

    /**
     * Stores a User model object inside the current request session.
     * 
     * @param request The HttpServletRequest request object.
     * @param user    The authenticated {@link User} object to store in session.
     */
    public static void setUserSession(HttpServletRequest request, User user) {
        // Retrieve the current session or create a new session if it doesn't exist
        HttpSession session = request.getSession();
        // Bind the user object to the session under the key "user"
        session.setAttribute("user", user);
    }

    /**
     * Retrieves the authenticated User object from the current active session.
     * Does not create a new session if one does not exist.
     * 
     * @param request The HttpServletRequest request object.
     * @return The {@link User} object if the session is active and user is found; {@code null} otherwise.
     */
    public static User getLoggedInUser(HttpServletRequest request) {
        // Retrieve the existing session without creating a new one (passing false)
        HttpSession session = request.getSession(false);
        // Check if the session is not null/active
        if (session != null) {
            // Retrieve and cast the session attribute "user" to User class
            return (User) session.getAttribute("user");
        }
        // Return null if no active session exists
        return null;
    }

    /**
     * Invalidates the current session if it exists to perform a logout.
     * 
     * @param request The HttpServletRequest request object.
     */
    public static void invalidateSession(HttpServletRequest request) {
        // Retrieve the existing session if one exists
        HttpSession session = request.getSession(false);
        // Check if the session exists
        if (session != null) {
            // Invalidate/destroy the session, clearing all stored session attributes
            session.invalidate();
        }
    }
}