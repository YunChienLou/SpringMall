package com.ryanlou.springmall.util.responseBuilder;


public interface ResponseBuilder {

    public ErrorResponse errorResponseBuilder (int status, String errorMessage, String route);
}
