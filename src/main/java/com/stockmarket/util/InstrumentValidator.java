package com.stockmarket.util;

import com.stockmarket.instruments.Instrument;

import java.util.ArrayList;
import java.util.List;

public class InstrumentValidator {

    private List<String> errorDetails = new ArrayList<String>();

    public String getErrorDetails() {
        String errorMsg = errorDetails.get(0);
        errorDetails = new ArrayList<String>();
        return errorMsg;
    }

    public boolean isInstrumentDetailsValid(Instrument instrument)
    {
        if(!instrumentTypeValidator(instrument.getType())){
            errorDetails.add("Invalid Instrument type - Provide one of the three values (STOCK, INDEX, DERIVATIVE)");
            return false;
        }
        return true;
    }

    public boolean instrumentTypeValidator(String type)
    {
        return type.equals("STOCK") || type.equals("DERIVATIVE") || type.equals("INDEX");
    }


}
