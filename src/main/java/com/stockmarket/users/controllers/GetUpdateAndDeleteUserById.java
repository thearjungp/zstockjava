package com.stockmarket.users.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarket.users.User;
import com.stockmarket.users.UserService;
import com.stockmarket.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/user/*"})
public class GetUpdateAndDeleteUserById extends HttpServlet
{
    private UserService userService;
    private ObjectMapper objectMapper;
    private UserDetailValidator validator;

    public GetUpdateAndDeleteUserById() {
        this.userService = new UserService();
        this.objectMapper = new ObjectMapper();
        this.validator = new UserDetailValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println(URLUtil.getFullURI(req));
        User user = (User)req.getAttribute("user");
        String responseBody = OutputUtil.convertToJSONString(user);
        CacheSetter.setCache(req, responseBody);
        OutputUtil.outputResponse(resp, responseBody, HttpServletResponse.SC_OK);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User reqUser = (User) req.getAttribute("user");
        User user = objectMapper.readValue(InputUtils.getRequestBody(req), User.class);

        if(validator.isUserDetailsValid(user))
        {
            try
            {
                String updatedUserMsg = this.userService.updateUser(reqUser.getId(), user);
                CacheSetter.flushCache();
                OutputUtil.outputResponse(resp,
                        OutputUtil.successObjResponse(updatedUserMsg),
                        HttpServletResponse.SC_CREATED);
            }
            catch(Exception e)
            {
                OutputUtil.outputResponse(resp, OutputUtil.errorObjResponse("Unable to Update user"), HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else
        {
            OutputUtil.outputResponse(resp, OutputUtil.errorObjResponse(validator.getErrorDetails()), HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User reqUser = (User) req.getAttribute("user");

        try
        {
            String deletedUserMsg = this.userService.deleteUser(reqUser);
            CacheSetter.flushCache();
            OutputUtil.outputResponse(resp, OutputUtil.successObjResponse(deletedUserMsg), HttpServletResponse.SC_OK);
        }
        catch(Exception e)
        {
            OutputUtil.outputResponse(resp, OutputUtil.errorObjResponse("Unable to delete the user with given User ID"), HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
