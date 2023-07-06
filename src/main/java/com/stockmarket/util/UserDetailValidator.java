package com.stockmarket.util;

import com.stockmarket.users.User;

import java.util.ArrayList;
import java.util.List;

public class UserDetailValidator
{

    private List<String> errorDetails = new ArrayList<String>();

    public String getErrorDetails() {
        String errorMsg = errorDetails.get(0);
        errorDetails = new ArrayList<String>();
        return errorMsg;
    }

    public boolean isUserDetailsValid(User user)
    {

        // phone number validation
        if(!phoneNumberValidator(user.getPhone()))
        {
            errorDetails.add("Invalid phone number");
            return false;
        }

        // email validation
        if(!emailValidator(user.getEmail()))
        {
            errorDetails.add("Invalid email");
            return false;
        }
        return true;
    }

    public boolean phoneNumberValidator(String phone)
    {
        try {
            Double.parseDouble(phone);
            return (true && phone.length() == 10);
        } catch(NumberFormatException e){
            return false;
        }
    }

    public boolean emailValidator(String email)
    {
        int atSymbol = email.indexOf("@");
        if(atSymbol < 1) return false;

        int dot = email.indexOf(".");
        if(dot <= atSymbol + 2) return false;

        // check that the dot is not at the end
        if (dot == email.length() - 1) return false;

        return true;
    }
}
