package com.artmarketplace.utilities;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class for managing HTTP Cookies.
 * Part of the Utility layer of the application.
 * Provides helper methods to create, retrieve, and delete cookies in HTTP requests and responses.
 */
public class CookieUtil {

    /**
     * Adds a new cookie to the HTTP response with a specified name, value, and maximum age.
     * Sets the path to "/" so the cookie is accessible across the entire application domain.
     * 
     * @param response The HttpServletResponse to which the cookie will be added.
     * @param name     The name of the cookie to create.
     * @param value    The value string associated with the cookie.
     * @param maxAge   The lifetime of the cookie in seconds.
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        // Create a new Cookie instance with name and value
        Cookie cookie = new Cookie(name, value);
        // Set the maximum age/lifetime of the cookie in seconds
        cookie.setMaxAge(maxAge);
        // Set root path so the cookie is visible to all pages/paths in the webapp
        cookie.setPath("/");
        // Add the configured cookie back into the HttpServletResponse object
        response.addCookie(cookie);
    }

    /**
     * Retrieves the value of a specific cookie by its name from the HTTP request.
     * Iterates over all cookies present in the request header.
     * 
     * @param request The HttpServletRequest carrying client request information.
     * @param name    The target cookie name to look up.
     * @return The cookie's value string if found; otherwise, an empty string.
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        // Extract array of all cookies attached to the request
        Cookie[] cookies = request.getCookies();

        // Check if there are any cookies present
        if (cookies != null) {
            // Loop through each cookie in the array
            for (Cookie cookie : cookies) {
                // If the current cookie name matches the target name, return its value
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }

        // Return empty string if no matching cookie was found
        return "";
    }

    /**
     * Deletes a specific cookie by setting its maxAge to 0 and overwriting its value to empty.
     * The path must match "/" to ensure deletion of cookies set at that level.
     * 
     * @param response The HttpServletResponse to which the deletion instruction is written.
     * @param name     The name of the cookie to delete.
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        // Create an empty-value cookie with the same name to overwrite the existing one
        Cookie cookie = new Cookie(name, "");
        // Set maxAge to 0 seconds, telling the browser to immediately expire and discard the cookie
        cookie.setMaxAge(0);
        // Set the path to match the original cookie's path
        cookie.setPath("/");
        // Write the cookie to the response to execute deletion on the client side
        response.addCookie(cookie);
    }
}