package com.stockmarket.users.filters;

import com.stockmarket.users.User;
import com.stockmarket.users.UserService;
import com.stockmarket.util.OutputUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UserByIdFilter implements Filter {

    UserService userService;

    public UserByIdFilter()
    {
        this.userService = new UserService();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        try
        {
            int userId = Integer.parseInt(pathParts[1]);
            User user = this.userService.getUserById(userId);
            req.setAttribute("user", user);
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (Exception e)
        {
            if(e instanceof NumberFormatException)
            {
                OutputUtil.outputResponse((HttpServletResponse) servletResponse, OutputUtil.errorObjResponse("Invalid User ID"), HttpServletResponse.SC_BAD_REQUEST);
            }
            else
            {
                OutputUtil.outputResponse((HttpServletResponse) servletResponse, OutputUtil.errorObjResponse(e.getMessage()), HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }

}
