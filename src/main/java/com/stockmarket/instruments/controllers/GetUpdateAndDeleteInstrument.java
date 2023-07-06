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

@WebServlet(urlPatterns = {"/instrument/*"})
public class GetUpdateAndDeleteInstrument extends HttpServlet
{
    private ObjectMapper objectMapper;
    private InstrumentService instrumentService;
    private InstrumentValidator validator;

    public GetUpdateAndDeleteInstrument()
    {
        this.objectMapper = new ObjectMapper();
        this.instrumentService = new InstrumentService();
        this.validator = new InstrumentValidator();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Instrument instrument = (Instrument) req.getAttribute("instrument");
        String responseBody = OutputUtil.convertToJSONString(instrument);
        CacheSetter.setCache(req, responseBody);
        OutputUtil.outputResponse(resp, responseBody, HttpServletResponse.SC_OK);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Instrument reqInstrument = (Instrument) req.getAttribute("instrument");
        Instrument instrument = this.objectMapper.readValue(InputUtils.getRequestBody(req), Instrument.class);

        if(validator.isInstrumentDetailsValid(instrument))
        {
            try
            {
                String updatedInstrumentMsg = this.instrumentService.updateInstrument(reqInstrument, instrument);
                OutputUtil.outputResponse(resp, OutputUtil.successObjResponse(updatedInstrumentMsg), HttpServletResponse.SC_CREATED);
            }
            catch(Exception e)
            {
                OutputUtil.outputResponse(resp, OutputUtil.errorObjResponse("Unable to update the Instrument"), HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else
        {
            OutputUtil.outputResponse(resp, OutputUtil.errorObjResponse(validator.getErrorDetails()), HttpServletResponse.SC_BAD_REQUEST);
        }

    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {

        Instrument reqInstrument = (Instrument) req.getAttribute("instrument");

        try
        {
            String deleteInstrumentMsg = this.instrumentService.deleteInstrument(reqInstrument);
            OutputUtil.outputResponse(resp, OutputUtil.successObjResponse(deleteInstrumentMsg), HttpServletResponse.SC_OK);

        } catch (Exception e) {
            OutputUtil.outputResponse(resp, OutputUtil.errorObjResponse("Unable to delete instrument with the provided Instrument ID"), HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
