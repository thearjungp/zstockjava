package com.stockmarket.instruments.filters;

import com.stockmarket.util.OutputUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class isAdminFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if(req.getMethod().equals("GET"))
        {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        if(req.getParameter("role") == null || req.getParameter("role").equals("") || !req.getParameter("role").equals("1"))
        {
            OutputUtil.outputResponse((HttpServletResponse) servletResponse, OutputUtil.errorObjResponse("You're not admin"), HttpServletResponse.SC_BAD_REQUEST);
        }
        else
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
