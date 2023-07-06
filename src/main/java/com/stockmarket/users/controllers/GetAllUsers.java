package com.stockmarket.users.controllers;

import com.stockmarket.CacheFilter;
import com.stockmarket.users.User;
import com.stockmarket.users.UserService;
import com.stockmarket.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/users"})
public class GetAllUsers extends HttpServlet {

    private UserService userService;

    public GetAllUsers() {
        this.userService = new UserService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String responseBody = null;
        try {
            List<User> users = this.userService.getAllUsers();
            responseBody = OutputUtil.convertToJSONString(users);
            CacheSetter.setCache(req, responseBody);
            OutputUtil.outputResponse(resp, responseBody, HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
