package com.stockmarket.instruments.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarket.instruments.Instrument;
import com.stockmarket.instruments.InstrumentService;
import com.stockmarket.util.CacheSetter;
import com.stockmarket.util.InputUtils;
import com.stockmarket.util.InstrumentValidator;
import com.stockmarket.util.OutputUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/createinstrument"})
public class CreateInstrument extends HttpServlet
{
    private ObjectMapper objectMapper;
    private InstrumentValidator validator;
    private InstrumentService instrumentService;

    public CreateInstrument()
    {
        this.objectMapper = new ObjectMapper();
        this.validator = new InstrumentValidator();
        this.instrumentService = new InstrumentService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Instrument instrument = (Instrument) req.getAttribute("instrument");
        instrument.setLtpChange(instrument.getLtp());

        if(validator.isInstrumentDetailsValid(instrument))
        {
            try
            {
                String createdInstrumentMsg = this.instrumentService.createInstrument(instrument);
                CacheSetter.flushCache();
                OutputUtil.outputResponse(resp, OutputUtil.successObjResponse(createdInstrumentMsg), HttpServletResponse.SC_CREATED);
            }
            catch(Exception e)
            {
                OutputUtil.outputResponse(resp, OutputUtil.errorObjResponse("Unable to create Instrument"), HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else
        {
            OutputUtil.outputResponse(resp, OutputUtil.errorObjResponse(validator.getErrorDetails()), HttpServletResponse.SC_BAD_REQUEST);
        }


    }
}
