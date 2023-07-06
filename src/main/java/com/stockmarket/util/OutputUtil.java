package com.stockmarket.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarket.users.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputUtil {

    static ObjectMapper objectMapper = new ObjectMapper();


    public static void outputResponse(HttpServletResponse response, String payload, int status) {
        response.setHeader("Content-Type", "application/json");
        try {
            response.setStatus(status);
            if (payload != null) {
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(payload.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String errorObjResponse(String message) throws JsonProcessingException {
        Map<String, String> eor = new HashMap<>();
        eor.put("error", message);
        String errorObjResponse = objectMapper.writeValueAsString(eor);
        return errorObjResponse;
    }

    public static String successObjResponse(String message) throws JsonProcessingException {
        Map<String, String> sor = new HashMap<>();
        sor.put("message", message);
        String successObjResponse = objectMapper.writeValueAsString(sor);
        return successObjResponse;
    }

    public static String convertToJSONString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
