package com.ryanlou.springmall.util.responseBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;


@Component
public class ErrorResponseBuilder implements ResponseBuilder{

    @Override
    public ErrorResponse errorResponseBuilder(int status, String errorMessage, String url) {
        Date now = new Date();
        return new ErrorResponse(status , now , errorMessage  ,url);
    }

    public void errorResponse (HttpServletResponse response, int status, String errorMessage, String url) throws IOException {
        Date now = new Date();
        response.setStatus(FORBIDDEN.value());
        response.setHeader("error", errorMessage);
        response.setContentType("application/json");

        Map<String , Object> error = new HashMap<>();
        error.put("error message" , errorMessage);
        error.put("status" , status);
        error.put("url" , url);
        error.put("timestamp" , now);

        new ObjectMapper().writeValue(response.getOutputStream(),error);
    }
}
