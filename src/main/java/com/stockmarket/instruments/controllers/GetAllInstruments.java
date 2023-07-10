package com.stockmarket.instruments.controllers;

import com.stockmarket.instruments.Instrument;
import com.stockmarket.instruments.InstrumentService;
import com.stockmarket.util.CacheSetter;
import com.stockmarket.util.OutputUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/instruments"})
public class GetAllInstruments extends HttpServlet
{
    InstrumentService instrumentService;

    public GetAllInstruments()
    {
        this.instrumentService = new InstrumentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try
        {
            List<Instrument> instruments = this.instrumentService.getAllInstruments();
            String responseBody = OutputUtil.convertToJSONString(instruments);
            OutputUtil.outputResponse(resp, responseBody, HttpServletResponse.SC_OK);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }
}
