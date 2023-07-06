package com.stockmarket.instruments.filters;

import com.stockmarket.instruments.Instrument;
import com.stockmarket.instruments.InstrumentService;
import com.stockmarket.util.OutputUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InstrumentByIdFilter implements Filter
{
    InstrumentService instrumentService;

    public InstrumentByIdFilter()
    {
        this.instrumentService = new InstrumentService();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        try
        {
            int instrumentId = Integer.parseInt(pathParts[1]);
            Instrument instrument = this.instrumentService.getInstrumentById(instrumentId);
            req.setAttribute("instrument", instrument);
            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (Exception e)
        {
            if(e instanceof NumberFormatException)
            {
                OutputUtil.outputResponse((HttpServletResponse) servletResponse, OutputUtil.errorObjResponse("Invalid Instrument ID"), HttpServletResponse.SC_BAD_REQUEST);
            }
            else
            {
                OutputUtil.outputResponse((HttpServletResponse) servletResponse, OutputUtil.errorObjResponse(e.getMessage()), HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
