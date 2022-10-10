package ro.nexttech.pinservice.security.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static final String AUTH_COOKIE_NAME = "jwtToken";

    public static void addCookieToResponse(HttpServletResponse response, String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        response.addCookie(cookie);

        response.setHeader(cookieName, cookieValue);
    }

    public static void deleteCookieFromResponse(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
