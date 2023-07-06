package com.stockmarket.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

public class InputUtils {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String getRequestBody(HttpServletRequest req) throws IOException {
        return req.getReader().lines().collect(Collectors.joining());
    }

}
