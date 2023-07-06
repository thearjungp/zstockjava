package com.stockmarket.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageObj
{

    String message;

    public MessageObj(String msg)
    {
        this.message = msg;
    }

}
