package com.stockmarket.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarket.users.User;
import com.stockmarket.users.UserService;
import com.stockmarket.util.CacheSetter;
import com.stockmarket.util.InputUtils;
import com.stockmarket.util.OutputUtil;
import com.stockmarket.util.UserDetailValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/createuser"})
public class CreateUser extends HttpServlet {

    private UserService userService;
    private ObjectMapper objectMapper;
    private UserDetailValidator validator;

    public CreateUser() {
        this.userService = new UserService();
        this.objectMapper = new ObjectMapper();
        this.validator = new UserDetailValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String responseBody = null;
        User user = objectMapper.readValue(InputUtils.getRequestBody(req), User.class);
        if(validator.isUserDetailsValid(user))
        {
            try
            {
                String createdUserMsg = this.userService.createUser(user);
                OutputUtil.outputResponse(resp,
                        OutputUtil.successObjResponse(createdUserMsg),
                        HttpServletResponse.SC_CREATED);
            }
            catch(Exception e)
            {
                OutputUtil.outputResponse(resp, OutputUtil.errorObjResponse("Unable to create user"), HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else
        {
            OutputUtil.outputResponse(resp, OutputUtil.errorObjResponse(validator.getErrorDetails()), HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
