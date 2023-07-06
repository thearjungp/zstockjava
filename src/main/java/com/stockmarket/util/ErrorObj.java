package com.stockmarket.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorObj extends Exception
{
    String error;

    public ErrorObj(String errorMsg)
    {
        error = errorMsg;
    }

    @Override
    public String getMessage() {
        return error;
    }
}
