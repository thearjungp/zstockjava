package com.stockmarket.util;

import javax.servlet.http.HttpServletRequest;

public class URLUtil {

    public static String getFullURI(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURI().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
