package com.stockmarket.instruments.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarket.instruments.Instrument;
import com.stockmarket.instruments.InstrumentService;
import com.stockmarket.util.InputUtils;
import com.stockmarket.util.OutputUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InstrumentExistsWithNameFilter implements Filter
{

    private InstrumentService instrumentService;
    private ObjectMapper objectMapper;

    public InstrumentExistsWithNameFilter()
    {
        this.instrumentService = new InstrumentService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        try
        {
            Instrument instrument = this.objectMapper.readValue(InputUtils.getRequestBody(request), Instrument.class);
            request.setAttribute("instrument", instrument);
            boolean exists = this.instrumentService.instrumentExistsWithName(instrument.getInstrumentName());
            if(!exists) filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (Exception e)
        {
            OutputUtil.outputResponse((HttpServletResponse) servletResponse, OutputUtil.errorObjResponse(e.getMessage()), HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
